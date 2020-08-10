package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSingleMaybe */
public final class ObservableSingleMaybe<T> extends Maybe<T> {
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSingleMaybe$SingleElementObserver */
    static final class SingleElementObserver<T> implements Observer<T>, Disposable {
        final MaybeObserver<? super T> actual;
        boolean done;

        /* renamed from: s */
        Disposable f4057s;
        T value;

        SingleElementObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4057s, disposable)) {
                this.f4057s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f4057s.dispose();
        }

        public boolean isDisposed() {
            return this.f4057s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.value != null) {
                    this.done = true;
                    this.f4057s.dispose();
                    this.actual.onError(new IllegalArgumentException("Sequence contains more than one element!"));
                    return;
                }
                this.value = t;
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                T t = this.value;
                this.value = null;
                if (t == null) {
                    this.actual.onComplete();
                } else {
                    this.actual.onSuccess(t);
                }
            }
        }
    }

    public ObservableSingleMaybe(ObservableSource<T> observableSource) {
        this.source = observableSource;
    }

    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new SingleElementObserver(maybeObserver));
    }
}
