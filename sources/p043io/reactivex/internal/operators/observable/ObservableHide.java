package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableHide */
public final class ObservableHide<T> extends AbstractObservableWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.observable.ObservableHide$HideDisposable */
    static final class HideDisposable<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;

        /* renamed from: d */
        Disposable f4028d;

        HideDisposable(Observer<? super T> observer) {
            this.actual = observer;
        }

        public void dispose() {
            this.f4028d.dispose();
        }

        public boolean isDisposed() {
            return this.f4028d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4028d, disposable)) {
                this.f4028d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }

    public ObservableHide(ObservableSource<T> observableSource) {
        super(observableSource);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new HideDisposable(observer));
    }
}
