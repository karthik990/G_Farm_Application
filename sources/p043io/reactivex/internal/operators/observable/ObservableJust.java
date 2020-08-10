package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.Observable;
import p043io.reactivex.Observer;
import p043io.reactivex.internal.fuseable.ScalarCallable;
import p043io.reactivex.internal.operators.observable.ObservableScalarXMap.ScalarDisposable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableJust */
public final class ObservableJust<T> extends Observable<T> implements ScalarCallable<T> {
    private final T value;

    public ObservableJust(T t) {
        this.value = t;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        ScalarDisposable scalarDisposable = new ScalarDisposable(observer, this.value);
        observer.onSubscribe(scalarDisposable);
        scalarDisposable.run();
    }

    public T call() {
        return this.value;
    }
}
