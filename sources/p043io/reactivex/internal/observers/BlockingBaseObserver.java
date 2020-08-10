package p043io.reactivex.internal.observers;

import java.util.concurrent.CountDownLatch;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.util.BlockingHelper;
import p043io.reactivex.internal.util.ExceptionHelper;

/* renamed from: io.reactivex.internal.observers.BlockingBaseObserver */
public abstract class BlockingBaseObserver<T> extends CountDownLatch implements Observer<T>, Disposable {
    volatile boolean cancelled;

    /* renamed from: d */
    Disposable f3780d;
    Throwable error;
    T value;

    public BlockingBaseObserver() {
        super(1);
    }

    public final void onSubscribe(Disposable disposable) {
        this.f3780d = disposable;
        if (this.cancelled) {
            disposable.dispose();
        }
    }

    public final void onComplete() {
        countDown();
    }

    public final void dispose() {
        this.cancelled = true;
        Disposable disposable = this.f3780d;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public final boolean isDisposed() {
        return this.cancelled;
    }

    public final T blockingGet() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                dispose();
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
