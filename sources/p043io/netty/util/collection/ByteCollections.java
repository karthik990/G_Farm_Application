package p043io.netty.util.collection;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import p043io.netty.util.collection.ByteObjectMap.PrimitiveEntry;

/* renamed from: io.netty.util.collection.ByteCollections */
public final class ByteCollections {
    private static final ByteObjectMap<Object> EMPTY_MAP = new EmptyMap();

    /* renamed from: io.netty.util.collection.ByteCollections$EmptyMap */
    private static final class EmptyMap implements ByteObjectMap<Object> {
        public void clear() {
        }

        public boolean containsKey(byte b) {
            return false;
        }

        public boolean containsKey(Object obj) {
            return false;
        }

        public boolean containsValue(Object obj) {
            return false;
        }

        public Object get(byte b) {
            return null;
        }

        public Object get(Object obj) {
            return null;
        }

        public boolean isEmpty() {
            return true;
        }

        public Object remove(byte b) {
            return null;
        }

        public Object remove(Object obj) {
            return null;
        }

        public int size() {
            return 0;
        }

        private EmptyMap() {
        }

        public Object put(byte b, Object obj) {
            throw new UnsupportedOperationException("put");
        }

        public Set<Byte> keySet() {
            return Collections.emptySet();
        }

        public Iterable<PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        public Object put(Byte b, Object obj) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Map<? extends Byte, ?> map) {
            throw new UnsupportedOperationException();
        }

        public Collection<Object> values() {
            return Collections.emptyList();
        }

        public Set<Entry<Byte, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    /* renamed from: io.netty.util.collection.ByteCollections$UnmodifiableMap */
    private static final class UnmodifiableMap<V> implements ByteObjectMap<V> {
        private Iterable<PrimitiveEntry<V>> entries;
        private Set<Entry<Byte, V>> entrySet;
        private Set<Byte> keySet;
        /* access modifiers changed from: private */
        public final ByteObjectMap<V> map;
        private Collection<V> values;

        /* renamed from: io.netty.util.collection.ByteCollections$UnmodifiableMap$EntryImpl */
        private class EntryImpl implements PrimitiveEntry<V> {
            private final PrimitiveEntry<V> entry;

            EntryImpl(PrimitiveEntry<V> primitiveEntry) {
                this.entry = primitiveEntry;
            }

            public byte key() {
                return this.entry.key();
            }

            public V value() {
                return this.entry.value();
            }

            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }

        /* renamed from: io.netty.util.collection.ByteCollections$UnmodifiableMap$IteratorImpl */
        private class IteratorImpl implements Iterator<PrimitiveEntry<V>> {
            final Iterator<PrimitiveEntry<V>> iter;

            IteratorImpl(Iterator<PrimitiveEntry<V>> it) {
                this.iter = it;
            }

            public boolean hasNext() {
                return this.iter.hasNext();
            }

            public PrimitiveEntry<V> next() {
                if (hasNext()) {
                    return new EntryImpl((PrimitiveEntry) this.iter.next());
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
            }
        }

        UnmodifiableMap(ByteObjectMap<V> byteObjectMap) {
            this.map = byteObjectMap;
        }

        public V get(byte b) {
            return this.map.get(b);
        }

        public V put(byte b, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(byte b) {
            throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
        }

        public int size() {
            return this.map.size();
        }

        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        public boolean containsKey(byte b) {
            return this.map.containsKey(b);
        }

        public boolean containsValue(Object obj) {
            return this.map.containsValue(obj);
        }

        public boolean containsKey(Object obj) {
            return this.map.containsKey(obj);
        }

        public V get(Object obj) {
            return this.map.get(obj);
        }

        public V put(Byte b, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(Object obj) {
            throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
        }

        public void putAll(Map<? extends Byte, ? extends V> map2) {
            throw new UnsupportedOperationException("putAll");
        }

        public Iterable<PrimitiveEntry<V>> entries() {
            if (this.entries == null) {
                this.entries = new Iterable<PrimitiveEntry<V>>() {
                    public Iterator<PrimitiveEntry<V>> iterator() {
                        UnmodifiableMap unmodifiableMap = UnmodifiableMap.this;
                        return new IteratorImpl(unmodifiableMap.map.entries().iterator());
                    }
                };
            }
            return this.entries;
        }

        public Set<Byte> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        public Set<Entry<Byte, V>> entrySet() {
            if (this.entrySet == null) {
                this.entrySet = Collections.unmodifiableSet(this.map.entrySet());
            }
            return this.entrySet;
        }

        public Collection<V> values() {
            if (this.values == null) {
                this.values = Collections.unmodifiableCollection(this.map.values());
            }
            return this.values;
        }
    }

    private ByteCollections() {
    }

    public static <V> ByteObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> ByteObjectMap<V> unmodifiableMap(ByteObjectMap<V> byteObjectMap) {
        return new UnmodifiableMap(byteObjectMap);
    }
}
