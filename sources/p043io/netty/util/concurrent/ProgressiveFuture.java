package p043io.netty.util.concurrent;

/* renamed from: io.netty.util.concurrent.ProgressiveFuture */
public interface ProgressiveFuture<V> extends Future<V> {
    ProgressiveFuture<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    ProgressiveFuture<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    ProgressiveFuture<V> await() throws InterruptedException;

    ProgressiveFuture<V> awaitUninterruptibly();

    ProgressiveFuture<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener);

    ProgressiveFuture<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr);

    ProgressiveFuture<V> sync() throws InterruptedException;

    ProgressiveFuture<V> syncUninterruptibly();
}
