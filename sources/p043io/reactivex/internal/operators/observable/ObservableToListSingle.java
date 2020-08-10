package p043io.reactivex.internal.operators.observable;

import java.util.Collection;
import java.util.concurrent.Callable;
import p043io.reactivex.Observable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.internal.functions.Functions;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.fuseable.FuseToObservable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableToListSingle */
public final class ObservableToListSingle<T, U extends Collection<? super T>> extends Single<U> implements FuseToObservable<U> {
    final Callable<U> collectionSupplier;
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableToListSingle$ToListObserver */
    static final class ToListObserver<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        final SingleObserver<? super U> actual;
        U collection;

        /* renamed from: s */
        Disposable f4081s;

        ToListObserver(SingleObserver<? super U> singleObserver, U u) {
            this.actual = singleObserver;
            this.collection = u;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4081s, disposable)) {
                this.f4081s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f4081s.dispose();
        }

        public boolean isDisposed() {
            return this.f4081s.isDisposed();
        }

        public void onNext(T t) {
            this.collection.add(t);
        }

        public void onError(Throwable th) {
            this.collection = null;
            this.actual.onError(th);
        }

        public void onComplete() {
            U u = this.collection;
            this.collection = null;
            this.actual.onSuccess(u);
        }
    }

    public ObservableToListSingle(ObservableSource<T> observableSource, int i) {
        this.source = observableSource;
        this.collectionSupplier = Functions.createArrayList(i);
    }

    public ObservableToListSingle(ObservableSource<T> observableSource, Callable<U> callable) {
        this.source = observableSource;
        this.collectionSupplier = callable;
    }

    public void subscribeActual(SingleObserver<? super U> singleObserver) {
        try {
            this.source.subscribe(new ToListObserver(singleObserver, (Collection) ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }

    public Observable<U> fuseToObservable() {
        return RxJavaPlugins.onAssembly((Observable<T>) new ObservableToList<T>(this.source, this.collectionSupplier));
    }
}
