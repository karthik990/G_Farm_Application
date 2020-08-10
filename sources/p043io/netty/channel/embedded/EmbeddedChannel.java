package p043io.netty.channel.embedded;

import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import p043io.netty.channel.AbstractChannel;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelId;
import p043io.netty.channel.ChannelInitializer;
import p043io.netty.channel.ChannelMetadata;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.ChannelPipeline;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.DefaultChannelConfig;
import p043io.netty.channel.DefaultChannelPipeline;
import p043io.netty.channel.EventLoop;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.RecyclableArrayList;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.embedded.EmbeddedChannel */
public class EmbeddedChannel extends AbstractChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ChannelHandler[] EMPTY_HANDLERS = new ChannelHandler[0];
    private static final SocketAddress LOCAL_ADDRESS = new EmbeddedSocketAddress();
    private static final ChannelMetadata METADATA_DISCONNECT = new ChannelMetadata(true);
    private static final ChannelMetadata METADATA_NO_DISCONNECT = new ChannelMetadata(false);
    private static final SocketAddress REMOTE_ADDRESS = new EmbeddedSocketAddress();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
    private final ChannelConfig config;
    private Queue<Object> inboundMessages;
    private Throwable lastException;
    private final EmbeddedEventLoop loop;
    private final ChannelMetadata metadata;
    private Queue<Object> outboundMessages;
    private final ChannelFutureListener recordExceptionListener;
    private State state;

    /* renamed from: io.netty.channel.embedded.EmbeddedChannel$DefaultUnsafe */
    private class DefaultUnsafe extends AbstractUnsafe {
        private DefaultUnsafe() {
            super();
        }

        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            safeSetSuccess(channelPromise);
        }
    }

    /* renamed from: io.netty.channel.embedded.EmbeddedChannel$EmbeddedChannelPipeline */
    private final class EmbeddedChannelPipeline extends DefaultChannelPipeline {
        public EmbeddedChannelPipeline(EmbeddedChannel embeddedChannel) {
            super(embeddedChannel);
        }

        /* access modifiers changed from: protected */
        public void onUnhandledInboundException(Throwable th) {
            EmbeddedChannel.this.recordException(th);
        }

        /* access modifiers changed from: protected */
        public void onUnhandledInboundMessage(Object obj) {
            EmbeddedChannel.this.handleInboundMessage(obj);
        }
    }

    /* renamed from: io.netty.channel.embedded.EmbeddedChannel$State */
    private enum State {
        OPEN,
        ACTIVE,
        CLOSED
    }

    /* access modifiers changed from: protected */
    public void doBeginRead() throws Exception {
    }

    /* access modifiers changed from: protected */
    public void doBind(SocketAddress socketAddress) throws Exception {
    }

    public EmbeddedChannel() {
        this(EMPTY_HANDLERS);
    }

    public EmbeddedChannel(ChannelId channelId) {
        this(channelId, EMPTY_HANDLERS);
    }

    public EmbeddedChannel(ChannelHandler... channelHandlerArr) {
        this(EmbeddedChannelId.INSTANCE, channelHandlerArr);
    }

    public EmbeddedChannel(boolean z, ChannelHandler... channelHandlerArr) {
        this(EmbeddedChannelId.INSTANCE, z, channelHandlerArr);
    }

    public EmbeddedChannel(ChannelId channelId, ChannelHandler... channelHandlerArr) {
        this(channelId, false, channelHandlerArr);
    }

    public EmbeddedChannel(ChannelId channelId, boolean z, ChannelHandler... channelHandlerArr) {
        super(null, channelId);
        this.loop = new EmbeddedEventLoop();
        this.recordExceptionListener = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                EmbeddedChannel.this.recordException(channelFuture);
            }
        };
        this.metadata = metadata(z);
        this.config = new DefaultChannelConfig(this);
        setup(channelHandlerArr);
    }

    public EmbeddedChannel(ChannelId channelId, boolean z, ChannelConfig channelConfig, ChannelHandler... channelHandlerArr) {
        super(null, channelId);
        this.loop = new EmbeddedEventLoop();
        this.recordExceptionListener = new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                EmbeddedChannel.this.recordException(channelFuture);
            }
        };
        this.metadata = metadata(z);
        this.config = (ChannelConfig) ObjectUtil.checkNotNull(channelConfig, "config");
        setup(channelHandlerArr);
    }

    private static ChannelMetadata metadata(boolean z) {
        return z ? METADATA_DISCONNECT : METADATA_NO_DISCONNECT;
    }

    private void setup(final ChannelHandler... channelHandlerArr) {
        ObjectUtil.checkNotNull(channelHandlerArr, "handlers");
        pipeline().addLast(new ChannelInitializer<Channel>() {
            /* access modifiers changed from: protected */
            public void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                ChannelHandler[] channelHandlerArr = channelHandlerArr;
                int length = channelHandlerArr.length;
                int i = 0;
                while (i < length) {
                    ChannelHandler channelHandler = channelHandlerArr[i];
                    if (channelHandler != null) {
                        pipeline.addLast(channelHandler);
                        i++;
                    } else {
                        return;
                    }
                }
            }
        });
        this.loop.register((Channel) this);
    }

    /* access modifiers changed from: protected */
    public final DefaultChannelPipeline newChannelPipeline() {
        return new EmbeddedChannelPipeline(this);
    }

    public ChannelMetadata metadata() {
        return this.metadata;
    }

    public ChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return this.state != State.CLOSED;
    }

    public boolean isActive() {
        return this.state == State.ACTIVE;
    }

    public Queue<Object> inboundMessages() {
        if (this.inboundMessages == null) {
            this.inboundMessages = new ArrayDeque();
        }
        return this.inboundMessages;
    }

    @Deprecated
    public Queue<Object> lastInboundBuffer() {
        return inboundMessages();
    }

    public Queue<Object> outboundMessages() {
        if (this.outboundMessages == null) {
            this.outboundMessages = new ArrayDeque();
        }
        return this.outboundMessages;
    }

    @Deprecated
    public Queue<Object> lastOutboundBuffer() {
        return outboundMessages();
    }

    public <T> T readInbound() {
        return poll(this.inboundMessages);
    }

    public <T> T readOutbound() {
        return poll(this.outboundMessages);
    }

    public boolean writeInbound(Object... objArr) {
        ensureOpen();
        if (objArr.length == 0) {
            return isNotEmpty(this.inboundMessages);
        }
        ChannelPipeline pipeline = pipeline();
        for (Object fireChannelRead : objArr) {
            pipeline.fireChannelRead(fireChannelRead);
        }
        flushInbound(false, voidPromise());
        return isNotEmpty(this.inboundMessages);
    }

    public ChannelFuture writeOneInbound(Object obj) {
        return writeOneInbound(obj, newPromise());
    }

    public ChannelFuture writeOneInbound(Object obj, ChannelPromise channelPromise) {
        if (checkOpen(true)) {
            pipeline().fireChannelRead(obj);
        }
        return checkException(channelPromise);
    }

    public EmbeddedChannel flushInbound() {
        flushInbound(true, voidPromise());
        return this;
    }

    private ChannelFuture flushInbound(boolean z, ChannelPromise channelPromise) {
        if (checkOpen(z)) {
            pipeline().fireChannelReadComplete();
            runPendingTasks();
        }
        return checkException(channelPromise);
    }

    public boolean writeOutbound(Object... objArr) {
        ensureOpen();
        if (objArr.length == 0) {
            return isNotEmpty(this.outboundMessages);
        }
        RecyclableArrayList newInstance = RecyclableArrayList.newInstance(objArr.length);
        try {
            int length = objArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Object obj = objArr[i];
                if (obj == null) {
                    break;
                }
                newInstance.add(write(obj));
                i++;
            }
            flushOutbound0();
            int size = newInstance.size();
            for (int i2 = 0; i2 < size; i2++) {
                ChannelFuture channelFuture = (ChannelFuture) newInstance.get(i2);
                if (channelFuture.isDone()) {
                    recordException(channelFuture);
                } else {
                    channelFuture.addListener(this.recordExceptionListener);
                }
            }
            checkException();
            return isNotEmpty(this.outboundMessages);
        } finally {
            newInstance.recycle();
        }
    }

    public ChannelFuture writeOneOutbound(Object obj) {
        return writeOneOutbound(obj, newPromise());
    }

    public ChannelFuture writeOneOutbound(Object obj, ChannelPromise channelPromise) {
        if (checkOpen(true)) {
            return write(obj, channelPromise);
        }
        return checkException(channelPromise);
    }

    public EmbeddedChannel flushOutbound() {
        if (checkOpen(true)) {
            flushOutbound0();
        }
        checkException(voidPromise());
        return this;
    }

    private void flushOutbound0() {
        runPendingTasks();
        flush();
    }

    public boolean finish() {
        return finish(false);
    }

    public boolean finishAndReleaseAll() {
        return finish(true);
    }

    /* JADX INFO: finally extract failed */
    private boolean finish(boolean z) {
        close();
        try {
            checkException();
            boolean z2 = isNotEmpty(this.inboundMessages) || isNotEmpty(this.outboundMessages);
            if (z) {
                releaseAll(this.inboundMessages);
                releaseAll(this.outboundMessages);
            }
            return z2;
        } catch (Throwable th) {
            if (z) {
                releaseAll(this.inboundMessages);
                releaseAll(this.outboundMessages);
            }
            throw th;
        }
    }

    public boolean releaseInbound() {
        return releaseAll(this.inboundMessages);
    }

    public boolean releaseOutbound() {
        return releaseAll(this.outboundMessages);
    }

    private static boolean releaseAll(Queue<Object> queue) {
        if (!isNotEmpty(queue)) {
            return false;
        }
        while (true) {
            Object poll = queue.poll();
            if (poll == null) {
                return true;
            }
            ReferenceCountUtil.release(poll);
        }
    }

    private void finishPendingTasks(boolean z) {
        runPendingTasks();
        if (z) {
            this.loop.cancelScheduledTasks();
        }
    }

    public final ChannelFuture close() {
        return close(newPromise());
    }

    public final ChannelFuture disconnect() {
        return disconnect(newPromise());
    }

    public final ChannelFuture close(ChannelPromise channelPromise) {
        runPendingTasks();
        ChannelFuture close = super.close(channelPromise);
        finishPendingTasks(true);
        return close;
    }

    public final ChannelFuture disconnect(ChannelPromise channelPromise) {
        ChannelFuture disconnect = super.disconnect(channelPromise);
        finishPendingTasks(!this.metadata.hasDisconnect());
        return disconnect;
    }

    private static boolean isNotEmpty(Queue<Object> queue) {
        return queue != null && !queue.isEmpty();
    }

    private static Object poll(Queue<Object> queue) {
        if (queue != null) {
            return queue.poll();
        }
        return null;
    }

    public void runPendingTasks() {
        try {
            this.loop.runTasks();
        } catch (Exception e) {
            recordException((Throwable) e);
        }
        try {
            this.loop.runScheduledTasks();
        } catch (Exception e2) {
            recordException((Throwable) e2);
        }
    }

    public long runScheduledPendingTasks() {
        try {
            return this.loop.runScheduledTasks();
        } catch (Exception e) {
            recordException((Throwable) e);
            return this.loop.nextScheduledTask();
        }
    }

    /* access modifiers changed from: private */
    public void recordException(ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            recordException(channelFuture.cause());
        }
    }

    /* access modifiers changed from: private */
    public void recordException(Throwable th) {
        if (this.lastException == null) {
            this.lastException = th;
        } else {
            logger.warn("More than one exception was raised. Will report only the first one and log others.", th);
        }
    }

    private ChannelFuture checkException(ChannelPromise channelPromise) {
        Throwable th = this.lastException;
        if (th == null) {
            return channelPromise.setSuccess();
        }
        this.lastException = null;
        if (channelPromise.isVoid()) {
            PlatformDependent.throwException(th);
        }
        return channelPromise.setFailure(th);
    }

    public void checkException() {
        checkException(voidPromise());
    }

    private boolean checkOpen(boolean z) {
        if (isOpen()) {
            return true;
        }
        if (z) {
            recordException((Throwable) new ClosedChannelException());
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final void ensureOpen() {
        if (!checkOpen(true)) {
            checkException();
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof EmbeddedEventLoop;
    }

    /* access modifiers changed from: protected */
    public SocketAddress localAddress0() {
        if (isActive()) {
            return LOCAL_ADDRESS;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public SocketAddress remoteAddress0() {
        if (isActive()) {
            return REMOTE_ADDRESS;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void doRegister() throws Exception {
        this.state = State.ACTIVE;
    }

    /* access modifiers changed from: protected */
    public void doDisconnect() throws Exception {
        if (!this.metadata.hasDisconnect()) {
            doClose();
        }
    }

    /* access modifiers changed from: protected */
    public void doClose() throws Exception {
        this.state = State.CLOSED;
    }

    /* access modifiers changed from: protected */
    public AbstractUnsafe newUnsafe() {
        return new DefaultUnsafe();
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current != null) {
                ReferenceCountUtil.retain(current);
                handleOutboundMessage(current);
                channelOutboundBuffer.remove();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void handleOutboundMessage(Object obj) {
        outboundMessages().add(obj);
    }

    /* access modifiers changed from: protected */
    public void handleInboundMessage(Object obj) {
        inboundMessages().add(obj);
    }
}
