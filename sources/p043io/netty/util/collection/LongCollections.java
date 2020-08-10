package p043io.netty.util.collection;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import p043io.netty.util.collection.LongObjectMap.PrimitiveEntry;

/* renamed from: io.netty.util.collection.LongCollections */
public final class LongCollections {
    private static final LongObjectMap<Object> EMPTY_MAP = new EmptyMap();

    /* renamed from: io.netty.util.collection.LongCollections$EmptyMap */
    private static final class EmptyMap implements LongObjectMap<Object> {
        public void clear() {
        }

        public boolean containsKey(long j) {
            return false;
        }

        public boolean containsKey(Object obj) {
            return false;
        }

        public boolean containsValue(Object obj) {
            return false;
        }

        public Object get(long j) {
            return null;
        }

        public Object get(Object obj) {
            return null;
        }

        public boolean isEmpty() {
            return true;
        }

        public Object remove(long j) {
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

        public Object put(long j, Object obj) {
            throw new UnsupportedOperationException("put");
        }

        public Set<Long> keySet() {
            return Collections.emptySet();
        }

        public Iterable<PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        public Object put(Long l, Object obj) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Map<? extends Long, ?> map) {
            throw new UnsupportedOperationException();
        }

        public Collection<Object> values() {
            return Collections.emptyList();
        }

        public Set<Entry<Long, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    /* renamed from: io.netty.util.collection.LongCollections$UnmodifiableMap */
    private static final class UnmodifiableMap<V> implements LongObjectMap<V> {
        private Iterable<PrimitiveEntry<V>> entries;
        private Set<Entry<Long, V>> entrySet;
        private Set<Long> keySet;
        /* access modifiers changed from: private */
        public final LongObjectMap<V> map;
        private Collection<V> values;

        /* renamed from: io.netty.util.collection.LongCollections$UnmodifiableMap$EntryImpl */
        private class EntryImpl implements PrimitiveEntry<V> {
            private final PrimitiveEntry<V> entry;

            EntryImpl(PrimitiveEntry<V> primitiveEntry) {
                this.entry = primitiveEntry;
            }

            public long key() {
                return this.entry.key();
            }

            public V value() {
                return this.entry.value();
            }

            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }

        /* renamed from: io.netty.util.collection.LongCollections$UnmodifiableMap$IteratorImpl */
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

        UnmodifiableMap(LongObjectMap<V> longObjectMap) {
            this.map = longObjectMap;
        }

        public V get(long j) {
            return this.map.get(j);
        }

        public V put(long j, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(long j) {
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

        public boolean containsKey(long j) {
            return this.map.containsKey(j);
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

        public V put(Long l, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(Object obj) {
            throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
        }

        public void putAll(Map<? extends Long, ? extends V> map2) {
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

        public Set<Long> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        public Set<Entry<Long, V>> entrySet() {
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

    private LongCollections() {
    }

    public static <V> LongObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> LongObjectMap<V> unmodifiableMap(LongObjectMap<V> longObjectMap) {
        return new UnmodifiableMap(longObjectMap);
    }
}
