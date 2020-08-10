package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFlattenIterable */
public final class ObservableFlattenIterable<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlattenIterable$FlattenIterableObserver */
    static final class FlattenIterableObserver<T, R> implements Observer<T>, Disposable {
        final Observer<? super R> actual;

        /* renamed from: d */
        Disposable f4024d;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;

        FlattenIterableObserver(Observer<? super R> observer, Function<? super T, ? extends Iterable<? extends R>> function) {
            this.actual = observer;
            this.mapper = function;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4024d, disposable)) {
                this.f4024d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.f4024d != DisposableHelper.DISPOSED) {
                try {
                    Observer<? super R> observer = this.actual;
                    for (Object requireNonNull : (Iterable) this.mapper.apply(t)) {
                        try {
                            try {
                                observer.onNext(ObjectHelper.requireNonNull(requireNonNull, "The iterator returned a null value"));
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.f4024d.dispose();
                                onError(th);
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.f4024d.dispose();
                            onError(th2);
                            return;
                        }
                    }
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    this.f4024d.dispose();
                    onError(th3);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.f4024d == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.f4024d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (this.f4024d != DisposableHelper.DISPOSED) {
                this.f4024d = DisposableHelper.DISPOSED;
                this.actual.onComplete();
            }
        }

        public boolean isDisposed() {
            return this.f4024d.isDisposed();
        }

        public void dispose() {
            this.f4024d.dispose();
            this.f4024d = DisposableHelper.DISPOSED;
        }
    }

    public ObservableFlattenIterable(ObservableSource<T> observableSource, Function<? super T, ? extends Iterable<? extends R>> function) {
        super(observableSource);
        this.mapper = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlattenIterableObserver(observer, this.mapper));
    }
}
