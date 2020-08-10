package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.MissingBackpressureException;
import p043io.reactivex.flowables.ConnectableFlowable;
import p043io.reactivex.internal.fuseable.HasUpstreamPublisher;
import p043io.reactivex.internal.fuseable.QueueSubscription;
import p043io.reactivex.internal.fuseable.SimpleQueue;
import p043io.reactivex.internal.queue.SpscArrayQueue;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BackpressureHelper;
import p043io.reactivex.internal.util.NotificationLite;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowablePublish */
public final class FlowablePublish<T> extends ConnectableFlowable<T> implements HasUpstreamPublisher<T> {
    static final long CANCELLED = Long.MIN_VALUE;
    final int bufferSize;
    final AtomicReference<PublishSubscriber<T>> current;
    final Publisher<T> onSubscribe;
    final Flowable<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowablePublish$FlowablePublisher */
    static final class FlowablePublisher<T> implements Publisher<T> {
        private final int bufferSize;
        private final AtomicReference<PublishSubscriber<T>> curr;

        FlowablePublisher(AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
            this.curr = atomicReference;
            this.bufferSize = i;
        }

        public void subscribe(Subscriber<? super T> subscriber) {
            PublishSubscriber<T> publishSubscriber;
            InnerSubscriber innerSubscriber = new InnerSubscriber(subscriber);
            subscriber.onSubscribe(innerSubscriber);
            while (true) {
                publishSubscriber = (PublishSubscriber) this.curr.get();
                if (publishSubscriber == null || publishSubscriber.isDisposed()) {
                    PublishSubscriber<T> publishSubscriber2 = new PublishSubscriber<>(this.curr, this.bufferSize);
                    if (!this.curr.compareAndSet(publishSubscriber, publishSubscriber2)) {
                        continue;
                    } else {
                        publishSubscriber = publishSubscriber2;
                    }
                }
                if (publishSubscriber.add(innerSubscriber)) {
                    break;
                }
            }
            if (innerSubscriber.get() == Long.MIN_VALUE) {
                publishSubscriber.remove(innerSubscriber);
            } else {
                innerSubscriber.parent = publishSubscriber;
            }
            publishSubscriber.dispatch();
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber */
    static final class InnerSubscriber<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = -4453897557930727610L;
        final Subscriber<? super T> child;
        volatile PublishSubscriber<T> parent;

        InnerSubscriber(Subscriber<? super T> subscriber) {
            this.child = subscriber;
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.addCancel(this, j);
                PublishSubscriber<T> publishSubscriber = this.parent;
                if (publishSubscriber != null) {
                    publishSubscriber.dispatch();
                }
            }
        }

        public long produced(long j) {
            return BackpressureHelper.producedCancel(this, j);
        }

        public void cancel() {
            if (get() != Long.MIN_VALUE && getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                PublishSubscriber<T> publishSubscriber = this.parent;
                if (publishSubscriber != null) {
                    publishSubscriber.remove(this);
                    publishSubscriber.dispatch();
                }
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber */
    static final class PublishSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Disposable {
        static final InnerSubscriber[] EMPTY = new InnerSubscriber[0];
        static final InnerSubscriber[] TERMINATED = new InnerSubscriber[0];
        private static final long serialVersionUID = -202316842419149694L;
        final int bufferSize;
        final AtomicReference<PublishSubscriber<T>> current;
        volatile SimpleQueue<T> queue;

        /* renamed from: s */
        final AtomicReference<Subscription> f3882s = new AtomicReference<>();
        final AtomicBoolean shouldConnect;
        int sourceMode;
        final AtomicReference<InnerSubscriber[]> subscribers = new AtomicReference<>(EMPTY);
        volatile Object terminalEvent;

        PublishSubscriber(AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
            this.current = atomicReference;
            this.shouldConnect = new AtomicBoolean();
            this.bufferSize = i;
        }

        public void dispose() {
            Object obj = this.subscribers.get();
            Object obj2 = TERMINATED;
            if (obj != obj2 && ((InnerSubscriber[]) this.subscribers.getAndSet(obj2)) != TERMINATED) {
                this.current.compareAndSet(this, null);
                SubscriptionHelper.cancel(this.f3882s);
            }
        }

        public boolean isDisposed() {
            return this.subscribers.get() == TERMINATED;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this.f3882s, subscription)) {
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        this.terminalEvent = NotificationLite.complete();
                        dispatch();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceMode = requestFusion;
                        this.queue = queueSubscription;
                        subscription.request((long) this.bufferSize);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.bufferSize);
                subscription.request((long) this.bufferSize);
            }
        }

        public void onNext(T t) {
            if (this.sourceMode != 0 || this.queue.offer(t)) {
                dispatch();
            } else {
                onError(new MissingBackpressureException("Prefetch queue is full?!"));
            }
        }

        public void onError(Throwable th) {
            if (this.terminalEvent == null) {
                this.terminalEvent = NotificationLite.error(th);
                dispatch();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (this.terminalEvent == null) {
                this.terminalEvent = NotificationLite.complete();
                dispatch();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean add(InnerSubscriber<T> innerSubscriber) {
            InnerSubscriber[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.subscribers.get();
                if (innerSubscriberArr == TERMINATED) {
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[(length + 1)];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void remove(InnerSubscriber<T> innerSubscriber) {
            InnerSubscriber[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = (InnerSubscriber[]) this.subscribers.get();
                int length = innerSubscriberArr.length;
                if (length == 0) {
                    break;
                }
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (innerSubscriberArr[i2].equals(innerSubscriber)) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        innerSubscriberArr2 = EMPTY;
                    } else {
                        InnerSubscriber[] innerSubscriberArr3 = new InnerSubscriber[(length - 1)];
                        System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr3, 0, i);
                        System.arraycopy(innerSubscriberArr, i + 1, innerSubscriberArr3, i, (length - i) - 1);
                        innerSubscriberArr2 = innerSubscriberArr3;
                    }
                } else {
                    return;
                }
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, innerSubscriberArr2));
        }

        /* access modifiers changed from: 0000 */
        public boolean checkTerminated(Object obj, boolean z) {
            int i = 0;
            if (obj != null) {
                if (!NotificationLite.isComplete(obj)) {
                    Throwable error = NotificationLite.getError(obj);
                    this.current.compareAndSet(this, null);
                    InnerSubscriber[] innerSubscriberArr = (InnerSubscriber[]) this.subscribers.getAndSet(TERMINATED);
                    if (innerSubscriberArr.length != 0) {
                        int length = innerSubscriberArr.length;
                        while (i < length) {
                            innerSubscriberArr[i].child.onError(error);
                            i++;
                        }
                    } else {
                        RxJavaPlugins.onError(error);
                    }
                    return true;
                } else if (z) {
                    this.current.compareAndSet(this, null);
                    InnerSubscriber[] innerSubscriberArr2 = (InnerSubscriber[]) this.subscribers.getAndSet(TERMINATED);
                    int length2 = innerSubscriberArr2.length;
                    while (i < length2) {
                        innerSubscriberArr2[i].child.onComplete();
                        i++;
                    }
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x0115, code lost:
            if (r16 == false) goto L_0x0117;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void dispatch() {
            /*
                r19 = this;
                r1 = r19
                int r0 = r19.getAndIncrement()
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                r2 = 1
                r3 = 1
            L_0x000b:
                java.lang.Object r0 = r1.terminalEvent
                io.reactivex.internal.fuseable.SimpleQueue<T> r4 = r1.queue
                if (r4 == 0) goto L_0x001a
                boolean r6 = r4.isEmpty()
                if (r6 == 0) goto L_0x0018
                goto L_0x001a
            L_0x0018:
                r6 = 0
                goto L_0x001b
            L_0x001a:
                r6 = 1
            L_0x001b:
                boolean r0 = r1.checkTerminated(r0, r6)
                if (r0 == 0) goto L_0x0022
                return
            L_0x0022:
                if (r6 != 0) goto L_0x0119
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber[]> r0 = r1.subscribers
                java.lang.Object r0 = r0.get()
                r7 = r0
                io.reactivex.internal.operators.flowable.FlowablePublish$InnerSubscriber[] r7 = (p043io.reactivex.internal.operators.flowable.FlowablePublish.InnerSubscriber[]) r7
                int r0 = r7.length
                r8 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r10 = r7.length
                r11 = r8
                r8 = 0
                r9 = 0
            L_0x0037:
                r13 = 0
                if (r8 >= r10) goto L_0x005a
                r15 = r7[r8]
                r16 = r6
                long r5 = r15.get()
                int r15 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
                if (r15 < 0) goto L_0x004d
                long r5 = java.lang.Math.min(r11, r5)
                r11 = r5
                goto L_0x0055
            L_0x004d:
                r13 = -9223372036854775808
                int r15 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
                if (r15 != 0) goto L_0x0055
                int r9 = r9 + 1
            L_0x0055:
                int r8 = r8 + 1
                r6 = r16
                goto L_0x0037
            L_0x005a:
                r16 = r6
                r5 = 1
                if (r0 != r9) goto L_0x009d
                java.lang.Object r0 = r1.terminalEvent
                java.lang.Object r8 = r4.poll()     // Catch:{ all -> 0x0067 }
                goto L_0x007e
            L_0x0067:
                r0 = move-exception
                r4 = r0
                p043io.reactivex.exceptions.Exceptions.throwIfFatal(r4)
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r0 = r1.f3882s
                java.lang.Object r0 = r0.get()
                org.reactivestreams.Subscription r0 = (org.reactivestreams.Subscription) r0
                r0.cancel()
                java.lang.Object r0 = p043io.reactivex.internal.util.NotificationLite.error(r4)
                r1.terminalEvent = r0
                r8 = 0
            L_0x007e:
                if (r8 != 0) goto L_0x0082
                r4 = 1
                goto L_0x0083
            L_0x0082:
                r4 = 0
            L_0x0083:
                boolean r0 = r1.checkTerminated(r0, r4)
                if (r0 == 0) goto L_0x008a
                return
            L_0x008a:
                int r0 = r1.sourceMode
                if (r0 == r2) goto L_0x0099
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r0 = r1.f3882s
                java.lang.Object r0 = r0.get()
                org.reactivestreams.Subscription r0 = (org.reactivestreams.Subscription) r0
                r0.request(r5)
            L_0x0099:
                r15 = r3
                r4 = 1
                goto L_0x0117
            L_0x009d:
                r15 = r3
                r9 = 0
            L_0x009f:
                long r2 = (long) r9
                int r0 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
                if (r0 >= 0) goto L_0x00fb
                java.lang.Object r0 = r1.terminalEvent
                java.lang.Object r16 = r4.poll()     // Catch:{ all -> 0x00ab }
                goto L_0x00c4
            L_0x00ab:
                r0 = move-exception
                r16 = r0
                p043io.reactivex.exceptions.Exceptions.throwIfFatal(r16)
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r0 = r1.f3882s
                java.lang.Object r0 = r0.get()
                org.reactivestreams.Subscription r0 = (org.reactivestreams.Subscription) r0
                r0.cancel()
                java.lang.Object r0 = p043io.reactivex.internal.util.NotificationLite.error(r16)
                r1.terminalEvent = r0
                r16 = 0
            L_0x00c4:
                if (r16 != 0) goto L_0x00c8
                r8 = 1
                goto L_0x00c9
            L_0x00c8:
                r8 = 0
            L_0x00c9:
                boolean r0 = r1.checkTerminated(r0, r8)
                if (r0 == 0) goto L_0x00d0
                return
            L_0x00d0:
                if (r8 == 0) goto L_0x00d5
                r16 = r8
                goto L_0x00fb
            L_0x00d5:
                java.lang.Object r0 = p043io.reactivex.internal.util.NotificationLite.getValue(r16)
                int r2 = r7.length
                r3 = 0
            L_0x00db:
                if (r3 >= r2) goto L_0x00f4
                r10 = r7[r3]
                long r17 = r10.get()
                int r16 = (r17 > r13 ? 1 : (r17 == r13 ? 0 : -1))
                if (r16 <= 0) goto L_0x00ef
                org.reactivestreams.Subscriber<? super T> r13 = r10.child
                r13.onNext(r0)
                r10.produced(r5)
            L_0x00ef:
                int r3 = r3 + 1
                r13 = 0
                goto L_0x00db
            L_0x00f4:
                int r9 = r9 + 1
                r16 = r8
                r13 = 0
                goto L_0x009f
            L_0x00fb:
                if (r9 <= 0) goto L_0x010e
                int r0 = r1.sourceMode
                r4 = 1
                if (r0 == r4) goto L_0x010f
                java.util.concurrent.atomic.AtomicReference<org.reactivestreams.Subscription> r0 = r1.f3882s
                java.lang.Object r0 = r0.get()
                org.reactivestreams.Subscription r0 = (org.reactivestreams.Subscription) r0
                r0.request(r2)
                goto L_0x010f
            L_0x010e:
                r4 = 1
            L_0x010f:
                r2 = 0
                int r0 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
                if (r0 == 0) goto L_0x011b
                if (r16 != 0) goto L_0x011b
            L_0x0117:
                r3 = r15
                goto L_0x0123
            L_0x0119:
                r4 = 1
                r15 = r3
            L_0x011b:
                int r0 = -r15
                int r3 = r1.addAndGet(r0)
                if (r3 != 0) goto L_0x0123
                return
            L_0x0123:
                r2 = 1
                goto L_0x000b
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowablePublish.PublishSubscriber.dispatch():void");
        }
    }

    public static <T> ConnectableFlowable<T> create(Flowable<T> flowable, int i) {
        AtomicReference atomicReference = new AtomicReference();
        return RxJavaPlugins.onAssembly((ConnectableFlowable<T>) new FlowablePublish<T>(new FlowablePublisher(atomicReference, i), flowable, atomicReference, i));
    }

    private FlowablePublish(Publisher<T> publisher, Flowable<T> flowable, AtomicReference<PublishSubscriber<T>> atomicReference, int i) {
        this.onSubscribe = publisher;
        this.source = flowable;
        this.current = atomicReference;
        this.bufferSize = i;
    }

    public Publisher<T> source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.onSubscribe.subscribe(subscriber);
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(p043io.reactivex.functions.Consumer<? super p043io.reactivex.disposables.Disposable> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r0 = r4.current
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber r0 = (p043io.reactivex.internal.operators.flowable.FlowablePublish.PublishSubscriber) r0
            if (r0 == 0) goto L_0x0010
            boolean r1 = r0.isDisposed()
            if (r1 == 0) goto L_0x0023
        L_0x0010:
            io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber r1 = new io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r2 = r4.current
            int r3 = r4.bufferSize
            r1.<init>(r2, r3)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.flowable.FlowablePublish$PublishSubscriber<T>> r2 = r4.current
            boolean r0 = r2.compareAndSet(r0, r1)
            if (r0 != 0) goto L_0x0022
            goto L_0x0000
        L_0x0022:
            r0 = r1
        L_0x0023:
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.shouldConnect
            boolean r1 = r1.get()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0036
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.shouldConnect
            boolean r1 = r1.compareAndSet(r3, r2)
            if (r1 == 0) goto L_0x0036
            goto L_0x0037
        L_0x0036:
            r2 = 0
        L_0x0037:
            r5.accept(r0)     // Catch:{ all -> 0x0042 }
            if (r2 == 0) goto L_0x0041
            io.reactivex.Flowable<T> r5 = r4.source
            r5.subscribe(r0)
        L_0x0041:
            return
        L_0x0042:
            r5 = move-exception
            p043io.reactivex.exceptions.Exceptions.throwIfFatal(r5)
            java.lang.RuntimeException r5 = p043io.reactivex.internal.util.ExceptionHelper.wrapOrThrow(r5)
            goto L_0x004c
        L_0x004b:
            throw r5
        L_0x004c:
            goto L_0x004b
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.flowable.FlowablePublish.connect(io.reactivex.functions.Consumer):void");
    }
}
