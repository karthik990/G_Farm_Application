package p043io.reactivex.observers;

import com.google.api.client.googleapis.notifications.ResourceStates;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.Observer;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.functions.Consumer;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.QueueDisposable;
import p043io.reactivex.internal.util.ExceptionHelper;

/* renamed from: io.reactivex.observers.TestObserver */
public class TestObserver<T> extends BaseTestConsumer<T, TestObserver<T>> implements Observer<T>, Disposable, MaybeObserver<T>, SingleObserver<T>, CompletableObserver {
    private final Observer<? super T> actual;

    /* renamed from: qs */
    private QueueDisposable<T> f4187qs;
    private final AtomicReference<Disposable> subscription;

    /* renamed from: io.reactivex.observers.TestObserver$EmptyObserver */
    enum EmptyObserver implements Observer<Object> {
        INSTANCE;

        public void onComplete() {
        }

        public void onError(Throwable th) {
        }

        public void onNext(Object obj) {
        }

        public void onSubscribe(Disposable disposable) {
        }
    }

    public static <T> TestObserver<T> create() {
        return new TestObserver<>();
    }

    public static <T> TestObserver<T> create(Observer<? super T> observer) {
        return new TestObserver<>(observer);
    }

    public TestObserver() {
        this(EmptyObserver.INSTANCE);
    }

    public TestObserver(Observer<? super T> observer) {
        this.subscription = new AtomicReference<>();
        this.actual = observer;
    }

    public void onSubscribe(Disposable disposable) {
        this.lastThread = Thread.currentThread();
        if (disposable == null) {
            this.errors.add(new NullPointerException("onSubscribe received a null Subscription"));
        } else if (!this.subscription.compareAndSet(null, disposable)) {
            disposable.dispose();
            if (this.subscription.get() != DisposableHelper.DISPOSED) {
                List list = this.errors;
                StringBuilder sb = new StringBuilder();
                sb.append("onSubscribe received multiple subscriptions: ");
                sb.append(disposable);
                list.add(new IllegalStateException(sb.toString()));
            }
        } else {
            if (this.initialFusionMode != 0 && (disposable instanceof QueueDisposable)) {
                this.f4187qs = (QueueDisposable) disposable;
                int requestFusion = this.f4187qs.requestFusion(this.initialFusionMode);
                this.establishedFusionMode = requestFusion;
                if (requestFusion == 1) {
                    this.checkSubscriptionOnce = true;
                    this.lastThread = Thread.currentThread();
                    while (true) {
                        try {
                            Object poll = this.f4187qs.poll();
                            if (poll == null) {
                                break;
                            }
                            this.values.add(poll);
                        } catch (Throwable th) {
                            this.errors.add(th);
                        }
                    }
                    this.completions++;
                    this.subscription.lazySet(DisposableHelper.DISPOSED);
                    return;
                }
            }
            this.actual.onSubscribe(disposable);
        }
    }

    public void onNext(T t) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        this.lastThread = Thread.currentThread();
        if (this.establishedFusionMode == 2) {
            while (true) {
                try {
                    Object poll = this.f4187qs.poll();
                    if (poll == null) {
                        break;
                    }
                    this.values.add(poll);
                } catch (Throwable th) {
                    this.errors.add(th);
                }
            }
            return;
        }
        this.values.add(t);
        if (t == null) {
            this.errors.add(new NullPointerException("onNext received a null value"));
        }
        this.actual.onNext(t);
    }

    public void onError(Throwable th) {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            if (th == null) {
                this.errors.add(new NullPointerException("onError received a null Throwable"));
            } else {
                this.errors.add(th);
            }
            this.actual.onError(th);
        } finally {
            this.done.countDown();
        }
    }

    public void onComplete() {
        if (!this.checkSubscriptionOnce) {
            this.checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                this.errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            this.lastThread = Thread.currentThread();
            this.completions++;
            this.actual.onComplete();
        } finally {
            this.done.countDown();
        }
    }

    public final boolean isCancelled() {
        return isDisposed();
    }

    public final void cancel() {
        dispose();
    }

    public final void dispose() {
        DisposableHelper.dispose(this.subscription);
    }

    public final boolean isDisposed() {
        return DisposableHelper.isDisposed((Disposable) this.subscription.get());
    }

    public final boolean hasSubscription() {
        return this.subscription.get() != null;
    }

    public final TestObserver<T> assertSubscribed() {
        if (this.subscription.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }

    public final TestObserver<T> assertNotSubscribed() {
        if (this.subscription.get() != null) {
            throw fail("Subscribed!");
        } else if (this.errors.isEmpty()) {
            return this;
        } else {
            throw fail("Not subscribed but errors found");
        }
    }

    public final TestObserver<T> assertOf(Consumer<? super TestObserver<T>> consumer) {
        try {
            consumer.accept(this);
            return this;
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    /* access modifiers changed from: 0000 */
    public final TestObserver<T> setInitialFusionMode(int i) {
        this.initialFusionMode = i;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public final TestObserver<T> assertFusionMode(int i) {
        int i2 = this.establishedFusionMode;
        if (i2 == i) {
            return this;
        }
        if (this.f4187qs != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fusion mode different. Expected: ");
            sb.append(fusionModeToString(i));
            sb.append(", actual: ");
            sb.append(fusionModeToString(i2));
            throw new AssertionError(sb.toString());
        }
        throw fail("Upstream is not fuseable");
    }

    static String fusionModeToString(int i) {
        if (i == 0) {
            return "NONE";
        }
        if (i == 1) {
            return ResourceStates.SYNC;
        }
        if (i == 2) {
            return "ASYNC";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown(");
        sb.append(i);
        sb.append(")");
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final TestObserver<T> assertFuseable() {
        if (this.f4187qs != null) {
            return this;
        }
        throw new AssertionError("Upstream is not fuseable.");
    }

    /* access modifiers changed from: 0000 */
    public final TestObserver<T> assertNotFuseable() {
        if (this.f4187qs == null) {
            return this;
        }
        throw new AssertionError("Upstream is fuseable.");
    }

    public void onSuccess(T t) {
        onNext(t);
        onComplete();
    }
}
