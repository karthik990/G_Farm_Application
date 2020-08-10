package p043io.grpc.internal;

import p043io.grpc.Metadata;
import p043io.grpc.Status;

/* renamed from: io.grpc.internal.ClientStreamListener */
public interface ClientStreamListener extends StreamListener {

    /* renamed from: io.grpc.internal.ClientStreamListener$RpcProgress */
    public enum RpcProgress {
        PROCESSED,
        REFUSED,
        DROPPED
    }

    void closed(Status status, Metadata metadata);

    void closed(Status status, RpcProgress rpcProgress, Metadata metadata);

    void headersRead(Metadata metadata);
}
