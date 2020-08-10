package p043io.reactivex.internal.observers;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.util.BlockingHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.observers.FutureSingleObserver */
public final class FutureSingleObserver<T> extends CountDownLatch implements SingleObserver<T>, Future<T>, Disposable {
    Throwable error;

    /* renamed from: s */
    final AtomicReference<Disposable> f3786s = new AtomicReference<>();
    T value;

    public void dispose() {
    }

    public FutureSingleObserver() {
        super(1);
    }

    public boolean cancel(boolean z) {
        Disposable disposable;
        do {
            disposable = (Disposable) this.f3786s.get();
            if (disposable == this || disposable == DisposableHelper.DISPOSED) {
                return false;
            }
        } while (!this.f3786s.compareAndSet(disposable, DisposableHelper.DISPOSED));
        if (disposable != null) {
            disposable.dispose();
        }
        countDown();
        return true;
    }

    public boolean isCancelled() {
        return DisposableHelper.isDisposed((Disposable) this.f3786s.get());
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

    public void onSubscribe(Disposable disposable) {
        DisposableHelper.setOnce(this.f3786s, disposable);
    }

    public void onSuccess(T t) {
        Disposable disposable = (Disposable) this.f3786s.get();
        if (disposable != DisposableHelper.DISPOSED) {
            this.value = t;
            this.f3786s.compareAndSet(disposable, this);
            countDown();
        }
    }

    public void onError(Throwable th) {
        Disposable disposable;
        do {
            disposable = (Disposable) this.f3786s.get();
            if (disposable == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
        } while (!this.f3786s.compareAndSet(disposable, this));
        countDown();
    }

    public boolean isDisposed() {
        return isDone();
    }
}
