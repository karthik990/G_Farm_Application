package p043io.reactivex.internal.operators.parallel;

import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiConsumer;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscribers.DeferredScalarSubscriber;
import p043io.reactivex.internal.subscriptions.EmptySubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.parallel.ParallelFlowable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.parallel.ParallelCollect */
public final class ParallelCollect<T, C> extends ParallelFlowable<C> {
    final BiConsumer<? super C, ? super T> collector;
    final Callable<? extends C> initialCollection;
    final ParallelFlowable<? extends T> source;

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelCollect$ParallelCollectSubscriber */
    static final class ParallelCollectSubscriber<T, C> extends DeferredScalarSubscriber<T, C> {
        private static final long serialVersionUID = -4767392946044436228L;
        C collection;
        final BiConsumer<? super C, ? super T> collector;
        boolean done;

        ParallelCollectSubscriber(Subscriber<? super C> subscriber, C c, BiConsumer<? super C, ? super T> biConsumer) {
            super(subscriber);
            this.collection = c;
            this.collector = biConsumer;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f4149s, subscription)) {
                this.f4149s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.collector.accept(this.collection, t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    cancel();
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
            this.collection = null;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                C c = this.collection;
                this.collection = null;
                complete(c);
            }
        }

        public void cancel() {
            super.cancel();
            this.f4149s.cancel();
        }
    }

    public ParallelCollect(ParallelFlowable<? extends T> parallelFlowable, Callable<? extends C> callable, BiConsumer<? super C, ? super T> biConsumer) {
        this.source = parallelFlowable;
        this.initialCollection = callable;
        this.collector = biConsumer;
    }

    public void subscribe(Subscriber<? super C>[] subscriberArr) {
        if (validate(subscriberArr)) {
            int length = subscriberArr.length;
            Subscriber[] subscriberArr2 = new Subscriber[length];
            int i = 0;
            while (i < length) {
                try {
                    subscriberArr2[i] = new ParallelCollectSubscriber(subscriberArr[i], ObjectHelper.requireNonNull(this.initialCollection.call(), "The initialSupplier returned a null value"), this.collector);
                    i++;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    reportError(subscriberArr, th);
                    return;
                }
            }
            this.source.subscribe(subscriberArr2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void reportError(Subscriber<?>[] subscriberArr, Throwable th) {
        for (Subscriber<?> error : subscriberArr) {
            EmptySubscription.error(th, error);
        }
    }

    public int parallelism() {
        return this.source.parallelism();
    }
}
