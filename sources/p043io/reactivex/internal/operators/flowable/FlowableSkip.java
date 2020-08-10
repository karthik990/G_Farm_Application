package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableSkip */
public final class FlowableSkip<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: n */
    final long f3904n;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSkip$SkipSubscriber */
    static final class SkipSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;
        long remaining;

        /* renamed from: s */
        Subscription f3905s;

        SkipSubscriber(Subscriber<? super T> subscriber, long j) {
            this.actual = subscriber;
            this.remaining = j;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3905s, subscription)) {
                long j = this.remaining;
                this.f3905s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(j);
            }
        }

        public void onNext(T t) {
            long j = this.remaining;
            if (j != 0) {
                this.remaining = j - 1;
            } else {
                this.actual.onNext(t);
            }
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void request(long j) {
            this.f3905s.request(j);
        }

        public void cancel() {
            this.f3905s.cancel();
        }
    }

    public FlowableSkip(Flowable<T> flowable, long j) {
        super(flowable);
        this.f3904n = j;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new SkipSubscriber<Object>(subscriber, this.f3904n));
    }
}
