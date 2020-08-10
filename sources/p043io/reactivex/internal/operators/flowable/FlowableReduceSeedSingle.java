package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiFunction;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableReduceSeedSingle */
public final class FlowableReduceSeedSingle<T, R> extends Single<R> {
    final BiFunction<R, ? super T, R> reducer;
    final R seed;
    final Publisher<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReduceSeedSingle$ReduceSeedObserver */
    static final class ReduceSeedObserver<T, R> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super R> actual;
        final BiFunction<R, ? super T, R> reducer;

        /* renamed from: s */
        Subscription f3887s;
        R value;

        ReduceSeedObserver(SingleObserver<? super R> singleObserver, BiFunction<R, ? super T, R> biFunction, R r) {
            this.actual = singleObserver;
            this.value = r;
            this.reducer = biFunction;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3887s, subscription)) {
                this.f3887s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            try {
                this.value = ObjectHelper.requireNonNull(this.reducer.apply(this.value, t), "The reducer returned a null value");
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.f3887s.cancel();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.value = null;
            this.f3887s = SubscriptionHelper.CANCELLED;
            this.actual.onError(th);
        }

        public void onComplete() {
            R r = this.value;
            this.value = null;
            this.f3887s = SubscriptionHelper.CANCELLED;
            this.actual.onSuccess(r);
        }

        public void dispose() {
            this.f3887s.cancel();
            this.f3887s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f3887s == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableReduceSeedSingle(Publisher<T> publisher, R r, BiFunction<R, ? super T, R> biFunction) {
        this.source = publisher;
        this.seed = r;
        this.reducer = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.source.subscribe(new ReduceSeedObserver(singleObserver, this.reducer, this.seed));
    }
}
