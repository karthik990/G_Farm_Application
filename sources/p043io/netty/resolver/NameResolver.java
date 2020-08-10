package p043io.netty.resolver;

import java.io.Closeable;
import java.util.List;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.Promise;

/* renamed from: io.netty.resolver.NameResolver */
public interface NameResolver<T> extends Closeable {
    void close();

    Future<T> resolve(String str);

    Future<T> resolve(String str, Promise<T> promise);

    Future<List<T>> resolveAll(String str);

    Future<List<T>> resolveAll(String str, Promise<List<T>> promise);
}
