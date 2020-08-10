package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.MissingBackpressureException;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BackpressureHelper;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableSamplePublisher */
public final class FlowableSamplePublisher<T> extends Flowable<T> {
    final boolean emitLast;
    final Publisher<?> other;
    final Publisher<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSamplePublisher$SampleMainEmitLast */
    static final class SampleMainEmitLast<T> extends SamplePublisherSubscriber<T> {
        private static final long serialVersionUID = -3029755663834015785L;
        volatile boolean done;
        final AtomicInteger wip = new AtomicInteger();

        SampleMainEmitLast(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            super(subscriber, publisher);
        }

        /* access modifiers changed from: 0000 */
        public void completeMain() {
            this.done = true;
            if (this.wip.getAndIncrement() == 0) {
                emit();
                this.actual.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void completeOther() {
            this.done = true;
            if (this.wip.getAndIncrement() == 0) {
                emit();
                this.actual.onComplete();
            }
        }

        /* access modifiers changed from: 0000 */
        public void run() {
            if (this.wip.getAndIncrement() == 0) {
                do {
                    boolean z = this.done;
                    emit();
                    if (z) {
                        this.actual.onComplete();
                        return;
                    }
                } while (this.wip.decrementAndGet() != 0);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSamplePublisher$SampleMainNoLast */
    static final class SampleMainNoLast<T> extends SamplePublisherSubscriber<T> {
        private static final long serialVersionUID = -3029755663834015785L;

        SampleMainNoLast(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            super(subscriber, publisher);
        }

        /* access modifiers changed from: 0000 */
        public void completeMain() {
            this.actual.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void completeOther() {
            this.actual.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void run() {
            emit();
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSamplePublisher$SamplePublisherSubscriber */
    static abstract class SamplePublisherSubscriber<T> extends AtomicReference<T> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -3517602651313910099L;
        final Subscriber<? super T> actual;
        final AtomicReference<Subscription> other = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        Subscription f3893s;
        final Publisher<?> sampler;

        /* access modifiers changed from: 0000 */
        public abstract void completeMain();

        /* access modifiers changed from: 0000 */
        public abstract void completeOther();

        /* access modifiers changed from: 0000 */
        public abstract void run();

        SamplePublisherSubscriber(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            this.actual = subscriber;
            this.sampler = publisher;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3893s, subscription)) {
                this.f3893s = subscription;
                this.actual.onSubscribe(this);
                if (this.other.get() == null) {
                    this.sampler.subscribe(new SamplerSubscriber(this));
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        public void onNext(T t) {
            lazySet(t);
        }

        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.other);
            this.actual.onError(th);
        }

        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            completeMain();
        }

        /* access modifiers changed from: 0000 */
        public boolean setOther(Subscription subscription) {
            return SubscriptionHelper.setOnce(this.other, subscription);
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        public void cancel() {
            SubscriptionHelper.cancel(this.other);
            this.f3893s.cancel();
        }

        public void error(Throwable th) {
            this.f3893s.cancel();
            this.actual.onError(th);
        }

        public void complete() {
            this.f3893s.cancel();
            completeOther();
        }

        /* access modifiers changed from: 0000 */
        public void emit() {
            Object andSet = getAndSet(null);
            if (andSet == null) {
                return;
            }
            if (this.requested.get() != 0) {
                this.actual.onNext(andSet);
                BackpressureHelper.produced(this.requested, 1);
                return;
            }
            cancel();
            this.actual.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSamplePublisher$SamplerSubscriber */
    static final class SamplerSubscriber<T> implements FlowableSubscriber<Object> {
        final SamplePublisherSubscriber<T> parent;

        SamplerSubscriber(SamplePublisherSubscriber<T> samplePublisherSubscriber) {
            this.parent = samplePublisherSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (this.parent.setOther(subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            this.parent.run();
        }

        public void onError(Throwable th) {
            this.parent.error(th);
        }

        public void onComplete() {
            this.parent.complete();
        }
    }

    public FlowableSamplePublisher(Publisher<T> publisher, Publisher<?> publisher2, boolean z) {
        this.source = publisher;
        this.other = publisher2;
        this.emitLast = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.emitLast) {
            this.source.subscribe(new SampleMainEmitLast(serializedSubscriber, this.other));
        } else {
            this.source.subscribe(new SampleMainNoLast(serializedSubscriber, this.other));
        }
    }
}
