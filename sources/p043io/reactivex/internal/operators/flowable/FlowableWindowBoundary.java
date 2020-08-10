package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.MissingBackpressureException;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.SimplePlainQueue;
import p043io.reactivex.internal.queue.MpscLinkedQueue;
import p043io.reactivex.internal.subscribers.QueueDrainSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.NotificationLite;
import p043io.reactivex.plugins.RxJavaPlugins;
import p043io.reactivex.processors.UnicastProcessor;
import p043io.reactivex.subscribers.DisposableSubscriber;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowBoundary */
public final class FlowableWindowBoundary<T, B> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final Publisher<B> other;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowBoundary$WindowBoundaryInnerSubscriber */
    static final class WindowBoundaryInnerSubscriber<T, B> extends DisposableSubscriber<B> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, B> parent;

        WindowBoundaryInnerSubscriber(WindowBoundaryMainSubscriber<T, B> windowBoundaryMainSubscriber) {
            this.parent = windowBoundaryMainSubscriber;
        }

        public void onNext(B b) {
            if (!this.done) {
                this.parent.next();
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
                this.parent.onComplete();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindowBoundary$WindowBoundaryMainSubscriber */
    static final class WindowBoundaryMainSubscriber<T, B> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        static final Object NEXT = new Object();
        final AtomicReference<Disposable> boundary = new AtomicReference<>();
        final int bufferSize;
        final Publisher<B> other;

        /* renamed from: s */
        Subscription f3933s;
        UnicastProcessor<T> window;
        final AtomicLong windows = new AtomicLong();

        public boolean accept(Subscriber<? super Flowable<T>> subscriber, Object obj) {
            return false;
        }

        WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> subscriber, Publisher<B> publisher, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.other = publisher;
            this.bufferSize = i;
            this.windows.lazySet(1);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3933s, subscription)) {
                this.f3933s = subscription;
                Subscriber subscriber = this.actual;
                subscriber.onSubscribe(this);
                if (!this.cancelled) {
                    UnicastProcessor<T> create = UnicastProcessor.create(this.bufferSize);
                    long requested = requested();
                    if (requested != 0) {
                        subscriber.onNext(create);
                        if (requested != Long.MAX_VALUE) {
                            produced(1);
                        }
                        this.window = create;
                        WindowBoundaryInnerSubscriber windowBoundaryInnerSubscriber = new WindowBoundaryInnerSubscriber(this);
                        if (this.boundary.compareAndSet(null, windowBoundaryInnerSubscriber)) {
                            this.windows.getAndIncrement();
                            subscription.request(Long.MAX_VALUE);
                            this.other.subscribe(windowBoundaryInnerSubscriber);
                        }
                    } else {
                        subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests"));
                    }
                }
            }
        }

        public void onNext(T t) {
            if (fastEnter()) {
                this.window.onNext(t);
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                DisposableHelper.dispose(this.boundary);
            }
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                if (enter()) {
                    drainLoop();
                }
                if (this.windows.decrementAndGet() == 0) {
                    DisposableHelper.dispose(this.boundary);
                }
                this.actual.onComplete();
            }
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            this.cancelled = true;
        }

        /* access modifiers changed from: 0000 */
        public void drainLoop() {
            SimplePlainQueue simplePlainQueue = this.queue;
            Subscriber subscriber = this.actual;
            UnicastProcessor<T> unicastProcessor = this.window;
            int i = 1;
            while (true) {
                boolean z = this.done;
                Object poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    DisposableHelper.dispose(this.boundary);
                    Throwable th = this.error;
                    if (th != null) {
                        unicastProcessor.onError(th);
                    } else {
                        unicastProcessor.onComplete();
                    }
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (poll == NEXT) {
                    unicastProcessor.onComplete();
                    if (this.windows.decrementAndGet() == 0) {
                        DisposableHelper.dispose(this.boundary);
                        return;
                    } else if (!this.cancelled) {
                        unicastProcessor = UnicastProcessor.create(this.bufferSize);
                        long requested = requested();
                        if (requested != 0) {
                            this.windows.getAndIncrement();
                            subscriber.onNext(unicastProcessor);
                            if (requested != Long.MAX_VALUE) {
                                produced(1);
                            }
                            this.window = unicastProcessor;
                        } else {
                            this.cancelled = true;
                            subscriber.onError(new MissingBackpressureException("Could not deliver new window due to lack of requests"));
                        }
                    }
                } else {
                    unicastProcessor.onNext(NotificationLite.getValue(poll));
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void next() {
            this.queue.offer(NEXT);
            if (enter()) {
                drainLoop();
            }
        }
    }

    public FlowableWindowBoundary(Flowable<T> flowable, Publisher<B> publisher, int i) {
        super(flowable);
        this.other = publisher;
        this.bufferSize = i;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new WindowBoundaryMainSubscriber<Object>(new SerializedSubscriber(subscriber), this.other, this.bufferSize));
    }
}
