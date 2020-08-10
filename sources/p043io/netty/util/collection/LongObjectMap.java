package p043io.netty.util.collection;

import java.util.Map;

/* renamed from: io.netty.util.collection.LongObjectMap */
public interface LongObjectMap<V> extends Map<Long, V> {

    /* renamed from: io.netty.util.collection.LongObjectMap$PrimitiveEntry */
    public interface PrimitiveEntry<V> {
        long key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(long j);

    Iterable<PrimitiveEntry<V>> entries();

    V get(long j);

    V put(long j, V v);

    V remove(long j);
}
