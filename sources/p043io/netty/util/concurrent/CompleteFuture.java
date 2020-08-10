package p043io.netty.util.concurrent;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import java.util.concurrent.TimeUnit;

/* renamed from: io.netty.util.concurrent.CompleteFuture */
public abstract class CompleteFuture<V> extends AbstractFuture<V> {
    private final EventExecutor executor;

    public Future<V> awaitUninterruptibly() {
        return this;
    }

    public boolean awaitUninterruptibly(long j) {
        return true;
    }

    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        return true;
    }

    public boolean cancel(boolean z) {
        return false;
    }

    public boolean isCancellable() {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }

    public Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        return this;
    }

    public Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        return this;
    }

    public Future<V> sync() throws InterruptedException {
        return this;
    }

    public Future<V> syncUninterruptibly() {
        return this;
    }

    protected CompleteFuture(EventExecutor eventExecutor) {
        this.executor = eventExecutor;
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        return this.executor;
    }

    public Future<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        if (genericFutureListener != null) {
            DefaultPromise.notifyListener(executor(), this, genericFutureListener);
            return this;
        }
        throw new NullPointerException(CastExtraArgs.LISTENER);
    }

    public Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        if (genericFutureListenerArr != null) {
            for (GenericFutureListener<? extends Future<? super V>> genericFutureListener : genericFutureListenerArr) {
                if (genericFutureListener == null) {
                    break;
                }
                DefaultPromise.notifyListener(executor(), this, genericFutureListener);
            }
            return this;
        }
        throw new NullPointerException("listeners");
    }

    public Future<V> await() throws InterruptedException {
        if (!Thread.interrupted()) {
            return this;
        }
        throw new InterruptedException();
    }

    public boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
        if (!Thread.interrupted()) {
            return true;
        }
        throw new InterruptedException();
    }

    public boolean await(long j) throws InterruptedException {
        if (!Thread.interrupted()) {
            return true;
        }
        throw new InterruptedException();
    }
}
