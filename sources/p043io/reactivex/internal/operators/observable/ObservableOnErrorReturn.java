package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableOnErrorReturn */
public final class ObservableOnErrorReturn<T> extends AbstractObservableWithUpstream<T, T> {
    final Function<? super Throwable, ? extends T> valueSupplier;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableOnErrorReturn$OnErrorReturnObserver */
    static final class OnErrorReturnObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;

        /* renamed from: s */
        Disposable f4037s;
        final Function<? super Throwable, ? extends T> valueSupplier;

        OnErrorReturnObserver(Observer<? super T> observer, Function<? super Throwable, ? extends T> function) {
            this.actual = observer;
            this.valueSupplier = function;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4037s, disposable)) {
                this.f4037s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f4037s.dispose();
        }

        public boolean isDisposed() {
            return this.f4037s.isDisposed();
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            try {
                Object apply = this.valueSupplier.apply(th);
                if (apply == null) {
                    NullPointerException nullPointerException = new NullPointerException("The supplied value is null");
                    nullPointerException.initCause(th);
                    this.actual.onError(nullPointerException);
                    return;
                }
                this.actual.onNext(apply);
                this.actual.onComplete();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.actual.onError(new CompositeException(th, th2));
            }
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }

    public ObservableOnErrorReturn(ObservableSource<T> observableSource, Function<? super Throwable, ? extends T> function) {
        super(observableSource);
        this.valueSupplier = function;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new OnErrorReturnObserver(observer, this.valueSupplier));
    }
}
