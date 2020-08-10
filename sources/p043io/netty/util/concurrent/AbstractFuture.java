package p043io.netty.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* renamed from: io.netty.util.concurrent.AbstractFuture */
public abstract class AbstractFuture<V> implements Future<V> {
    public V get() throws InterruptedException, ExecutionException {
        await();
        Throwable cause = cause();
        if (cause == null) {
            return getNow();
        }
        if (cause instanceof CancellationException) {
            throw ((CancellationException) cause);
        }
        throw new ExecutionException(cause);
    }

    public V get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (await(j, timeUnit)) {
            Throwable cause = cause();
            if (cause == null) {
                return getNow();
            }
            if (cause instanceof CancellationException) {
                throw ((CancellationException) cause);
            }
            throw new ExecutionException(cause);
        }
        throw new TimeoutException();
    }
}
