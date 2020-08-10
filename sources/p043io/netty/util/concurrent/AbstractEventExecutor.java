package p043io.netty.util.concurrent;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.concurrent.AbstractEventExecutor */
public abstract class AbstractEventExecutor extends AbstractExecutorService implements EventExecutor {
    static final long DEFAULT_SHUTDOWN_QUIET_PERIOD = 2;
    static final long DEFAULT_SHUTDOWN_TIMEOUT = 15;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractEventExecutor.class);
    private final EventExecutorGroup parent;
    private final Collection<EventExecutor> selfCollection;

    public EventExecutor next() {
        return this;
    }

    @Deprecated
    public abstract void shutdown();

    protected AbstractEventExecutor() {
        this(null);
    }

    protected AbstractEventExecutor(EventExecutorGroup eventExecutorGroup) {
        this.selfCollection = Collections.singleton(this);
        this.parent = eventExecutorGroup;
    }

    public EventExecutorGroup parent() {
        return this.parent;
    }

    public boolean inEventLoop() {
        return inEventLoop(Thread.currentThread());
    }

    public Iterator<EventExecutor> iterator() {
        return this.selfCollection.iterator();
    }

    public Future<?> shutdownGracefully() {
        return shutdownGracefully(2, DEFAULT_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
    }

    @Deprecated
    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
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

    public Future<?> submit(Runnable runnable) {
        return (Future) super.submit(runnable);
    }

    public <T> Future<T> submit(Runnable runnable, T t) {
        return (Future) super.submit(runnable, t);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return (Future) super.submit(callable);
    }

    /* access modifiers changed from: protected */
    public final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new PromiseTask(this, runnable, t);
    }

    /* access modifiers changed from: protected */
    public final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new PromiseTask(this, callable);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    protected static void safeExecute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable th) {
            logger.warn("A task raised an exception. Task: {}", runnable, th);
        }
    }
}
