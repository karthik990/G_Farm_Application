package p043io.grpc.internal;

import com.google.common.base.MoreObjects;
import java.util.concurrent.TimeUnit;
import p043io.grpc.CallOptions;
import p043io.grpc.ClientCall;
import p043io.grpc.ConnectivityState;
import p043io.grpc.ManagedChannel;
import p043io.grpc.MethodDescriptor;

/* renamed from: io.grpc.internal.ForwardingManagedChannel */
abstract class ForwardingManagedChannel extends ManagedChannel {
    private final ManagedChannel delegate;

    ForwardingManagedChannel(ManagedChannel managedChannel) {
        this.delegate = managedChannel;
    }

    public ManagedChannel shutdown() {
        return this.delegate.shutdown();
    }

    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    public ManagedChannel shutdownNow() {
        return this.delegate.shutdownNow();
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.awaitTermination(j, timeUnit);
    }

    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        return this.delegate.newCall(methodDescriptor, callOptions);
    }

    public String authority() {
        return this.delegate.authority();
    }

    public ConnectivityState getState(boolean z) {
        return this.delegate.getState(z);
    }

    public void notifyWhenStateChanged(ConnectivityState connectivityState, Runnable runnable) {
        this.delegate.notifyWhenStateChanged(connectivityState, runnable);
    }

    public void resetConnectBackoff() {
        this.delegate.resetConnectBackoff();
    }

    public void enterIdle() {
        this.delegate.enterIdle();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) this.delegate).toString();
    }
}
