package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeLastOne */
public final class FlowableTakeLastOne<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeLastOne$TakeLastOneSubscriber */
    static final class TakeLastOneSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -5467847744262967226L;

        /* renamed from: s */
        Subscription f3915s;

        TakeLastOneSubscriber(Subscriber<? super T> subscriber) {
            super(subscriber);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3915s, subscription)) {
                this.f3915s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            this.value = t;
        }

        public void onError(Throwable th) {
            this.value = null;
            this.actual.onError(th);
        }

        public void onComplete() {
            Object obj = this.value;
            if (obj != null) {
                complete(obj);
            } else {
                this.actual.onComplete();
            }
        }

        public void cancel() {
            super.cancel();
            this.f3915s.cancel();
        }
    }

    public FlowableTakeLastOne(Flowable<T> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new TakeLastOneSubscriber<Object>(subscriber));
    }
}
