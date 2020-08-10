package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiFunction;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableWithLatestFrom */
public final class FlowableWithLatestFrom<T, U, R> extends AbstractFlowableWithUpstream<T, R> {
    final BiFunction<? super T, ? super U, ? extends R> combiner;
    final Publisher<? extends U> other;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWithLatestFrom$FlowableWithLatestSubscriber */
    final class FlowableWithLatestSubscriber implements FlowableSubscriber<U> {
        private final WithLatestFromSubscriber<T, U, R> wlf;

        public void onComplete() {
        }

        FlowableWithLatestSubscriber(WithLatestFromSubscriber<T, U, R> withLatestFromSubscriber) {
            this.wlf = withLatestFromSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (this.wlf.setOther(subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(U u) {
            this.wlf.lazySet(u);
        }

        public void onError(Throwable th) {
            this.wlf.otherError(th);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWithLatestFrom$WithLatestFromSubscriber */
    static final class WithLatestFromSubscriber<T, U, R> extends AtomicReference<U> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -312246233408980075L;
        final Subscriber<? super R> actual;
        final BiFunction<? super T, ? super U, ? extends R> combiner;
        final AtomicReference<Subscription> other = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        final AtomicReference<Subscription> f3943s = new AtomicReference<>();

        WithLatestFromSubscriber(Subscriber<? super R> subscriber, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.actual = subscriber;
            this.combiner = biFunction;
        }

        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.f3943s, this.requested, subscription);
        }

        public void onNext(T t) {
            Object obj = get();
            if (obj != null) {
                try {
                    this.actual.onNext(ObjectHelper.requireNonNull(this.combiner.apply(t, obj), "The combiner returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    this.actual.onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.other);
            this.actual.onError(th);
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            this.actual.onComplete();
        }

        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.f3943s, this.requested, j);
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.f3943s);
            SubscriptionHelper.cancel(this.other);
        }

        public boolean setOther(Subscription subscription) {
            return SubscriptionHelper.setOnce(this.other, subscription);
        }

        public void otherError(Throwable th) {
            SubscriptionHelper.cancel(this.f3943s);
            this.actual.onError(th);
        }
    }

    public FlowableWithLatestFrom(Flowable<T> flowable, BiFunction<? super T, ? super U, ? extends R> biFunction, Publisher<? extends U> publisher) {
        super(flowable);
        this.combiner = biFunction;
        this.other = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        WithLatestFromSubscriber withLatestFromSubscriber = new WithLatestFromSubscriber(serializedSubscriber, this.combiner);
        serializedSubscriber.onSubscribe(withLatestFromSubscriber);
        this.other.subscribe(new FlowableWithLatestSubscriber(withLatestFromSubscriber));
        this.source.subscribe((FlowableSubscriber<? super T>) withLatestFromSubscriber);
    }
}
