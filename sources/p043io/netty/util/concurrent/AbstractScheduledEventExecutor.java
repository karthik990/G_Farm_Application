package p043io.netty.util.concurrent;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.util.concurrent.AbstractScheduledEventExecutor */
public abstract class AbstractScheduledEventExecutor extends AbstractEventExecutor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Queue<ScheduledFutureTask<?>> scheduledTaskQueue;

    protected AbstractScheduledEventExecutor() {
    }

    protected AbstractScheduledEventExecutor(EventExecutorGroup eventExecutorGroup) {
        super(eventExecutorGroup);
    }

    protected static long nanoTime() {
        return ScheduledFutureTask.nanoTime();
    }

    /* access modifiers changed from: 0000 */
    public Queue<ScheduledFutureTask<?>> scheduledTaskQueue() {
        if (this.scheduledTaskQueue == null) {
            this.scheduledTaskQueue = new PriorityQueue();
        }
        return this.scheduledTaskQueue;
    }

    private static boolean isNullOrEmpty(Queue<ScheduledFutureTask<?>> queue) {
        return queue == null || queue.isEmpty();
    }

    /* access modifiers changed from: protected */
    public void cancelScheduledTasks() {
        Queue<ScheduledFutureTask<?>> queue = this.scheduledTaskQueue;
        if (!isNullOrEmpty(queue)) {
            for (ScheduledFutureTask cancelWithoutRemove : (ScheduledFutureTask[]) queue.toArray(new ScheduledFutureTask[queue.size()])) {
                cancelWithoutRemove.cancelWithoutRemove(false);
            }
            queue.clear();
        }
    }

    /* access modifiers changed from: protected */
    public final Runnable pollScheduledTask() {
        return pollScheduledTask(nanoTime());
    }

    /* access modifiers changed from: protected */
    public final Runnable pollScheduledTask(long j) {
        ScheduledFutureTask scheduledFutureTask;
        Queue<ScheduledFutureTask<?>> queue = this.scheduledTaskQueue;
        if (queue == null) {
            scheduledFutureTask = null;
        } else {
            scheduledFutureTask = (ScheduledFutureTask) queue.peek();
        }
        if (scheduledFutureTask == null || scheduledFutureTask.deadlineNanos() > j) {
            return null;
        }
        queue.remove();
        return scheduledFutureTask;
    }

    /* access modifiers changed from: protected */
    public final long nextScheduledTaskNano() {
        ScheduledFutureTask scheduledFutureTask;
        Queue<ScheduledFutureTask<?>> queue = this.scheduledTaskQueue;
        if (queue == null) {
            scheduledFutureTask = null;
        } else {
            scheduledFutureTask = (ScheduledFutureTask) queue.peek();
        }
        if (scheduledFutureTask == null) {
            return -1;
        }
        return Math.max(0, scheduledFutureTask.deadlineNanos() - nanoTime());
    }

    /* access modifiers changed from: 0000 */
    public final ScheduledFutureTask<?> peekScheduledTask() {
        Queue<ScheduledFutureTask<?>> queue = this.scheduledTaskQueue;
        if (queue == null) {
            return null;
        }
        return (ScheduledFutureTask) queue.peek();
    }

    /* access modifiers changed from: protected */
    public final boolean hasScheduledTasks() {
        ScheduledFutureTask scheduledFutureTask;
        Queue<ScheduledFutureTask<?>> queue = this.scheduledTaskQueue;
        if (queue == null) {
            scheduledFutureTask = null;
        } else {
            scheduledFutureTask = (ScheduledFutureTask) queue.peek();
        }
        return scheduledFutureTask != null && scheduledFutureTask.deadlineNanos() <= nanoTime();
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (j < 0) {
            j = 0;
        }
        ScheduledFutureTask scheduledFutureTask = new ScheduledFutureTask(this, runnable, null, ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(j)));
        return schedule(scheduledFutureTask);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(callable, "callable");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (j < 0) {
            j = 0;
        }
        return schedule(new ScheduledFutureTask(this, callable, ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(j))));
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        Runnable runnable2 = runnable;
        long j3 = j;
        long j4 = j2;
        TimeUnit timeUnit2 = timeUnit;
        ObjectUtil.checkNotNull(runnable2, "command");
        ObjectUtil.checkNotNull(timeUnit2, "unit");
        if (j3 < 0) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[]{Long.valueOf(j)}));
        } else if (j4 > 0) {
            ScheduledFutureTask scheduledFutureTask = new ScheduledFutureTask(this, Executors.callable(runnable2, null), ScheduledFutureTask.deadlineNanos(timeUnit2.toNanos(j3)), timeUnit2.toNanos(j4));
            return schedule(scheduledFutureTask);
        } else {
            throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[]{Long.valueOf(j2)}));
        }
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        Runnable runnable2 = runnable;
        long j3 = j;
        long j4 = j2;
        TimeUnit timeUnit2 = timeUnit;
        ObjectUtil.checkNotNull(runnable2, "command");
        ObjectUtil.checkNotNull(timeUnit2, "unit");
        if (j3 < 0) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[]{Long.valueOf(j)}));
        } else if (j4 > 0) {
            ScheduledFutureTask scheduledFutureTask = new ScheduledFutureTask(this, Executors.callable(runnable2, null), ScheduledFutureTask.deadlineNanos(timeUnit2.toNanos(j3)), -timeUnit2.toNanos(j4));
            return schedule(scheduledFutureTask);
        } else {
            throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[]{Long.valueOf(j2)}));
        }
    }

    /* access modifiers changed from: 0000 */
    public <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> scheduledFutureTask) {
        if (inEventLoop()) {
            scheduledTaskQueue().add(scheduledFutureTask);
        } else {
            execute(new Runnable() {
                public void run() {
                    AbstractScheduledEventExecutor.this.scheduledTaskQueue().add(scheduledFutureTask);
                }
            });
        }
        return scheduledFutureTask;
    }

    /* access modifiers changed from: 0000 */
    public final void removeScheduled(final ScheduledFutureTask<?> scheduledFutureTask) {
        if (inEventLoop()) {
            scheduledTaskQueue().remove(scheduledFutureTask);
        } else {
            execute(new Runnable() {
                public void run() {
                    AbstractScheduledEventExecutor.this.removeScheduled(scheduledFutureTask);
                }
            });
        }
    }
}
