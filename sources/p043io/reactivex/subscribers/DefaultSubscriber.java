package p043io.reactivex.subscribers;

import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.subscribers.DefaultSubscriber */
public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {

    /* renamed from: s */
    private Subscription f4189s;

    public final void onSubscribe(Subscription subscription) {
        if (EndConsumerHelper.validate(this.f4189s, subscription, getClass())) {
            this.f4189s = subscription;
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public final void request(long j) {
        Subscription subscription = this.f4189s;
        if (subscription != null) {
            subscription.request(j);
        }
    }

    /* access modifiers changed from: protected */
    public final void cancel() {
        Subscription subscription = this.f4189s;
        this.f4189s = SubscriptionHelper.CANCELLED;
        subscription.cancel();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        request(Long.MAX_VALUE);
    }
}
