package p043io.reactivex.internal.operators.single;

import p043io.reactivex.Observable;
import p043io.reactivex.Observer;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleToObservable */
public final class SingleToObservable<T> extends Observable<T> {
    final SingleSource<? extends T> source;

    /* renamed from: io.reactivex.internal.operators.single.SingleToObservable$SingleToObservableObserver */
    static final class SingleToObservableObserver<T> implements SingleObserver<T>, Disposable {
        final Observer<? super T> actual;

        /* renamed from: d */
        Disposable f4138d;

        SingleToObservableObserver(Observer<? super T> observer) {
            this.actual = observer;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4138d, disposable)) {
                this.f4138d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.actual.onNext(t);
            this.actual.onComplete();
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void dispose() {
            this.f4138d.dispose();
        }

        public boolean isDisposed() {
            return this.f4138d.isDisposed();
        }
    }

    public SingleToObservable(SingleSource<? extends T> singleSource) {
        this.source = singleSource;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SingleToObservableObserver(observer));
    }
}
