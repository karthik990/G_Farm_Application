package p043io.reactivex.internal.subscribers;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.AtomicThrowable;
import p043io.reactivex.internal.util.HalfSerializer;

/* renamed from: io.reactivex.internal.subscribers.StrictSubscriber */
public class StrictSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
    private static final long serialVersionUID = -4945028590049415624L;
    final Subscriber<? super T> actual;
    volatile boolean done;
    final AtomicThrowable error = new AtomicThrowable();
    final AtomicBoolean once = new AtomicBoolean();
    final AtomicLong requested = new AtomicLong();

    /* renamed from: s */
    final AtomicReference<Subscription> f4171s = new AtomicReference<>();

    public StrictSubscriber(Subscriber<? super T> subscriber) {
        this.actual = subscriber;
    }

    public void request(long j) {
        if (j <= 0) {
            cancel();
            StringBuilder sb = new StringBuilder();
            sb.append("§3.9 violated: positive request amount required but it was ");
            sb.append(j);
            onError(new IllegalArgumentException(sb.toString()));
            return;
        }
        SubscriptionHelper.deferredRequest(this.f4171s, this.requested, j);
    }

    public void cancel() {
        if (!this.done) {
            SubscriptionHelper.cancel(this.f4171s);
        }
    }

    public void onSubscribe(Subscription subscription) {
        if (this.once.compareAndSet(false, true)) {
            this.actual.onSubscribe(this);
            SubscriptionHelper.deferredSetOnce(this.f4171s, this.requested, subscription);
            return;
        }
        subscription.cancel();
        cancel();
        onError(new IllegalStateException("§2.12 violated: onSubscribe must be called at most once"));
    }

    public void onNext(T t) {
        HalfSerializer.onNext(this.actual, t, (AtomicInteger) this, this.error);
    }

    public void onError(Throwable th) {
        this.done = true;
        HalfSerializer.onError(this.actual, th, (AtomicInteger) this, this.error);
    }

    public void onComplete() {
        this.done = true;
        HalfSerializer.onComplete(this.actual, (AtomicInteger) this, this.error);
    }
}
