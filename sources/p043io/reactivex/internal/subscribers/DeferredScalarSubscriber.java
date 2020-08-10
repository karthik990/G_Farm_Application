package p043io.reactivex.internal.subscribers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.subscribers.DeferredScalarSubscriber */
public abstract class DeferredScalarSubscriber<T, R> extends DeferredScalarSubscription<R> implements FlowableSubscriber<T> {
    private static final long serialVersionUID = 2984505488220891551L;
    protected boolean hasValue;

    /* renamed from: s */
    protected Subscription f4149s;

    public DeferredScalarSubscriber(Subscriber<? super R> subscriber) {
        super(subscriber);
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.f4149s, subscription)) {
            this.f4149s = subscription;
            this.actual.onSubscribe(this);
            subscription.request(Long.MAX_VALUE);
        }
    }

    public void onError(Throwable th) {
        this.value = null;
        this.actual.onError(th);
    }

    public void onComplete() {
        if (this.hasValue) {
            complete(this.value);
        } else {
            this.actual.onComplete();
        }
    }

    public void cancel() {
        super.cancel();
        this.f4149s.cancel();
    }
}
