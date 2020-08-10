package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.fuseable.FuseToFlowable;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableElementAtMaybe */
public final class FlowableElementAtMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
    final long index;
    final Flowable<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableElementAtMaybe$ElementAtSubscriber */
    static final class ElementAtSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> actual;
        long count;
        boolean done;
        final long index;

        /* renamed from: s */
        Subscription f3857s;

        ElementAtSubscriber(MaybeObserver<? super T> maybeObserver, long j) {
            this.actual = maybeObserver;
            this.index = j;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3857s, subscription)) {
                this.f3857s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long j = this.count;
                if (j == this.index) {
                    this.done = true;
                    this.f3857s.cancel();
                    this.f3857s = SubscriptionHelper.CANCELLED;
                    this.actual.onSuccess(t);
                    return;
                }
                this.count = j + 1;
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.f3857s = SubscriptionHelper.CANCELLED;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f3857s = SubscriptionHelper.CANCELLED;
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }

        public void dispose() {
            this.f3857s.cancel();
            this.f3857s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f3857s == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableElementAtMaybe(Flowable<T> flowable, long j) {
        this.source = flowable;
        this.index = j;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe((FlowableSubscriber<? super T>) new ElementAtSubscriber<Object>(maybeObserver, this.index));
    }

    public Flowable<T> fuseToFlowable() {
        FlowableElementAt flowableElementAt = new FlowableElementAt(this.source, this.index, null, false);
        return RxJavaPlugins.onAssembly((Flowable<T>) flowableElementAt);
    }
}
