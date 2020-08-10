package p043io.grpc.internal;

import javax.annotation.Nonnull;
import p043io.grpc.Attributes;
import p043io.grpc.Deadline;
import p043io.grpc.DecompressorRegistry;
import p043io.grpc.Status;

/* renamed from: io.grpc.internal.ClientStream */
public interface ClientStream extends Stream {
    void cancel(Status status);

    Attributes getAttributes();

    void halfClose();

    void setAuthority(String str);

    void setDeadline(@Nonnull Deadline deadline);

    void setDecompressorRegistry(DecompressorRegistry decompressorRegistry);

    void setFullStreamDecompression(boolean z);

    void setMaxInboundMessageSize(int i);

    void setMaxOutboundMessageSize(int i);

    void start(ClientStreamListener clientStreamListener);
}
