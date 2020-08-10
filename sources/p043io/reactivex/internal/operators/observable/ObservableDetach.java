package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.util.EmptyComponent;

/* renamed from: io.reactivex.internal.operators.observable.ObservableDetach */
public final class ObservableDetach<T> extends AbstractObservableWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.observable.ObservableDetach$DetachObserver */
    static final class DetachObserver<T> implements Observer<T>, Disposable {
        Observer<? super T> actual;

        /* renamed from: s */
        Disposable f4011s;

        DetachObserver(Observer<? super T> observer) {
            this.actual = observer;
        }

        public void dispose() {
            Disposable disposable = this.f4011s;
            this.f4011s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asObserver();
            disposable.dispose();
        }

        public boolean isDisposed() {
            return this.f4011s.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4011s, disposable)) {
                this.f4011s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            Observer<? super T> observer = this.actual;
            this.f4011s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asObserver();
            observer.onError(th);
        }

        public void onComplete() {
            Observer<? super T> observer = this.actual;
            this.f4011s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asObserver();
            observer.onComplete();
        }
    }

    public ObservableDetach(ObservableSource<T> observableSource) {
        super(observableSource);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DetachObserver(observer));
    }
}
