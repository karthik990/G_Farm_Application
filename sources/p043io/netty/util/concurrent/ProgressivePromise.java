package p043io.netty.util.concurrent;

/* renamed from: io.netty.util.concurrent.ProgressivePromise */
public interface ProgressivePromise<V> extends Promise<V>, ProgressiveFuture<V> {
    ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    ProgressivePromise<V> await() throws InterruptedException;

    ProgressivePromise<V> awaitUninterruptibly();

    ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    ProgressivePromise<V> setFailure(Throwable th);

    ProgressivePromise<V> setProgress(long j, long j2);

    ProgressivePromise<V> setSuccess(V v);

    ProgressivePromise<V> sync() throws InterruptedException;

    ProgressivePromise<V> syncUninterruptibly();

    boolean tryProgress(long j, long j2);
}
