package p043io.netty.util.collection;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import p043io.netty.util.collection.CharObjectMap.PrimitiveEntry;

/* renamed from: io.netty.util.collection.CharCollections */
public final class CharCollections {
    private static final CharObjectMap<Object> EMPTY_MAP = new EmptyMap();

    /* renamed from: io.netty.util.collection.CharCollections$EmptyMap */
    private static final class EmptyMap implements CharObjectMap<Object> {
        public void clear() {
        }

        public boolean containsKey(char c) {
            return false;
        }

        public boolean containsKey(Object obj) {
            return false;
        }

        public boolean containsValue(Object obj) {
            return false;
        }

        public Object get(char c) {
            return null;
        }

        public Object get(Object obj) {
            return null;
        }

        public boolean isEmpty() {
            return true;
        }

        public Object remove(char c) {
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

        public Object put(char c, Object obj) {
            throw new UnsupportedOperationException("put");
        }

        public Set<Character> keySet() {
            return Collections.emptySet();
        }

        public Iterable<PrimitiveEntry<Object>> entries() {
            return Collections.emptySet();
        }

        public Object put(Character ch, Object obj) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Map<? extends Character, ?> map) {
            throw new UnsupportedOperationException();
        }

        public Collection<Object> values() {
            return Collections.emptyList();
        }

        public Set<Entry<Character, Object>> entrySet() {
            return Collections.emptySet();
        }
    }

    /* renamed from: io.netty.util.collection.CharCollections$UnmodifiableMap */
    private static final class UnmodifiableMap<V> implements CharObjectMap<V> {
        private Iterable<PrimitiveEntry<V>> entries;
        private Set<Entry<Character, V>> entrySet;
        private Set<Character> keySet;
        /* access modifiers changed from: private */
        public final CharObjectMap<V> map;
        private Collection<V> values;

        /* renamed from: io.netty.util.collection.CharCollections$UnmodifiableMap$EntryImpl */
        private class EntryImpl implements PrimitiveEntry<V> {
            private final PrimitiveEntry<V> entry;

            EntryImpl(PrimitiveEntry<V> primitiveEntry) {
                this.entry = primitiveEntry;
            }

            public char key() {
                return this.entry.key();
            }

            public V value() {
                return this.entry.value();
            }

            public void setValue(V v) {
                throw new UnsupportedOperationException("setValue");
            }
        }

        /* renamed from: io.netty.util.collection.CharCollections$UnmodifiableMap$IteratorImpl */
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

        UnmodifiableMap(CharObjectMap<V> charObjectMap) {
            this.map = charObjectMap;
        }

        public V get(char c) {
            return this.map.get(c);
        }

        public V put(char c, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(char c) {
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

        public boolean containsKey(char c) {
            return this.map.containsKey(c);
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

        public V put(Character ch, V v) {
            throw new UnsupportedOperationException("put");
        }

        public V remove(Object obj) {
            throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
        }

        public void putAll(Map<? extends Character, ? extends V> map2) {
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

        public Set<Character> keySet() {
            if (this.keySet == null) {
                this.keySet = Collections.unmodifiableSet(this.map.keySet());
            }
            return this.keySet;
        }

        public Set<Entry<Character, V>> entrySet() {
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

    private CharCollections() {
    }

    public static <V> CharObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> CharObjectMap<V> unmodifiableMap(CharObjectMap<V> charObjectMap) {
        return new UnmodifiableMap(charObjectMap);
    }
}
