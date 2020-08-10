package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.fuseable.FuseToFlowable;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableCountSingle */
public final class FlowableCountSingle<T> extends Single<Long> implements FuseToFlowable<Long> {
    final Flowable<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableCountSingle$CountSubscriber */
    static final class CountSubscriber implements FlowableSubscriber<Object>, Disposable {
        final SingleObserver<? super Long> actual;
        long count;

        /* renamed from: s */
        Subscription f3841s;

        CountSubscriber(SingleObserver<? super Long> singleObserver) {
            this.actual = singleObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3841s, subscription)) {
                this.f3841s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            this.count++;
        }

        public void onError(Throwable th) {
            this.f3841s = SubscriptionHelper.CANCELLED;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f3841s = SubscriptionHelper.CANCELLED;
            this.actual.onSuccess(Long.valueOf(this.count));
        }

        public void dispose() {
            this.f3841s.cancel();
            this.f3841s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f3841s == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableCountSingle(Flowable<T> flowable) {
        this.source = flowable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Long> singleObserver) {
        this.source.subscribe((FlowableSubscriber<? super T>) new CountSubscriber<Object>(singleObserver));
    }

    public Flowable<Long> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableCount<T>(this.source));
    }
}
