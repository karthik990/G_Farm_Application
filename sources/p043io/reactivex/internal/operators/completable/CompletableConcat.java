package p043io.reactivex.internal.operators.completable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.exceptions.MissingBackpressureException;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.QueueSubscription;
import p043io.reactivex.internal.fuseable.SimpleQueue;
import p043io.reactivex.internal.queue.SpscArrayQueue;
import p043io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableConcat */
public final class CompletableConcat extends Completable {
    final int prefetch;
    final Publisher<? extends CompletableSource> sources;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableConcat$CompletableConcatSubscriber */
    static final class CompletableConcatSubscriber extends AtomicInteger implements FlowableSubscriber<CompletableSource>, Disposable {
        private static final long serialVersionUID = 9032184911934499404L;
        volatile boolean active;
        final CompletableObserver actual;
        int consumed;
        volatile boolean done;
        final ConcatInnerObserver inner = new ConcatInnerObserver(this);
        final int limit;
        final AtomicBoolean once = new AtomicBoolean();
        final int prefetch;
        SimpleQueue<CompletableSource> queue;

        /* renamed from: s */
        Subscription f3798s;
        int sourceFused;

        /* renamed from: io.reactivex.internal.operators.completable.CompletableConcat$CompletableConcatSubscriber$ConcatInnerObserver */
        static final class ConcatInnerObserver extends AtomicReference<Disposable> implements CompletableObserver {
            private static final long serialVersionUID = -5454794857847146511L;
            final CompletableConcatSubscriber parent;

            ConcatInnerObserver(CompletableConcatSubscriber completableConcatSubscriber) {
                this.parent = completableConcatSubscriber;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this, disposable);
            }

            public void onError(Throwable th) {
                this.parent.innerError(th);
            }

            public void onComplete() {
                this.parent.innerComplete();
            }
        }

        CompletableConcatSubscriber(CompletableObserver completableObserver, int i) {
            this.actual = completableObserver;
            this.prefetch = i;
            this.limit = i - (i >> 2);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3798s, subscription)) {
                this.f3798s = subscription;
                int i = this.prefetch;
                long j = i == Integer.MAX_VALUE ? Long.MAX_VALUE : (long) i;
                if (subscription instanceof QueueSubscription) {
                    QueueSubscription queueSubscription = (QueueSubscription) subscription;
                    int requestFusion = queueSubscription.requestFusion(3);
                    if (requestFusion == 1) {
                        this.sourceFused = requestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        drain();
                        return;
                    } else if (requestFusion == 2) {
                        this.sourceFused = requestFusion;
                        this.queue = queueSubscription;
                        this.actual.onSubscribe(this);
                        subscription.request(j);
                        return;
                    }
                }
                int i2 = this.prefetch;
                if (i2 == Integer.MAX_VALUE) {
                    this.queue = new SpscLinkedArrayQueue(Flowable.bufferSize());
                } else {
                    this.queue = new SpscArrayQueue(i2);
                }
                this.actual.onSubscribe(this);
                subscription.request(j);
            }
        }

        public void onNext(CompletableSource completableSource) {
            if (this.sourceFused != 0 || this.queue.offer(completableSource)) {
                drain();
            } else {
                onError(new MissingBackpressureException());
            }
        }

        public void onError(Throwable th) {
            if (this.once.compareAndSet(false, true)) {
                DisposableHelper.dispose(this.inner);
                this.actual.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        public void dispose() {
            this.f3798s.cancel();
            DisposableHelper.dispose(this.inner);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.inner.get());
        }

        /* access modifiers changed from: 0000 */
        public void drain() {
            if (getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.active) {
                        boolean z = this.done;
                        try {
                            CompletableSource completableSource = (CompletableSource) this.queue.poll();
                            boolean z2 = completableSource == null;
                            if (z && z2) {
                                if (this.once.compareAndSet(false, true)) {
                                    this.actual.onComplete();
                                }
                                return;
                            } else if (!z2) {
                                this.active = true;
                                completableSource.subscribe(this.inner);
                                request();
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            innerError(th);
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void request() {
            if (this.sourceFused != 1) {
                int i = this.consumed + 1;
                if (i == this.limit) {
                    this.consumed = 0;
                    this.f3798s.request((long) i);
                    return;
                }
                this.consumed = i;
            }
        }

        /* access modifiers changed from: 0000 */
        public void innerError(Throwable th) {
            if (this.once.compareAndSet(false, true)) {
                this.f3798s.cancel();
                this.actual.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* access modifiers changed from: 0000 */
        public void innerComplete() {
            this.active = false;
            drain();
        }
    }

    public CompletableConcat(Publisher<? extends CompletableSource> publisher, int i) {
        this.sources = publisher;
        this.prefetch = i;
    }

    public void subscribeActual(CompletableObserver completableObserver) {
        this.sources.subscribe(new CompletableConcatSubscriber(completableObserver, this.prefetch));
    }
}
