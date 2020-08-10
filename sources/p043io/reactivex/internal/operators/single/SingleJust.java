package p043io.reactivex.internal.operators.single;

import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposables;

/* renamed from: io.reactivex.internal.operators.single.SingleJust */
public final class SingleJust<T> extends Single<T> {
    final T value;

    public SingleJust(T t) {
        this.value = t;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        singleObserver.onSubscribe(Disposables.disposed());
        singleObserver.onSuccess(this.value);
    }
}
