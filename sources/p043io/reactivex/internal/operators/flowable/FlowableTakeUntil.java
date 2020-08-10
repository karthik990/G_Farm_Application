package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.AtomicThrowable;
import p043io.reactivex.internal.util.HalfSerializer;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeUntil */
public final class FlowableTakeUntil<T, U> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<? extends U> other;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeUntil$TakeUntilMainSubscriber */
    static final class TakeUntilMainSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4945480365982832967L;
        final Subscriber<? super T> actual;
        final AtomicThrowable error = new AtomicThrowable();
        final OtherSubscriber other = new OtherSubscriber<>();
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        final AtomicReference<Subscription> f3917s = new AtomicReference<>();

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeUntil$TakeUntilMainSubscriber$OtherSubscriber */
        final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -3592821756711087922L;

            OtherSubscriber() {
            }

            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }

            public void onNext(Object obj) {
                SubscriptionHelper.cancel(this);
                onComplete();
            }

            public void onError(Throwable th) {
                SubscriptionHelper.cancel(TakeUntilMainSubscriber.this.f3917s);
                Subscriber<? super T> subscriber = TakeUntilMainSubscriber.this.actual;
                TakeUntilMainSubscriber takeUntilMainSubscriber = TakeUntilMainSubscriber.this;
                HalfSerializer.onError(subscriber, th, (AtomicInteger) takeUntilMainSubscriber, takeUntilMainSubscriber.error);
            }

            public void onComplete() {
                SubscriptionHelper.cancel(TakeUntilMainSubscriber.this.f3917s);
                Subscriber<? super T> subscriber = TakeUntilMainSubscriber.this.actual;
                TakeUntilMainSubscriber takeUntilMainSubscriber = TakeUntilMainSubscriber.this;
                HalfSerializer.onComplete(subscriber, (AtomicInteger) takeUntilMainSubscriber, takeUntilMainSubscriber.error);
            }
        }

        TakeUntilMainSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.f3917s, this.requested, subscription);
        }

        public void onNext(T t) {
            HalfSerializer.onNext(this.actual, t, (AtomicInteger) this, this.error);
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
            SubscriptionHelper.deferredRequest(this.f3917s, this.requested, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.f3917s);
            SubscriptionHelper.cancel(this.other);
        }
    }

    public FlowableTakeUntil(Flowable<T> flowable, Publisher<? extends U> publisher) {
        super(flowable);
        this.other = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        TakeUntilMainSubscriber takeUntilMainSubscriber = new TakeUntilMainSubscriber(subscriber);
        subscriber.onSubscribe(takeUntilMainSubscriber);
        this.other.subscribe(takeUntilMainSubscriber.other);
        this.source.subscribe((FlowableSubscriber<? super T>) takeUntilMainSubscriber);
    }
}
