package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableCount */
public final class ObservableCount<T> extends AbstractObservableWithUpstream<T, Long> {

    /* renamed from: io.reactivex.internal.operators.observable.ObservableCount$CountObserver */
    static final class CountObserver implements Observer<Object>, Disposable {
        final Observer<? super Long> actual;
        long count;

        /* renamed from: s */
        Disposable f4003s;

        CountObserver(Observer<? super Long> observer) {
            this.actual = observer;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4003s, disposable)) {
                this.f4003s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f4003s.dispose();
        }

        public boolean isDisposed() {
            return this.f4003s.isDisposed();
        }

        public void onNext(Object obj) {
            this.count++;
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            this.actual.onNext(Long.valueOf(this.count));
            this.actual.onComplete();
        }
    }

    public ObservableCount(ObservableSource<T> observableSource) {
        super(observableSource);
    }

    public void subscribeActual(Observer<? super Long> observer) {
        this.source.subscribe(new CountObserver(observer));
    }
}
