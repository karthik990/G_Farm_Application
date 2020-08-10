package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiFunction;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableReduce */
public final class FlowableReduce<T> extends AbstractFlowableWithUpstream<T, T> {
    final BiFunction<T, T, T> reducer;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReduce$ReduceSubscriber */
    static final class ReduceSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -4663883003264602070L;
        final BiFunction<T, T, T> reducer;

        /* renamed from: s */
        Subscription f3885s;

        ReduceSubscriber(Subscriber<? super T> subscriber, BiFunction<T, T, T> biFunction) {
            super(subscriber);
            this.reducer = biFunction;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3885s, subscription)) {
                this.f3885s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (this.f3885s != SubscriptionHelper.CANCELLED) {
                Object obj = this.value;
                if (obj == null) {
                    this.value = t;
                } else {
                    try {
                        this.value = ObjectHelper.requireNonNull(this.reducer.apply(obj, t), "The reducer returned a null value");
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f3885s.cancel();
                        onError(th);
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (this.f3885s == SubscriptionHelper.CANCELLED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.f3885s = SubscriptionHelper.CANCELLED;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (this.f3885s != SubscriptionHelper.CANCELLED) {
                this.f3885s = SubscriptionHelper.CANCELLED;
                Object obj = this.value;
                if (obj != null) {
                    complete(obj);
                } else {
                    this.actual.onComplete();
                }
            }
        }

        public void cancel() {
            super.cancel();
            this.f3885s.cancel();
            this.f3885s = SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableReduce(Flowable<T> flowable, BiFunction<T, T, T> biFunction) {
        super(flowable);
        this.reducer = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new ReduceSubscriber<Object>(subscriber, this.reducer));
    }
}
