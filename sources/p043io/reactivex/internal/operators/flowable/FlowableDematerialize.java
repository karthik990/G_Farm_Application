package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Notification;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDematerialize */
public final class FlowableDematerialize<T> extends AbstractFlowableWithUpstream<Notification<T>, T> {

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDematerialize$DematerializeSubscriber */
    static final class DematerializeSubscriber<T> implements FlowableSubscriber<Notification<T>>, Subscription {
        final Subscriber<? super T> actual;
        boolean done;

        /* renamed from: s */
        Subscription f3849s;

        DematerializeSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3849s, subscription)) {
                this.f3849s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(Notification<T> notification) {
            if (this.done) {
                if (notification.isOnError()) {
                    RxJavaPlugins.onError(notification.getError());
                }
                return;
            }
            if (notification.isOnError()) {
                this.f3849s.cancel();
                onError(notification.getError());
            } else if (notification.isOnComplete()) {
                this.f3849s.cancel();
                onComplete();
            } else {
                this.actual.onNext(notification.getValue());
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }

        public void request(long j) {
            this.f3849s.request(j);
        }

        public void cancel() {
            this.f3849s.cancel();
        }
    }

    public FlowableDematerialize(Flowable<Notification<T>> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new DematerializeSubscriber<Object>(subscriber));
    }
}
