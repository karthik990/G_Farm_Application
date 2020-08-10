package p043io.grpc;

/* renamed from: io.grpc.ClientStreamTracer */
public abstract class ClientStreamTracer extends StreamTracer {

    /* renamed from: io.grpc.ClientStreamTracer$Factory */
    public static abstract class Factory {
        @Deprecated
        public ClientStreamTracer newClientStreamTracer(Metadata metadata) {
            throw new UnsupportedOperationException("This method will be deleted. Do not call.");
        }

        public ClientStreamTracer newClientStreamTracer(CallOptions callOptions, Metadata metadata) {
            return newClientStreamTracer(metadata);
        }
    }

    public void inboundHeaders() {
    }

    public void outboundHeaders() {
    }
}
