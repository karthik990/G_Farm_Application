package p043io.netty.util.concurrent;

/* renamed from: io.netty.util.concurrent.RejectedExecutionHandler */
public interface RejectedExecutionHandler {
    void rejected(Runnable runnable, SingleThreadEventExecutor singleThreadEventExecutor);
}
