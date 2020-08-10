package p043io.reactivex.internal.operators.parallel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Predicate;
import p043io.reactivex.internal.fuseable.ConditionalSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.parallel.ParallelFlowable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.parallel.ParallelFilter */
public final class ParallelFilter<T> extends ParallelFlowable<T> {
    final Predicate<? super T> predicate;
    final ParallelFlowable<T> source;

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilter$BaseFilterSubscriber */
    static abstract class BaseFilterSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final Predicate<? super T> predicate;

        /* renamed from: s */
        Subscription f4103s;

        BaseFilterSubscriber(Predicate<? super T> predicate2) {
            this.predicate = predicate2;
        }

        public final void request(long j) {
            this.f4103s.request(j);
        }

        public final void cancel() {
            this.f4103s.cancel();
        }

        public final void onNext(T t) {
            if (!tryOnNext(t) && !this.done) {
                this.f4103s.request(1);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilter$ParallelFilterConditionalSubscriber */
    static final class ParallelFilterConditionalSubscriber<T> extends BaseFilterSubscriber<T> {
        final ConditionalSubscriber<? super T> actual;

        ParallelFilterConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Predicate<? super T> predicate) {
            super(predicate);
            this.actual = conditionalSubscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f4103s, subscription)) {
                this.f4103s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public boolean tryOnNext(T t) {
            if (!this.done) {
                try {
                    if (this.predicate.test(t)) {
                        return this.actual.tryOnNext(t);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                }
            }
            return false;
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilter$ParallelFilterSubscriber */
    static final class ParallelFilterSubscriber<T> extends BaseFilterSubscriber<T> {
        final Subscriber<? super T> actual;

        ParallelFilterSubscriber(Subscriber<? super T> subscriber, Predicate<? super T> predicate) {
            super(predicate);
            this.actual = subscriber;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f4103s, subscription)) {
                this.f4103s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public boolean tryOnNext(T t) {
            if (!this.done) {
                try {
                    if (this.predicate.test(t)) {
                        this.actual.onNext(t);
                        return true;
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
                    onError(th);
                }
            }
            return false;
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }
    }

    public ParallelFilter(ParallelFlowable<T> parallelFlowable, Predicate<? super T> predicate2) {
        this.source = parallelFlowable;
        this.predicate = predicate2;
    }

    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            for (int i = 0; i < length; i++) {
                ConditionalSubscriber conditionalSubscriber = subscriberArr[i];
                if (conditionalSubscriber instanceof ConditionalSubscriber) {
                    subscriberArr2[i] = new ParallelFilterConditionalSubscriber(conditionalSubscriber, this.predicate);
                } else {
                    subscriberArr2[i] = new ParallelFilterSubscriber(conditionalSubscriber, this.predicate);
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }

    public int parallelism() {
        return this.source.parallelism();
    }
}
