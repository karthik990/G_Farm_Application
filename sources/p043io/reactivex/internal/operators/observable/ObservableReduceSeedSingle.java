package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiFunction;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableReduceSeedSingle */
public final class ObservableReduceSeedSingle<T, R> extends Single<R> {
    final BiFunction<R, ? super T, R> reducer;
    final R seed;
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableReduceSeedSingle$ReduceSeedObserver */
    static final class ReduceSeedObserver<T, R> implements Observer<T>, Disposable {
        final SingleObserver<? super R> actual;

        /* renamed from: d */
        Disposable f4041d;
        final BiFunction<R, ? super T, R> reducer;
        R value;

        ReduceSeedObserver(SingleObserver<? super R> singleObserver, BiFunction<R, ? super T, R> biFunction, R r) {
            this.actual = singleObserver;
            this.value = r;
            this.reducer = biFunction;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4041d, disposable)) {
                this.f4041d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            R r = this.value;
            if (r != null) {
                try {
                    this.value = ObjectHelper.requireNonNull(this.reducer.apply(r, t), "The reducer returned a null value");
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f4041d.dispose();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            R r = this.value;
            this.value = null;
            if (r != null) {
                this.actual.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            R r = this.value;
            this.value = null;
            if (r != null) {
                this.actual.onSuccess(r);
            }
        }

        public void dispose() {
            this.f4041d.dispose();
        }

        public boolean isDisposed() {
            return this.f4041d.isDisposed();
        }
    }

    public ObservableReduceSeedSingle(ObservableSource<T> observableSource, R r, BiFunction<R, ? super T, R> biFunction) {
        this.source = observableSource;
        this.seed = r;
        this.reducer = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.source.subscribe(new ReduceSeedObserver(singleObserver, this.reducer, this.seed));
    }
}
