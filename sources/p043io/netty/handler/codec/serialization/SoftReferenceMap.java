package p043io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;

/* renamed from: io.netty.handler.codec.serialization.SoftReferenceMap */
final class SoftReferenceMap<K, V> extends ReferenceMap<K, V> {
    SoftReferenceMap(Map<K, Reference<V>> map) {
        super(map);
    }

    /* access modifiers changed from: 0000 */
    public Reference<V> fold(V v) {
        return new SoftReference(v);
    }
}
