package p043io.grpc.internal;

import java.util.concurrent.Executor;
import p043io.grpc.CallOptions;
import p043io.grpc.InternalChannelz.SocketStats;
import p043io.grpc.InternalInstrumented;
import p043io.grpc.Metadata;
import p043io.grpc.MethodDescriptor;

/* renamed from: io.grpc.internal.ClientTransport */
public interface ClientTransport extends InternalInstrumented<SocketStats> {

    /* renamed from: io.grpc.internal.ClientTransport$PingCallback */
    public interface PingCallback {
        void onFailure(Throwable th);

        void onSuccess(long j);
    }

    ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions);

    void ping(PingCallback pingCallback, Executor executor);
}
