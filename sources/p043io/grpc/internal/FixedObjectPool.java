package p043io.grpc.internal;

import com.google.common.base.Preconditions;

/* renamed from: io.grpc.internal.FixedObjectPool */
public final class FixedObjectPool<T> implements ObjectPool<T> {
    private final T object;

    public T returnObject(Object obj) {
        return null;
    }

    public FixedObjectPool(T t) {
        this.object = Preconditions.checkNotNull(t, "object");
    }

    public T getObject() {
        return this.object;
    }
}
