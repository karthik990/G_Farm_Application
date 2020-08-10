package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiFunction;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.fuseable.SimplePlainQueue;
import p043io.reactivex.internal.queue.SpscArrayQueue;
import p043io.reactivex.internal.subscriptions.EmptySubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BackpressureHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableScanSeed */
public final class FlowableScanSeed<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final BiFunction<R, ? super T, R> accumulator;
    final Callable<R> seedSupplier;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableScanSeed$ScanSeedSubscriber */
    static final class ScanSeedSubscriber<T, R> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -1776795561228106469L;
        final BiFunction<R, ? super T, R> accumulator;
        final Subscriber<? super R> actual;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        Throwable error;
        final int limit;
        final int prefetch;
        final SimplePlainQueue<R> queue;
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        Subscription f3896s;
        R value;

        ScanSeedSubscriber(Subscriber<? super R> subscriber, BiFunction<R, ? super T, R> biFunction, R r, int i) {
            this.actual = subscriber;
            this.accumulator = biFunction;
            this.value = r;
            this.prefetch = i;
            this.limit = i - (i >> 2);
            this.queue = new SpscArrayQueue(i);
            this.queue.offer(r);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3896s, subscription)) {
                this.f3896s = subscription;
                this.actual.onSubscribe(this);
                subscription.request((long) (this.prefetch - 1));
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    R requireNonNull = ObjectHelper.requireNonNull(this.accumulator.apply(this.value, t), "The accumulator returned a null value");
                    this.value = requireNonNull;
                    this.queue.offer(requireNonNull);
                    drain();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f3896s.cancel();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            drain();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                drain();
            }
        }

        public void cancel() {
            this.cancelled = true;
            this.f3896s.cancel();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                drain();
            }
        }

        /* access modifiers changed from: 0000 */
        public void drain() {
            if (getAndIncrement() == 0) {
                Subscriber<? super R> subscriber = this.actual;
                SimplePlainQueue<R> simplePlainQueue = this.queue;
                int i = this.limit;
                int i2 = this.consumed;
                int i3 = 1;
                do {
                    long j = this.requested.get();
                    long j2 = 0;
                    while (j2 != j) {
                        if (this.cancelled) {
                            simplePlainQueue.clear();
                            return;
                        }
                        boolean z = this.done;
                        if (z) {
                            Throwable th = this.error;
                            if (th != null) {
                                simplePlainQueue.clear();
                                subscriber.onError(th);
                                return;
                            }
                        }
                        Object poll = simplePlainQueue.poll();
                        boolean z2 = poll == null;
                        if (z && z2) {
                            subscriber.onComplete();
                            return;
                        } else if (z2) {
                            break;
                        } else {
                            subscriber.onNext(poll);
                            j2++;
                            i2++;
                            if (i2 == i) {
                                this.f3896s.request((long) i);
                                i2 = 0;
                            }
                        }
                    }
                    if (j2 == j && this.done) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            simplePlainQueue.clear();
                            subscriber.onError(th2);
                            return;
                        } else if (simplePlainQueue.isEmpty()) {
                            subscriber.onComplete();
                            return;
                        }
                    }
                    if (j2 != 0) {
                        BackpressureHelper.produced(this.requested, j2);
                    }
                    this.consumed = i2;
                    i3 = addAndGet(-i3);
                } while (i3 != 0);
            }
        }
    }

    public FlowableScanSeed(Flowable<T> flowable, Callable<R> callable, BiFunction<R, ? super T, R> biFunction) {
        super(flowable);
        this.accumulator = biFunction;
        this.seedSupplier = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super R> subscriber) {
        try {
            this.source.subscribe((FlowableSubscriber<? super T>) new ScanSeedSubscriber<Object>(subscriber, this.accumulator, ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seed supplied is null"), bufferSize()));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}