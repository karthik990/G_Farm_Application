package p043io.reactivex.internal.operators.parallel;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Scheduler.Worker;
import p043io.reactivex.exceptions.MissingBackpressureException;
import p043io.reactivex.internal.fuseable.ConditionalSubscriber;
import p043io.reactivex.internal.queue.SpscArrayQueue;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BackpressureHelper;
import p043io.reactivex.parallel.ParallelFlowable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.parallel.ParallelRunOn */
public final class ParallelRunOn<T> extends ParallelFlowable<T> {
    final int prefetch;
    final Scheduler scheduler;
    final ParallelFlowable<? extends T> source;

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelRunOn$BaseRunOnSubscriber */
    static abstract class BaseRunOnSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = 9222303586456402150L;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        Throwable error;
        final int limit;
        final int prefetch;
        final SpscArrayQueue<T> queue;
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        Subscription f4113s;
        final Worker worker;

        BaseRunOnSubscriber(int i, SpscArrayQueue<T> spscArrayQueue, Worker worker2) {
            this.prefetch = i;
            this.queue = spscArrayQueue;
            this.limit = i - (i >> 2);
            this.worker = worker2;
        }

        public final void onNext(T t) {
            if (!this.done) {
                if (!this.queue.offer(t)) {
                    this.f4113s.cancel();
                    onError(new MissingBackpressureException("Queue is full?!"));
                    return;
                }
                schedule();
            }
        }

        public final void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            schedule();
        }

        public final void onComplete() {
            if (!this.done) {
                this.done = true;
                schedule();
            }
        }

        public final void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                schedule();
            }
        }

        public final void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.f4113s.cancel();
                this.worker.dispose();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void schedule() {
            if (getAndIncrement() == 0) {
                this.worker.schedule(this);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelRunOn$RunOnConditionalSubscriber */
    static final class RunOnConditionalSubscriber<T> extends BaseRunOnSubscriber<T> {
        private static final long serialVersionUID = 1075119423897941642L;
        final ConditionalSubscriber<? super T> actual;

        RunOnConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, int i, SpscArrayQueue<T> spscArrayQueue, Worker worker) {
            super(i, spscArrayQueue, worker);
            this.actual = conditionalSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f4113s, subscription)) {
                this.f4113s = subscription;
                this.actual.onSubscribe(this);
                subscription.request((long) this.prefetch);
            }
        }

        public void run() {
            int i;
            int i2 = this.consumed;
            SpscArrayQueue spscArrayQueue = this.queue;
            ConditionalSubscriber<? super T> conditionalSubscriber = this.actual;
            int i3 = this.limit;
            int i4 = 1;
            while (true) {
                long j = this.requested.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.cancelled) {
                        spscArrayQueue.clear();
                        return;
                    }
                    boolean z = this.done;
                    if (z) {
                        Throwable th = this.error;
                        if (th != null) {
                            spscArrayQueue.clear();
                            conditionalSubscriber.onError(th);
                            this.worker.dispose();
                            return;
                        }
                    }
                    Object poll = spscArrayQueue.poll();
                    boolean z2 = poll == null;
                    if (z && z2) {
                        conditionalSubscriber.onComplete();
                        this.worker.dispose();
                        return;
                    } else if (z2) {
                        break;
                    } else {
                        if (conditionalSubscriber.tryOnNext(poll)) {
                            j2++;
                        }
                        i2++;
                        if (i2 == i3) {
                            i = i4;
                            this.f4113s.request((long) i2);
                            i2 = 0;
                        } else {
                            i = i4;
                        }
                        i4 = i;
                    }
                }
                int i5 = i4;
                if (j2 == j) {
                    if (this.cancelled) {
                        spscArrayQueue.clear();
                        return;
                    } else if (this.done) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            spscArrayQueue.clear();
                            conditionalSubscriber.onError(th2);
                            this.worker.dispose();
                            return;
                        } else if (spscArrayQueue.isEmpty()) {
                            conditionalSubscriber.onComplete();
                            this.worker.dispose();
                            return;
                        }
                    }
                }
                if (!(j2 == 0 || j == Long.MAX_VALUE)) {
                    this.requested.addAndGet(-j2);
                }
                int i6 = get();
                int i7 = i5;
                if (i6 == i7) {
                    this.consumed = i2;
                    i6 = addAndGet(-i7);
                    if (i6 == 0) {
                        return;
                    }
                }
                i4 = i6;
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelRunOn$RunOnSubscriber */
    static final class RunOnSubscriber<T> extends BaseRunOnSubscriber<T> {
        private static final long serialVersionUID = 1075119423897941642L;
        final Subscriber<? super T> actual;

        RunOnSubscriber(Subscriber<? super T> subscriber, int i, SpscArrayQueue<T> spscArrayQueue, Worker worker) {
            super(i, spscArrayQueue, worker);
            this.actual = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f4113s, subscription)) {
                this.f4113s = subscription;
                this.actual.onSubscribe(this);
                subscription.request((long) this.prefetch);
            }
        }

        public void run() {
            int i;
            int i2 = this.consumed;
            SpscArrayQueue spscArrayQueue = this.queue;
            Subscriber<? super T> subscriber = this.actual;
            int i3 = this.limit;
            int i4 = 1;
            while (true) {
                long j = this.requested.get();
                long j2 = 0;
                while (j2 != j) {
                    if (this.cancelled) {
                        spscArrayQueue.clear();
                        return;
                    }
                    boolean z = this.done;
                    if (z) {
                        Throwable th = this.error;
                        if (th != null) {
                            spscArrayQueue.clear();
                            subscriber.onError(th);
                            this.worker.dispose();
                            return;
                        }
                    }
                    Object poll = spscArrayQueue.poll();
                    boolean z2 = poll == null;
                    if (z && z2) {
                        subscriber.onComplete();
                        this.worker.dispose();
                        return;
                    } else if (z2) {
                        break;
                    } else {
                        subscriber.onNext(poll);
                        j2++;
                        i2++;
                        if (i2 == i3) {
                            i = i4;
                            this.f4113s.request((long) i2);
                            i2 = 0;
                        } else {
                            i = i4;
                        }
                        i4 = i;
                    }
                }
                int i5 = i4;
                if (j2 == j) {
                    if (this.cancelled) {
                        spscArrayQueue.clear();
                        return;
                    } else if (this.done) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            spscArrayQueue.clear();
                            subscriber.onError(th2);
                            this.worker.dispose();
                            return;
                        } else if (spscArrayQueue.isEmpty()) {
                            subscriber.onComplete();
                            this.worker.dispose();
                            return;
                        }
                    }
                }
                if (!(j2 == 0 || j == Long.MAX_VALUE)) {
                    this.requested.addAndGet(-j2);
                }
                int i6 = get();
                int i7 = i5;
                if (i6 == i7) {
                    this.consumed = i2;
                    i6 = addAndGet(-i7);
                    if (i6 == 0) {
                        return;
                    }
                }
                i4 = i6;
            }
        }
    }

    public ParallelRunOn(ParallelFlowable<? extends T> parallelFlowable, Scheduler scheduler2, int i) {
        this.source = parallelFlowable;
        this.scheduler = scheduler2;
        this.prefetch = i;
    }

    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            int i = this.prefetch;
            for (int i2 = 0; i2 < length; i2++) {
                ConditionalSubscriber conditionalSubscriber = subscriberArr[i2];
                Worker createWorker = this.scheduler.createWorker();
                SpscArrayQueue spscArrayQueue = new SpscArrayQueue(i);
                if (conditionalSubscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i2] = new RunOnConditionalSubscriber(conditionalSubscriber, i, spscArrayQueue, createWorker);
                } else {
                    subscriberArr2[i2] = new RunOnSubscriber(conditionalSubscriber, i, spscArrayQueue, createWorker);
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }

    public int parallelism() {
        return this.source.parallelism();
    }
}
