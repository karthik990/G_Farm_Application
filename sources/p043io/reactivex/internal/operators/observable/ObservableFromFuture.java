package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import p043io.reactivex.Observable;
import p043io.reactivex.Observer;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.observers.DeferredScalarDisposable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFromFuture */
public final class ObservableFromFuture<T> extends Observable<T> {
    final Future<? extends T> future;
    final long timeout;
    final TimeUnit unit;

    public ObservableFromFuture(Future<? extends T> future2, long j, TimeUnit timeUnit) {
        this.future = future2;
        this.timeout = j;
        this.unit = timeUnit;
    }

    public void subscribeActual(Observer<? super T> observer) {
        DeferredScalarDisposable deferredScalarDisposable = new DeferredScalarDisposable(observer);
        observer.onSubscribe(deferredScalarDisposable);
        if (!deferredScalarDisposable.isDisposed()) {
            try {
                deferredScalarDisposable.complete(ObjectHelper.requireNonNull(this.unit != null ? this.future.get(this.timeout, this.unit) : this.future.get(), "Future returned null"));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (!deferredScalarDisposable.isDisposed()) {
                    observer.onError(th);
                }
            }
        }
    }
}
