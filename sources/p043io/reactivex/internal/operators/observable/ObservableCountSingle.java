package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.Observable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.FuseToObservable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableCountSingle */
public final class ObservableCountSingle<T> extends Single<Long> implements FuseToObservable<Long> {
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableCountSingle$CountObserver */
    static final class CountObserver implements Observer<Object>, Disposable {
        final SingleObserver<? super Long> actual;
        long count;

        /* renamed from: d */
        Disposable f4004d;

        CountObserver(SingleObserver<? super Long> singleObserver) {
            this.actual = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4004d, disposable)) {
                this.f4004d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f4004d.dispose();
            this.f4004d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f4004d.isDisposed();
        }

        public void onNext(Object obj) {
            this.count++;
        }

        public void onError(Throwable th) {
            this.f4004d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f4004d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(Long.valueOf(this.count));
        }
    }

    public ObservableCountSingle(ObservableSource<T> observableSource) {
        this.source = observableSource;
    }

    public void subscribeActual(SingleObserver<? super Long> singleObserver) {
        this.source.subscribe(new CountObserver(singleObserver));
    }

    public Observable<Long> fuseToObservable() {
        return RxJavaPlugins.onAssembly((Observable<T>) new ObservableCount<T>(this.source));
    }
}
