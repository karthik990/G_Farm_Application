package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Scheduler.Worker;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.ObserverFullArbiter;
import p043io.reactivex.internal.observers.FullArbiterObserver;
import p043io.reactivex.observers.SerializedObserver;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableTimeoutTimed */
public final class ObservableTimeoutTimed<T> extends AbstractObservableWithUpstream<T, T> {
    static final Disposable NEW_TIMER = new EmptyDisposable();
    final ObservableSource<? extends T> other;
    final Scheduler scheduler;
    final long timeout;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableTimeoutTimed$EmptyDisposable */
    static final class EmptyDisposable implements Disposable {
        public void dispose() {
        }

        public boolean isDisposed() {
            return true;
        }

        EmptyDisposable() {
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableTimeoutTimed$TimeoutTimedObserver */
    static final class TimeoutTimedObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -8387234228317808253L;
        final Observer<? super T> actual;
        volatile boolean done;
        volatile long index;

        /* renamed from: s */
        Disposable f4078s;
        final long timeout;
        final TimeUnit unit;
        final Worker worker;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableTimeoutTimed$TimeoutTimedObserver$TimeoutTask */
        final class TimeoutTask implements Runnable {
            private final long idx;

            TimeoutTask(long j) {
                this.idx = j;
            }

            public void run() {
                if (this.idx == TimeoutTimedObserver.this.index) {
                    TimeoutTimedObserver timeoutTimedObserver = TimeoutTimedObserver.this;
                    timeoutTimedObserver.done = true;
                    timeoutTimedObserver.f4078s.dispose();
                    DisposableHelper.dispose(TimeoutTimedObserver.this);
                    TimeoutTimedObserver.this.actual.onError(new TimeoutException());
                    TimeoutTimedObserver.this.worker.dispose();
                }
            }
        }

        TimeoutTimedObserver(Observer<? super T> observer, long j, TimeUnit timeUnit, Worker worker2) {
            this.actual = observer;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4078s, disposable)) {
                this.f4078s = disposable;
                this.actual.onSubscribe(this);
                scheduleTimeout(0);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long j = this.index + 1;
                this.index = j;
                this.actual.onNext(t);
                scheduleTimeout(j);
            }
        }

        /* access modifiers changed from: 0000 */
        public void scheduleTimeout(long j) {
            Disposable disposable = (Disposable) get();
            if (disposable != null) {
                disposable.dispose();
            }
            if (compareAndSet(disposable, ObservableTimeoutTimed.NEW_TIMER)) {
                DisposableHelper.replace(this, this.worker.schedule(new TimeoutTask(j), this.timeout, this.unit));
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
            dispose();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
                dispose();
            }
        }

        public void dispose() {
            this.f4078s.dispose();
            this.worker.dispose();
        }

        public boolean isDisposed() {
            return this.worker.isDisposed();
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableTimeoutTimed$TimeoutTimedOtherObserver */
    static final class TimeoutTimedOtherObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -4619702551964128179L;
        final Observer<? super T> actual;
        final ObserverFullArbiter<T> arbiter;
        volatile boolean done;
        volatile long index;
        final ObservableSource<? extends T> other;

        /* renamed from: s */
        Disposable f4079s;
        final long timeout;
        final TimeUnit unit;
        final Worker worker;

        /* renamed from: io.reactivex.internal.operators.observable.ObservableTimeoutTimed$TimeoutTimedOtherObserver$SubscribeNext */
        final class SubscribeNext implements Runnable {
            private final long idx;

            SubscribeNext(long j) {
                this.idx = j;
            }

            public void run() {
                if (this.idx == TimeoutTimedOtherObserver.this.index) {
                    TimeoutTimedOtherObserver timeoutTimedOtherObserver = TimeoutTimedOtherObserver.this;
                    timeoutTimedOtherObserver.done = true;
                    timeoutTimedOtherObserver.f4079s.dispose();
                    DisposableHelper.dispose(TimeoutTimedOtherObserver.this);
                    TimeoutTimedOtherObserver.this.subscribeNext();
                    TimeoutTimedOtherObserver.this.worker.dispose();
                }
            }
        }

        TimeoutTimedOtherObserver(Observer<? super T> observer, long j, TimeUnit timeUnit, Worker worker2, ObservableSource<? extends T> observableSource) {
            this.actual = observer;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker2;
            this.other = observableSource;
            this.arbiter = new ObserverFullArbiter<>(observer, this, 8);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4079s, disposable)) {
                this.f4079s = disposable;
                if (this.arbiter.setDisposable(disposable)) {
                    this.actual.onSubscribe(this.arbiter);
                    scheduleTimeout(0);
                }
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long j = this.index + 1;
                this.index = j;
                if (this.arbiter.onNext(t, this.f4079s)) {
                    scheduleTimeout(j);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void scheduleTimeout(long j) {
            Disposable disposable = (Disposable) get();
            if (disposable != null) {
                disposable.dispose();
            }
            if (compareAndSet(disposable, ObservableTimeoutTimed.NEW_TIMER)) {
                DisposableHelper.replace(this, this.worker.schedule(new SubscribeNext(j), this.timeout, this.unit));
            }
        }

        /* access modifiers changed from: 0000 */
        public void subscribeNext() {
            this.other.subscribe(new FullArbiterObserver(this.arbiter));
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.arbiter.onError(th, this.f4079s);
            this.worker.dispose();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.arbiter.onComplete(this.f4079s);
                this.worker.dispose();
            }
        }

        public void dispose() {
            this.f4079s.dispose();
            this.worker.dispose();
        }

        public boolean isDisposed() {
            return this.worker.isDisposed();
        }
    }

    public ObservableTimeoutTimed(ObservableSource<T> observableSource, long j, TimeUnit timeUnit, Scheduler scheduler2, ObservableSource<? extends T> observableSource2) {
        super(observableSource);
        this.timeout = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
        this.other = observableSource2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        if (this.other == null) {
            ObservableSource observableSource = this.source;
            TimeoutTimedObserver timeoutTimedObserver = new TimeoutTimedObserver(new SerializedObserver(observer), this.timeout, this.unit, this.scheduler.createWorker());
            observableSource.subscribe(timeoutTimedObserver);
            return;
        }
        ObservableSource observableSource2 = this.source;
        TimeoutTimedOtherObserver timeoutTimedOtherObserver = new TimeoutTimedOtherObserver(observer, this.timeout, this.unit, this.scheduler.createWorker(), this.other);
        observableSource2.subscribe(timeoutTimedOtherObserver);
    }
}
