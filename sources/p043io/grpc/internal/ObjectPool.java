package p043io.grpc.internal;

/* renamed from: io.grpc.internal.ObjectPool */
public interface ObjectPool<T> {
    T getObject();

    T returnObject(Object obj);
}
