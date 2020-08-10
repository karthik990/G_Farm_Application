package p043io.netty.util.internal;

import java.util.Queue;

/* renamed from: io.netty.util.internal.PriorityQueue */
public interface PriorityQueue<T> extends Queue<T> {
    boolean containsTyped(T t);

    void priorityChanged(T t);

    boolean removeTyped(T t);
}
