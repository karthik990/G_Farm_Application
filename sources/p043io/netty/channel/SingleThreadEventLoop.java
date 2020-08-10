package p043io.netty.channel;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import p043io.netty.util.concurrent.EventExecutorGroup;
import p043io.netty.util.concurrent.RejectedExecutionHandler;
import p043io.netty.util.concurrent.RejectedExecutionHandlers;
import p043io.netty.util.concurrent.SingleThreadEventExecutor;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.SystemPropertyUtil;

/* renamed from: io.netty.channel.SingleThreadEventLoop */
public abstract class SingleThreadEventLoop extends SingleThreadEventExecutor implements EventLoop {
    protected static final int DEFAULT_MAX_PENDING_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventLoop.maxPendingTasks", Integer.MAX_VALUE));
    private final Queue<Runnable> tailTasks;

    /* renamed from: io.netty.channel.SingleThreadEventLoop$NonWakeupRunnable */
    interface NonWakeupRunnable extends Runnable {
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory, boolean z) {
        this(eventLoopGroup, threadFactory, z, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, Executor executor, boolean z) {
        this(eventLoopGroup, executor, z, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory, boolean z, int i, RejectedExecutionHandler rejectedExecutionHandler) {
        super((EventExecutorGroup) eventLoopGroup, threadFactory, z, i, rejectedExecutionHandler);
        this.tailTasks = newTaskQueue(i);
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, Executor executor, boolean z, int i, RejectedExecutionHandler rejectedExecutionHandler) {
        super((EventExecutorGroup) eventLoopGroup, executor, z, i, rejectedExecutionHandler);
        this.tailTasks = newTaskQueue(i);
    }

    public EventLoopGroup parent() {
        return (EventLoopGroup) super.parent();
    }

    public EventLoop next() {
        return (EventLoop) super.next();
    }

    public ChannelFuture register(Channel channel) {
        return register((ChannelPromise) new DefaultChannelPromise(channel, this));
    }

    public ChannelFuture register(ChannelPromise channelPromise) {
        ObjectUtil.checkNotNull(channelPromise, "promise");
        channelPromise.channel().unsafe().register(this, channelPromise);
        return channelPromise;
    }

    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        } else if (channelPromise != null) {
            channel.unsafe().register(this, channelPromise);
            return channelPromise;
        } else {
            throw new NullPointerException("promise");
        }
    }

    public final void executeAfterEventLoopIteration(Runnable runnable) {
        ObjectUtil.checkNotNull(runnable, "task");
        if (isShutdown()) {
            reject();
        }
        if (!this.tailTasks.offer(runnable)) {
            reject(runnable);
        }
        if (wakesUpForTask(runnable)) {
            wakeup(inEventLoop());
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean removeAfterEventLoopIterationTask(Runnable runnable) {
        return this.tailTasks.remove(ObjectUtil.checkNotNull(runnable, "task"));
    }

    /* access modifiers changed from: protected */
    public boolean wakesUpForTask(Runnable runnable) {
        return !(runnable instanceof NonWakeupRunnable);
    }

    /* access modifiers changed from: protected */
    public void afterRunningAllTasks() {
        runAllTasksFrom(this.tailTasks);
    }

    /* access modifiers changed from: protected */
    public boolean hasTasks() {
        return super.hasTasks() || !this.tailTasks.isEmpty();
    }

    public int pendingTasks() {
        return super.pendingTasks() + this.tailTasks.size();
    }
}
