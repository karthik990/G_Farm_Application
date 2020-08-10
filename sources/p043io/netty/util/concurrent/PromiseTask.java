package p043io.netty.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.util.concurrent.PromiseTask */
class PromiseTask<V> extends DefaultPromise<V> implements RunnableFuture<V> {
    protected final Callable<V> task;

    /* renamed from: io.netty.util.concurrent.PromiseTask$RunnableAdapter */
    private static final class RunnableAdapter<T> implements Callable<T> {
        final T result;
        final Runnable task;

        RunnableAdapter(Runnable runnable, T t) {
            this.task = runnable;
            this.result = t;
        }

        public T call() {
            this.task.run();
            return this.result;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Callable(task: ");
            sb.append(this.task);
            sb.append(", result: ");
            sb.append(this.result);
            sb.append(')');
            return sb.toString();
        }
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    public final boolean tryFailure(Throwable th) {
        return false;
    }

    public final boolean trySuccess(V v) {
        return false;
    }

    static <T> Callable<T> toCallable(Runnable runnable, T t) {
        return new RunnableAdapter(runnable, t);
    }

    PromiseTask(EventExecutor eventExecutor, Runnable runnable, V v) {
        this(eventExecutor, toCallable(runnable, v));
    }

    PromiseTask(EventExecutor eventExecutor, Callable<V> callable) {
        super(eventExecutor);
        this.task = callable;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    public void run() {
        try {
            if (setUncancellableInternal()) {
                setSuccessInternal(this.task.call());
            }
        } catch (Throwable th) {
            setFailureInternal(th);
        }
    }

    public final Promise<V> setFailure(Throwable th) {
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final Promise<V> setFailureInternal(Throwable th) {
        super.setFailure(th);
        return this;
    }

    /* access modifiers changed from: protected */
    public final boolean tryFailureInternal(Throwable th) {
        return super.tryFailure(th);
    }

    public final Promise<V> setSuccess(V v) {
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final Promise<V> setSuccessInternal(V v) {
        super.setSuccess(v);
        return this;
    }

    /* access modifiers changed from: protected */
    public final boolean trySuccessInternal(V v) {
        return super.trySuccess(v);
    }

    public final boolean setUncancellable() {
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public final boolean setUncancellableInternal() {
        return super.setUncancellable();
    }

    /* access modifiers changed from: protected */
    public StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, StringUtil.COMMA);
        stringBuilder.append(" task: ");
        stringBuilder.append(this.task);
        stringBuilder.append(')');
        return stringBuilder;
    }
}
