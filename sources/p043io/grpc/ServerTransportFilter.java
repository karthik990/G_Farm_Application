package p043io.grpc;

/* renamed from: io.grpc.ServerTransportFilter */
public abstract class ServerTransportFilter {
    public Attributes transportReady(Attributes attributes) {
        return attributes;
    }

    public void transportTerminated(Attributes attributes) {
    }
}
