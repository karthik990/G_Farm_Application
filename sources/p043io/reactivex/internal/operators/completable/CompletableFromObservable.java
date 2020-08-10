package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromObservable */
public final class CompletableFromObservable<T> extends Completable {
    final ObservableSource<T> observable;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableFromObservable$CompletableFromObservableObserver */
    static final class CompletableFromObservableObserver<T> implements Observer<T> {

        /* renamed from: co */
        final CompletableObserver f3806co;

        public void onNext(T t) {
        }

        CompletableFromObservableObserver(CompletableObserver completableObserver) {
            this.f3806co = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.f3806co.onSubscribe(disposable);
        }

        public void onError(Throwable th) {
            this.f3806co.onError(th);
        }

        public void onComplete() {
            this.f3806co.onComplete();
        }
    }

    public CompletableFromObservable(ObservableSource<T> observableSource) {
        this.observable = observableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.observable.subscribe(new CompletableFromObservableObserver(completableObserver));
    }
}
