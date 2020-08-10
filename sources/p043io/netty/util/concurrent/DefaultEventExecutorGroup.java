package p043io.netty.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/* renamed from: io.netty.util.concurrent.DefaultEventExecutorGroup */
public class DefaultEventExecutorGroup extends MultithreadEventExecutorGroup {
    public DefaultEventExecutorGroup(int i) {
        this(i, null);
    }

    public DefaultEventExecutorGroup(int i, ThreadFactory threadFactory) {
        this(i, threadFactory, SingleThreadEventExecutor.DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
    }

    public DefaultEventExecutorGroup(int i, ThreadFactory threadFactory, int i2, RejectedExecutionHandler rejectedExecutionHandler) {
        super(i, threadFactory, Integer.valueOf(i2), rejectedExecutionHandler);
    }

    /* access modifiers changed from: protected */
    public EventExecutor newChild(Executor executor, Object... objArr) throws Exception {
        return new DefaultEventExecutor((EventExecutorGroup) this, executor, objArr[0].intValue(), objArr[1]);
    }
}
