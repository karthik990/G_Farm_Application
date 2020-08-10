package p043io.grpc;

import p043io.grpc.ServerCall.Listener;

/* renamed from: io.grpc.ForwardingServerCallListener */
public abstract class ForwardingServerCallListener<ReqT> extends PartialForwardingServerCallListener<ReqT> {

    /* renamed from: io.grpc.ForwardingServerCallListener$SimpleForwardingServerCallListener */
    public static abstract class SimpleForwardingServerCallListener<ReqT> extends ForwardingServerCallListener<ReqT> {
        private final Listener<ReqT> delegate;

        public /* bridge */ /* synthetic */ void onCancel() {
            ForwardingServerCallListener.super.onCancel();
        }

        public /* bridge */ /* synthetic */ void onComplete() {
            ForwardingServerCallListener.super.onComplete();
        }

        public /* bridge */ /* synthetic */ void onHalfClose() {
            ForwardingServerCallListener.super.onHalfClose();
        }

        public /* bridge */ /* synthetic */ void onReady() {
            ForwardingServerCallListener.super.onReady();
        }

        public /* bridge */ /* synthetic */ String toString() {
            return ForwardingServerCallListener.super.toString();
        }

        protected SimpleForwardingServerCallListener(Listener<ReqT> listener) {
            this.delegate = listener;
        }

        /* access modifiers changed from: protected */
        public Listener<ReqT> delegate() {
            return this.delegate;
        }
    }

    /* access modifiers changed from: protected */
    public abstract Listener<ReqT> delegate();

    public /* bridge */ /* synthetic */ void onCancel() {
        super.onCancel();
    }

    public /* bridge */ /* synthetic */ void onComplete() {
        super.onComplete();
    }

    public /* bridge */ /* synthetic */ void onHalfClose() {
        super.onHalfClose();
    }

    public /* bridge */ /* synthetic */ void onReady() {
        super.onReady();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public void onMessage(ReqT reqt) {
        delegate().onMessage(reqt);
    }
}
