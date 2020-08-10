package p043io.reactivex.internal.subscribers;

import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.FullArbiter;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.subscribers.FullArbiterSubscriber */
public final class FullArbiterSubscriber<T> implements FlowableSubscriber<T> {
    final FullArbiter<T> arbiter;

    /* renamed from: s */
    Subscription f4150s;

    public FullArbiterSubscriber(FullArbiter<T> fullArbiter) {
        this.arbiter = fullArbiter;
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.f4150s, subscription)) {
            this.f4150s = subscription;
            this.arbiter.setSubscription(subscription);
        }
    }

    public void onNext(T t) {
        this.arbiter.onNext(t, this.f4150s);
    }

    public void onError(Throwable th) {
        this.arbiter.onError(th, this.f4150s);
    }

    public void onComplete() {
        this.arbiter.onComplete(this.f4150s);
    }
}
