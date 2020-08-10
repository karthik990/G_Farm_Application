package p043io.netty.util.concurrent;

import java.util.LinkedHashSet;
import java.util.Set;
import p043io.netty.util.concurrent.Future;

@Deprecated
/* renamed from: io.netty.util.concurrent.PromiseAggregator */
public class PromiseAggregator<V, F extends Future<V>> implements GenericFutureListener<F> {
    private final Promise<?> aggregatePromise;
    private final boolean failPending;
    private Set<Promise<V>> pendingPromises;

    public PromiseAggregator(Promise<Void> promise, boolean z) {
        if (promise != null) {
            this.aggregatePromise = promise;
            this.failPending = z;
            return;
        }
        throw new NullPointerException("aggregatePromise");
    }

    public PromiseAggregator(Promise<Void> promise) {
        this(promise, true);
    }

    @SafeVarargs
    public final PromiseAggregator<V, F> add(Promise<V>... promiseArr) {
        if (promiseArr == null) {
            throw new NullPointerException("promises");
        } else if (promiseArr.length == 0) {
            return this;
        } else {
            synchronized (this) {
                if (this.pendingPromises == null) {
                    this.pendingPromises = new LinkedHashSet(promiseArr.length > 1 ? promiseArr.length : 2);
                }
                for (Promise<V> promise : promiseArr) {
                    if (promise != null) {
                        this.pendingPromises.add(promise);
                        promise.addListener(this);
                    }
                }
            }
            return this;
        }
    }

    public synchronized void operationComplete(F f) throws Exception {
        if (this.pendingPromises == null) {
            this.aggregatePromise.setSuccess(null);
        } else {
            this.pendingPromises.remove(f);
            if (!f.isSuccess()) {
                Throwable cause = f.cause();
                this.aggregatePromise.setFailure(cause);
                if (this.failPending) {
                    for (Promise failure : this.pendingPromises) {
                        failure.setFailure(cause);
                    }
                }
            } else if (this.pendingPromises.isEmpty()) {
                this.aggregatePromise.setSuccess(null);
            }
        }
    }
}
