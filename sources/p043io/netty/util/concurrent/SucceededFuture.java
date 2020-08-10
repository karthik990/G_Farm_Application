package p043io.netty.util.concurrent;

/* renamed from: io.netty.util.concurrent.SucceededFuture */
public final class SucceededFuture<V> extends CompleteFuture<V> {
    private final V result;

    public Throwable cause() {
        return null;
    }

    public boolean isSuccess() {
        return true;
    }

    public SucceededFuture(EventExecutor eventExecutor, V v) {
        super(eventExecutor);
        this.result = v;
    }

    public V getNow() {
        return this.result;
    }
}
