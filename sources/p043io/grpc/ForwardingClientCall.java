package p043io.grpc;

import javax.annotation.Nullable;
import p043io.grpc.ClientCall.Listener;

/* renamed from: io.grpc.ForwardingClientCall */
public abstract class ForwardingClientCall<ReqT, RespT> extends PartialForwardingClientCall<ReqT, RespT> {

    /* renamed from: io.grpc.ForwardingClientCall$SimpleForwardingClientCall */
    public static abstract class SimpleForwardingClientCall<ReqT, RespT> extends ForwardingClientCall<ReqT, RespT> {
        private final ClientCall<ReqT, RespT> delegate;

        public /* bridge */ /* synthetic */ void cancel(@Nullable String str, @Nullable Throwable th) {
            ForwardingClientCall.super.cancel(str, th);
        }

        public /* bridge */ /* synthetic */ Attributes getAttributes() {
            return ForwardingClientCall.super.getAttributes();
        }

        public /* bridge */ /* synthetic */ void halfClose() {
            ForwardingClientCall.super.halfClose();
        }

        public /* bridge */ /* synthetic */ boolean isReady() {
            return ForwardingClientCall.super.isReady();
        }

        public /* bridge */ /* synthetic */ void request(int i) {
            ForwardingClientCall.super.request(i);
        }

        public /* bridge */ /* synthetic */ void setMessageCompression(boolean z) {
            ForwardingClientCall.super.setMessageCompression(z);
        }

        public /* bridge */ /* synthetic */ String toString() {
            return ForwardingClientCall.super.toString();
        }

        protected SimpleForwardingClientCall(ClientCall<ReqT, RespT> clientCall) {
            this.delegate = clientCall;
        }

        /* access modifiers changed from: protected */
        public ClientCall<ReqT, RespT> delegate() {
            return this.delegate;
        }
    }

    /* access modifiers changed from: protected */
    public abstract ClientCall<ReqT, RespT> delegate();

    public /* bridge */ /* synthetic */ void cancel(@Nullable String str, @Nullable Throwable th) {
        super.cancel(str, th);
    }

    public /* bridge */ /* synthetic */ Attributes getAttributes() {
        return super.getAttributes();
    }

    public /* bridge */ /* synthetic */ void halfClose() {
        super.halfClose();
    }

    public /* bridge */ /* synthetic */ boolean isReady() {
        return super.isReady();
    }

    public /* bridge */ /* synthetic */ void request(int i) {
        super.request(i);
    }

    public /* bridge */ /* synthetic */ void setMessageCompression(boolean z) {
        super.setMessageCompression(z);
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public void start(Listener<RespT> listener, Metadata metadata) {
        delegate().start(listener, metadata);
    }

    public void sendMessage(ReqT reqt) {
        delegate().sendMessage(reqt);
    }
}
