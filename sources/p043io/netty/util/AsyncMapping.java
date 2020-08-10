package p043io.netty.util;

import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.Promise;

/* renamed from: io.netty.util.AsyncMapping */
public interface AsyncMapping<IN, OUT> {
    Future<OUT> map(IN in, Promise<OUT> promise);
}
