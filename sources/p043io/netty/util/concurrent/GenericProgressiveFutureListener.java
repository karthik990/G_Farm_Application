package p043io.netty.util.concurrent;

import p043io.netty.util.concurrent.ProgressiveFuture;

/* renamed from: io.netty.util.concurrent.GenericProgressiveFutureListener */
public interface GenericProgressiveFutureListener<F extends ProgressiveFuture<?>> extends GenericFutureListener<F> {
    void operationProgressed(F f, long j, long j2) throws Exception;
}
