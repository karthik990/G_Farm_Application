package p043io.grpc.internal;

import com.google.common.base.Stopwatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* renamed from: io.grpc.internal.Rescheduler */
final class Rescheduler {
    /* access modifiers changed from: private */
    public boolean enabled;
    /* access modifiers changed from: private */
    public long runAtNanos;
    /* access modifiers changed from: private */
    public final Runnable runnable;
    /* access modifiers changed from: private */
    public final ScheduledExecutorService scheduler;
    /* access modifiers changed from: private */
    public final Executor serializingExecutor;
    private final Stopwatch stopwatch;
    /* access modifiers changed from: private */
    public ScheduledFuture<?> wakeUp;

    /* renamed from: io.grpc.internal.Rescheduler$ChannelFutureRunnable */
    private final class ChannelFutureRunnable implements Runnable {
        private ChannelFutureRunnable() {
        }

        public void run() {
            if (!Rescheduler.this.enabled) {
                Rescheduler.this.wakeUp = null;
                return;
            }
            long access$500 = Rescheduler.this.nanoTime();
            if (Rescheduler.this.runAtNanos - access$500 > 0) {
                Rescheduler rescheduler = Rescheduler.this;
                rescheduler.wakeUp = rescheduler.scheduler.schedule(new FutureRunnable(), Rescheduler.this.runAtNanos - access$500, TimeUnit.NANOSECONDS);
            } else {
                Rescheduler.this.enabled = false;
                Rescheduler.this.wakeUp = null;
                Rescheduler.this.runnable.run();
            }
        }
    }

    /* renamed from: io.grpc.internal.Rescheduler$FutureRunnable */
    private final class FutureRunnable implements Runnable {
        private FutureRunnable() {
        }

        public void run() {
            Rescheduler.this.serializingExecutor.execute(new ChannelFutureRunnable());
        }

        /* access modifiers changed from: private */
        public boolean isEnabled() {
            return Rescheduler.this.enabled;
        }
    }

    Rescheduler(Runnable runnable2, Executor executor, ScheduledExecutorService scheduledExecutorService, Stopwatch stopwatch2) {
        this.runnable = runnable2;
        this.serializingExecutor = executor;
        this.scheduler = scheduledExecutorService;
        this.stopwatch = stopwatch2;
        stopwatch2.start();
    }

    /* access modifiers changed from: 0000 */
    public void reschedule(long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j);
        long nanoTime = nanoTime() + nanos;
        this.enabled = true;
        if (nanoTime - this.runAtNanos < 0 || this.wakeUp == null) {
            ScheduledFuture<?> scheduledFuture = this.wakeUp;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            this.wakeUp = this.scheduler.schedule(new FutureRunnable(), nanos, TimeUnit.NANOSECONDS);
        }
        this.runAtNanos = nanoTime;
    }

    /* access modifiers changed from: 0000 */
    public void cancel(boolean z) {
        this.enabled = false;
        if (z) {
            ScheduledFuture<?> scheduledFuture = this.wakeUp;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
                this.wakeUp = null;
            }
        }
    }

    static boolean isEnabled(Runnable runnable2) {
        return ((FutureRunnable) runnable2).isEnabled();
    }

    /* access modifiers changed from: private */
    public long nanoTime() {
        return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
    }
}
