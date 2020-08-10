package p043io.reactivex.internal.subscribers;

import java.util.concurrent.CountDownLatch;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BlockingHelper;
import p043io.reactivex.internal.util.ExceptionHelper;

/* renamed from: io.reactivex.internal.subscribers.BlockingBaseSubscriber */
public abstract class BlockingBaseSubscriber<T> extends CountDownLatch implements FlowableSubscriber<T> {
    volatile boolean cancelled;
    Throwable error;

    /* renamed from: s */
    Subscription f4148s;
    T value;

    public BlockingBaseSubscriber() {
        super(1);
    }

    public final void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.f4148s, subscription)) {
            this.f4148s = subscription;
            if (!this.cancelled) {
                subscription.request(Long.MAX_VALUE);
                if (this.cancelled) {
                    this.f4148s = SubscriptionHelper.CANCELLED;
                    subscription.cancel();
                }
            }
        }
    }

    public final void onComplete() {
        countDown();
    }

    public final T blockingGet() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                Subscription subscription = this.f4148s;
                this.f4148s = SubscriptionHelper.CANCELLED;
                if (subscription != null) {
                    subscription.cancel();
                }
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        Throwable th = this.error;
        if (th == null) {
            return this.value;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }
}
