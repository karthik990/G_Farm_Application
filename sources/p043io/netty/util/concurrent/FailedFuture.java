package p043io.netty.util.concurrent;

import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.util.concurrent.FailedFuture */
public final class FailedFuture<V> extends CompleteFuture<V> {
    private final Throwable cause;

    public V getNow() {
        return null;
    }

    public boolean isSuccess() {
        return false;
    }

    public FailedFuture(EventExecutor eventExecutor, Throwable th) {
        super(eventExecutor);
        if (th != null) {
            this.cause = th;
            return;
        }
        throw new NullPointerException("cause");
    }

    public Throwable cause() {
        return this.cause;
    }

    public Future<V> sync() {
        PlatformDependent.throwException(this.cause);
        return this;
    }

    public Future<V> syncUninterruptibly() {
        PlatformDependent.throwException(this.cause);
        return this;
    }
}
