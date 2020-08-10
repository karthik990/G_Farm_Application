package p043io.grpc.internal;

import java.util.concurrent.ScheduledExecutorService;
import p043io.grpc.InternalChannelz.SocketStats;
import p043io.grpc.InternalInstrumented;
import p043io.grpc.Status;

/* renamed from: io.grpc.internal.ServerTransport */
public interface ServerTransport extends InternalInstrumented<SocketStats> {
    ScheduledExecutorService getScheduledExecutorService();

    void shutdown();

    void shutdownNow(Status status);
}
