package p043io.netty.util.collection;

import java.util.Map;

/* renamed from: io.netty.util.collection.ShortObjectMap */
public interface ShortObjectMap<V> extends Map<Short, V> {

    /* renamed from: io.netty.util.collection.ShortObjectMap$PrimitiveEntry */
    public interface PrimitiveEntry<V> {
        short key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(short s);

    Iterable<PrimitiveEntry<V>> entries();

    V get(short s);

    V put(short s, V v);

    V remove(short s);
}
