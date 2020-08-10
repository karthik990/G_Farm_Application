package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.fuseable.QueueSubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableIgnoreElements */
public final class FlowableIgnoreElements<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableIgnoreElements$IgnoreElementsSubscriber */
    static final class IgnoreElementsSubscriber<T> implements FlowableSubscriber<T>, QueueSubscription<T> {
        final Subscriber<? super T> actual;

        /* renamed from: s */
        Subscription f3871s;

        public void clear() {
        }

        public boolean isEmpty() {
            return true;
        }

        public void onNext(T t) {
        }

        public T poll() {
            return null;
        }

        public void request(long j) {
        }

        public int requestFusion(int i) {
            return i & 2;
        }

        IgnoreElementsSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3871s, subscription)) {
                this.f3871s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public boolean offer(T t) {
            throw new UnsupportedOperationException("Should not be called!");
        }

        public boolean offer(T t, T t2) {
            throw new UnsupportedOperationException("Should not be called!");
        }

        public void cancel() {
            this.f3871s.cancel();
        }
    }

    public FlowableIgnoreElements(Flowable<T> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new IgnoreElementsSubscriber<Object>(subscriber));
    }
}
