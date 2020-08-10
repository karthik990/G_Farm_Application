package p043io.netty.util.concurrent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: io.netty.util.concurrent.AbstractEventExecutorGroup */
public abstract class AbstractEventExecutorGroup implements EventExecutorGroup {
    @Deprecated
    public abstract void shutdown();

    public Future<?> submit(Runnable runnable) {
        return next().submit(runnable);
    }

    public <T> Future<T> submit(Runnable runnable, T t) {
        return next().submit(runnable, t);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return next().submit(callable);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return next().schedule(runnable, j, timeUnit);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
        return next().schedule(callable, j, timeUnit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return next().scheduleAtFixedRate(runnable, j, j2, timeUnit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return next().scheduleWithFixedDelay(runnable, j, j2, timeUnit);
    }

    public Future<?> shutdownGracefully() {
        return shutdownGracefully(2, 15, TimeUnit.SECONDS);
    }

    @Deprecated
    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return next().invokeAll(collection);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException {
        return next().invokeAll(collection, j, timeUnit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return next().invokeAny(collection);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return next().invokeAny(collection, j, timeUnit);
    }

    public void execute(Runnable runnable) {
        next().execute(runnable);
    }
}
