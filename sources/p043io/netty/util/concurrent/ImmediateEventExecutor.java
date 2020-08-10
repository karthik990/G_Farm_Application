package p043io.netty.util.concurrent;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.concurrent.ImmediateEventExecutor */
public final class ImmediateEventExecutor extends AbstractEventExecutor {
    private static final FastThreadLocal<Queue<Runnable>> DELAYED_RUNNABLES = new FastThreadLocal<Queue<Runnable>>() {
        /* access modifiers changed from: protected */
        public Queue<Runnable> initialValue() throws Exception {
            return new ArrayDeque();
        }
    };
    public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
    private static final FastThreadLocal<Boolean> RUNNING = new FastThreadLocal<Boolean>() {
        /* access modifiers changed from: protected */
        public Boolean initialValue() throws Exception {
            return Boolean.valueOf(false);
        }
    };
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ImmediateEventExecutor.class);
    private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());

    /* renamed from: io.netty.util.concurrent.ImmediateEventExecutor$ImmediateProgressivePromise */
    static class ImmediateProgressivePromise<V> extends DefaultProgressivePromise<V> {
        /* access modifiers changed from: protected */
        public void checkDeadLock() {
        }

        ImmediateProgressivePromise(EventExecutor eventExecutor) {
            super(eventExecutor);
        }
    }

    /* renamed from: io.netty.util.concurrent.ImmediateEventExecutor$ImmediatePromise */
    static class ImmediatePromise<V> extends DefaultPromise<V> {
        /* access modifiers changed from: protected */
        public void checkDeadLock() {
        }

        ImmediatePromise(EventExecutor eventExecutor) {
            super(eventExecutor);
        }
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        return false;
    }

    public boolean inEventLoop() {
        return true;
    }

    public boolean inEventLoop(Thread thread) {
        return true;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isShuttingDown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    @Deprecated
    public void shutdown() {
    }

    private ImmediateEventExecutor() {
    }

    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        return terminationFuture();
    }

    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    public void execute(Runnable runnable) {
        Runnable runnable2;
        String str = "Throwable caught while executing Runnable {}";
        if (runnable == null) {
            throw new NullPointerException("command");
        } else if (!((Boolean) RUNNING.get()).booleanValue()) {
            RUNNING.set(Boolean.valueOf(true));
            try {
                runnable.run();
                Queue queue = (Queue) DELAYED_RUNNABLES.get();
                while (true) {
                    Runnable runnable3 = (Runnable) queue.poll();
                    if (runnable3 == null) {
                        break;
                    }
                    try {
                        runnable3.run();
                    } catch (Throwable th) {
                        logger.info(str, runnable3, th);
                    }
                }
            } catch (Throwable th2) {
                logger.info(str, runnable2, th2);
            }
            RUNNING.set(Boolean.valueOf(false));
        } else {
            ((Queue) DELAYED_RUNNABLES.get()).add(runnable);
        }
    }

    public <V> Promise<V> newPromise() {
        return new ImmediatePromise(this);
    }

    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new ImmediateProgressivePromise(this);
    }
}
