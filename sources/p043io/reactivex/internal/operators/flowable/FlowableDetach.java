package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.EmptyComponent;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDetach */
public final class FlowableDetach<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDetach$DetachSubscriber */
    static final class DetachSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        Subscriber<? super T> actual;

        /* renamed from: s */
        Subscription f3850s;

        DetachSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        public void request(long j) {
            this.f3850s.request(j);
        }

        public void cancel() {
            Subscription subscription = this.f3850s;
            this.f3850s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asSubscriber();
            subscription.cancel();
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3850s, subscription)) {
                this.f3850s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            Subscriber<? super T> subscriber = this.actual;
            this.f3850s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asSubscriber();
            subscriber.onError(th);
        }

        public void onComplete() {
            Subscriber<? super T> subscriber = this.actual;
            this.f3850s = EmptyComponent.INSTANCE;
            this.actual = EmptyComponent.asSubscriber();
            subscriber.onComplete();
        }
    }

    public FlowableDetach(Flowable<T> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new DetachSubscriber<Object>(subscriber));
    }
}
