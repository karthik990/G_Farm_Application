package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.ArrayCompositeDisposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.observers.SerializedObserver;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSkipUntil */
public final class ObservableSkipUntil<T, U> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<U> other;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSkipUntil$SkipUntil */
    final class SkipUntil implements Observer<U> {
        private final ArrayCompositeDisposable frc;

        /* renamed from: s */
        Disposable f4063s;
        private final SerializedObserver<T> serial;
        private final SkipUntilObserver<T> sus;

        SkipUntil(ArrayCompositeDisposable arrayCompositeDisposable, SkipUntilObserver<T> skipUntilObserver, SerializedObserver<T> serializedObserver) {
            this.frc = arrayCompositeDisposable;
            this.sus = skipUntilObserver;
            this.serial = serializedObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4063s, disposable)) {
                this.f4063s = disposable;
                this.frc.setResource(1, disposable);
            }
        }

        public void onNext(U u) {
            this.f4063s.dispose();
            this.sus.notSkipping = true;
        }

        public void onError(Throwable th) {
            this.frc.dispose();
            this.serial.onError(th);
        }

        public void onComplete() {
            this.sus.notSkipping = true;
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSkipUntil$SkipUntilObserver */
    static final class SkipUntilObserver<T> implements Observer<T> {
        final Observer<? super T> actual;
        final ArrayCompositeDisposable frc;
        volatile boolean notSkipping;
        boolean notSkippingLocal;

        /* renamed from: s */
        Disposable f4064s;

        SkipUntilObserver(Observer<? super T> observer, ArrayCompositeDisposable arrayCompositeDisposable) {
            this.actual = observer;
            this.frc = arrayCompositeDisposable;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4064s, disposable)) {
                this.f4064s = disposable;
                this.frc.setResource(0, disposable);
            }
        }

        public void onNext(T t) {
            if (this.notSkippingLocal) {
                this.actual.onNext(t);
            } else if (this.notSkipping) {
                this.notSkippingLocal = true;
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable th) {
            this.frc.dispose();
            this.actual.onError(th);
        }

        public void onComplete() {
            this.frc.dispose();
            this.actual.onComplete();
        }
    }

    public ObservableSkipUntil(ObservableSource<T> observableSource, ObservableSource<U> observableSource2) {
        super(observableSource);
        this.other = observableSource2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        ArrayCompositeDisposable arrayCompositeDisposable = new ArrayCompositeDisposable(2);
        serializedObserver.onSubscribe(arrayCompositeDisposable);
        SkipUntilObserver skipUntilObserver = new SkipUntilObserver(serializedObserver, arrayCompositeDisposable);
        this.other.subscribe(new SkipUntil(arrayCompositeDisposable, skipUntilObserver, serializedObserver));
        this.source.subscribe(skipUntilObserver);
    }
}
