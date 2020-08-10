package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.TimeUnit;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Scheduler.Worker;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.observers.SerializedObserver;

/* renamed from: io.reactivex.internal.operators.observable.ObservableDelay */
public final class ObservableDelay<T> extends AbstractObservableWithUpstream<T, T> {
    final long delay;
    final boolean delayError;
    final Scheduler scheduler;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$DelayObserver */
    static final class DelayObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;
        final long delay;
        final boolean delayError;

        /* renamed from: s */
        Disposable f4007s;
        final TimeUnit unit;

        /* renamed from: w */
        final Worker f4008w;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$DelayObserver$OnComplete */
        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                try {
                    DelayObserver.this.actual.onComplete();
                } finally {
                    DelayObserver.this.f4008w.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$DelayObserver$OnError */
        final class OnError implements Runnable {
            private final Throwable throwable;

            OnError(Throwable th) {
                this.throwable = th;
            }

            public void run() {
                try {
                    DelayObserver.this.actual.onError(this.throwable);
                } finally {
                    DelayObserver.this.f4008w.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.ObservableDelay$DelayObserver$OnNext */
        final class OnNext implements Runnable {

            /* renamed from: t */
            private final T f4009t;

            OnNext(T t) {
                this.f4009t = t;
            }

            public void run() {
                DelayObserver.this.actual.onNext(this.f4009t);
            }
        }

        DelayObserver(Observer<? super T> observer, long j, TimeUnit timeUnit, Worker worker, boolean z) {
            this.actual = observer;
            this.delay = j;
            this.unit = timeUnit;
            this.f4008w = worker;
            this.delayError = z;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4007s, disposable)) {
                this.f4007s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.f4008w.schedule(new OnNext(t), this.delay, this.unit);
        }

        public void onError(Throwable th) {
            this.f4008w.schedule(new OnError(th), this.delayError ? this.delay : 0, this.unit);
        }

        public void onComplete() {
            this.f4008w.schedule(new OnComplete(), this.delay, this.unit);
        }

        public void dispose() {
            this.f4007s.dispose();
            this.f4008w.dispose();
        }

        public boolean isDisposed() {
            return this.f4008w.isDisposed();
        }
    }

    public ObservableDelay(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler2, boolean z) {
        super(observableSource);
        this.delay = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
        this.delayError = z;
    }

    public void subscribeActual(Observer<? super T> observer) {
        Observer<? super T> observer2;
        if (this.delayError) {
            observer2 = observer;
        } else {
            observer2 = new SerializedObserver<>(observer);
        }
        Worker createWorker = this.scheduler.createWorker();
        ObservableSource observableSource = this.source;
        DelayObserver delayObserver = new DelayObserver(observer2, this.delay, this.unit, createWorker, this.delayError);
        observableSource.subscribe(delayObserver);
    }
}
