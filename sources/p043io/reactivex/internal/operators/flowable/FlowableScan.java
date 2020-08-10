package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiFunction;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableScan */
public final class FlowableScan<T> extends AbstractFlowableWithUpstream<T, T> {
    final BiFunction<T, T, T> accumulator;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableScan$ScanSubscriber */
    static final class ScanSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final BiFunction<T, T, T> accumulator;
        final Subscriber<? super T> actual;
        boolean done;

        /* renamed from: s */
        Subscription f3895s;
        T value;

        ScanSubscriber(Subscriber<? super T> subscriber, BiFunction<T, T, T> biFunction) {
            this.actual = subscriber;
            this.accumulator = biFunction;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3895s, subscription)) {
                this.f3895s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                Subscriber<? super T> subscriber = this.actual;
                T t2 = this.value;
                if (t2 == null) {
                    this.value = t;
                    subscriber.onNext(t);
                } else {
                    try {
                        T requireNonNull = ObjectHelper.requireNonNull(this.accumulator.apply(t2, t), "The value returned by the accumulator is null");
                        this.value = requireNonNull;
                        subscriber.onNext(requireNonNull);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f3895s.cancel();
                        onError(th);
                    }
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
            this.f3895s.request(j);
        }

        public void cancel() {
            this.f3895s.cancel();
        }
    }

    public FlowableScan(Flowable<T> flowable, BiFunction<T, T, T> biFunction) {
        super(flowable);
        this.accumulator = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new ScanSubscriber<Object>(subscriber, this.accumulator));
    }
}
