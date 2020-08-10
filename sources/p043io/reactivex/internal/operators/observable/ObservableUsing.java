package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import p043io.reactivex.Observable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Consumer;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableUsing */
public final class ObservableUsing<T, D> extends Observable<T> {
    final Consumer<? super D> disposer;
    final boolean eager;
    final Callable<? extends D> resourceSupplier;
    final Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableUsing$UsingObserver */
    static final class UsingObserver<T, D> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = 5904473792286235046L;
        final Observer<? super T> actual;
        final Consumer<? super D> disposer;
        final boolean eager;
        final D resource;

        /* renamed from: s */
        Disposable f4083s;

        UsingObserver(Observer<? super T> observer, D d, Consumer<? super D> consumer, boolean z) {
            this.actual = observer;
            this.resource = d;
            this.disposer = consumer;
            this.eager = z;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4083s, disposable)) {
                this.f4083s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            if (this.eager) {
                if (compareAndSet(false, true)) {
                    try {
                        this.disposer.accept(this.resource);
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        th = new CompositeException(th, th2);
                    }
                }
                this.f4083s.dispose();
                this.actual.onError(th);
                return;
            }
            this.actual.onError(th);
            this.f4083s.dispose();
            disposeAfter();
        }

        public void onComplete() {
            if (this.eager) {
                if (compareAndSet(false, true)) {
                    try {
                        this.disposer.accept(this.resource);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.actual.onError(th);
                        return;
                    }
                }
                this.f4083s.dispose();
                this.actual.onComplete();
            } else {
                this.actual.onComplete();
                this.f4083s.dispose();
                disposeAfter();
            }
        }

        public void dispose() {
            disposeAfter();
            this.f4083s.dispose();
        }

        public boolean isDisposed() {
            return get();
        }

        /* access modifiers changed from: 0000 */
        public void disposeAfter() {
            if (compareAndSet(false, true)) {
                try {
                    this.disposer.accept(this.resource);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public ObservableUsing(Callable<? extends D> callable, Function<? super D, ? extends ObservableSource<? extends T>> function, Consumer<? super D> consumer, boolean z) {
        this.resourceSupplier = callable;
        this.sourceSupplier = function;
        this.disposer = consumer;
        this.eager = z;
    }

    public void subscribeActual(Observer<? super T> observer) {
        try {
            Object call = this.resourceSupplier.call();
            try {
                ((ObservableSource) this.sourceSupplier.apply(call)).subscribe(new UsingObserver(observer, call, this.disposer, this.eager));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error((Throwable) new CompositeException(th, th), observer);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, observer);
        }
    }
}
