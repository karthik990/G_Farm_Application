package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscribers.FullArbiterSubscriber;
import p043io.reactivex.internal.subscriptions.FullArbiter;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;
import p043io.reactivex.subscribers.DisposableSubscriber;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeout */
public final class FlowableTimeout<T, U, V> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<U> firstTimeoutIndicator;
    final Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator;
    final Publisher<? extends T> other;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeout$OnTimeout */
    interface OnTimeout {
        void onError(Throwable th);

        void timeout(long j);
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeout$TimeoutInnerSubscriber */
    static final class TimeoutInnerSubscriber<T, U, V> extends DisposableSubscriber<Object> {
        boolean done;
        final long index;
        final OnTimeout parent;

        TimeoutInnerSubscriber(OnTimeout onTimeout, long j) {
            this.parent = onTimeout;
            this.index = j;
        }

        public void onNext(Object obj) {
            if (!this.done) {
                this.done = true;
                cancel();
                this.parent.timeout(this.index);
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.parent.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.timeout(this.index);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeout$TimeoutOtherSubscriber */
    static final class TimeoutOtherSubscriber<T, U, V> implements FlowableSubscriber<T>, Disposable, OnTimeout {
        final Subscriber<? super T> actual;
        final FullArbiter<T> arbiter;
        volatile boolean cancelled;
        boolean done;
        final Publisher<U> firstTimeoutIndicator;
        volatile long index;
        final Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator;
        final Publisher<? extends T> other;

        /* renamed from: s */
        Subscription f3922s;
        final AtomicReference<Disposable> timeout = new AtomicReference<>();

        TimeoutOtherSubscriber(Subscriber<? super T> subscriber, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
            this.actual = subscriber;
            this.firstTimeoutIndicator = publisher;
            this.itemTimeoutIndicator = function;
            this.other = publisher2;
            this.arbiter = new FullArbiter<>(subscriber, this, 8);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3922s, subscription)) {
                this.f3922s = subscription;
                if (this.arbiter.setSubscription(subscription)) {
                    Subscriber<? super T> subscriber = this.actual;
                    Publisher<U> publisher = this.firstTimeoutIndicator;
                    if (publisher != null) {
                        TimeoutInnerSubscriber timeoutInnerSubscriber = new TimeoutInnerSubscriber(this, 0);
                        if (this.timeout.compareAndSet(null, timeoutInnerSubscriber)) {
                            subscriber.onSubscribe(this.arbiter);
                            publisher.subscribe(timeoutInnerSubscriber);
                        }
                    } else {
                        subscriber.onSubscribe(this.arbiter);
                    }
                }
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long j = this.index + 1;
                this.index = j;
                if (this.arbiter.onNext(t, this.f3922s)) {
                    Disposable disposable = (Disposable) this.timeout.get();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    try {
                        Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(t), "The publisher returned is null");
                        TimeoutInnerSubscriber timeoutInnerSubscriber = new TimeoutInnerSubscriber(this, j);
                        if (this.timeout.compareAndSet(disposable, timeoutInnerSubscriber)) {
                            publisher.subscribe(timeoutInnerSubscriber);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.actual.onError(th);
                    }
                }
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            dispose();
            this.arbiter.onError(th, this.f3922s);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                dispose();
                this.arbiter.onComplete(this.f3922s);
            }
        }

        public void dispose() {
            this.cancelled = true;
            this.f3922s.cancel();
            DisposableHelper.dispose(this.timeout);
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        public void timeout(long j) {
            if (j == this.index) {
                dispose();
                this.other.subscribe(new FullArbiterSubscriber(this.arbiter));
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTimeout$TimeoutSubscriber */
    static final class TimeoutSubscriber<T, U, V> implements FlowableSubscriber<T>, Subscription, OnTimeout {
        final Subscriber<? super T> actual;
        volatile boolean cancelled;
        final Publisher<U> firstTimeoutIndicator;
        volatile long index;
        final Function<? super T, ? extends Publisher<V>> itemTimeoutIndicator;

        /* renamed from: s */
        Subscription f3923s;
        final AtomicReference<Disposable> timeout = new AtomicReference<>();

        TimeoutSubscriber(Subscriber<? super T> subscriber, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function) {
            this.actual = subscriber;
            this.firstTimeoutIndicator = publisher;
            this.itemTimeoutIndicator = function;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3923s, subscription)) {
                this.f3923s = subscription;
                if (!this.cancelled) {
                    Subscriber<? super T> subscriber = this.actual;
                    Publisher<U> publisher = this.firstTimeoutIndicator;
                    if (publisher != null) {
                        TimeoutInnerSubscriber timeoutInnerSubscriber = new TimeoutInnerSubscriber(this, 0);
                        if (this.timeout.compareAndSet(null, timeoutInnerSubscriber)) {
                            subscriber.onSubscribe(this);
                            publisher.subscribe(timeoutInnerSubscriber);
                        }
                    } else {
                        subscriber.onSubscribe(this);
                    }
                }
            }
        }

        public void onNext(T t) {
            long j = this.index + 1;
            this.index = j;
            this.actual.onNext(t);
            Disposable disposable = (Disposable) this.timeout.get();
            if (disposable != null) {
                disposable.dispose();
            }
            try {
                Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.itemTimeoutIndicator.apply(t), "The publisher returned is null");
                TimeoutInnerSubscriber timeoutInnerSubscriber = new TimeoutInnerSubscriber(this, j);
                if (this.timeout.compareAndSet(disposable, timeoutInnerSubscriber)) {
                    publisher.subscribe(timeoutInnerSubscriber);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.actual.onError(th);
            }
        }

        public void onError(Throwable th) {
            cancel();
            this.actual.onError(th);
        }

        public void onComplete() {
            cancel();
            this.actual.onComplete();
        }

        public void request(long j) {
            this.f3923s.request(j);
        }

        public void cancel() {
            this.cancelled = true;
            this.f3923s.cancel();
            DisposableHelper.dispose(this.timeout);
        }

        public void timeout(long j) {
            if (j == this.index) {
                cancel();
                this.actual.onError(new TimeoutException());
            }
        }
    }

    public FlowableTimeout(Flowable<T> flowable, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
        super(flowable);
        this.firstTimeoutIndicator = publisher;
        this.itemTimeoutIndicator = function;
        this.other = publisher2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (this.other == null) {
            this.source.subscribe((FlowableSubscriber<? super T>) new TimeoutSubscriber<Object>(new SerializedSubscriber(subscriber), this.firstTimeoutIndicator, this.itemTimeoutIndicator));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new TimeoutOtherSubscriber<Object>(subscriber, this.firstTimeoutIndicator, this.itemTimeoutIndicator, this.other));
        }
    }
}
