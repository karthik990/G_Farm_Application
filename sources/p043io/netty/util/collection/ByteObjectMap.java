package p043io.netty.util.collection;

import java.util.Map;

/* renamed from: io.netty.util.collection.ByteObjectMap */
public interface ByteObjectMap<V> extends Map<Byte, V> {

    /* renamed from: io.netty.util.collection.ByteObjectMap$PrimitiveEntry */
    public interface PrimitiveEntry<V> {
        byte key();

        void setValue(V v);

        V value();
    }

    boolean containsKey(byte b);

    Iterable<PrimitiveEntry<V>> entries();

    V get(byte b);

    V put(byte b, V v);

    V remove(byte b);
}
