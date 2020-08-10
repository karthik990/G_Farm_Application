package p043io.grpc.internal;

import java.io.IOException;
import java.util.List;
import p043io.grpc.InternalChannelz.SocketStats;
import p043io.grpc.InternalInstrumented;

/* renamed from: io.grpc.internal.InternalServer */
public interface InternalServer {
    List<InternalInstrumented<SocketStats>> getListenSockets();

    int getPort();

    void shutdown();

    void start(ServerListener serverListener) throws IOException;
}
