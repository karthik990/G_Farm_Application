package p043io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;
import p043io.grpc.Attributes;
import p043io.grpc.CallOptions;
import p043io.grpc.InternalChannelz.SocketStats;
import p043io.grpc.InternalLogId;
import p043io.grpc.Metadata;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.Status;
import p043io.grpc.internal.ClientTransport.PingCallback;
import p043io.grpc.internal.ManagedClientTransport.Listener;

/* renamed from: io.grpc.internal.ForwardingConnectionClientTransport */
abstract class ForwardingConnectionClientTransport implements ConnectionClientTransport {
    /* access modifiers changed from: protected */
    public abstract ConnectionClientTransport delegate();

    ForwardingConnectionClientTransport() {
    }

    public Runnable start(Listener listener) {
        return delegate().start(listener);
    }

    public void shutdown(Status status) {
        delegate().shutdown(status);
    }

    public void shutdownNow(Status status) {
        delegate().shutdownNow(status);
    }

    public ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
        return delegate().newStream(methodDescriptor, metadata, callOptions);
    }

    public void ping(PingCallback pingCallback, Executor executor) {
        delegate().ping(pingCallback, executor);
    }

    public InternalLogId getLogId() {
        return delegate().getLogId();
    }

    public Attributes getAttributes() {
        return delegate().getAttributes();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) delegate()).toString();
    }

    public ListenableFuture<SocketStats> getStats() {
        return delegate().getStats();
    }
}
