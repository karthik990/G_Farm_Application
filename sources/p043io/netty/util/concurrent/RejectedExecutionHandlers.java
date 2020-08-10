package p043io.netty.util.concurrent;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.util.concurrent.RejectedExecutionHandlers */
public final class RejectedExecutionHandlers {
    private static final RejectedExecutionHandler REJECT = new RejectedExecutionHandler() {
        public void rejected(Runnable runnable, SingleThreadEventExecutor singleThreadEventExecutor) {
            throw new RejectedExecutionException();
        }
    };

    private RejectedExecutionHandlers() {
    }

    public static RejectedExecutionHandler reject() {
        return REJECT;
    }

    public static RejectedExecutionHandler backoff(final int i, long j, TimeUnit timeUnit) {
        ObjectUtil.checkPositive(i, "retries");
        final long nanos = timeUnit.toNanos(j);
        return new RejectedExecutionHandler() {
            public void rejected(Runnable runnable, SingleThreadEventExecutor singleThreadEventExecutor) {
                if (!singleThreadEventExecutor.inEventLoop()) {
                    int i = 0;
                    while (i < i) {
                        singleThreadEventExecutor.wakeup(false);
                        LockSupport.parkNanos(nanos);
                        if (!singleThreadEventExecutor.offerTask(runnable)) {
                            i++;
                        } else {
                            return;
                        }
                    }
                }
                throw new RejectedExecutionException();
            }
        };
    }
}
