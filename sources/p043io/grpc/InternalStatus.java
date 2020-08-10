package p043io.grpc;

import p043io.grpc.Metadata.Key;

/* renamed from: io.grpc.InternalStatus */
public final class InternalStatus {
    public static final Key<Status> CODE_KEY = Status.CODE_KEY;
    public static final Key<String> MESSAGE_KEY = Status.MESSAGE_KEY;

    private InternalStatus() {
    }
}
