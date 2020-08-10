package p043io.netty.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.util.concurrent.ScheduledFutureTask */
final class ScheduledFutureTask<V> extends PromiseTask<V> implements ScheduledFuture<V> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long START_TIME = System.nanoTime();
    private static final AtomicLong nextTaskId = new AtomicLong();
    private long deadlineNanos;

    /* renamed from: id */
    private final long f3746id;
    private final long periodNanos;

    static long nanoTime() {
        return System.nanoTime() - START_TIME;
    }

    static long deadlineNanos(long j) {
        return nanoTime() + j;
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Runnable runnable, V v, long j) {
        this(abstractScheduledEventExecutor, toCallable(runnable, v), j);
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Callable<V> callable, long j, long j2) {
        super(abstractScheduledEventExecutor, callable);
        this.f3746id = nextTaskId.getAndIncrement();
        if (j2 != 0) {
            this.deadlineNanos = j;
            this.periodNanos = j2;
            return;
        }
        throw new IllegalArgumentException("period: 0 (expected: != 0)");
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Callable<V> callable, long j) {
        super(abstractScheduledEventExecutor, callable);
        this.f3746id = nextTaskId.getAndIncrement();
        this.deadlineNanos = j;
        this.periodNanos = 0;
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        return super.executor();
    }

    public long deadlineNanos() {
        return this.deadlineNanos;
    }

    public long delayNanos() {
        return Math.max(0, deadlineNanos() - nanoTime());
    }

    public long delayNanos(long j) {
        return Math.max(0, deadlineNanos() - (j - START_TIME));
    }

    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(delayNanos(), TimeUnit.NANOSECONDS);
    }

    public int compareTo(Delayed delayed) {
        if (this == delayed) {
            return 0;
        }
        ScheduledFutureTask scheduledFutureTask = (ScheduledFutureTask) delayed;
        long deadlineNanos2 = deadlineNanos() - scheduledFutureTask.deadlineNanos();
        if (deadlineNanos2 < 0) {
            return -1;
        }
        if (deadlineNanos2 > 0) {
            return 1;
        }
        long j = this.f3746id;
        long j2 = scheduledFutureTask.f3746id;
        if (j < j2) {
            return -1;
        }
        if (j != j2) {
            return 1;
        }
        throw new Error();
    }

    public void run() {
        try {
            if (this.periodNanos == 0) {
                if (setUncancellableInternal()) {
                    setSuccessInternal(this.task.call());
                }
            } else if (!isCancelled()) {
                this.task.call();
                if (!executor().isShutdown()) {
                    long j = this.periodNanos;
                    if (j > 0) {
                        this.deadlineNanos += j;
                    } else {
                        this.deadlineNanos = nanoTime() - j;
                    }
                    if (!isCancelled()) {
                        ((AbstractScheduledEventExecutor) executor()).scheduledTaskQueue.add(this);
                    }
                }
            }
        } catch (Throwable th) {
            setFailureInternal(th);
        }
    }

    public boolean cancel(boolean z) {
        boolean cancel = super.cancel(z);
        if (cancel) {
            ((AbstractScheduledEventExecutor) executor()).removeScheduled(this);
        }
        return cancel;
    }

    /* access modifiers changed from: 0000 */
    public boolean cancelWithoutRemove(boolean z) {
        return super.cancel(z);
    }

    /* access modifiers changed from: protected */
    public StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, StringUtil.COMMA);
        stringBuilder.append(" id: ");
        stringBuilder.append(this.f3746id);
        stringBuilder.append(", deadline: ");
        stringBuilder.append(this.deadlineNanos);
        stringBuilder.append(", period: ");
        stringBuilder.append(this.periodNanos);
        stringBuilder.append(')');
        return stringBuilder;
    }
}
