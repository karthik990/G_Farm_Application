package p043io.netty.resolver;

import java.io.Closeable;
import java.net.SocketAddress;
import java.util.List;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.Promise;

/* renamed from: io.netty.resolver.AddressResolver */
public interface AddressResolver<T extends SocketAddress> extends Closeable {
    void close();

    boolean isResolved(SocketAddress socketAddress);

    boolean isSupported(SocketAddress socketAddress);

    Future<T> resolve(SocketAddress socketAddress);

    Future<T> resolve(SocketAddress socketAddress, Promise<T> promise);

    Future<List<T>> resolveAll(SocketAddress socketAddress);

    Future<List<T>> resolveAll(SocketAddress socketAddress, Promise<List<T>> promise);
}
