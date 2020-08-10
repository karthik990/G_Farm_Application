package p043io.netty.channel;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import p043io.netty.util.NettyRuntime;
import p043io.netty.util.concurrent.DefaultThreadFactory;
import p043io.netty.util.concurrent.EventExecutorChooserFactory;
import p043io.netty.util.concurrent.MultithreadEventExecutorGroup;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.MultithreadEventLoopGroup */
public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup {
    private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);

    /* access modifiers changed from: protected */
    public abstract EventLoop newChild(Executor executor, Object... objArr) throws Exception;

    static {
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.eventLoopThreads: {}", (Object) Integer.valueOf(DEFAULT_EVENT_LOOP_THREADS));
        }
    }

    protected MultithreadEventLoopGroup(int i, Executor executor, Object... objArr) {
        if (i == 0) {
            i = DEFAULT_EVENT_LOOP_THREADS;
        }
        super(i, executor, objArr);
    }

    protected MultithreadEventLoopGroup(int i, ThreadFactory threadFactory, Object... objArr) {
        if (i == 0) {
            i = DEFAULT_EVENT_LOOP_THREADS;
        }
        super(i, threadFactory, objArr);
    }

    protected MultithreadEventLoopGroup(int i, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, Object... objArr) {
        if (i == 0) {
            i = DEFAULT_EVENT_LOOP_THREADS;
        }
        super(i, executor, eventExecutorChooserFactory, objArr);
    }

    /* access modifiers changed from: protected */
    public ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(getClass(), 10);
    }

    public EventLoop next() {
        return (EventLoop) super.next();
    }

    public ChannelFuture register(Channel channel) {
        return next().register(channel);
    }

    public ChannelFuture register(ChannelPromise channelPromise) {
        return next().register(channelPromise);
    }

    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        return next().register(channel, channelPromise);
    }
}
