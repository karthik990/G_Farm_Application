package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSkip */
public final class ObservableSkip<T> extends AbstractObservableWithUpstream<T, T> {

    /* renamed from: n */
    final long f4059n;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSkip$SkipObserver */
    static final class SkipObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;

        /* renamed from: d */
        Disposable f4060d;
        long remaining;

        SkipObserver(Observer<? super T> observer, long j) {
            this.actual = observer;
            this.remaining = j;
        }

        public void onSubscribe(Disposable disposable) {
            this.f4060d = disposable;
            this.actual.onSubscribe(this);
        }

        public void onNext(T t) {
            long j = this.remaining;
            if (j != 0) {
                this.remaining = j - 1;
            } else {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void dispose() {
            this.f4060d.dispose();
        }

        public boolean isDisposed() {
            return this.f4060d.isDisposed();
        }
    }

    public ObservableSkip(ObservableSource<T> observableSource, long j) {
        super(observableSource);
        this.f4059n = j;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SkipObserver(observer, this.f4059n));
    }
}
