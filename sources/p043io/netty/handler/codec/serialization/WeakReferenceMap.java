package p043io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

/* renamed from: io.netty.handler.codec.serialization.WeakReferenceMap */
final class WeakReferenceMap<K, V> extends ReferenceMap<K, V> {
    WeakReferenceMap(Map<K, Reference<V>> map) {
        super(map);
    }

    /* access modifiers changed from: 0000 */
    public Reference<V> fold(V v) {
        return new WeakReference(v);
    }
}
