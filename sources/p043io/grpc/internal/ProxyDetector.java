package p043io.grpc.internal;

import java.io.IOException;
import java.net.SocketAddress;
import javax.annotation.Nullable;

/* renamed from: io.grpc.internal.ProxyDetector */
public interface ProxyDetector {
    @Nullable
    ProxyParameters proxyFor(SocketAddress socketAddress) throws IOException;
}
