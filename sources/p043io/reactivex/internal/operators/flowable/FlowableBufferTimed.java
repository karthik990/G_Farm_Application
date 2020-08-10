package p043io.reactivex.internal.operators.flowable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Scheduler.Worker;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.queue.MpscLinkedQueue;
import p043io.reactivex.internal.subscribers.QueueDrainSubscriber;
import p043io.reactivex.internal.subscriptions.EmptySubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.QueueDrainHelper;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferTimed */
public final class FlowableBufferTimed<T, U extends Collection<? super T>> extends AbstractFlowableWithUpstream<T, U> {
    final Callable<U> bufferSupplier;
    final int maxSize;
    final boolean restartTimerOnMaxSize;
    final Scheduler scheduler;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferTimed$BufferExactBoundedSubscriber */
    static final class BufferExactBoundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Subscription, Runnable, Disposable {
        U buffer;
        final Callable<U> bufferSupplier;
        long consumerIndex;
        final int maxSize;
        long producerIndex;
        final boolean restartTimerOnMaxSize;

        /* renamed from: s */
        Subscription f3829s;
        Disposable timer;
        final long timespan;
        final TimeUnit unit;

        /* renamed from: w */
        final Worker f3830w;

        BufferExactBoundedSubscriber(Subscriber<? super U> subscriber, Callable<U> callable, long j, TimeUnit timeUnit, int i, boolean z, Worker worker) {
            super(subscriber, new MpscLinkedQueue());
            this.bufferSupplier = callable;
            this.timespan = j;
            this.unit = timeUnit;
            this.maxSize = i;
            this.restartTimerOnMaxSize = z;
            this.f3830w = worker;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3829s, subscription)) {
                this.f3829s = subscription;
                try {
                    this.buffer = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
                    this.actual.onSubscribe(this);
                    Worker worker = this.f3830w;
                    long j = this.timespan;
                    this.timer = worker.schedulePeriodically(this, j, j, this.unit);
                    subscription.request(Long.MAX_VALUE);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f3830w.dispose();
                    subscription.cancel();
                    EmptySubscription.error(th, this.actual);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0019, code lost:
            if (r12.restartTimerOnMaxSize == false) goto L_0x0028;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x001b, code lost:
            r12.buffer = null;
            r12.producerIndex++;
            r12.timer.dispose();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
            fastPathOrderedEmitMax(r0, false, r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r13 = (java.util.Collection) p043io.reactivex.internal.functions.ObjectHelper.requireNonNull(r12.bufferSupplier.call(), "The supplied buffer is null");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003c, code lost:
            if (r12.restartTimerOnMaxSize == false) goto L_0x0059;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
            monitor-enter(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r12.buffer = r13;
            r12.consumerIndex++;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
            monitor-exit(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0047, code lost:
            r5 = r12.f3830w;
            r9 = r12.timespan;
            r12.timer = r5.schedulePeriodically(r12, r9, r9, r12.unit);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0059, code lost:
            monitor-enter(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            r12.buffer = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x005c, code lost:
            monitor-exit(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x005d, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0061, code lost:
            r13 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0062, code lost:
            p043io.reactivex.exceptions.Exceptions.throwIfFatal(r13);
            cancel();
            r12.actual.onError(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x006d, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onNext(T r13) {
            /*
                r12 = this;
                monitor-enter(r12)
                U r0 = r12.buffer     // Catch:{ all -> 0x006e }
                if (r0 != 0) goto L_0x0007
                monitor-exit(r12)     // Catch:{ all -> 0x006e }
                return
            L_0x0007:
                r0.add(r13)     // Catch:{ all -> 0x006e }
                int r13 = r0.size()     // Catch:{ all -> 0x006e }
                int r1 = r12.maxSize     // Catch:{ all -> 0x006e }
                if (r13 >= r1) goto L_0x0014
                monitor-exit(r12)     // Catch:{ all -> 0x006e }
                return
            L_0x0014:
                monitor-exit(r12)     // Catch:{ all -> 0x006e }
                boolean r13 = r12.restartTimerOnMaxSize
                r1 = 1
                if (r13 == 0) goto L_0x0028
                r13 = 0
                r12.buffer = r13
                long r3 = r12.producerIndex
                long r3 = r3 + r1
                r12.producerIndex = r3
                io.reactivex.disposables.Disposable r13 = r12.timer
                r13.dispose()
            L_0x0028:
                r13 = 0
                r12.fastPathOrderedEmitMax(r0, r13, r12)
                java.util.concurrent.Callable<U> r13 = r12.bufferSupplier     // Catch:{ all -> 0x0061 }
                java.lang.Object r13 = r13.call()     // Catch:{ all -> 0x0061 }
                java.lang.String r0 = "The supplied buffer is null"
                java.lang.Object r13 = p043io.reactivex.internal.functions.ObjectHelper.requireNonNull(r13, r0)     // Catch:{ all -> 0x0061 }
                java.util.Collection r13 = (java.util.Collection) r13     // Catch:{ all -> 0x0061 }
                boolean r0 = r12.restartTimerOnMaxSize
                if (r0 == 0) goto L_0x0059
                monitor-enter(r12)
                r12.buffer = r13     // Catch:{ all -> 0x0056 }
                long r3 = r12.consumerIndex     // Catch:{ all -> 0x0056 }
                long r3 = r3 + r1
                r12.consumerIndex = r3     // Catch:{ all -> 0x0056 }
                monitor-exit(r12)     // Catch:{ all -> 0x0056 }
                io.reactivex.Scheduler$Worker r5 = r12.f3830w
                long r9 = r12.timespan
                java.util.concurrent.TimeUnit r11 = r12.unit
                r6 = r12
                r7 = r9
                io.reactivex.disposables.Disposable r13 = r5.schedulePeriodically(r6, r7, r9, r11)
                r12.timer = r13
                goto L_0x005d
            L_0x0056:
                r13 = move-exception
                monitor-exit(r12)     // Catch:{ all -> 0x0056 }
                throw r13
            L_0x0059:
                monitor-enter(r12)
                r12.buffer = r13     // Catch:{ all -> 0x005e }
                monitor-exit(r12)     // Catch:{ all -> 0x005e }
            L_0x005d:
                return
            L_0x005e:
                r13 = move-exception
                monitor-exit(r12)     // Catch:{ all -> 0x005e }
                throw r13
            L_0x0061:
                r13 = move-exception
                p043io.reactivex.exceptions.Exceptions.throwIfFatal(r13)
                r12.cancel()
                org.reactivestreams.Subscriber r0 = r12.actual
                r0.onError(r13)
                return
            L_0x006e:
                r13 = move-exception
                monitor-exit(r12)     // Catch:{ all -> 0x006e }
                throw r13
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowableBufferTimed.BufferExactBoundedSubscriber.onNext(java.lang.Object):void");
        }

        public void onError(Throwable th) {
            synchronized (this) {
                this.buffer = null;
            }
            this.actual.onError(th);
            this.f3830w.dispose();
        }

        public void onComplete() {
            U u;
            synchronized (this) {
                u = this.buffer;
                this.buffer = null;
            }
            this.queue.offer(u);
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainMaxLoop(this.queue, this.actual, false, this, this);
            }
            this.f3830w.dispose();
        }

        public boolean accept(Subscriber<? super U> subscriber, U u) {
            subscriber.onNext(u);
            return true;
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                dispose();
            }
        }

        public void dispose() {
            synchronized (this) {
                this.buffer = null;
            }
            this.f3829s.cancel();
            this.f3830w.dispose();
        }

        public boolean isDisposed() {
            return this.f3830w.isDisposed();
        }

        public void run() {
            try {
                U u = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
                synchronized (this) {
                    U u2 = this.buffer;
                    if (u2 != null) {
                        if (this.producerIndex == this.consumerIndex) {
                            this.buffer = u;
                            fastPathOrderedEmitMax(u2, false, this);
                        }
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.actual.onError(th);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferTimed$BufferExactUnboundedSubscriber */
    static final class BufferExactUnboundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Subscription, Runnable, Disposable {
        U buffer;
        final Callable<U> bufferSupplier;

        /* renamed from: s */
        Subscription f3831s;
        final Scheduler scheduler;
        final AtomicReference<Disposable> timer = new AtomicReference<>();
        final long timespan;
        final TimeUnit unit;

        BufferExactUnboundedSubscriber(Subscriber<? super U> subscriber, Callable<U> callable, long j, TimeUnit timeUnit, Scheduler scheduler2) {
            super(subscriber, new MpscLinkedQueue());
            this.bufferSupplier = callable;
            this.timespan = j;
            this.unit = timeUnit;
            this.scheduler = scheduler2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3831s, subscription)) {
                this.f3831s = subscription;
                try {
                    this.buffer = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
                    this.actual.onSubscribe(this);
                    if (!this.cancelled) {
                        subscription.request(Long.MAX_VALUE);
                        Scheduler scheduler2 = this.scheduler;
                        long j = this.timespan;
                        Disposable schedulePeriodicallyDirect = scheduler2.schedulePeriodicallyDirect(this, j, j, this.unit);
                        if (!this.timer.compareAndSet(null, schedulePeriodicallyDirect)) {
                            schedulePeriodicallyDirect.dispose();
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    EmptySubscription.error(th, this.actual);
                }
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                U u = this.buffer;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this.timer);
            synchronized (this) {
                this.buffer = null;
            }
            this.actual.onError(th);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
            if (enter() == false) goto L_0x0026;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
            p043io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(r3.queue, r3.actual, false, r3, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
            r3.queue.offer(r0);
            r3.done = true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onComplete() {
            /*
                r3 = this;
                java.util.concurrent.atomic.AtomicReference<io.reactivex.disposables.Disposable> r0 = r3.timer
                p043io.reactivex.internal.disposables.DisposableHelper.dispose(r0)
                monitor-enter(r3)
                U r0 = r3.buffer     // Catch:{ all -> 0x0027 }
                if (r0 != 0) goto L_0x000c
                monitor-exit(r3)     // Catch:{ all -> 0x0027 }
                return
            L_0x000c:
                r1 = 0
                r3.buffer = r1     // Catch:{ all -> 0x0027 }
                monitor-exit(r3)     // Catch:{ all -> 0x0027 }
                io.reactivex.internal.fuseable.SimplePlainQueue r1 = r3.queue
                r1.offer(r0)
                r0 = 1
                r3.done = r0
                boolean r0 = r3.enter()
                if (r0 == 0) goto L_0x0026
                io.reactivex.internal.fuseable.SimplePlainQueue r0 = r3.queue
                org.reactivestreams.Subscriber r1 = r3.actual
                r2 = 0
                p043io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(r0, r1, r2, r3, r3)
            L_0x0026:
                return
            L_0x0027:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0027 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowableBufferTimed.BufferExactUnboundedSubscriber.onComplete():void");
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            this.f3831s.cancel();
            DisposableHelper.dispose(this.timer);
        }

        public void run() {
            U u;
            try {
                U u2 = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
                synchronized (this) {
                    u = this.buffer;
                    if (u != null) {
                        this.buffer = u2;
                    }
                }
                if (u == null) {
                    DisposableHelper.dispose(this.timer);
                } else {
                    fastPathEmitMax(u, false, this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.actual.onError(th);
            }
        }

        public boolean accept(Subscriber<? super U> subscriber, U u) {
            this.actual.onNext(u);
            return true;
        }

        public void dispose() {
            cancel();
        }

        public boolean isDisposed() {
            return this.timer.get() == DisposableHelper.DISPOSED;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferTimed$BufferSkipBoundedSubscriber */
    static final class BufferSkipBoundedSubscriber<T, U extends Collection<? super T>> extends QueueDrainSubscriber<T, U, U> implements Subscription, Runnable {
        final Callable<U> bufferSupplier;
        final List<U> buffers = new LinkedList();

        /* renamed from: s */
        Subscription f3832s;
        final long timeskip;
        final long timespan;
        final TimeUnit unit;

        /* renamed from: w */
        final Worker f3833w;

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableBufferTimed$BufferSkipBoundedSubscriber$RemoveFromBuffer */
        final class RemoveFromBuffer implements Runnable {
            private final U buffer;

            RemoveFromBuffer(U u) {
                this.buffer = u;
            }

            public void run() {
                synchronized (BufferSkipBoundedSubscriber.this) {
                    BufferSkipBoundedSubscriber.this.buffers.remove(this.buffer);
                }
                BufferSkipBoundedSubscriber bufferSkipBoundedSubscriber = BufferSkipBoundedSubscriber.this;
                bufferSkipBoundedSubscriber.fastPathOrderedEmitMax(this.buffer, false, bufferSkipBoundedSubscriber.f3833w);
            }
        }

        BufferSkipBoundedSubscriber(Subscriber<? super U> subscriber, Callable<U> callable, long j, long j2, TimeUnit timeUnit, Worker worker) {
            super(subscriber, new MpscLinkedQueue());
            this.bufferSupplier = callable;
            this.timespan = j;
            this.timeskip = j2;
            this.unit = timeUnit;
            this.f3833w = worker;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3832s, subscription)) {
                this.f3832s = subscription;
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
                    this.buffers.add(collection);
                    this.actual.onSubscribe(this);
                    subscription.request(Long.MAX_VALUE);
                    Worker worker = this.f3833w;
                    long j = this.timeskip;
                    worker.schedulePeriodically(this, j, j, this.unit);
                    this.f3833w.schedule(new RemoveFromBuffer(collection), this.timespan, this.unit);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f3833w.dispose();
                    subscription.cancel();
                    EmptySubscription.error(th, this.actual);
                }
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                for (U add : this.buffers) {
                    add.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            this.done = true;
            this.f3833w.dispose();
            clear();
            this.actual.onError(th);
        }

        public void onComplete() {
            ArrayList<Collection> arrayList;
            synchronized (this) {
                arrayList = new ArrayList<>(this.buffers);
                this.buffers.clear();
            }
            for (Collection offer : arrayList) {
                this.queue.offer(offer);
            }
            this.done = true;
            if (enter()) {
                QueueDrainHelper.drainMaxLoop(this.queue, this.actual, false, this.f3833w, this);
            }
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            clear();
            this.f3832s.cancel();
            this.f3833w.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void clear() {
            synchronized (this) {
                this.buffers.clear();
            }
        }

        public void run() {
            if (!this.cancelled) {
                try {
                    Collection collection = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The supplied buffer is null");
                    synchronized (this) {
                        if (!this.cancelled) {
                            this.buffers.add(collection);
                            this.f3833w.schedule(new RemoveFromBuffer(collection), this.timespan, this.unit);
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    this.actual.onError(th);
                }
            }
        }

        public boolean accept(Subscriber<? super U> subscriber, U u) {
            subscriber.onNext(u);
            return true;
        }
    }

    public FlowableBufferTimed(Flowable<T> flowable, long j, long j2, TimeUnit timeUnit, Scheduler scheduler2, Callable<U> callable, int i, boolean z) {
        super(flowable);
        this.timespan = j;
        this.timeskip = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
        this.bufferSupplier = callable;
        this.maxSize = i;
        this.restartTimerOnMaxSize = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (this.timespan == this.timeskip && this.maxSize == Integer.MAX_VALUE) {
            Flowable flowable = this.source;
            BufferExactUnboundedSubscriber bufferExactUnboundedSubscriber = new BufferExactUnboundedSubscriber(new SerializedSubscriber(subscriber), this.bufferSupplier, this.timespan, this.unit, this.scheduler);
            flowable.subscribe((FlowableSubscriber<? super T>) bufferExactUnboundedSubscriber);
            return;
        }
        Worker createWorker = this.scheduler.createWorker();
        if (this.timespan == this.timeskip) {
            Flowable flowable2 = this.source;
            BufferExactBoundedSubscriber bufferExactBoundedSubscriber = new BufferExactBoundedSubscriber(new SerializedSubscriber(subscriber), this.bufferSupplier, this.timespan, this.unit, this.maxSize, this.restartTimerOnMaxSize, createWorker);
            flowable2.subscribe((FlowableSubscriber<? super T>) bufferExactBoundedSubscriber);
            return;
        }
        Flowable flowable3 = this.source;
        BufferSkipBoundedSubscriber bufferSkipBoundedSubscriber = new BufferSkipBoundedSubscriber(new SerializedSubscriber(subscriber), this.bufferSupplier, this.timespan, this.timeskip, this.unit, createWorker);
        flowable3.subscribe((FlowableSubscriber<? super T>) bufferSkipBoundedSubscriber);
    }
}
