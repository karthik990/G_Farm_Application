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

/* renamed from: io.reactivex.internal.operators.flowable.FlowableSingleMaybe */
public final class FlowableSingleMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
    final Flowable<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSingleMaybe$SingleElementSubscriber */
    static final class SingleElementSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> actual;
        boolean done;

        /* renamed from: s */
        Subscription f3902s;
        T value;

        SingleElementSubscriber(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3902s, subscription)) {
                this.f3902s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.value != null) {
                    this.done = true;
                    this.f3902s.cancel();
                    this.f3902s = SubscriptionHelper.CANCELLED;
                    this.actual.onError(new IllegalArgumentException("Sequence contains more than one element!"));
                    return;
                }
                this.value = t;
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.f3902s = SubscriptionHelper.CANCELLED;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.f3902s = SubscriptionHelper.CANCELLED;
                T t = this.value;
                this.value = null;
                if (t == null) {
                    this.actual.onComplete();
                } else {
                    this.actual.onSuccess(t);
                }
            }
        }

        public void dispose() {
            this.f3902s.cancel();
            this.f3902s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f3902s == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableSingleMaybe(Flowable<T> flowable) {
        this.source = flowable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe((FlowableSubscriber<? super T>) new SingleElementSubscriber<Object>(maybeObserver));
    }

    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableSingle<T>(this.source, null));
    }
}
