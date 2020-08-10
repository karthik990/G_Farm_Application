package p043io.reactivex.internal.schedulers;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.functions.Functions;

/* renamed from: io.reactivex.internal.schedulers.AbstractDirectTask */
abstract class AbstractDirectTask extends AtomicReference<Future<?>> implements Disposable {
    protected static final FutureTask<Void> DISPOSED = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
    protected static final FutureTask<Void> FINISHED = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
    private static final long serialVersionUID = 1811839108042568751L;
    protected final Runnable runnable;
    protected Thread runner;

    AbstractDirectTask(Runnable runnable2) {
        this.runnable = runnable2;
    }

    public final void dispose() {
        Future future = (Future) get();
        if (future != FINISHED) {
            FutureTask<Void> futureTask = DISPOSED;
            if (future != futureTask && compareAndSet(future, futureTask) && future != null) {
                future.cancel(this.runner != Thread.currentThread());
            }
        }
    }

    public final boolean isDisposed() {
        Future future = (Future) get();
        return future == FINISHED || future == DISPOSED;
    }

    public final void setFuture(Future<?> future) {
        Future future2;
        do {
            future2 = (Future) get();
            if (future2 != FINISHED) {
                if (future2 == DISPOSED) {
                    future.cancel(this.runner != Thread.currentThread());
                    return;
                }
            } else {
                return;
            }
        } while (!compareAndSet(future2, future));
    }
}
