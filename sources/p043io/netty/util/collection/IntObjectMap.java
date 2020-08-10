package p043io.netty.util.collection;

import java.util.Map;

/* renamed from: io.netty.util.collection.IntObjectMap */
public interface IntObjectMap<V> extends Map<Integer, V> {

    /* renamed from: io.netty.util.collection.IntObjectMap$PrimitiveEntry */
    public interface PrimitiveEntry<V> {
        int key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(int i);

    Iterable<PrimitiveEntry<V>> entries();

    V get(int i);

    V put(int i, V v);

    V remove(int i);
}
