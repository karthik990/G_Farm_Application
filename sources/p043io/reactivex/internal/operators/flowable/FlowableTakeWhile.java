package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Predicate;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeWhile */
public final class FlowableTakeWhile<T> extends AbstractFlowableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeWhile$TakeWhileSubscriber */
    static final class TakeWhileSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;
        boolean done;
        final Predicate<? super T> predicate;

        /* renamed from: s */
        Subscription f3919s;

        TakeWhileSubscriber(Subscriber<? super T> subscriber, Predicate<? super T> predicate2) {
            this.actual = subscriber;
            this.predicate = predicate2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3919s, subscription)) {
                this.f3919s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    if (!this.predicate.test(t)) {
                        this.done = true;
                        this.f3919s.cancel();
                        this.actual.onComplete();
                        return;
                    }
                    this.actual.onNext(t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f3919s.cancel();
                    onError(th);
                }
            }
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

        public void request(long j) {
            this.f3919s.request(j);
        }

        public void cancel() {
            this.f3919s.cancel();
        }
    }

    public FlowableTakeWhile(Flowable<T> flowable, Predicate<? super T> predicate2) {
        super(flowable);
        this.predicate = predicate2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new TakeWhileSubscriber<Object>(subscriber, this.predicate));
    }
}
