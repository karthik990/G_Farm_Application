package p043io.grpc.internal;

import p043io.grpc.Status;

/* renamed from: io.grpc.internal.ServerStreamListener */
public interface ServerStreamListener extends StreamListener {
    void closed(Status status);

    void halfClosed();
}
