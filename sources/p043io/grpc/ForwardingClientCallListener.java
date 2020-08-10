package p043io.grpc;

import p043io.grpc.ClientCall.Listener;

/* renamed from: io.grpc.ForwardingClientCallListener */
public abstract class ForwardingClientCallListener<RespT> extends PartialForwardingClientCallListener<RespT> {

    /* renamed from: io.grpc.ForwardingClientCallListener$SimpleForwardingClientCallListener */
    public static abstract class SimpleForwardingClientCallListener<RespT> extends ForwardingClientCallListener<RespT> {
        private final Listener<RespT> delegate;

        public /* bridge */ /* synthetic */ void onClose(Status status, Metadata metadata) {
            ForwardingClientCallListener.super.onClose(status, metadata);
        }

        public /* bridge */ /* synthetic */ void onHeaders(Metadata metadata) {
            ForwardingClientCallListener.super.onHeaders(metadata);
        }

        public /* bridge */ /* synthetic */ void onReady() {
            ForwardingClientCallListener.super.onReady();
        }

        public /* bridge */ /* synthetic */ String toString() {
            return ForwardingClientCallListener.super.toString();
        }

        protected SimpleForwardingClientCallListener(Listener<RespT> listener) {
            this.delegate = listener;
        }

        /* access modifiers changed from: protected */
        public Listener<RespT> delegate() {
            return this.delegate;
        }
    }

    /* access modifiers changed from: protected */
    public abstract Listener<RespT> delegate();

    public /* bridge */ /* synthetic */ void onClose(Status status, Metadata metadata) {
        super.onClose(status, metadata);
    }

    public /* bridge */ /* synthetic */ void onHeaders(Metadata metadata) {
        super.onHeaders(metadata);
    }

    public /* bridge */ /* synthetic */ void onReady() {
        super.onReady();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public void onMessage(RespT respt) {
        delegate().onMessage(respt);
    }
}
