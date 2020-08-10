package p043io.netty.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/* renamed from: io.netty.util.concurrent.ThreadPerTaskExecutor */
public final class ThreadPerTaskExecutor implements Executor {
    private final ThreadFactory threadFactory;

    public ThreadPerTaskExecutor(ThreadFactory threadFactory2) {
        if (threadFactory2 != null) {
            this.threadFactory = threadFactory2;
            return;
        }
        throw new NullPointerException("threadFactory");
    }

    public void execute(Runnable runnable) {
        this.threadFactory.newThread(runnable).start();
    }
}
