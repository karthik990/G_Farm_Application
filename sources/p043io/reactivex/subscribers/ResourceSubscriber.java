package p043io.reactivex.subscribers;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.ListCompositeDisposable;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.subscribers.ResourceSubscriber */
public abstract class ResourceSubscriber<T> implements FlowableSubscriber<T>, Disposable {
    private final AtomicLong missedRequested = new AtomicLong();
    private final ListCompositeDisposable resources = new ListCompositeDisposable();

    /* renamed from: s */
    private final AtomicReference<Subscription> f4191s = new AtomicReference<>();

    public final void add(Disposable disposable) {
        ObjectHelper.requireNonNull(disposable, "resource is null");
        this.resources.add(disposable);
    }

    public final void onSubscribe(Subscription subscription) {
        if (EndConsumerHelper.setOnce(this.f4191s, subscription, getClass())) {
            long andSet = this.missedRequested.getAndSet(0);
            if (andSet != 0) {
                subscription.request(andSet);
            }
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        request(Long.MAX_VALUE);
    }

    /* access modifiers changed from: protected */
    public final void request(long j) {
        SubscriptionHelper.deferredRequest(this.f4191s, this.missedRequested, j);
    }

    public final void dispose() {
        if (SubscriptionHelper.cancel(this.f4191s)) {
            this.resources.dispose();
        }
    }

    public final boolean isDisposed() {
        return SubscriptionHelper.isCancelled((Subscription) this.f4191s.get());
    }
}
