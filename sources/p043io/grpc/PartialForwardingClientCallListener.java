package p043io.grpc;

import com.google.common.base.MoreObjects;
import p043io.grpc.ClientCall.Listener;

/* renamed from: io.grpc.PartialForwardingClientCallListener */
abstract class PartialForwardingClientCallListener<RespT> extends Listener<RespT> {
    /* access modifiers changed from: protected */
    public abstract Listener<?> delegate();

    PartialForwardingClientCallListener() {
    }

    public void onHeaders(Metadata metadata) {
        delegate().onHeaders(metadata);
    }

    public void onClose(Status status, Metadata metadata) {
        delegate().onClose(status, metadata);
    }

    public void onReady() {
        delegate().onReady();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) delegate()).toString();
    }
}
