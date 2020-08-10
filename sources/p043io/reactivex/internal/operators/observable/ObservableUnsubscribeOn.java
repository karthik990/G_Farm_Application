package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicBoolean;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.Scheduler;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableUnsubscribeOn */
public final class ObservableUnsubscribeOn<T> extends AbstractObservableWithUpstream<T, T> {
    final Scheduler scheduler;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableUnsubscribeOn$UnsubscribeObserver */
    static final class UnsubscribeObserver<T> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = 1015244841293359600L;
        final Observer<? super T> actual;

        /* renamed from: s */
        Disposable f4082s;
        final Scheduler scheduler;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableUnsubscribeOn$UnsubscribeObserver$DisposeTask */
        final class DisposeTask implements Runnable {
            DisposeTask() {
            }

            public void run() {
                UnsubscribeObserver.this.f4082s.dispose();
            }
        }

        UnsubscribeObserver(Observer<? super T> observer, Scheduler scheduler2) {
            this.actual = observer;
            this.scheduler = scheduler2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4082s, disposable)) {
                this.f4082s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!get()) {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable th) {
            if (get()) {
                RxJavaPlugins.onError(th);
            } else {
                this.actual.onError(th);
            }
        }

        public void onComplete() {
            if (!get()) {
                this.actual.onComplete();
            }
        }

        public void dispose() {
            if (compareAndSet(false, true)) {
                this.scheduler.scheduleDirect(new DisposeTask());
            }
        }

        public boolean isDisposed() {
            return get();
        }
    }

    public ObservableUnsubscribeOn(ObservableSource<T> observableSource, Scheduler scheduler2) {
        super(observableSource);
        this.scheduler = scheduler2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new UnsubscribeObserver(observer, this.scheduler));
    }
}
