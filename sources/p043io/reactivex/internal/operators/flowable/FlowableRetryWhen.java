package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscriptions.EmptySubscription;
import p043io.reactivex.processors.FlowableProcessor;
import p043io.reactivex.processors.UnicastProcessor;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableRetryWhen */
public final class FlowableRetryWhen<T> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super Flowable<Throwable>, ? extends Publisher<?>> handler;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableRetryWhen$RetryWhenSubscriber */
    static final class RetryWhenSubscriber<T> extends WhenSourceSubscriber<T, Throwable> {
        private static final long serialVersionUID = -2680129890138081029L;

        RetryWhenSubscriber(Subscriber<? super T> subscriber, FlowableProcessor<Throwable> flowableProcessor, Subscription subscription) {
            super(subscriber, flowableProcessor, subscription);
        }

        public void onError(Throwable th) {
            again(th);
        }

        public void onComplete() {
            this.receiver.cancel();
            this.actual.onComplete();
        }
    }

    public FlowableRetryWhen(Flowable<T> flowable, Function<? super Flowable<Throwable>, ? extends Publisher<?>> function) {
        super(flowable);
        this.handler = function;
    }

    public void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        FlowableProcessor serialized = UnicastProcessor.create(8).toSerialized();
        try {
            Publisher publisher = (Publisher) ObjectHelper.requireNonNull(this.handler.apply(serialized), "handler returned a null Publisher");
            WhenReceiver whenReceiver = new WhenReceiver(this.source);
            RetryWhenSubscriber retryWhenSubscriber = new RetryWhenSubscriber(serializedSubscriber, serialized, whenReceiver);
            whenReceiver.subscriber = retryWhenSubscriber;
            subscriber.onSubscribe(retryWhenSubscriber);
            publisher.subscribe(whenReceiver);
            whenReceiver.onNext(Integer.valueOf(0));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
