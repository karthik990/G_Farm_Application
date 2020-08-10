package p043io.grpc.internal;

import javax.annotation.Nullable;
import p043io.grpc.Attributes;
import p043io.grpc.Decompressor;
import p043io.grpc.Metadata;
import p043io.grpc.Status;

/* renamed from: io.grpc.internal.ServerStream */
public interface ServerStream extends Stream {
    void cancel(Status status);

    void close(Status status, Metadata metadata);

    Attributes getAttributes();

    @Nullable
    String getAuthority();

    void setDecompressor(Decompressor decompressor);

    void setListener(ServerStreamListener serverStreamListener);

    StatsTraceContext statsTraceContext();

    void writeHeaders(Metadata metadata);
}
