package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Action;
import p043io.reactivex.internal.fuseable.ConditionalSubscriber;
import p043io.reactivex.internal.fuseable.QueueSubscription;
import p043io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDoFinally */
public final class FlowableDoFinally<T> extends AbstractFlowableWithUpstream<T, T> {
    final Action onFinally;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDoFinally$DoFinallyConditionalSubscriber */
    static final class DoFinallyConditionalSubscriber<T> extends BasicIntQueueSubscription<T> implements ConditionalSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final ConditionalSubscriber<? super T> actual;
        final Action onFinally;

        /* renamed from: qs */
        QueueSubscription<T> f3851qs;

        /* renamed from: s */
        Subscription f3852s;
        boolean syncFused;

        DoFinallyConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Action action) {
            this.actual = conditionalSubscriber;
            this.onFinally = action;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3852s, subscription)) {
                this.f3852s = subscription;
                if (subscription instanceof QueueSubscription) {
                    this.f3851qs = (QueueSubscription) subscription;
                }
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public boolean tryOnNext(T t) {
            return this.actual.tryOnNext(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
            runFinally();
        }

        public void onComplete() {
            this.actual.onComplete();
            runFinally();
        }

        public void cancel() {
            this.f3852s.cancel();
            runFinally();
        }

        public void request(long j) {
            this.f3852s.request(j);
        }

        public int requestFusion(int i) {
            QueueSubscription<T> queueSubscription = this.f3851qs;
            if (queueSubscription == null || (i & 4) != 0) {
                return 0;
            }
            int requestFusion = queueSubscription.requestFusion(i);
            if (requestFusion != 0) {
                boolean z = true;
                if (requestFusion != 1) {
                    z = false;
                }
                this.syncFused = z;
            }
            return requestFusion;
        }

        public void clear() {
            this.f3851qs.clear();
        }

        public boolean isEmpty() {
            return this.f3851qs.isEmpty();
        }

        public T poll() throws Exception {
            T poll = this.f3851qs.poll();
            if (poll == null && this.syncFused) {
                runFinally();
            }
            return poll;
        }

        /* access modifiers changed from: 0000 */
        public void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDoFinally$DoFinallySubscriber */
    static final class DoFinallySubscriber<T> extends BasicIntQueueSubscription<T> implements FlowableSubscriber<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final Subscriber<? super T> actual;
        final Action onFinally;

        /* renamed from: qs */
        QueueSubscription<T> f3853qs;

        /* renamed from: s */
        Subscription f3854s;
        boolean syncFused;

        DoFinallySubscriber(Subscriber<? super T> subscriber, Action action) {
            this.actual = subscriber;
            this.onFinally = action;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3854s, subscription)) {
                this.f3854s = subscription;
                if (subscription instanceof QueueSubscription) {
                    this.f3853qs = (QueueSubscription) subscription;
                }
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
            runFinally();
        }

        public void onComplete() {
            this.actual.onComplete();
            runFinally();
        }

        public void cancel() {
            this.f3854s.cancel();
            runFinally();
        }

        public void request(long j) {
            this.f3854s.request(j);
        }

        public int requestFusion(int i) {
            QueueSubscription<T> queueSubscription = this.f3853qs;
            if (queueSubscription == null || (i & 4) != 0) {
                return 0;
            }
            int requestFusion = queueSubscription.requestFusion(i);
            if (requestFusion != 0) {
                boolean z = true;
                if (requestFusion != 1) {
                    z = false;
                }
                this.syncFused = z;
            }
            return requestFusion;
        }

        public void clear() {
            this.f3853qs.clear();
        }

        public boolean isEmpty() {
            return this.f3853qs.isEmpty();
        }

        public T poll() throws Exception {
            T poll = this.f3853qs.poll();
            if (poll == null && this.syncFused) {
                runFinally();
            }
            return poll;
        }

        /* access modifiers changed from: 0000 */
        public void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public FlowableDoFinally(Flowable<T> flowable, Action action) {
        super(flowable);
        this.onFinally = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber<? super T>) new DoFinallyConditionalSubscriber<Object>((ConditionalSubscriber) subscriber, this.onFinally));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new DoFinallySubscriber<Object>(subscriber, this.onFinally));
        }
    }
}
