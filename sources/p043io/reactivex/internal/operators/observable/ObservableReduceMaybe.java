package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiFunction;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableReduceMaybe */
public final class ObservableReduceMaybe<T> extends Maybe<T> {
    final BiFunction<T, T, T> reducer;
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableReduceMaybe$ReduceObserver */
    static final class ReduceObserver<T> implements Observer<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f4040d;
        boolean done;
        final BiFunction<T, T, T> reducer;
        T value;

        ReduceObserver(MaybeObserver<? super T> maybeObserver, BiFunction<T, T, T> biFunction) {
            this.actual = maybeObserver;
            this.reducer = biFunction;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4040d, disposable)) {
                this.f4040d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                T t2 = this.value;
                if (t2 == null) {
                    this.value = t;
                    return;
                }
                try {
                    this.value = ObjectHelper.requireNonNull(this.reducer.apply(t2, t), "The reducer returned a null value");
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f4040d.dispose();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                T t = this.value;
                this.value = null;
                if (t != null) {
                    this.actual.onSuccess(t);
                } else {
                    this.actual.onComplete();
                }
            }
        }

        public void dispose() {
            this.f4040d.dispose();
        }

        public boolean isDisposed() {
            return this.f4040d.isDisposed();
        }
    }

    public ObservableReduceMaybe(ObservableSource<T> observableSource, BiFunction<T, T, T> biFunction) {
        this.source = observableSource;
        this.reducer = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new ReduceObserver(maybeObserver, this.reducer));
    }
}
