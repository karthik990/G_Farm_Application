package p043io.reactivex.internal.schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableContainer;

/* renamed from: io.reactivex.internal.schedulers.ScheduledRunnable */
public final class ScheduledRunnable extends AtomicReferenceArray<Object> implements Runnable, Callable<Object>, Disposable {
    static final Object DISPOSED = new Object();
    static final Object DONE = new Object();
    static final int FUTURE_INDEX = 1;
    static final int PARENT_INDEX = 0;
    static final int THREAD_INDEX = 2;
    private static final long serialVersionUID = -6120223772001106981L;
    final Runnable actual;

    public ScheduledRunnable(Runnable runnable, DisposableContainer disposableContainer) {
        super(3);
        this.actual = runnable;
        lazySet(0, disposableContainer);
    }

    public Object call() {
        run();
        return null;
    }

    public void run() {
        Object obj;
        Object obj2;
        lazySet(2, Thread.currentThread());
        try {
            this.actual.run();
        } catch (Throwable th) {
            lazySet(2, null);
            Object obj3 = get(0);
            if (!(obj3 == DISPOSED || obj3 == null || !compareAndSet(0, obj3, DONE))) {
                ((DisposableContainer) obj3).delete(this);
            }
            do {
                obj2 = get(1);
                if (obj2 == DISPOSED) {
                    break;
                }
            } while (!compareAndSet(1, obj2, DONE));
            throw th;
        }
        lazySet(2, null);
        Object obj4 = get(0);
        if (!(obj4 == DISPOSED || obj4 == null || !compareAndSet(0, obj4, DONE))) {
            ((DisposableContainer) obj4).delete(this);
        }
        do {
            obj = get(1);
            if (obj == DISPOSED) {
                return;
            }
        } while (!compareAndSet(1, obj, DONE));
    }

    public void setFuture(Future<?> future) {
        Object obj;
        do {
            boolean z = true;
            obj = get(1);
            if (obj != DONE) {
                if (obj == DISPOSED) {
                    if (get(2) == Thread.currentThread()) {
                        z = false;
                    }
                    future.cancel(z);
                    return;
                }
            } else {
                return;
            }
        } while (!compareAndSet(1, obj, future));
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispose() {
        /*
            r5 = this;
        L_0x0000:
            r0 = 1
            java.lang.Object r1 = r5.get(r0)
            java.lang.Object r2 = DONE
            r3 = 0
            if (r1 == r2) goto L_0x0029
            java.lang.Object r2 = DISPOSED
            if (r1 != r2) goto L_0x000f
            goto L_0x0029
        L_0x000f:
            boolean r2 = r5.compareAndSet(r0, r1, r2)
            if (r2 == 0) goto L_0x0000
            if (r1 == 0) goto L_0x0029
            java.util.concurrent.Future r1 = (java.util.concurrent.Future) r1
            r2 = 2
            java.lang.Object r2 = r5.get(r2)
            java.lang.Thread r4 = java.lang.Thread.currentThread()
            if (r2 == r4) goto L_0x0025
            goto L_0x0026
        L_0x0025:
            r0 = 0
        L_0x0026:
            r1.cancel(r0)
        L_0x0029:
            java.lang.Object r0 = r5.get(r3)
            java.lang.Object r1 = DONE
            if (r0 == r1) goto L_0x0043
            java.lang.Object r1 = DISPOSED
            if (r0 == r1) goto L_0x0043
            if (r0 != 0) goto L_0x0038
            goto L_0x0043
        L_0x0038:
            boolean r1 = r5.compareAndSet(r3, r0, r1)
            if (r1 == 0) goto L_0x0029
            io.reactivex.internal.disposables.DisposableContainer r0 = (p043io.reactivex.internal.disposables.DisposableContainer) r0
            r0.delete(r5)
        L_0x0043:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.schedulers.ScheduledRunnable.dispose():void");
    }

    public boolean isDisposed() {
        Object obj = get(1);
        if (obj == DISPOSED || obj == DONE) {
            return true;
        }
        return false;
    }
}
