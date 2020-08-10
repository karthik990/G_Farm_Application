package p043io.grpc.internal;

import p043io.grpc.Attributes;
import p043io.grpc.Metadata;

/* renamed from: io.grpc.internal.ServerTransportListener */
public interface ServerTransportListener {
    void streamCreated(ServerStream serverStream, String str, Metadata metadata);

    Attributes transportReady(Attributes attributes);

    void transportTerminated();
}
