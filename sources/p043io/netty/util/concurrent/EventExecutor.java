package p043io.netty.util.concurrent;

/* renamed from: io.netty.util.concurrent.EventExecutor */
public interface EventExecutor extends EventExecutorGroup {
    boolean inEventLoop();

    boolean inEventLoop(Thread thread);

    <V> Future<V> newFailedFuture(Throwable th);

    <V> ProgressivePromise<V> newProgressivePromise();

    <V> Promise<V> newPromise();

    <V> Future<V> newSucceededFuture(V v);

    EventExecutor next();

    EventExecutorGroup parent();
}
