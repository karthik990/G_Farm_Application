package p043io.reactivex.subscribers;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.subscribers.DisposableSubscriber */
public abstract class DisposableSubscriber<T> implements FlowableSubscriber<T>, Disposable {

    /* renamed from: s */
    final AtomicReference<Subscription> f4190s = new AtomicReference<>();

    public final void onSubscribe(Subscription subscription) {
        if (EndConsumerHelper.setOnce(this.f4190s, subscription, getClass())) {
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        ((Subscription) this.f4190s.get()).request(Long.MAX_VALUE);
    }

    /* access modifiers changed from: protected */
    public final void request(long j) {
        ((Subscription) this.f4190s.get()).request(j);
    }

    /* access modifiers changed from: protected */
    public final void cancel() {
        dispose();
    }

    public final boolean isDisposed() {
        return this.f4190s.get() == SubscriptionHelper.CANCELLED;
    }

    public final void dispose() {
        SubscriptionHelper.cancel(this.f4190s);
    }
}
