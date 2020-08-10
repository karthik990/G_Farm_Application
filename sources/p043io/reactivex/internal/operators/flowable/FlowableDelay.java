package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Scheduler.Worker;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay */
public final class FlowableDelay<T> extends AbstractFlowableWithUpstream<T, T> {
    final long delay;
    final boolean delayError;
    final Scheduler scheduler;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$DelaySubscriber */
    static final class DelaySubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;
        final long delay;
        final boolean delayError;

        /* renamed from: s */
        Subscription f3844s;
        final TimeUnit unit;

        /* renamed from: w */
        final Worker f3845w;

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$DelaySubscriber$OnComplete */
        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                try {
                    DelaySubscriber.this.actual.onComplete();
                } finally {
                    DelaySubscriber.this.f3845w.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$DelaySubscriber$OnError */
        final class OnError implements Runnable {

            /* renamed from: t */
            private final Throwable f3846t;

            OnError(Throwable th) {
                this.f3846t = th;
            }

            public void run() {
                try {
                    DelaySubscriber.this.actual.onError(this.f3846t);
                } finally {
                    DelaySubscriber.this.f3845w.dispose();
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDelay$DelaySubscriber$OnNext */
        final class OnNext implements Runnable {

            /* renamed from: t */
            private final T f3847t;

            OnNext(T t) {
                this.f3847t = t;
            }

            public void run() {
                DelaySubscriber.this.actual.onNext(this.f3847t);
            }
        }

        DelaySubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Worker worker, boolean z) {
            this.actual = subscriber;
            this.delay = j;
            this.unit = timeUnit;
            this.f3845w = worker;
            this.delayError = z;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3844s, subscription)) {
                this.f3844s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.f3845w.schedule(new OnNext(t), this.delay, this.unit);
        }

        public void onError(Throwable th) {
            this.f3845w.schedule(new OnError(th), this.delayError ? this.delay : 0, this.unit);
        }

        public void onComplete() {
            this.f3845w.schedule(new OnComplete(), this.delay, this.unit);
        }

        public void request(long j) {
            this.f3844s.request(j);
        }

        public void cancel() {
            this.f3844s.cancel();
            this.f3845w.dispose();
        }
    }

    public FlowableDelay(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler2, boolean z) {
        super(flowable);
        this.delay = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
        this.delayError = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Subscriber<? super T> subscriber2;
        if (this.delayError) {
            subscriber2 = subscriber;
        } else {
            subscriber2 = new SerializedSubscriber<>(subscriber);
        }
        Worker createWorker = this.scheduler.createWorker();
        Flowable flowable = this.source;
        DelaySubscriber delaySubscriber = new DelaySubscriber(subscriber2, this.delay, this.unit, createWorker, this.delayError);
        flowable.subscribe((FlowableSubscriber<? super T>) delaySubscriber);
    }
}
