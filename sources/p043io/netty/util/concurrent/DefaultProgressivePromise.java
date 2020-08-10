package p043io.netty.util.concurrent;

/* renamed from: io.netty.util.concurrent.DefaultProgressivePromise */
public class DefaultProgressivePromise<V> extends DefaultPromise<V> implements ProgressivePromise<V> {
    public DefaultProgressivePromise(EventExecutor eventExecutor) {
        super(eventExecutor);
    }

    protected DefaultProgressivePromise() {
    }

    public ProgressivePromise<V> setProgress(long j, long j2) {
        String str = "progress: ";
        if (j2 < 0) {
            j2 = -1;
            if (j < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(j);
                sb.append(" (expected: >= 0)");
                throw new IllegalArgumentException(sb.toString());
            }
        } else if (j < 0 || j > j2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(j);
            sb2.append(" (expected: 0 <= progress <= total (");
            sb2.append(j2);
            sb2.append("))");
            throw new IllegalArgumentException(sb2.toString());
        }
        if (!isDone()) {
            notifyProgressiveListeners(j, j2);
            return this;
        }
        throw new IllegalStateException("complete already");
    }

    public boolean tryProgress(long j, long j2) {
        if (j2 < 0) {
            j2 = -1;
            if (j < 0 || isDone()) {
                return false;
            }
        } else if (j < 0 || j > j2 || isDone()) {
            return false;
        }
        notifyProgressiveListeners(j, j2);
        return true;
    }

    public ProgressivePromise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        super.addListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    public ProgressivePromise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        super.addListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    public ProgressivePromise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        super.removeListener((GenericFutureListener) genericFutureListener);
        return this;
    }

    public ProgressivePromise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        super.removeListeners((GenericFutureListener[]) genericFutureListenerArr);
        return this;
    }

    public ProgressivePromise<V> sync() throws InterruptedException {
        super.sync();
        return this;
    }

    public ProgressivePromise<V> syncUninterruptibly() {
        super.syncUninterruptibly();
        return this;
    }

    public ProgressivePromise<V> await() throws InterruptedException {
        super.await();
        return this;
    }

    public ProgressivePromise<V> awaitUninterruptibly() {
        super.awaitUninterruptibly();
        return this;
    }

    public ProgressivePromise<V> setSuccess(V v) {
        super.setSuccess(v);
        return this;
    }

    public ProgressivePromise<V> setFailure(Throwable th) {
        super.setFailure(th);
        return this;
    }
}
