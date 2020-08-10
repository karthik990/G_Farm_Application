package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscription;
import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.fuseable.FuseToFlowable;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableIgnoreElementsCompletable */
public final class FlowableIgnoreElementsCompletable<T> extends Completable implements FuseToFlowable<T> {
    final Flowable<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableIgnoreElementsCompletable$IgnoreElementsSubscriber */
    static final class IgnoreElementsSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final CompletableObserver actual;

        /* renamed from: s */
        Subscription f3872s;

        public void onNext(T t) {
        }

        IgnoreElementsSubscriber(CompletableObserver completableObserver) {
            this.actual = completableObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3872s, subscription)) {
                this.f3872s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onError(Throwable th) {
            this.f3872s = SubscriptionHelper.CANCELLED;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f3872s = SubscriptionHelper.CANCELLED;
            this.actual.onComplete();
        }

        public void dispose() {
            this.f3872s.cancel();
            this.f3872s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f3872s == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableIgnoreElementsCompletable(Flowable<T> flowable) {
        this.source = flowable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe((FlowableSubscriber<? super T>) new IgnoreElementsSubscriber<Object>(completableObserver));
    }

    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableIgnoreElements<T>(this.source));
    }
}
