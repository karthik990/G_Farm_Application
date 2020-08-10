package p043io.netty.util.concurrent;

import java.util.concurrent.Executor;

/* renamed from: io.netty.util.concurrent.ImmediateExecutor */
public final class ImmediateExecutor implements Executor {
    public static final ImmediateExecutor INSTANCE = new ImmediateExecutor();

    private ImmediateExecutor() {
    }

    public void execute(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
            return;
        }
        throw new NullPointerException("command");
    }
}
