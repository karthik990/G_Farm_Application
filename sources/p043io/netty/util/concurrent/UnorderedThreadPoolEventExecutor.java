package p043io.netty.util.concurrent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.concurrent.UnorderedThreadPoolEventExecutor */
public final class UnorderedThreadPoolEventExecutor extends ScheduledThreadPoolExecutor implements EventExecutor {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(UnorderedThreadPoolEventExecutor.class);
    private final Set<EventExecutor> executorSet;
    private final Promise<?> terminationFuture;

    /* renamed from: io.netty.util.concurrent.UnorderedThreadPoolEventExecutor$NonNotifyRunnable */
    private static final class NonNotifyRunnable implements Runnable {
        private final Runnable task;

        NonNotifyRunnable(Runnable runnable) {
            this.task = runnable;
        }

        public void run() {
            this.task.run();
        }
    }

    /* renamed from: io.netty.util.concurrent.UnorderedThreadPoolEventExecutor$RunnableScheduledFutureTask */
    private static final class RunnableScheduledFutureTask<V> extends PromiseTask<V> implements RunnableScheduledFuture<V>, ScheduledFuture<V> {
        private final RunnableScheduledFuture<V> future;

        RunnableScheduledFutureTask(EventExecutor eventExecutor, Runnable runnable, RunnableScheduledFuture<V> runnableScheduledFuture) {
            super(eventExecutor, runnable, null);
            this.future = runnableScheduledFuture;
        }

        RunnableScheduledFutureTask(EventExecutor eventExecutor, Callable<V> callable, RunnableScheduledFuture<V> runnableScheduledFuture) {
            super(eventExecutor, callable);
            this.future = runnableScheduledFuture;
        }

        public void run() {
            if (!isPeriodic()) {
                super.run();
            } else if (!isDone()) {
                try {
                    this.task.call();
                } catch (Throwable th) {
                    if (!tryFailureInternal(th)) {
                        UnorderedThreadPoolEventExecutor.logger.warn("Failure during execution of task", th);
                    }
                }
            }
        }

        public boolean isPeriodic() {
            return this.future.isPeriodic();
        }

        public long getDelay(TimeUnit timeUnit) {
            return this.future.getDelay(timeUnit);
        }

        public int compareTo(Delayed delayed) {
            return this.future.compareTo(delayed);
        }
    }

    public boolean inEventLoop() {
        return false;
    }

    public boolean inEventLoop(Thread thread) {
        return false;
    }

    public EventExecutor next() {
        return this;
    }

    public EventExecutorGroup parent() {
        return this;
    }

    public UnorderedThreadPoolEventExecutor(int i) {
        this(i, (ThreadFactory) new DefaultThreadFactory(UnorderedThreadPoolEventExecutor.class));
    }

    public UnorderedThreadPoolEventExecutor(int i, ThreadFactory threadFactory) {
        super(i, threadFactory);
        this.terminationFuture = GlobalEventExecutor.INSTANCE.newPromise();
        this.executorSet = Collections.singleton(this);
    }

    public UnorderedThreadPoolEventExecutor(int i, RejectedExecutionHandler rejectedExecutionHandler) {
        this(i, new DefaultThreadFactory(UnorderedThreadPoolEventExecutor.class), rejectedExecutionHandler);
    }

    public UnorderedThreadPoolEventExecutor(int i, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(i, threadFactory, rejectedExecutionHandler);
        this.terminationFuture = GlobalEventExecutor.INSTANCE.newPromise();
        this.executorSet = Collections.singleton(this);
    }

    public <V> Promise<V> newPromise() {
        return new DefaultPromise(this);
    }

    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new DefaultProgressivePromise(this);
    }

    public <V> Future<V> newSucceededFuture(V v) {
        return new SucceededFuture(this, v);
    }

    public <V> Future<V> newFailedFuture(Throwable th) {
        return new FailedFuture(this, th);
    }

    public boolean isShuttingDown() {
        return isShutdown();
    }

    public List<Runnable> shutdownNow() {
        List<Runnable> shutdownNow = super.shutdownNow();
        this.terminationFuture.trySuccess(null);
        return shutdownNow;
    }

    public void shutdown() {
        super.shutdown();
        this.terminationFuture.trySuccess(null);
    }

    public Future<?> shutdownGracefully() {
        return shutdownGracefully(2, 15, TimeUnit.SECONDS);
    }

    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        shutdown();
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    public Iterator<EventExecutor> iterator() {
        return this.executorSet.iterator();
    }

    /* access modifiers changed from: protected */
    public <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> runnableScheduledFuture) {
        return runnable instanceof NonNotifyRunnable ? runnableScheduledFuture : new RunnableScheduledFutureTask((EventExecutor) this, runnable, runnableScheduledFuture);
    }

    /* access modifiers changed from: protected */
    public <V> RunnableScheduledFuture<V> decorateTask(Callable<V> callable, RunnableScheduledFuture<V> runnableScheduledFuture) {
        return new RunnableScheduledFutureTask((EventExecutor) this, callable, runnableScheduledFuture);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return (ScheduledFuture) super.schedule(runnable, j, timeUnit);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
        return (ScheduledFuture) super.schedule(callable, j, timeUnit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return (ScheduledFuture) super.scheduleAtFixedRate(runnable, j, j2, timeUnit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return (ScheduledFuture) super.scheduleWithFixedDelay(runnable, j, j2, timeUnit);
    }

    public Future<?> submit(Runnable runnable) {
        return (Future) super.submit(runnable);
    }

    public <T> Future<T> submit(Runnable runnable, T t) {
        return (Future) super.submit(runnable, t);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return (Future) super.submit(callable);
    }

    public void execute(Runnable runnable) {
        super.schedule(new NonNotifyRunnable(runnable), 0, TimeUnit.NANOSECONDS);
    }
}
