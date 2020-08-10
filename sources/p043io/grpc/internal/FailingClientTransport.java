package p043io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.Executor;
import p043io.grpc.CallOptions;
import p043io.grpc.InternalChannelz.SocketStats;
import p043io.grpc.InternalLogId;
import p043io.grpc.Metadata;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.Status;
import p043io.grpc.internal.ClientStreamListener.RpcProgress;
import p043io.grpc.internal.ClientTransport.PingCallback;

/* renamed from: io.grpc.internal.FailingClientTransport */
class FailingClientTransport implements ClientTransport {
    final Status error;
    private final RpcProgress rpcProgress;

    FailingClientTransport(Status status, RpcProgress rpcProgress2) {
        Preconditions.checkArgument(!status.isOk(), "error must not be OK");
        this.error = status;
        this.rpcProgress = rpcProgress2;
    }

    public ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
        return new FailingClientStream(this.error, this.rpcProgress);
    }

    public void ping(final PingCallback pingCallback, Executor executor) {
        executor.execute(new Runnable() {
            public void run() {
                pingCallback.onFailure(FailingClientTransport.this.error.asException());
            }
        });
    }

    public ListenableFuture<SocketStats> getStats() {
        SettableFuture create = SettableFuture.create();
        create.set(null);
        return create;
    }

    public InternalLogId getLogId() {
        throw new UnsupportedOperationException("Not a real transport");
    }
}
