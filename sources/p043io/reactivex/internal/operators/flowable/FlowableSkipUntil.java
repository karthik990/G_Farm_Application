package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.fuseable.ConditionalSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.AtomicThrowable;
import p043io.reactivex.internal.util.HalfSerializer;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableSkipUntil */
public final class FlowableSkipUntil<T, U> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<U> other;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSkipUntil$SkipUntilMainSubscriber */
    static final class SkipUntilMainSubscriber<T> extends AtomicInteger implements ConditionalSubscriber<T>, Subscription {
        private static final long serialVersionUID = -6270983465606289181L;
        final Subscriber<? super T> actual;
        final AtomicThrowable error = new AtomicThrowable();
        volatile boolean gate;
        final OtherSubscriber other = new OtherSubscriber<>();
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        final AtomicReference<Subscription> f3908s = new AtomicReference<>();

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableSkipUntil$SkipUntilMainSubscriber$OtherSubscriber */
        final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -5592042965931999169L;

            OtherSubscriber() {
            }

            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }

            public void onNext(Object obj) {
                SkipUntilMainSubscriber.this.gate = true;
                ((Subscription) get()).cancel();
            }

            public void onError(Throwable th) {
                SubscriptionHelper.cancel(SkipUntilMainSubscriber.this.f3908s);
                Subscriber<? super T> subscriber = SkipUntilMainSubscriber.this.actual;
                SkipUntilMainSubscriber skipUntilMainSubscriber = SkipUntilMainSubscriber.this;
                HalfSerializer.onError(subscriber, th, (AtomicInteger) skipUntilMainSubscriber, skipUntilMainSubscriber.error);
            }

            public void onComplete() {
                SkipUntilMainSubscriber.this.gate = true;
            }
        }

        SkipUntilMainSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.f3908s, this.requested, subscription);
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                ((Subscription) this.f3908s.get()).request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (!this.gate) {
                return false;
            }
            HalfSerializer.onNext(this.actual, t, (AtomicInteger) this, this.error);
            return true;
        }

        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.other);
            HalfSerializer.onError(this.actual, th, (AtomicInteger) this, this.error);
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            HalfSerializer.onComplete(this.actual, (AtomicInteger) this, this.error);
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.f3908s, this.requested, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.f3908s);
            SubscriptionHelper.cancel(this.other);
        }
    }

    public FlowableSkipUntil(Flowable<T> flowable, Publisher<U> publisher) {
        super(flowable);
        this.other = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SkipUntilMainSubscriber skipUntilMainSubscriber = new SkipUntilMainSubscriber(subscriber);
        subscriber.onSubscribe(skipUntilMainSubscriber);
        this.other.subscribe(skipUntilMainSubscriber.other);
        this.source.subscribe((FlowableSubscriber<? super T>) skipUntilMainSubscriber);
    }
}
