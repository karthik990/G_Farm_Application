package p043io.reactivex.internal.operators.flowable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Scheduler;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.flowables.ConnectableFlowable;
import p043io.reactivex.functions.Consumer;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.fuseable.HasUpstreamPublisher;
import p043io.reactivex.internal.subscribers.SubscriberResourceWrapper;
import p043io.reactivex.internal.subscriptions.EmptySubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BackpressureHelper;
import p043io.reactivex.internal.util.NotificationLite;
import p043io.reactivex.plugins.RxJavaPlugins;
import p043io.reactivex.schedulers.Timed;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay */
public final class FlowableReplay<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T>, Disposable {
    static final Callable DEFAULT_UNBOUNDED_FACTORY = new DefaultUnboundedFactory();
    final Callable<? extends ReplayBuffer<T>> bufferFactory;
    final AtomicReference<ReplaySubscriber<T>> current;
    final Publisher<T> onSubscribe;
    final Flowable<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$BoundedReplayBuffer */
    static class BoundedReplayBuffer<T> extends AtomicReference<Node> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 2346567790059478686L;
        long index;
        int size;
        Node tail;

        /* access modifiers changed from: 0000 */
        public Object enterTransform(Object obj) {
            return obj;
        }

        /* access modifiers changed from: 0000 */
        public Object leaveTransform(Object obj) {
            return obj;
        }

        /* access modifiers changed from: 0000 */
        public void truncate() {
        }

        /* access modifiers changed from: 0000 */
        public void truncateFinal() {
        }

        BoundedReplayBuffer() {
            Node node = new Node(null, 0);
            this.tail = node;
            set(node);
        }

        /* access modifiers changed from: 0000 */
        public final void addLast(Node node) {
            this.tail.set(node);
            this.tail = node;
            this.size++;
        }

        /* access modifiers changed from: 0000 */
        public final void removeFirst() {
            Node node = (Node) ((Node) get()).get();
            if (node != null) {
                this.size--;
                setFirst(node);
                return;
            }
            throw new IllegalStateException("Empty list!");
        }

        /* access modifiers changed from: 0000 */
        public final void removeSome(int i) {
            Node node = (Node) get();
            while (i > 0) {
                node = (Node) node.get();
                i--;
                this.size--;
            }
            setFirst(node);
        }

        /* access modifiers changed from: 0000 */
        public final void setFirst(Node node) {
            set(node);
        }

        public final void next(T t) {
            Object enterTransform = enterTransform(NotificationLite.next(t));
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(enterTransform, j));
            truncate();
        }

        public final void error(Throwable th) {
            Object enterTransform = enterTransform(NotificationLite.error(th));
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(enterTransform, j));
            truncateFinal();
        }

        public final void complete() {
            Object enterTransform = enterTransform(NotificationLite.complete());
            long j = this.index + 1;
            this.index = j;
            addLast(new Node(enterTransform, j));
            truncateFinal();
        }

        /* JADX INFO: used method not loaded: io.reactivex.internal.util.NotificationLite.accept(java.lang.Object, org.reactivestreams.Subscriber):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0011, code lost:
            if (r14.isDisposed() == false) goto L_0x0014;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0014, code lost:
            r2 = r14.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
            if (r2 != Long.MAX_VALUE) goto L_0x0024;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
            r4 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0024, code lost:
            r4 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0025, code lost:
            r5 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.Node) r14.index();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x002d, code lost:
            if (r5 != null) goto L_0x003c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002f, code lost:
            r5 = getHead();
            r14.index = r5;
            p043io.reactivex.internal.util.BackpressureHelper.add(r14.totalRequested, r5.index);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x003c, code lost:
            r8 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
            if (r2 == 0) goto L_0x0083;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0041, code lost:
            r10 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.Node) r5.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0047, code lost:
            if (r10 == null) goto L_0x0083;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0049, code lost:
            r5 = leaveTransform(r10.value);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
            if (p043io.reactivex.internal.util.NotificationLite.accept(r5, (org.reactivestreams.Subscriber) r14.child) == false) goto L_0x005b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0058, code lost:
            r14.index = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x005b, code lost:
            r8 = r8 + 1;
            r2 = r2 - 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0063, code lost:
            if (r14.isDisposed() == false) goto L_0x0066;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0065, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0066, code lost:
            r5 = r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x0068, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0069, code lost:
            p043io.reactivex.exceptions.Exceptions.throwIfFatal(r0);
            r14.index = null;
            r14.dispose();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x007d, code lost:
            r14.child.onError(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0082, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0085, code lost:
            if (r8 == 0) goto L_0x008e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0087, code lost:
            r14.index = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0089, code lost:
            if (r4 != false) goto L_0x008e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x008b, code lost:
            r14.produced(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x008e, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0091, code lost:
            if (r14.missed != false) goto L_0x0097;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x0093, code lost:
            r14.emitting = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0095, code lost:
            monitor-exit(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0096, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0097, code lost:
            r14.missed = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x0099, code lost:
            monitor-exit(r14);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void replay(p043io.reactivex.internal.operators.flowable.FlowableReplay.InnerSubscription<T> r14) {
            /*
                r13 = this;
                monitor-enter(r14)
                boolean r0 = r14.emitting     // Catch:{ all -> 0x009f }
                r1 = 1
                if (r0 == 0) goto L_0x000a
                r14.missed = r1     // Catch:{ all -> 0x009f }
                monitor-exit(r14)     // Catch:{ all -> 0x009f }
                return
            L_0x000a:
                r14.emitting = r1     // Catch:{ all -> 0x009f }
                monitor-exit(r14)     // Catch:{ all -> 0x009f }
            L_0x000d:
                boolean r0 = r14.isDisposed()
                if (r0 == 0) goto L_0x0014
                return
            L_0x0014:
                long r2 = r14.get()
                r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                r0 = 0
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 != 0) goto L_0x0024
                r4 = 1
                goto L_0x0025
            L_0x0024:
                r4 = 0
            L_0x0025:
                java.lang.Object r5 = r14.index()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r5 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.Node) r5
                r6 = 0
                if (r5 != 0) goto L_0x003c
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r5 = r13.getHead()
                r14.index = r5
                java.util.concurrent.atomic.AtomicLong r8 = r14.totalRequested
                long r9 = r5.index
                p043io.reactivex.internal.util.BackpressureHelper.add(r8, r9)
            L_0x003c:
                r8 = r6
            L_0x003d:
                int r10 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r10 == 0) goto L_0x0083
                java.lang.Object r10 = r5.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r10 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.Node) r10
                if (r10 == 0) goto L_0x0083
                java.lang.Object r5 = r10.value
                java.lang.Object r5 = r13.leaveTransform(r5)
                r11 = 0
                org.reactivestreams.Subscriber<? super T> r12 = r14.child     // Catch:{ all -> 0x0068 }
                boolean r12 = p043io.reactivex.internal.util.NotificationLite.accept(r5, r12)     // Catch:{ all -> 0x0068 }
                if (r12 == 0) goto L_0x005b
                r14.index = r11     // Catch:{ all -> 0x0068 }
                return
            L_0x005b:
                r11 = 1
                long r8 = r8 + r11
                long r2 = r2 - r11
                boolean r5 = r14.isDisposed()
                if (r5 == 0) goto L_0x0066
                return
            L_0x0066:
                r5 = r10
                goto L_0x003d
            L_0x0068:
                r0 = move-exception
                p043io.reactivex.exceptions.Exceptions.throwIfFatal(r0)
                r14.index = r11
                r14.dispose()
                boolean r1 = p043io.reactivex.internal.util.NotificationLite.isError(r5)
                if (r1 != 0) goto L_0x0082
                boolean r1 = p043io.reactivex.internal.util.NotificationLite.isComplete(r5)
                if (r1 != 0) goto L_0x0082
                org.reactivestreams.Subscriber<? super T> r14 = r14.child
                r14.onError(r0)
            L_0x0082:
                return
            L_0x0083:
                int r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r2 == 0) goto L_0x008e
                r14.index = r5
                if (r4 != 0) goto L_0x008e
                r14.produced(r8)
            L_0x008e:
                monitor-enter(r14)
                boolean r2 = r14.missed     // Catch:{ all -> 0x009c }
                if (r2 != 0) goto L_0x0097
                r14.emitting = r0     // Catch:{ all -> 0x009c }
                monitor-exit(r14)     // Catch:{ all -> 0x009c }
                return
            L_0x0097:
                r14.missed = r0     // Catch:{ all -> 0x009c }
                monitor-exit(r14)     // Catch:{ all -> 0x009c }
                goto L_0x000d
            L_0x009c:
                r0 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x009c }
                throw r0
            L_0x009f:
                r0 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x009f }
                goto L_0x00a3
            L_0x00a2:
                throw r0
            L_0x00a3:
                goto L_0x00a2
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowableReplay.BoundedReplayBuffer.replay(io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription):void");
        }

        /* access modifiers changed from: 0000 */
        public final void collect(Collection<? super T> collection) {
            Node head = getHead();
            while (true) {
                head = (Node) head.get();
                if (head != null) {
                    Object leaveTransform = leaveTransform(head.value);
                    if (!NotificationLite.isComplete(leaveTransform) && !NotificationLite.isError(leaveTransform)) {
                        collection.add(NotificationLite.getValue(leaveTransform));
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean hasError() {
            return this.tail.value != null && NotificationLite.isError(leaveTransform(this.tail.value));
        }

        /* access modifiers changed from: 0000 */
        public boolean hasCompleted() {
            return this.tail.value != null && NotificationLite.isComplete(leaveTransform(this.tail.value));
        }

        /* access modifiers changed from: 0000 */
        public Node getHead() {
            return (Node) get();
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$ConnectableFlowableReplay */
    static final class ConnectableFlowableReplay<T> extends ConnectableFlowable<T> {

        /* renamed from: co */
        private final ConnectableFlowable<T> f3890co;
        private final Flowable<T> observable;

        ConnectableFlowableReplay(ConnectableFlowable<T> connectableFlowable, Flowable<T> flowable) {
            this.f3890co = connectableFlowable;
            this.observable = flowable;
        }

        public void connect(Consumer<? super Disposable> consumer) {
            this.f3890co.connect(consumer);
        }

        /* access modifiers changed from: protected */
        public void subscribeActual(Subscriber<? super T> subscriber) {
            this.observable.subscribe(subscriber);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$DefaultUnboundedFactory */
    static final class DefaultUnboundedFactory implements Callable<Object> {
        DefaultUnboundedFactory() {
        }

        public Object call() {
            return new UnboundedReplayBuffer(16);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription */
    static final class InnerSubscription<T> extends AtomicLong implements Subscription, Disposable {
        static final long CANCELLED = Long.MIN_VALUE;
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        boolean emitting;
        Object index;
        boolean missed;
        final ReplaySubscriber<T> parent;
        final AtomicLong totalRequested = new AtomicLong();

        InnerSubscription(ReplaySubscriber<T> replaySubscriber, Subscriber<? super T> subscriber) {
            this.parent = replaySubscriber;
            this.child = subscriber;
        }

        public void request(long j) {
            long j2;
            if (SubscriptionHelper.validate(j)) {
                do {
                    j2 = get();
                    if (j2 != Long.MIN_VALUE) {
                        if (j2 >= 0 && j == 0) {
                            return;
                        }
                    } else {
                        return;
                    }
                } while (!compareAndSet(j2, BackpressureHelper.addCap(j2, j)));
                BackpressureHelper.add(this.totalRequested, j);
                this.parent.manageRequests();
                this.parent.buffer.replay(this);
            }
        }

        public long produced(long j) {
            return BackpressureHelper.producedCancel(this, j);
        }

        public boolean isDisposed() {
            return get() == Long.MIN_VALUE;
        }

        public void cancel() {
            dispose();
        }

        public void dispose() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                this.parent.manageRequests();
            }
        }

        /* access modifiers changed from: 0000 */
        public <U> U index() {
            return this.index;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$MultiCastPublisher */
    static final class MultiCastPublisher<R, U> implements Publisher<R> {
        private final Callable<? extends ConnectableFlowable<U>> connectableFactory;
        private final Function<? super Flowable<U>, ? extends Publisher<R>> selector;

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$MultiCastPublisher$DisposableConsumer */
        final class DisposableConsumer implements Consumer<Disposable> {
            private final SubscriberResourceWrapper<R> srw;

            DisposableConsumer(SubscriberResourceWrapper<R> subscriberResourceWrapper) {
                this.srw = subscriberResourceWrapper;
            }

            public void accept(Disposable disposable) {
                this.srw.setResource(disposable);
            }
        }

        MultiCastPublisher(Callable<? extends ConnectableFlowable<U>> callable, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
            this.connectableFactory = callable;
            this.selector = function;
        }

        public void subscribe(Subscriber<? super R> subscriber) {
            try {
                ConnectableFlowable connectableFlowable = (ConnectableFlowable) ObjectHelper.requireNonNull(this.connectableFactory.call(), "The connectableFactory returned null");
                try {
                    Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.selector.apply(connectableFlowable), "The selector returned a null Publisher");
                    SubscriberResourceWrapper subscriberResourceWrapper = new SubscriberResourceWrapper(subscriber);
                    publisher.subscribe(subscriberResourceWrapper);
                    connectableFlowable.connect(new DisposableConsumer(subscriberResourceWrapper));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    EmptySubscription.error(th, subscriber);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                EmptySubscription.error(th2, subscriber);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$Node */
    static final class Node extends AtomicReference<Node> {
        private static final long serialVersionUID = 245354315435971818L;
        final long index;
        final Object value;

        Node(Object obj, long j) {
            this.value = obj;
            this.index = j;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer */
    interface ReplayBuffer<T> {
        void complete();

        void error(Throwable th);

        void next(T t);

        void replay(InnerSubscription<T> innerSubscription);
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBufferTask */
    static final class ReplayBufferTask<T> implements Callable<ReplayBuffer<T>> {
        private final int bufferSize;

        ReplayBufferTask(int i) {
            this.bufferSize = i;
        }

        public ReplayBuffer<T> call() {
            return new SizeBoundReplayBuffer(this.bufferSize);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$ReplayPublisher */
    static final class ReplayPublisher<T> implements Publisher<T> {
        private final Callable<? extends ReplayBuffer<T>> bufferFactory;
        private final AtomicReference<ReplaySubscriber<T>> curr;

        ReplayPublisher(AtomicReference<ReplaySubscriber<T>> atomicReference, Callable<? extends ReplayBuffer<T>> callable) {
            this.curr = atomicReference;
            this.bufferFactory = callable;
        }

        /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void subscribe(org.reactivestreams.Subscriber<? super T> r4) {
            /*
                r3 = this;
            L_0x0000:
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber<T>> r0 = r3.curr
                java.lang.Object r0 = r0.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber r0 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.ReplaySubscriber) r0
                if (r0 != 0) goto L_0x002c
                java.util.concurrent.Callable<? extends io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer<T>> r0 = r3.bufferFactory     // Catch:{ all -> 0x0023 }
                java.lang.Object r0 = r0.call()     // Catch:{ all -> 0x0023 }
                io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer r0 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.ReplayBuffer) r0     // Catch:{ all -> 0x0023 }
                io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber r1 = new io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber
                r1.<init>(r0)
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber<T>> r0 = r3.curr
                r2 = 0
                boolean r0 = r0.compareAndSet(r2, r1)
                if (r0 != 0) goto L_0x0021
                goto L_0x0000
            L_0x0021:
                r0 = r1
                goto L_0x002c
            L_0x0023:
                r4 = move-exception
                p043io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                java.lang.RuntimeException r4 = p043io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r4)
                throw r4
            L_0x002c:
                io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription r1 = new io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription
                r1.<init>(r0, r4)
                r4.onSubscribe(r1)
                r0.add(r1)
                boolean r4 = r1.isDisposed()
                if (r4 == 0) goto L_0x0041
                r0.remove(r1)
                return
            L_0x0041:
                r0.manageRequests()
                io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer<T> r4 = r0.buffer
                r4.replay(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowableReplay.ReplayPublisher.subscribe(org.reactivestreams.Subscriber):void");
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber */
    static final class ReplaySubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Disposable {
        static final InnerSubscription[] EMPTY = new InnerSubscription[0];
        static final InnerSubscription[] TERMINATED = new InnerSubscription[0];
        private static final long serialVersionUID = 7224554242710036740L;
        final ReplayBuffer<T> buffer;
        boolean done;
        final AtomicInteger management = new AtomicInteger();
        long maxChildRequested;
        long maxUpstreamRequested;
        final AtomicBoolean shouldConnect = new AtomicBoolean();
        final AtomicReference<InnerSubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);

        ReplaySubscriber(ReplayBuffer<T> replayBuffer) {
            this.buffer = replayBuffer;
        }

        public boolean isDisposed() {
            return this.subscribers.get() == TERMINATED;
        }

        public void dispose() {
            this.subscribers.set(TERMINATED);
            SubscriptionHelper.cancel(this);
        }

        /* access modifiers changed from: 0000 */
        public boolean add(InnerSubscription<T> innerSubscription) {
            InnerSubscription[] innerSubscriptionArr;
            InnerSubscription[] innerSubscriptionArr2;
            if (innerSubscription != null) {
                do {
                    innerSubscriptionArr = (InnerSubscription[]) this.subscribers.get();
                    if (innerSubscriptionArr == TERMINATED) {
                        return false;
                    }
                    int length = innerSubscriptionArr.length;
                    innerSubscriptionArr2 = new InnerSubscription[(length + 1)];
                    System.arraycopy(innerSubscriptionArr, 0, innerSubscriptionArr2, 0, length);
                    innerSubscriptionArr2[length] = innerSubscription;
                } while (!this.subscribers.compareAndSet(innerSubscriptionArr, innerSubscriptionArr2));
                return true;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: 0000 */
        public void remove(InnerSubscription<T> innerSubscription) {
            InnerSubscription[] innerSubscriptionArr;
            InnerSubscription[] innerSubscriptionArr2;
            do {
                innerSubscriptionArr = (InnerSubscription[]) this.subscribers.get();
                int length = innerSubscriptionArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (innerSubscriptionArr[i2].equals(innerSubscription)) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            innerSubscriptionArr2 = EMPTY;
                        } else {
                            InnerSubscription[] innerSubscriptionArr3 = new InnerSubscription[(length - 1)];
                            System.arraycopy(innerSubscriptionArr, 0, innerSubscriptionArr3, 0, i);
                            System.arraycopy(innerSubscriptionArr, i + 1, innerSubscriptionArr3, i, (length - i) - 1);
                            innerSubscriptionArr2 = innerSubscriptionArr3;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.subscribers.compareAndSet(innerSubscriptionArr, innerSubscriptionArr2));
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                manageRequests();
                for (InnerSubscription replay : (InnerSubscription[]) this.subscribers.get()) {
                    this.buffer.replay(replay);
                }
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                this.buffer.next(t);
                for (InnerSubscription replay : (InnerSubscription[]) this.subscribers.get()) {
                    this.buffer.replay(replay);
                }
            }
        }

        public void onError(Throwable th) {
            if (!this.done) {
                this.done = true;
                this.buffer.error(th);
                for (InnerSubscription replay : (InnerSubscription[]) this.subscribers.getAndSet(TERMINATED)) {
                    this.buffer.replay(replay);
                }
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.buffer.complete();
                for (InnerSubscription replay : (InnerSubscription[]) this.subscribers.getAndSet(TERMINATED)) {
                    this.buffer.replay(replay);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void manageRequests() {
            if (this.management.getAndIncrement() == 0) {
                int i = 1;
                while (!isDisposed()) {
                    InnerSubscription[] innerSubscriptionArr = (InnerSubscription[]) this.subscribers.get();
                    long j = this.maxChildRequested;
                    long j2 = j;
                    for (InnerSubscription innerSubscription : innerSubscriptionArr) {
                        j2 = Math.max(j2, innerSubscription.totalRequested.get());
                    }
                    long j3 = this.maxUpstreamRequested;
                    Subscription subscription = (Subscription) get();
                    long j4 = j2 - j;
                    if (j4 != 0) {
                        this.maxChildRequested = j2;
                        if (subscription == null) {
                            long j5 = j3 + j4;
                            if (j5 < 0) {
                                j5 = Long.MAX_VALUE;
                            }
                            this.maxUpstreamRequested = j5;
                        } else if (j3 != 0) {
                            this.maxUpstreamRequested = 0;
                            subscription.request(j3 + j4);
                        } else {
                            subscription.request(j4);
                        }
                    } else if (!(j3 == 0 || subscription == null)) {
                        this.maxUpstreamRequested = 0;
                        subscription.request(j3);
                    }
                    i = this.management.addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$ScheduledReplayBufferTask */
    static final class ScheduledReplayBufferTask<T> implements Callable<ReplayBuffer<T>> {
        private final int bufferSize;
        private final long maxAge;
        private final Scheduler scheduler;
        private final TimeUnit unit;

        ScheduledReplayBufferTask(int i, long j, TimeUnit timeUnit, Scheduler scheduler2) {
            this.bufferSize = i;
            this.maxAge = j;
            this.unit = timeUnit;
            this.scheduler = scheduler2;
        }

        public ReplayBuffer<T> call() {
            SizeAndTimeBoundReplayBuffer sizeAndTimeBoundReplayBuffer = new SizeAndTimeBoundReplayBuffer(this.bufferSize, this.maxAge, this.unit, this.scheduler);
            return sizeAndTimeBoundReplayBuffer;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$SizeAndTimeBoundReplayBuffer */
    static final class SizeAndTimeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = 3457957419649567404L;
        final int limit;
        final long maxAge;
        final Scheduler scheduler;
        final TimeUnit unit;

        SizeAndTimeBoundReplayBuffer(int i, long j, TimeUnit timeUnit, Scheduler scheduler2) {
            this.scheduler = scheduler2;
            this.limit = i;
            this.maxAge = j;
            this.unit = timeUnit;
        }

        /* access modifiers changed from: 0000 */
        public Object enterTransform(Object obj) {
            return new Timed(obj, this.scheduler.now(this.unit), this.unit);
        }

        /* access modifiers changed from: 0000 */
        public Object leaveTransform(Object obj) {
            return ((Timed) obj).value();
        }

        /* access modifiers changed from: 0000 */
        public void truncate() {
            Node node;
            long now = this.scheduler.now(this.unit) - this.maxAge;
            Node node2 = (Node) get();
            Node node3 = (Node) node2.get();
            int i = 0;
            while (true) {
                Node node4 = node3;
                node = node2;
                node2 = node4;
                if (node2 != null) {
                    if (this.size <= this.limit) {
                        if (((Timed) node2.value).time() > now) {
                            break;
                        }
                        i++;
                        this.size--;
                        node3 = (Node) node2.get();
                    } else {
                        i++;
                        this.size--;
                        node3 = (Node) node2.get();
                    }
                } else {
                    break;
                }
            }
            if (i != 0) {
                setFirst(node);
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:9:0x003e  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void truncateFinal() {
            /*
                r10 = this;
                io.reactivex.Scheduler r0 = r10.scheduler
                java.util.concurrent.TimeUnit r1 = r10.unit
                long r0 = r0.now(r1)
                long r2 = r10.maxAge
                long r0 = r0 - r2
                java.lang.Object r2 = r10.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r2 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.Node) r2
                java.lang.Object r3 = r2.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r3 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.Node) r3
                r4 = 0
            L_0x0018:
                r9 = r3
                r3 = r2
                r2 = r9
                if (r2 == 0) goto L_0x003c
                int r5 = r10.size
                r6 = 1
                if (r5 <= r6) goto L_0x003c
                java.lang.Object r5 = r2.value
                io.reactivex.schedulers.Timed r5 = (p043io.reactivex.schedulers.Timed) r5
                long r7 = r5.time()
                int r5 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r5 > 0) goto L_0x003c
                int r4 = r4 + 1
                int r3 = r10.size
                int r3 = r3 - r6
                r10.size = r3
                java.lang.Object r3 = r2.get()
                io.reactivex.internal.operators.flowable.FlowableReplay$Node r3 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.Node) r3
                goto L_0x0018
            L_0x003c:
                if (r4 == 0) goto L_0x0041
                r10.setFirst(r3)
            L_0x0041:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowableReplay.SizeAndTimeBoundReplayBuffer.truncateFinal():void");
        }

        /* access modifiers changed from: 0000 */
        public Node getHead() {
            Node node;
            long now = this.scheduler.now(this.unit) - this.maxAge;
            Node node2 = (Node) get();
            Object obj = node2.get();
            while (true) {
                Node node3 = (Node) obj;
                node = node2;
                node2 = node3;
                if (node2 != null) {
                    Timed timed = (Timed) node2.value;
                    if (NotificationLite.isComplete(timed.value()) || NotificationLite.isError(timed.value()) || timed.time() > now) {
                        break;
                    }
                    obj = node2.get();
                } else {
                    break;
                }
            }
            return node;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$SizeBoundReplayBuffer */
    static final class SizeBoundReplayBuffer<T> extends BoundedReplayBuffer<T> {
        private static final long serialVersionUID = -5898283885385201806L;
        final int limit;

        SizeBoundReplayBuffer(int i) {
            this.limit = i;
        }

        /* access modifiers changed from: 0000 */
        public void truncate() {
            if (this.size > this.limit) {
                removeFirst();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableReplay$UnboundedReplayBuffer */
    static final class UnboundedReplayBuffer<T> extends ArrayList<Object> implements ReplayBuffer<T> {
        private static final long serialVersionUID = 7063189396499112664L;
        volatile int size;

        UnboundedReplayBuffer(int i) {
            super(i);
        }

        public void next(T t) {
            add(NotificationLite.next(t));
            this.size++;
        }

        public void error(Throwable th) {
            add(NotificationLite.error(th));
            this.size++;
        }

        public void complete() {
            add(NotificationLite.complete());
            this.size++;
        }

        /* JADX INFO: used method not loaded: io.reactivex.internal.util.NotificationLite.accept(java.lang.Object, org.reactivestreams.Subscriber):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
            if (r15.isDisposed() == false) goto L_0x0016;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0015, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0016, code lost:
            r1 = r14.size;
            r2 = (java.lang.Integer) r15.index();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
            if (r2 == null) goto L_0x0026;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0021, code lost:
            r2 = r2.intValue();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0026, code lost:
            r2 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0027, code lost:
            r4 = r15.get();
            r8 = r4;
            r10 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
            if (r8 == 0) goto L_0x0065;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0033, code lost:
            if (r2 >= r1) goto L_0x0065;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0035, code lost:
            r12 = get(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x003d, code lost:
            if (p043io.reactivex.internal.util.NotificationLite.accept(r12, (org.reactivestreams.Subscriber) r0) == false) goto L_0x0040;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x003f, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0044, code lost:
            if (r15.isDisposed() == false) goto L_0x0047;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0046, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0047, code lost:
            r2 = r2 + 1;
            r8 = r8 - 1;
            r10 = r10 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x004e, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x004f, code lost:
            p043io.reactivex.exceptions.Exceptions.throwIfFatal(r1);
            r15.dispose();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0061, code lost:
            r0.onError(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0064, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0067, code lost:
            if (r10 == 0) goto L_0x007b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0069, code lost:
            r15.index = java.lang.Integer.valueOf(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0076, code lost:
            if (r4 == Long.MAX_VALUE) goto L_0x007b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0078, code lost:
            r15.produced(r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x007b, code lost:
            monitor-enter(r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x007e, code lost:
            if (r15.missed != false) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0080, code lost:
            r15.emitting = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x0082, code lost:
            monitor-exit(r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x0083, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0084, code lost:
            r15.missed = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x0086, code lost:
            monitor-exit(r15);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x000d, code lost:
            r0 = r15.child;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void replay(p043io.reactivex.internal.operators.flowable.FlowableReplay.InnerSubscription<T> r15) {
            /*
                r14 = this;
                monitor-enter(r15)
                boolean r0 = r15.emitting     // Catch:{ all -> 0x008b }
                r1 = 1
                if (r0 == 0) goto L_0x000a
                r15.missed = r1     // Catch:{ all -> 0x008b }
                monitor-exit(r15)     // Catch:{ all -> 0x008b }
                return
            L_0x000a:
                r15.emitting = r1     // Catch:{ all -> 0x008b }
                monitor-exit(r15)     // Catch:{ all -> 0x008b }
                org.reactivestreams.Subscriber<? super T> r0 = r15.child
            L_0x000f:
                boolean r1 = r15.isDisposed()
                if (r1 == 0) goto L_0x0016
                return
            L_0x0016:
                int r1 = r14.size
                java.lang.Object r2 = r15.index()
                java.lang.Integer r2 = (java.lang.Integer) r2
                r3 = 0
                if (r2 == 0) goto L_0x0026
                int r2 = r2.intValue()
                goto L_0x0027
            L_0x0026:
                r2 = 0
            L_0x0027:
                long r4 = r15.get()
                r6 = 0
                r8 = r4
                r10 = r6
            L_0x002f:
                int r12 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r12 == 0) goto L_0x0065
                if (r2 >= r1) goto L_0x0065
                java.lang.Object r12 = r14.get(r2)
                boolean r12 = p043io.reactivex.internal.util.NotificationLite.accept(r12, r0)     // Catch:{ all -> 0x004e }
                if (r12 == 0) goto L_0x0040
                return
            L_0x0040:
                boolean r12 = r15.isDisposed()
                if (r12 == 0) goto L_0x0047
                return
            L_0x0047:
                int r2 = r2 + 1
                r12 = 1
                long r8 = r8 - r12
                long r10 = r10 + r12
                goto L_0x002f
            L_0x004e:
                r1 = move-exception
                p043io.reactivex.exceptions.Exceptions.throwIfFatal(r1)
                r15.dispose()
                boolean r15 = p043io.reactivex.internal.util.NotificationLite.isError(r12)
                if (r15 != 0) goto L_0x0064
                boolean r15 = p043io.reactivex.internal.util.NotificationLite.isComplete(r12)
                if (r15 != 0) goto L_0x0064
                r0.onError(r1)
            L_0x0064:
                return
            L_0x0065:
                int r1 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r1 == 0) goto L_0x007b
                java.lang.Integer r1 = java.lang.Integer.valueOf(r2)
                r15.index = r1
                r1 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r6 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
                if (r6 == 0) goto L_0x007b
                r15.produced(r10)
            L_0x007b:
                monitor-enter(r15)
                boolean r1 = r15.missed     // Catch:{ all -> 0x0088 }
                if (r1 != 0) goto L_0x0084
                r15.emitting = r3     // Catch:{ all -> 0x0088 }
                monitor-exit(r15)     // Catch:{ all -> 0x0088 }
                return
            L_0x0084:
                r15.missed = r3     // Catch:{ all -> 0x0088 }
                monitor-exit(r15)     // Catch:{ all -> 0x0088 }
                goto L_0x000f
            L_0x0088:
                r0 = move-exception
                monitor-exit(r15)     // Catch:{ all -> 0x0088 }
                throw r0
            L_0x008b:
                r0 = move-exception
                monitor-exit(r15)     // Catch:{ all -> 0x008b }
                goto L_0x008f
            L_0x008e:
                throw r0
            L_0x008f:
                goto L_0x008e
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowableReplay.UnboundedReplayBuffer.replay(io.reactivex.internal.operators.flowable.FlowableReplay$InnerSubscription):void");
        }
    }

    public static <U, R> Flowable<R> multicastSelector(Callable<? extends ConnectableFlowable<U>> callable, Function<? super Flowable<U>, ? extends Publisher<R>> function) {
        return Flowable.unsafeCreate(new MultiCastPublisher(callable, function));
    }

    public static <T> ConnectableFlowable<T> observeOn(ConnectableFlowable<T> connectableFlowable, Scheduler scheduler) {
        return RxJavaPlugins.onAssembly((ConnectableFlowable<T>) new ConnectableFlowableReplay<T>(connectableFlowable, connectableFlowable.observeOn(scheduler)));
    }

    public static <T> ConnectableFlowable<T> createFrom(Flowable<? extends T> flowable) {
        return create(flowable, DEFAULT_UNBOUNDED_FACTORY);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i) {
        if (i == Integer.MAX_VALUE) {
            return createFrom(flowable);
        }
        return create(flowable, (Callable<? extends ReplayBuffer<T>>) new ReplayBufferTask<Object>(i));
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler) {
        return create(flowable, j, timeUnit, scheduler, Integer.MAX_VALUE);
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        ScheduledReplayBufferTask scheduledReplayBufferTask = new ScheduledReplayBufferTask(i, j, timeUnit, scheduler);
        return create(flowable, (Callable<? extends ReplayBuffer<T>>) scheduledReplayBufferTask);
    }

    static <T> ConnectableFlowable<T> create(Flowable<T> flowable, Callable<? extends ReplayBuffer<T>> callable) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable<T>) new FlowableReplay<T>(new ReplayPublisher(atomicReference, callable), flowable, atomicReference, callable));
    }

    private FlowableReplay(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<ReplaySubscriber<T>> atomicReference, Callable<? extends ReplayBuffer<T>> callable) {
        this.onSubscribe = publisher;
        this.source = flowable;
        this.current = atomicReference;
        this.bufferFactory = callable;
    }

    public Publisher<T> source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.onSubscribe.subscribe(subscriber);
    }

    public void dispose() {
        this.current.lazySet(null);
    }

    public boolean isDisposed() {
        Disposable disposable = (Disposable) this.current.get();
        return disposable == null || disposable.isDisposed();
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(p043io.reactivex.functions.Consumer<? super p043io.reactivex.disposables.Disposable> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber<T>> r0 = r4.current
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber r0 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.ReplaySubscriber) r0
            if (r0 == 0) goto L_0x0010
            boolean r1 = r0.isDisposed()
            if (r1 == 0) goto L_0x0027
        L_0x0010:
            java.util.concurrent.Callable<? extends io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer<T>> r1 = r4.bufferFactory     // Catch:{ all -> 0x0057 }
            java.lang.Object r1 = r1.call()     // Catch:{ all -> 0x0057 }
            io.reactivex.internal.operators.flowable.FlowableReplay$ReplayBuffer r1 = (p043io.reactivex.internal.operators.flowable.FlowableReplay.ReplayBuffer) r1     // Catch:{ all -> 0x0057 }
            io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber r2 = new io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber
            r2.<init>(r1)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowableReplay$ReplaySubscriber<T>> r1 = r4.current
            boolean r0 = r1.compareAndSet(r0, r2)
            if (r0 != 0) goto L_0x0026
            goto L_0x0000
        L_0x0026:
            r0 = r2
        L_0x0027:
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.shouldConnect
            boolean r1 = r1.get()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x003b
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.shouldConnect
            boolean r1 = r1.compareAndSet(r3, r2)
            if (r1 == 0) goto L_0x003b
            r1 = 1
            goto L_0x003c
        L_0x003b:
            r1 = 0
        L_0x003c:
            r5.accept(r0)     // Catch:{ all -> 0x0047 }
            if (r1 == 0) goto L_0x0046
            io.reactivex.Flowable<T> r5 = r4.source
            r5.subscribe(r0)
        L_0x0046:
            return
        L_0x0047:
            r5 = move-exception
            if (r1 == 0) goto L_0x004f
            java.util.concurrent.atomic.AtomicBoolean r0 = r0.shouldConnect
            r0.compareAndSet(r2, r3)
        L_0x004f:
            p043io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = p043io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            throw r5
        L_0x0057:
            r5 = move-exception
            p043io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = p043io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            goto L_0x0061
        L_0x0060:
            throw r5
        L_0x0061:
            goto L_0x0060
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowableReplay.connect(io.reactivex.functions.Consumer):void");
    }
}