package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Scheduler.Worker;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.subscribers.FullArbiterSubscriber;
import p043io.reactivex.internal.subscriptions.FullArbiter;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeoutTimed */
public final class FlowableTimeoutTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    static final Disposable NEW_TIMER = new EmptyDispose();
    final Publisher<? extends T> other;
    final Scheduler scheduler;
    final long timeout;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeoutTimed$EmptyDispose */
    static final class EmptyDispose implements Disposable {
        public void dispose() {
        }

        public boolean isDisposed() {
            return true;
        }

        EmptyDispose() {
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeoutTimed$TimeoutTimedOtherSubscriber */
    static final class TimeoutTimedOtherSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final Subscriber<? super T> actual;
        final FullArbiter<T> arbiter;
        volatile boolean done;
        volatile long index;
        final Publisher<? extends T> other;

        /* renamed from: s */
        Subscription f3924s;
        final long timeout;
        final AtomicReference<Disposable> timer = new AtomicReference<>();
        final TimeUnit unit;
        final Worker worker;

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeoutTimed$TimeoutTimedOtherSubscriber$TimeoutTask */
        final class TimeoutTask implements Runnable {
            private final long idx;

            TimeoutTask(long j) {
                this.idx = j;
            }

            public void run() {
                if (this.idx == TimeoutTimedOtherSubscriber.this.index) {
                    TimeoutTimedOtherSubscriber timeoutTimedOtherSubscriber = TimeoutTimedOtherSubscriber.this;
                    timeoutTimedOtherSubscriber.done = true;
                    timeoutTimedOtherSubscriber.f3924s.cancel();
                    DisposableHelper.dispose(TimeoutTimedOtherSubscriber.this.timer);
                    TimeoutTimedOtherSubscriber.this.subscribeNext();
                    TimeoutTimedOtherSubscriber.this.worker.dispose();
                }
            }
        }

        TimeoutTimedOtherSubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Worker worker2, Publisher<? extends T> publisher) {
            this.actual = subscriber;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker2;
            this.other = publisher;
            this.arbiter = new FullArbiter<>(subscriber, this, 8);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3924s, subscription)) {
                this.f3924s = subscription;
                if (this.arbiter.setSubscription(subscription)) {
                    this.actual.onSubscribe(this.arbiter);
                    scheduleTimeout(0);
                }
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long j = this.index + 1;
                this.index = j;
                if (this.arbiter.onNext(t, this.f3924s)) {
                    scheduleTimeout(j);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void scheduleTimeout(long j) {
            Disposable disposable = (Disposable) this.timer.get();
            if (disposable != null) {
                disposable.dispose();
            }
            if (this.timer.compareAndSet(disposable, FlowableTimeoutTimed.NEW_TIMER)) {
                DisposableHelper.replace(this.timer, this.worker.schedule(new TimeoutTask(j), this.timeout, this.unit));
            }
        }

        /* access modifiers changed from: 0000 */
        public void subscribeNext() {
            this.other.subscribe(new FullArbiterSubscriber(this.arbiter));
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.arbiter.onError(th, this.f3924s);
            this.worker.dispose();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.arbiter.onComplete(this.f3924s);
                this.worker.dispose();
            }
        }

        public void dispose() {
            this.f3924s.cancel();
            this.worker.dispose();
        }

        public boolean isDisposed() {
            return this.worker.isDisposed();
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeoutTimed$TimeoutTimedSubscriber */
    static final class TimeoutTimedSubscriber<T> implements FlowableSubscriber<T>, Disposable, Subscription {
        final Subscriber<? super T> actual;
        volatile boolean done;
        volatile long index;

        /* renamed from: s */
        Subscription f3925s;
        final long timeout;
        final AtomicReference<Disposable> timer = new AtomicReference<>();
        final TimeUnit unit;
        final Worker worker;

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeoutTimed$TimeoutTimedSubscriber$TimeoutTask */
        final class TimeoutTask implements Runnable {
            private final long idx;

            TimeoutTask(long j) {
                this.idx = j;
            }

            public void run() {
                if (this.idx == TimeoutTimedSubscriber.this.index) {
                    TimeoutTimedSubscriber timeoutTimedSubscriber = TimeoutTimedSubscriber.this;
                    timeoutTimedSubscriber.done = true;
                    timeoutTimedSubscriber.dispose();
                    TimeoutTimedSubscriber.this.actual.onError(new TimeoutException());
                }
            }
        }

        TimeoutTimedSubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Worker worker2) {
            this.actual = subscriber;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3925s, subscription)) {
                this.f3925s = subscription;
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
            Disposable disposable = (Disposable) this.timer.get();
            if (disposable != null) {
                disposable.dispose();
            }
            if (this.timer.compareAndSet(disposable, FlowableTimeoutTimed.NEW_TIMER)) {
                DisposableHelper.replace(this.timer, this.worker.schedule(new TimeoutTask(j), this.timeout, this.unit));
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
            this.worker.dispose();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
                this.worker.dispose();
            }
        }

        public void dispose() {
            this.f3925s.cancel();
            this.worker.dispose();
        }

        public boolean isDisposed() {
            return this.worker.isDisposed();
        }

        public void request(long j) {
            this.f3925s.request(j);
        }

        public void cancel() {
            dispose();
        }
    }

    public FlowableTimeoutTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler2, Publisher<? extends T> publisher) {
        super(flowable);
        this.timeout = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
        this.other = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.other == null) {
            Flowable flowable = this.source;
            TimeoutTimedSubscriber timeoutTimedSubscriber = new TimeoutTimedSubscriber(new SerializedSubscriber(subscriber), this.timeout, this.unit, this.scheduler.createWorker());
            flowable.subscribe((FlowableSubscriber<? super T>) timeoutTimedSubscriber);
            return;
        }
        Flowable flowable2 = this.source;
        TimeoutTimedOtherSubscriber timeoutTimedOtherSubscriber = new TimeoutTimedOtherSubscriber(subscriber, this.timeout, this.unit, this.scheduler.createWorker(), this.other);
        flowable2.subscribe((FlowableSubscriber<? super T>) timeoutTimedOtherSubscriber);
    }
}
