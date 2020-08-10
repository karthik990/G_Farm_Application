package p043io.netty.util.concurrent;

import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.util.concurrent.PromiseCombiner */
public final class PromiseCombiner {
    private Promise<Void> aggregatePromise;
    /* access modifiers changed from: private */
    public Throwable cause;
    /* access modifiers changed from: private */
    public boolean doneAdding;
    /* access modifiers changed from: private */
    public int doneCount;
    /* access modifiers changed from: private */
    public int expectedCount;
    private final GenericFutureListener<Future<?>> listener = new GenericFutureListener<Future<?>>() {
        public void operationComplete(Future<?> future) throws Exception {
            PromiseCombiner.access$004(PromiseCombiner.this);
            if (!future.isSuccess() && PromiseCombiner.this.cause == null) {
                PromiseCombiner.this.cause = future.cause();
            }
            if (PromiseCombiner.this.doneCount == PromiseCombiner.this.expectedCount && PromiseCombiner.this.doneAdding) {
                PromiseCombiner.this.tryPromise();
            }
        }
    };

    static /* synthetic */ int access$004(PromiseCombiner promiseCombiner) {
        int i = promiseCombiner.doneCount + 1;
        promiseCombiner.doneCount = i;
        return i;
    }

    @Deprecated
    public void add(Promise promise) {
        add((Future) promise);
    }

    public void add(Future future) {
        checkAddAllowed();
        this.expectedCount++;
        future.addListener(this.listener);
    }

    @Deprecated
    public void addAll(Promise... promiseArr) {
        addAll((Future[]) promiseArr);
    }

    public void addAll(Future... futureArr) {
        for (Future add : futureArr) {
            add(add);
        }
    }

    public void finish(Promise<Void> promise) {
        if (!this.doneAdding) {
            this.doneAdding = true;
            this.aggregatePromise = (Promise) ObjectUtil.checkNotNull(promise, "aggregatePromise");
            if (this.doneCount == this.expectedCount) {
                tryPromise();
                return;
            }
            return;
        }
        throw new IllegalStateException("Already finished");
    }

    /* access modifiers changed from: private */
    public boolean tryPromise() {
        Throwable th = this.cause;
        return th == null ? this.aggregatePromise.trySuccess(null) : this.aggregatePromise.tryFailure(th);
    }

    private void checkAddAllowed() {
        if (this.doneAdding) {
            throw new IllegalStateException("Adding promises is not allowed after finished adding");
        }
    }
}
