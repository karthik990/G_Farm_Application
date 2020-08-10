package p043io.grpc;

import com.google.common.base.MoreObjects;
import p043io.grpc.ServerCall.Listener;

/* renamed from: io.grpc.PartialForwardingServerCallListener */
abstract class PartialForwardingServerCallListener<ReqT> extends Listener<ReqT> {
    /* access modifiers changed from: protected */
    public abstract Listener<?> delegate();

    PartialForwardingServerCallListener() {
    }

    public void onHalfClose() {
        delegate().onHalfClose();
    }

    public void onCancel() {
        delegate().onCancel();
    }

    public void onComplete() {
        delegate().onComplete();
    }

    public void onReady() {
        delegate().onReady();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) delegate()).toString();
    }
}
