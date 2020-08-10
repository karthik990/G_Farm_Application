package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableHide */
public final class FlowableHide<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableHide$HideSubscriber */
    static final class HideSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;

        /* renamed from: s */
        Subscription f3870s;

        HideSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        public void request(long j) {
            this.f3870s.request(j);
        }

        public void cancel() {
            this.f3870s.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3870s, subscription)) {
                this.f3870s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }

    public FlowableHide(Flowable<T> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new HideSubscriber<Object>(subscriber));
    }
}
