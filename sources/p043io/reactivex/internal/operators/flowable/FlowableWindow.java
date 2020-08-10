package p043io.reactivex.internal.operators.flowable;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BackpressureHelper;
import p043io.reactivex.plugins.RxJavaPlugins;
import p043io.reactivex.processors.UnicastProcessor;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableWindow */
public final class FlowableWindow<T> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final long size;
    final long skip;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindow$WindowExactSubscriber */
    static final class WindowExactSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = -2365647875069161133L;
        final Subscriber<? super Flowable<T>> actual;
        final int bufferSize;
        long index;
        final AtomicBoolean once = new AtomicBoolean();

        /* renamed from: s */
        Subscription f3930s;
        final long size;
        UnicastProcessor<T> window;

        WindowExactSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, int i) {
            super(1);
            this.actual = subscriber;
            this.size = j;
            this.bufferSize = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3930s, subscription)) {
                this.f3930s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long j = this.index;
            UnicastProcessor<T> unicastProcessor = this.window;
            if (j == 0) {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.bufferSize, this);
                this.window = unicastProcessor;
                this.actual.onNext(unicastProcessor);
            }
            long j2 = j + 1;
            unicastProcessor.onNext(t);
            if (j2 == this.size) {
                this.index = 0;
                this.window = null;
                unicastProcessor.onComplete();
                return;
            }
            this.index = j2;
        }

        public void onError(Throwable th) {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onError(th);
            }
            this.actual.onError(th);
        }

        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onComplete();
            }
            this.actual.onComplete();
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                this.f3930s.request(BackpressureHelper.multiplyCap(this.size, j));
            }
        }

        public void cancel() {
            if (this.once.compareAndSet(false, true)) {
                run();
            }
        }

        public void run() {
            if (decrementAndGet() == 0) {
                this.f3930s.cancel();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindow$WindowOverlapSubscriber */
    static final class WindowOverlapSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = 2428527070996323976L;
        final Subscriber<? super Flowable<T>> actual;
        final int bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        Throwable error;
        final AtomicBoolean firstRequest = new AtomicBoolean();
        long index;
        final AtomicBoolean once = new AtomicBoolean();
        long produced;
        final SpscLinkedArrayQueue<UnicastProcessor<T>> queue;
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        Subscription f3931s;
        final long size;
        final long skip;
        final ArrayDeque<UnicastProcessor<T>> windows = new ArrayDeque<>();
        final AtomicInteger wip = new AtomicInteger();

        WindowOverlapSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, long j2, int i) {
            super(1);
            this.actual = subscriber;
            this.size = j;
            this.skip = j2;
            this.queue = new SpscLinkedArrayQueue<>(i);
            this.bufferSize = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3931s, subscription)) {
                this.f3931s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long j = this.index;
                if (j == 0 && !this.cancelled) {
                    getAndIncrement();
                    UnicastProcessor create = UnicastProcessor.create(this.bufferSize, this);
                    this.windows.offer(create);
                    this.queue.offer(create);
                    drain();
                }
                long j2 = j + 1;
                Iterator it = this.windows.iterator();
                while (it.hasNext()) {
                    ((Processor) it.next()).onNext(t);
                }
                long j3 = this.produced + 1;
                if (j3 == this.size) {
                    this.produced = j3 - this.skip;
                    Processor processor = (Processor) this.windows.poll();
                    if (processor != null) {
                        processor.onComplete();
                    }
                } else {
                    this.produced = j3;
                }
                if (j2 == this.skip) {
                    this.index = 0;
                } else {
                    this.index = j2;
                }
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            Iterator it = this.windows.iterator();
            while (it.hasNext()) {
                ((Processor) it.next()).onError(th);
            }
            this.windows.clear();
            this.error = th;
            this.done = true;
            drain();
        }

        public void onComplete() {
            if (!this.done) {
                Iterator it = this.windows.iterator();
                while (it.hasNext()) {
                    ((Processor) it.next()).onComplete();
                }
                this.windows.clear();
                this.done = true;
                drain();
            }
        }

        /* access modifiers changed from: 0000 */
        public void drain() {
            if (this.wip.getAndIncrement() == 0) {
                Subscriber<? super Flowable<T>> subscriber = this.actual;
                SpscLinkedArrayQueue<UnicastProcessor<T>> spscLinkedArrayQueue = this.queue;
                int i = 1;
                do {
                    long j = this.requested.get();
                    long j2 = 0;
                    while (j2 != j) {
                        boolean z = this.done;
                        UnicastProcessor unicastProcessor = (UnicastProcessor) spscLinkedArrayQueue.poll();
                        boolean z2 = unicastProcessor == null;
                        if (!checkTerminated(z, z2, subscriber, spscLinkedArrayQueue)) {
                            if (z2) {
                                break;
                            }
                            subscriber.onNext(unicastProcessor);
                            j2++;
                        } else {
                            return;
                        }
                    }
                    if (j2 != j || !checkTerminated(this.done, spscLinkedArrayQueue.isEmpty(), subscriber, spscLinkedArrayQueue)) {
                        if (!(j2 == 0 || j == Long.MAX_VALUE)) {
                            this.requested.addAndGet(-j2);
                        }
                        i = this.wip.addAndGet(-i);
                    } else {
                        return;
                    }
                } while (i != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            if (this.cancelled) {
                spscLinkedArrayQueue.clear();
                return true;
            }
            if (z) {
                Throwable th = this.error;
                if (th != null) {
                    spscLinkedArrayQueue.clear();
                    subscriber.onError(th);
                    return true;
                } else if (z2) {
                    subscriber.onComplete();
                    return true;
                }
            }
            return false;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                if (this.firstRequest.get() || !this.firstRequest.compareAndSet(false, true)) {
                    this.f3931s.request(BackpressureHelper.multiplyCap(this.skip, j));
                } else {
                    this.f3931s.request(BackpressureHelper.addCap(this.size, BackpressureHelper.multiplyCap(this.skip, j - 1)));
                }
                drain();
            }
        }

        public void cancel() {
            this.cancelled = true;
            if (this.once.compareAndSet(false, true)) {
                run();
            }
        }

        public void run() {
            if (decrementAndGet() == 0) {
                this.f3931s.cancel();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableWindow$WindowSkipSubscriber */
    static final class WindowSkipSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription, Runnable {
        private static final long serialVersionUID = -8792836352386833856L;
        final Subscriber<? super Flowable<T>> actual;
        final int bufferSize;
        final AtomicBoolean firstRequest = new AtomicBoolean();
        long index;
        final AtomicBoolean once = new AtomicBoolean();

        /* renamed from: s */
        Subscription f3932s;
        final long size;
        final long skip;
        UnicastProcessor<T> window;

        WindowSkipSubscriber(Subscriber<? super Flowable<T>> subscriber, long j, long j2, int i) {
            super(1);
            this.actual = subscriber;
            this.size = j;
            this.skip = j2;
            this.bufferSize = i;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3932s, subscription)) {
                this.f3932s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            long j = this.index;
            UnicastProcessor<T> unicastProcessor = this.window;
            if (j == 0) {
                getAndIncrement();
                unicastProcessor = UnicastProcessor.create(this.bufferSize, this);
                this.window = unicastProcessor;
                this.actual.onNext(unicastProcessor);
            }
            long j2 = j + 1;
            if (unicastProcessor != null) {
                unicastProcessor.onNext(t);
            }
            if (j2 == this.size) {
                this.window = null;
                unicastProcessor.onComplete();
            }
            if (j2 == this.skip) {
                this.index = 0;
            } else {
                this.index = j2;
            }
        }

        public void onError(Throwable th) {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onError(th);
            }
            this.actual.onError(th);
        }

        public void onComplete() {
            UnicastProcessor<T> unicastProcessor = this.window;
            if (unicastProcessor != null) {
                this.window = null;
                unicastProcessor.onComplete();
            }
            this.actual.onComplete();
        }

        public void request(long j) {
            if (!SubscriptionHelper.validate(j)) {
                return;
            }
            if (this.firstRequest.get() || !this.firstRequest.compareAndSet(false, true)) {
                this.f3932s.request(BackpressureHelper.multiplyCap(this.skip, j));
                return;
            }
            this.f3932s.request(BackpressureHelper.addCap(BackpressureHelper.multiplyCap(this.size, j), BackpressureHelper.multiplyCap(this.skip - this.size, j - 1)));
        }

        public void cancel() {
            if (this.once.compareAndSet(false, true)) {
                run();
            }
        }

        public void run() {
            if (decrementAndGet() == 0) {
                this.f3932s.cancel();
            }
        }
    }

    public FlowableWindow(Flowable<T> flowable, long j, long j2, int i) {
        super(flowable);
        this.size = j;
        this.skip = j2;
        this.bufferSize = i;
    }

    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        long j = this.skip;
        long j2 = this.size;
        if (j == j2) {
            this.source.subscribe((FlowableSubscriber<? super T>) new WindowExactSubscriber<Object>(subscriber, this.size, this.bufferSize));
        } else if (j > j2) {
            Flowable flowable = this.source;
            WindowSkipSubscriber windowSkipSubscriber = new WindowSkipSubscriber(subscriber, this.size, this.skip, this.bufferSize);
            flowable.subscribe((FlowableSubscriber<? super T>) windowSkipSubscriber);
        } else {
            Flowable flowable2 = this.source;
            WindowOverlapSubscriber windowOverlapSubscriber = new WindowOverlapSubscriber(subscriber, this.size, this.skip, this.bufferSize);
            flowable2.subscribe((FlowableSubscriber<? super T>) windowOverlapSubscriber);
        }
    }
}
