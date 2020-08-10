package p043io.reactivex.internal.observers;

import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.ObserverFullArbiter;

/* renamed from: io.reactivex.internal.observers.FullArbiterObserver */
public final class FullArbiterObserver<T> implements Observer<T> {
    final ObserverFullArbiter<T> arbiter;

    /* renamed from: s */
    Disposable f3784s;

    public FullArbiterObserver(ObserverFullArbiter<T> observerFullArbiter) {
        this.arbiter = observerFullArbiter;
    }

    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.f3784s, disposable)) {
            this.f3784s = disposable;
            this.arbiter.setDisposable(disposable);
        }
    }

    public void onNext(T t) {
        this.arbiter.onNext(t, this.f3784s);
    }

    public void onError(Throwable th) {
        this.arbiter.onError(th, this.f3784s);
    }

    public void onComplete() {
        this.arbiter.onComplete(this.f3784s);
    }
}
