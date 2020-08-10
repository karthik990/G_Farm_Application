package p043io.netty.channel.pool;

import java.util.Deque;
import p043io.netty.bootstrap.Bootstrap;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelInitializer;
import p043io.netty.channel.EventLoop;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.ThrowableUtil;

/* renamed from: io.netty.channel.pool.SimpleChannelPool */
public class SimpleChannelPool implements ChannelPool {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final IllegalStateException FULL_EXCEPTION;
    private static final AttributeKey<SimpleChannelPool> POOL_KEY = AttributeKey.newInstance("channelPool");
    private static final IllegalStateException UNHEALTHY_NON_OFFERED_TO_POOL;
    private final Bootstrap bootstrap;
    private final Deque<Channel> deque;
    private final ChannelPoolHandler handler;
    private final ChannelHealthChecker healthCheck;
    private final boolean releaseHealthCheck;

    static {
        Class<SimpleChannelPool> cls = SimpleChannelPool.class;
        String str = "releaseAndOffer(...)";
        FULL_EXCEPTION = (IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("ChannelPool full"), cls, str);
        UNHEALTHY_NON_OFFERED_TO_POOL = (IllegalStateException) ThrowableUtil.unknownStackTrace(new IllegalStateException("Channel is unhealthy not offering it back to pool"), cls, str);
    }

    public SimpleChannelPool(Bootstrap bootstrap2, ChannelPoolHandler channelPoolHandler) {
        this(bootstrap2, channelPoolHandler, ChannelHealthChecker.ACTIVE);
    }

    public SimpleChannelPool(Bootstrap bootstrap2, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker) {
        this(bootstrap2, channelPoolHandler, channelHealthChecker, true);
    }

    public SimpleChannelPool(Bootstrap bootstrap2, final ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, boolean z) {
        this.deque = PlatformDependent.newConcurrentDeque();
        this.handler = (ChannelPoolHandler) ObjectUtil.checkNotNull(channelPoolHandler, "handler");
        this.healthCheck = (ChannelHealthChecker) ObjectUtil.checkNotNull(channelHealthChecker, "healthCheck");
        this.releaseHealthCheck = z;
        this.bootstrap = ((Bootstrap) ObjectUtil.checkNotNull(bootstrap2, "bootstrap")).clone();
        this.bootstrap.handler(new ChannelInitializer<Channel>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<SimpleChannelPool> cls = SimpleChannelPool.class;
            }

            /* access modifiers changed from: protected */
            public void initChannel(Channel channel) throws Exception {
                channelPoolHandler.channelCreated(channel);
            }
        });
    }

    /* access modifiers changed from: protected */
    public Bootstrap bootstrap() {
        return this.bootstrap;
    }

    /* access modifiers changed from: protected */
    public ChannelPoolHandler handler() {
        return this.handler;
    }

    /* access modifiers changed from: protected */
    public ChannelHealthChecker healthChecker() {
        return this.healthCheck;
    }

    /* access modifiers changed from: protected */
    public boolean releaseHealthCheck() {
        return this.releaseHealthCheck;
    }

    public final Future<Channel> acquire() {
        return acquire(this.bootstrap.config().group().next().newPromise());
    }

    public Future<Channel> acquire(Promise<Channel> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        return acquireHealthyFromPoolOrNew(promise);
    }

    private Future<Channel> acquireHealthyFromPoolOrNew(final Promise<Channel> promise) {
        try {
            final Channel pollChannel = pollChannel();
            if (pollChannel == null) {
                Bootstrap clone = this.bootstrap.clone();
                clone.attr(POOL_KEY, this);
                ChannelFuture connectChannel = connectChannel(clone);
                if (connectChannel.isDone()) {
                    notifyConnect(connectChannel, promise);
                } else {
                    connectChannel.addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            SimpleChannelPool.this.notifyConnect(channelFuture, promise);
                        }
                    });
                }
                return promise;
            }
            EventLoop eventLoop = pollChannel.eventLoop();
            if (eventLoop.inEventLoop()) {
                doHealthCheck(pollChannel, promise);
            } else {
                eventLoop.execute(new Runnable() {
                    public void run() {
                        SimpleChannelPool.this.doHealthCheck(pollChannel, promise);
                    }
                });
            }
            return promise;
        } catch (Throwable th) {
            promise.tryFailure(th);
        }
    }

    /* access modifiers changed from: private */
    public void notifyConnect(ChannelFuture channelFuture, Promise<Channel> promise) {
        if (channelFuture.isSuccess()) {
            Channel channel = channelFuture.channel();
            if (!promise.trySuccess(channel)) {
                release(channel);
                return;
            }
            return;
        }
        promise.tryFailure(channelFuture.cause());
    }

    /* access modifiers changed from: private */
    public void doHealthCheck(final Channel channel, final Promise<Channel> promise) {
        Future isHealthy = this.healthCheck.isHealthy(channel);
        if (isHealthy.isDone()) {
            notifyHealthCheck(isHealthy, channel, promise);
        } else {
            isHealthy.addListener(new FutureListener<Boolean>() {
                public void operationComplete(Future<Boolean> future) throws Exception {
                    SimpleChannelPool.this.notifyHealthCheck(future, channel, promise);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void notifyHealthCheck(Future<Boolean> future, Channel channel, Promise<Channel> promise) {
        if (!future.isSuccess()) {
            closeChannel(channel);
            acquireHealthyFromPoolOrNew(promise);
        } else if (((Boolean) future.getNow()).booleanValue()) {
            try {
                channel.attr(POOL_KEY).set(this);
                this.handler.channelAcquired(channel);
                promise.setSuccess(channel);
            } catch (Throwable th) {
                closeAndFail(channel, th, promise);
            }
        } else {
            closeChannel(channel);
            acquireHealthyFromPoolOrNew(promise);
        }
    }

    /* access modifiers changed from: protected */
    public ChannelFuture connectChannel(Bootstrap bootstrap2) {
        return bootstrap2.connect();
    }

    public final Future<Void> release(Channel channel) {
        return release(channel, channel.eventLoop().newPromise());
    }

    public Future<Void> release(final Channel channel, final Promise<Void> promise) {
        ObjectUtil.checkNotNull(channel, "channel");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            EventLoop eventLoop = channel.eventLoop();
            if (eventLoop.inEventLoop()) {
                doReleaseChannel(channel, promise);
            } else {
                eventLoop.execute(new Runnable() {
                    public void run() {
                        SimpleChannelPool.this.doReleaseChannel(channel, promise);
                    }
                });
            }
        } catch (Throwable th) {
            closeAndFail(channel, th, promise);
        }
        return promise;
    }

    /* access modifiers changed from: private */
    public void doReleaseChannel(Channel channel, Promise<Void> promise) {
        if (channel.attr(POOL_KEY).getAndSet(null) != this) {
            StringBuilder sb = new StringBuilder();
            sb.append("Channel ");
            sb.append(channel);
            sb.append(" was not acquired from this ChannelPool");
            closeAndFail(channel, new IllegalArgumentException(sb.toString()), promise);
            return;
        }
        try {
            if (this.releaseHealthCheck) {
                doHealthCheckOnRelease(channel, promise);
            } else {
                releaseAndOffer(channel, promise);
            }
        } catch (Throwable th) {
            closeAndFail(channel, th, promise);
        }
    }

    private void doHealthCheckOnRelease(final Channel channel, final Promise<Void> promise) throws Exception {
        final Future isHealthy = this.healthCheck.isHealthy(channel);
        if (isHealthy.isDone()) {
            releaseAndOfferIfHealthy(channel, promise, isHealthy);
        } else {
            isHealthy.addListener(new FutureListener<Boolean>() {
                public void operationComplete(Future<Boolean> future) throws Exception {
                    SimpleChannelPool.this.releaseAndOfferIfHealthy(channel, promise, isHealthy);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void releaseAndOfferIfHealthy(Channel channel, Promise<Void> promise, Future<Boolean> future) throws Exception {
        if (((Boolean) future.getNow()).booleanValue()) {
            releaseAndOffer(channel, promise);
            return;
        }
        this.handler.channelReleased(channel);
        closeAndFail(channel, UNHEALTHY_NON_OFFERED_TO_POOL, promise);
    }

    private void releaseAndOffer(Channel channel, Promise<Void> promise) throws Exception {
        if (offerChannel(channel)) {
            this.handler.channelReleased(channel);
            promise.setSuccess(null);
            return;
        }
        closeAndFail(channel, FULL_EXCEPTION, promise);
    }

    private static void closeChannel(Channel channel) {
        channel.attr(POOL_KEY).getAndSet(null);
        channel.close();
    }

    private static void closeAndFail(Channel channel, Throwable th, Promise<?> promise) {
        closeChannel(channel);
        promise.tryFailure(th);
    }

    /* access modifiers changed from: protected */
    public Channel pollChannel() {
        return (Channel) this.deque.pollLast();
    }

    /* access modifiers changed from: protected */
    public boolean offerChannel(Channel channel) {
        return this.deque.offer(channel);
    }

    public void close() {
        while (true) {
            Channel pollChannel = pollChannel();
            if (pollChannel != null) {
                pollChannel.close();
            } else {
                return;
            }
        }
    }
}
