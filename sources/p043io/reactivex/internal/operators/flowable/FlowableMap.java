package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.fuseable.ConditionalSubscriber;
import p043io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import p043io.reactivex.internal.subscribers.BasicFuseableSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableMap */
public final class FlowableMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final Function<? super T, ? extends U> mapper;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableMap$MapConditionalSubscriber */
    static final class MapConditionalSubscriber<T, U> extends BasicFuseableConditionalSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapConditionalSubscriber(ConditionalSubscriber<? super U> conditionalSubscriber, Function<? super T, ? extends U> function) {
            super(conditionalSubscriber);
            this.mapper = function;
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.actual.onNext(null);
                    return;
                }
                try {
                    this.actual.onNext(ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value."));
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            try {
                return this.actual.tryOnNext(ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value."));
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        public U poll() throws Exception {
            Object poll = this.f4144qs.poll();
            if (poll != null) {
                return ObjectHelper.requireNonNull(this.mapper.apply(poll), "The mapper function returned a null value.");
            }
            return null;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableMap$MapSubscriber */
    static final class MapSubscriber<T, U> extends BasicFuseableSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapSubscriber(Subscriber<? super U> subscriber, Function<? super T, ? extends U> function) {
            super(subscriber);
            this.mapper = function;
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.actual.onNext(null);
                    return;
                }
                try {
                    this.actual.onNext(ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper function returned a null value."));
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        public U poll() throws Exception {
            Object poll = this.f4146qs.poll();
            if (poll != null) {
                return ObjectHelper.requireNonNull(this.mapper.apply(poll), "The mapper function returned a null value.");
            }
            return null;
        }
    }

    public FlowableMap(Flowable<T> flowable, Function<? super T, ? extends U> function) {
        super(flowable);
        this.mapper = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber<? super T>) new MapConditionalSubscriber<Object>((ConditionalSubscriber) subscriber, this.mapper));
        } else {
            this.source.subscribe((FlowableSubscriber<? super T>) new MapSubscriber<Object>(subscriber, this.mapper));
        }
    }
}
