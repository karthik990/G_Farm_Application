package p043io.grpc.stub;

import javax.annotation.Nullable;

/* renamed from: io.grpc.stub.ClientCallStreamObserver */
public abstract class ClientCallStreamObserver<V> extends CallStreamObserver<V> {
    public abstract void cancel(@Nullable String str, @Nullable Throwable th);
}
