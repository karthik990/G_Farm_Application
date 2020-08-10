package p043io.grpc.stub;

/* renamed from: io.grpc.stub.ServerCallStreamObserver */
public abstract class ServerCallStreamObserver<V> extends CallStreamObserver<V> {
    public abstract boolean isCancelled();

    public abstract void setCompression(String str);

    public abstract void setOnCancelHandler(Runnable runnable);
}
