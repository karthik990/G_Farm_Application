package p043io.reactivex.internal.operators.flowable;

import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import p043io.reactivex.internal.subscriptions.EmptySubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableToList */
public final class FlowableToList<T, U extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, U> {
    final Callable<U> collectionSupplier;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableToList$ToListSubscriber */
    static final class ToListSubscriber<T, U extends Collection<? super T>> extends DeferredScalarSubscription<U> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -8134157938864266736L;

        /* renamed from: s */
        Subscription f3926s;

        ToListSubscriber(Subscriber<? super U> subscriber, U u) {
            super(subscriber);
            this.value = u;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3926s, subscription)) {
                this.f3926s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            Collection collection = (Collection) this.value;
            if (collection != null) {
                collection.add(t);
            }
        }

        public void onError(Throwable th) {
            this.value = null;
            this.actual.onError(th);
        }

        public void onComplete() {
            complete(this.value);
        }

        public void cancel() {
            super.cancel();
            this.f3926s.cancel();
        }
    }

    public FlowableToList(Flowable<T> flowable, Callable<U> callable) {
        super(flowable);
        this.collectionSupplier = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber<? super T>) new ToListSubscriber<Object>(subscriber, (Collection) ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.")));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
