package p043io.reactivex.internal.subscribers;

import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BlockingHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.subscribers.FutureSubscriber */
public final class FutureSubscriber<T> extends CountDownLatch implements FlowableSubscriber<T>, Future<T>, Subscription {
    Throwable error;

    /* renamed from: s */
    final AtomicReference<Subscription> f4151s = new AtomicReference<>();
    T value;

    public void cancel() {
    }

    public void request(long j) {
    }

    public FutureSubscriber() {
        super(1);
    }

    public boolean cancel(boolean z) {
        Subscription subscription;
        do {
            subscription = (Subscription) this.f4151s.get();
            if (subscription == this || subscription == SubscriptionHelper.CANCELLED) {
                return false;
            }
        } while (!this.f4151s.compareAndSet(subscription, SubscriptionHelper.CANCELLED));
        if (subscription != null) {
            subscription.cancel();
        }
        countDown();
        return true;
    }

    public boolean isCancelled() {
        return SubscriptionHelper.isCancelled((Subscription) this.f4151s.get());
    }

    public boolean isDone() {
        return getCount() == 0;
    }

    public T get() throws InterruptedException, ExecutionException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            await();
        }
        if (!isCancelled()) {
            Throwable th = this.error;
            if (th == null) {
                return this.value;
            }
            throw new ExecutionException(th);
        }
        throw new CancellationException();
    }

    public T get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (getCount() != 0) {
            BlockingHelper.verifyNonBlocking();
            if (!await(j, timeUnit)) {
                throw new TimeoutException();
            }
        }
        if (!isCancelled()) {
            Throwable th = this.error;
            if (th == null) {
                return this.value;
            }
            throw new ExecutionException(th);
        }
        throw new CancellationException();
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this.f4151s, subscription)) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public void onNext(T t) {
        if (this.value != null) {
            ((Subscription) this.f4151s.get()).cancel();
            onError(new IndexOutOfBoundsException("More than one element received"));
            return;
        }
        this.value = t;
    }

    public void onError(Throwable th) {
        Subscription subscription;
        do {
            subscription = (Subscription) this.f4151s.get();
            if (subscription == this || subscription == SubscriptionHelper.CANCELLED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
        } while (!this.f4151s.compareAndSet(subscription, this));
        countDown();
    }

    public void onComplete() {
        if (this.value == null) {
            onError(new NoSuchElementException("The source is empty"));
            return;
        }
        while (true) {
            Subscription subscription = (Subscription) this.f4151s.get();
            if (subscription != this && subscription != SubscriptionHelper.CANCELLED) {
                if (this.f4151s.compareAndSet(subscription, this)) {
                    countDown();
                    break;
                }
            } else {
                break;
            }
        }
    }
}
