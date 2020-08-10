package p043io.netty.util.concurrent;

import java.util.EventListener;
import p043io.netty.util.concurrent.Future;

/* renamed from: io.netty.util.concurrent.GenericFutureListener */
public interface GenericFutureListener<F extends Future<?>> extends EventListener {
    void operationComplete(F f) throws Exception;
}
