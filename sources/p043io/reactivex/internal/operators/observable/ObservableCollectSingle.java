package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.Callable;
import p043io.reactivex.Observable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.functions.BiConsumer;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.fuseable.FuseToObservable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableCollectSingle */
public final class ObservableCollectSingle<T, U> extends Single<U> implements FuseToObservable<U> {
    final BiConsumer<? super U, ? super T> collector;
    final Callable<? extends U> initialSupplier;
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableCollectSingle$CollectObserver */
    static final class CollectObserver<T, U> implements Observer<T>, Disposable {
        final SingleObserver<? super U> actual;
        final BiConsumer<? super U, ? super T> collector;
        boolean done;

        /* renamed from: s */
        Disposable f3996s;

        /* renamed from: u */
        final U f3997u;

        CollectObserver(SingleObserver<? super U> singleObserver, U u, BiConsumer<? super U, ? super T> biConsumer) {
            this.actual = singleObserver;
            this.collector = biConsumer;
            this.f3997u = u;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3996s, disposable)) {
                this.f3996s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f3996s.dispose();
        }

        public boolean isDisposed() {
            return this.f3996s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.collector.accept(this.f3997u, t);
                } catch (Throwable th) {
                    this.f3996s.dispose();
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
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onSuccess(this.f3997u);
            }
        }
    }

    public ObservableCollectSingle(ObservableSource<T> observableSource, Callable<? extends U> callable, BiConsumer<? super U, ? super T> biConsumer) {
        this.source = observableSource;
        this.initialSupplier = callable;
        this.collector = biConsumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super U> singleObserver) {
        try {
            this.source.subscribe(new CollectObserver(singleObserver, ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value"), this.collector));
        } catch (Throwable th) {
            EmptyDisposable.error(th, singleObserver);
        }
    }

    public Observable<U> fuseToObservable() {
        return RxJavaPlugins.onAssembly((Observable<T>) new ObservableCollect<T>(this.source, this.initialSupplier, this.collector));
    }
}
