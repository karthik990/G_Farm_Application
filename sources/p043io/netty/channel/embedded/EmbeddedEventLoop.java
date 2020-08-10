package p043io.netty.channel.embedded;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.DefaultChannelPromise;
import p043io.netty.channel.EventLoop;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.util.concurrent.AbstractScheduledEventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.channel.embedded.EmbeddedEventLoop */
final class EmbeddedEventLoop extends AbstractScheduledEventExecutor implements EventLoop {
    private final Queue<Runnable> tasks = new ArrayDeque(2);

    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        return false;
    }

    public boolean inEventLoop() {
        return true;
    }

    public boolean inEventLoop(Thread thread) {
        return true;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isShuttingDown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    EmbeddedEventLoop() {
    }

    public EventLoopGroup parent() {
        return (EventLoopGroup) super.parent();
    }

    public EventLoop next() {
        return (EventLoop) super.next();
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            this.tasks.add(runnable);
            return;
        }
        throw new NullPointerException("command");
    }

    /* access modifiers changed from: 0000 */
    public void runTasks() {
        while (true) {
            Runnable runnable = (Runnable) this.tasks.poll();
            if (runnable != null) {
                runnable.run();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public long runScheduledTasks() {
        long nanoTime = AbstractScheduledEventExecutor.nanoTime();
        while (true) {
            Runnable pollScheduledTask = pollScheduledTask(nanoTime);
            if (pollScheduledTask == null) {
                return nextScheduledTaskNano();
            }
            pollScheduledTask.run();
        }
    }

    /* access modifiers changed from: 0000 */
    public long nextScheduledTask() {
        return nextScheduledTaskNano();
    }

    /* access modifiers changed from: protected */
    public void cancelScheduledTasks() {
        super.cancelScheduledTasks();
    }

    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public Future<?> terminationFuture() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
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
        channel.unsafe().register(this, channelPromise);
        return channelPromise;
    }
}
