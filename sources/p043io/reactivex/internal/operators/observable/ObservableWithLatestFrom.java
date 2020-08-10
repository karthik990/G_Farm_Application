package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiFunction;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.observers.SerializedObserver;

/* renamed from: io.reactivex.internal.operators.observable.ObservableWithLatestFrom */
public final class ObservableWithLatestFrom<T, U, R> extends AbstractObservableWithUpstream<T, R> {
    final BiFunction<? super T, ? super U, ? extends R> combiner;
    final ObservableSource<? extends U> other;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWithLatestFrom$WithLastFrom */
    final class WithLastFrom implements Observer<U> {
        private final WithLatestFromObserver<T, U, R> wlf;

        public void onComplete() {
        }

        WithLastFrom(WithLatestFromObserver<T, U, R> withLatestFromObserver) {
            this.wlf = withLatestFromObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.wlf.setOther(disposable);
        }

        public void onNext(U u) {
            this.wlf.lazySet(u);
        }

        public void onError(Throwable th) {
            this.wlf.otherError(th);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableWithLatestFrom$WithLatestFromObserver */
    static final class WithLatestFromObserver<T, U, R> extends AtomicReference<U> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -312246233408980075L;
        final Observer<? super R> actual;
        final BiFunction<? super T, ? super U, ? extends R> combiner;
        final AtomicReference<Disposable> other = new AtomicReference<>();

        /* renamed from: s */
        final AtomicReference<Disposable> f4097s = new AtomicReference<>();

        WithLatestFromObserver(Observer<? super R> observer, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.actual = observer;
            this.combiner = biFunction;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.f4097s, disposable);
        }

        public void onNext(T t) {
            Object obj = get();
            if (obj != null) {
                try {
                    this.actual.onNext(ObjectHelper.requireNonNull(this.combiner.apply(t, obj), "The combiner returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    dispose();
                    this.actual.onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this.other);
            this.actual.onError(th);
        }

        public void onComplete() {
            DisposableHelper.dispose(this.other);
            this.actual.onComplete();
        }

        public void dispose() {
            DisposableHelper.dispose(this.f4097s);
            DisposableHelper.dispose(this.other);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.f4097s.get());
        }

        public boolean setOther(Disposable disposable) {
            return DisposableHelper.setOnce(this.other, disposable);
        }

        public void otherError(Throwable th) {
            DisposableHelper.dispose(this.f4097s);
            this.actual.onError(th);
        }
    }

    public ObservableWithLatestFrom(ObservableSource<T> observableSource, BiFunction<? super T, ? super U, ? extends R> biFunction, ObservableSource<? extends U> observableSource2) {
        super(observableSource);
        this.combiner = biFunction;
        this.other = observableSource2;
    }

    public void subscribeActual(Observer<? super R> observer) {
        SerializedObserver serializedObserver = new SerializedObserver(observer);
        WithLatestFromObserver withLatestFromObserver = new WithLatestFromObserver(serializedObserver, this.combiner);
        serializedObserver.onSubscribe(withLatestFromObserver);
        this.other.subscribe(new WithLastFrom(withLatestFromObserver));
        this.source.subscribe(withLatestFromObserver);
    }
}
