package p043io.netty.util.collection;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import org.objectweb.asm.signature.SignatureVisitor;
import p043io.netty.util.collection.ShortObjectMap.PrimitiveEntry;
import p043io.netty.util.internal.MathUtil;

/* renamed from: io.netty.util.collection.ShortObjectHashMap */
public class ShortObjectHashMap<V> implements ShortObjectMap<V> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DEFAULT_CAPACITY = 8;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object NULL_VALUE = new Object();
    private final Iterable<PrimitiveEntry<V>> entries;
    /* access modifiers changed from: private */
    public final Set<Entry<Short, V>> entrySet;
    private final Set<Short> keySet;
    /* access modifiers changed from: private */
    public short[] keys;
    private final float loadFactor;
    private int mask;
    private int maxSize;
    /* access modifiers changed from: private */
    public int size;
    /* access modifiers changed from: private */
    public V[] values;

    /* renamed from: io.netty.util.collection.ShortObjectHashMap$EntrySet */
    private final class EntrySet extends AbstractSet<Entry<Short, V>> {
        private EntrySet() {
        }

        public Iterator<Entry<Short, V>> iterator() {
            return new MapIterator();
        }

        public int size() {
            return ShortObjectHashMap.this.size();
        }
    }

    /* renamed from: io.netty.util.collection.ShortObjectHashMap$KeySet */
    private final class KeySet extends AbstractSet<Short> {
        private KeySet() {
        }

        public int size() {
            return ShortObjectHashMap.this.size();
        }

        public boolean contains(Object obj) {
            return ShortObjectHashMap.this.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return ShortObjectHashMap.this.remove(obj) != null;
        }

        public boolean retainAll(Collection<?> collection) {
            Iterator it = ShortObjectHashMap.this.entries().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(Short.valueOf(((PrimitiveEntry) it.next()).key()))) {
                    z = true;
                    it.remove();
                }
            }
            return z;
        }

        public void clear() {
            ShortObjectHashMap.this.clear();
        }

        public Iterator<Short> iterator() {
            return new Iterator<Short>() {
                private final Iterator<Entry<Short, V>> iter = ShortObjectHashMap.this.entrySet.iterator();

                public boolean hasNext() {
                    return this.iter.hasNext();
                }

                public Short next() {
                    return (Short) ((Entry) this.iter.next()).getKey();
                }

                public void remove() {
                    this.iter.remove();
                }
            };
        }
    }

    /* renamed from: io.netty.util.collection.ShortObjectHashMap$MapEntry */
    final class MapEntry implements Entry<Short, V> {
        private final int entryIndex;

        MapEntry(int i) {
            this.entryIndex = i;
        }

        public Short getKey() {
            verifyExists();
            return Short.valueOf(ShortObjectHashMap.this.keys[this.entryIndex]);
        }

        public V getValue() {
            verifyExists();
            return ShortObjectHashMap.toExternal(ShortObjectHashMap.this.values[this.entryIndex]);
        }

        public V setValue(V v) {
            verifyExists();
            V access$900 = ShortObjectHashMap.toExternal(ShortObjectHashMap.this.values[this.entryIndex]);
            ShortObjectHashMap.this.values[this.entryIndex] = ShortObjectHashMap.toInternal(v);
            return access$900;
        }

        private void verifyExists() {
            if (ShortObjectHashMap.this.values[this.entryIndex] == null) {
                throw new IllegalStateException("The map entry has been removed");
            }
        }
    }

    /* renamed from: io.netty.util.collection.ShortObjectHashMap$MapIterator */
    private final class MapIterator implements Iterator<Entry<Short, V>> {
        private final PrimitiveIterator iter;

        private MapIterator() {
            this.iter = new PrimitiveIterator<>();
        }

        public boolean hasNext() {
            return this.iter.hasNext();
        }

        public Entry<Short, V> next() {
            if (hasNext()) {
                this.iter.next();
                return new MapEntry(this.iter.entryIndex);
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            this.iter.remove();
        }
    }

    /* renamed from: io.netty.util.collection.ShortObjectHashMap$PrimitiveIterator */
    private final class PrimitiveIterator implements Iterator<PrimitiveEntry<V>>, PrimitiveEntry<V> {
        /* access modifiers changed from: private */
        public int entryIndex;
        private int nextIndex;
        private int prevIndex;

        private PrimitiveIterator() {
            this.prevIndex = -1;
            this.nextIndex = -1;
            this.entryIndex = -1;
        }

        private void scanNext() {
            do {
                int i = this.nextIndex + 1;
                this.nextIndex = i;
                if (i == ShortObjectHashMap.this.values.length) {
                    return;
                }
            } while (ShortObjectHashMap.this.values[this.nextIndex] == null);
        }

        public boolean hasNext() {
            if (this.nextIndex == -1) {
                scanNext();
            }
            return this.nextIndex != ShortObjectHashMap.this.values.length;
        }

        public PrimitiveEntry<V> next() {
            if (hasNext()) {
                this.prevIndex = this.nextIndex;
                scanNext();
                this.entryIndex = this.prevIndex;
                return this;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            int i = this.prevIndex;
            if (i != -1) {
                if (ShortObjectHashMap.this.removeAt(i)) {
                    this.nextIndex = this.prevIndex;
                }
                this.prevIndex = -1;
                return;
            }
            throw new IllegalStateException("next must be called before each remove.");
        }

        public short key() {
            return ShortObjectHashMap.this.keys[this.entryIndex];
        }

        public V value() {
            return ShortObjectHashMap.toExternal(ShortObjectHashMap.this.values[this.entryIndex]);
        }

        public void setValue(V v) {
            ShortObjectHashMap.this.values[this.entryIndex] = ShortObjectHashMap.toInternal(v);
        }
    }

    private static int hashCode(short s) {
        return s;
    }

    public ShortObjectHashMap() {
        this(8, 0.5f);
    }

    public ShortObjectHashMap(int i) {
        this(i, 0.5f);
    }

    public ShortObjectHashMap(int i, float f) {
        this.keySet = new KeySet();
        this.entrySet = new EntrySet();
        this.entries = new Iterable<PrimitiveEntry<V>>() {
            public Iterator<PrimitiveEntry<V>> iterator() {
                return new PrimitiveIterator();
            }
        };
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
        }
        this.loadFactor = f;
        int safeFindNextPositivePowerOfTwo = MathUtil.safeFindNextPositivePowerOfTwo(i);
        this.mask = safeFindNextPositivePowerOfTwo - 1;
        this.keys = new short[safeFindNextPositivePowerOfTwo];
        this.values = (Object[]) new Object[safeFindNextPositivePowerOfTwo];
        this.maxSize = calcMaxSize(safeFindNextPositivePowerOfTwo);
    }

    /* access modifiers changed from: private */
    public static <T> T toExternal(T t) {
        if (t == NULL_VALUE) {
            return null;
        }
        return t;
    }

    /* access modifiers changed from: private */
    public static <T> T toInternal(T t) {
        return t == null ? NULL_VALUE : t;
    }

    public V get(short s) {
        int indexOf = indexOf(s);
        if (indexOf == -1) {
            return null;
        }
        return toExternal(this.values[indexOf]);
    }

    public V put(short s, V v) {
        int hashIndex = hashIndex(s);
        int i = hashIndex;
        do {
            V[] vArr = this.values;
            if (vArr[i] == null) {
                this.keys[i] = s;
                vArr[i] = toInternal(v);
                growSize();
                return null;
            } else if (this.keys[i] == s) {
                V v2 = vArr[i];
                vArr[i] = toInternal(v);
                return toExternal(v2);
            } else {
                i = probeNext(i);
            }
        } while (i != hashIndex);
        throw new IllegalStateException("Unable to insert");
    }

    public void putAll(Map<? extends Short, ? extends V> map) {
        if (map instanceof ShortObjectHashMap) {
            ShortObjectHashMap shortObjectHashMap = (ShortObjectHashMap) map;
            int i = 0;
            while (true) {
                V[] vArr = shortObjectHashMap.values;
                if (i < vArr.length) {
                    V v = vArr[i];
                    if (v != null) {
                        put(shortObjectHashMap.keys[i], v);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else {
            for (Entry entry : map.entrySet()) {
                put((Short) entry.getKey(), (V) entry.getValue());
            }
        }
    }

    public V remove(short s) {
        int indexOf = indexOf(s);
        if (indexOf == -1) {
            return null;
        }
        V v = this.values[indexOf];
        removeAt(indexOf);
        return toExternal(v);
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        Arrays.fill(this.keys, 0);
        Arrays.fill(this.values, null);
        this.size = 0;
    }

    public boolean containsKey(short s) {
        return indexOf(s) >= 0;
    }

    public boolean containsValue(Object obj) {
        V[] vArr;
        Object internal = toInternal(obj);
        for (V v : this.values) {
            if (v != null && v.equals(internal)) {
                return true;
            }
        }
        return false;
    }

    public Iterable<PrimitiveEntry<V>> entries() {
        return this.entries;
    }

    public Collection<V> values() {
        return new AbstractCollection<V>() {
            public Iterator<V> iterator() {
                return new Iterator<V>() {
                    final PrimitiveIterator iter = new PrimitiveIterator<>();

                    public boolean hasNext() {
                        return this.iter.hasNext();
                    }

                    public V next() {
                        return this.iter.next().value();
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            public int size() {
                return ShortObjectHashMap.this.size;
            }
        };
    }

    public int hashCode() {
        int i = this.size;
        for (short hashCode : this.keys) {
            i ^= hashCode(hashCode);
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortObjectMap)) {
            return false;
        }
        ShortObjectMap shortObjectMap = (ShortObjectMap) obj;
        if (this.size != shortObjectMap.size()) {
            return false;
        }
        int i = 0;
        while (true) {
            V[] vArr = this.values;
            if (i >= vArr.length) {
                return true;
            }
            V v = vArr[i];
            if (v != null) {
                Object obj2 = shortObjectMap.get(this.keys[i]);
                if (v == NULL_VALUE) {
                    if (obj2 != null) {
                        return false;
                    }
                } else if (!v.equals(obj2)) {
                    return false;
                }
            }
            i++;
        }
    }

    public boolean containsKey(Object obj) {
        return containsKey(objectToKey(obj));
    }

    public V get(Object obj) {
        return get(objectToKey(obj));
    }

    public V put(Short sh, V v) {
        return put(objectToKey(sh), v);
    }

    public V remove(Object obj) {
        return remove(objectToKey(obj));
    }

    public Set<Short> keySet() {
        return this.keySet;
    }

    public Set<Entry<Short, V>> entrySet() {
        return this.entrySet;
    }

    private short objectToKey(Object obj) {
        return ((Short) obj).shortValue();
    }

    private int indexOf(short s) {
        int hashIndex = hashIndex(s);
        int i = hashIndex;
        while (this.values[i] != null) {
            if (s == this.keys[i]) {
                return i;
            }
            i = probeNext(i);
            if (i == hashIndex) {
                return -1;
            }
        }
        return -1;
    }

    private int hashIndex(short s) {
        return hashCode(s) & this.mask;
    }

    private int probeNext(int i) {
        return (i + 1) & this.mask;
    }

    private void growSize() {
        this.size++;
        if (this.size > this.maxSize) {
            short[] sArr = this.keys;
            if (sArr.length != Integer.MAX_VALUE) {
                rehash(sArr.length << 1);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Max capacity reached at size=");
            sb.append(this.size);
            throw new IllegalStateException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public boolean removeAt(int i) {
        this.size--;
        this.keys[i] = 0;
        this.values[i] = null;
        int probeNext = probeNext(i);
        V v = this.values[probeNext];
        int i2 = probeNext;
        int i3 = i;
        while (v != null) {
            short s = this.keys[i2];
            int hashIndex = hashIndex(s);
            if ((i2 < hashIndex && (hashIndex <= i3 || i3 <= i2)) || (hashIndex <= i3 && i3 <= i2)) {
                short[] sArr = this.keys;
                sArr[i3] = s;
                V[] vArr = this.values;
                vArr[i3] = v;
                sArr[i2] = 0;
                vArr[i2] = null;
                i3 = i2;
            }
            V[] vArr2 = this.values;
            i2 = probeNext(i2);
            v = vArr2[i2];
        }
        if (i3 != i) {
            return true;
        }
        return false;
    }

    private int calcMaxSize(int i) {
        return Math.min(i - 1, (int) (((float) i) * this.loadFactor));
    }

    private void rehash(int i) {
        V[] vArr;
        short[] sArr = this.keys;
        V[] vArr2 = this.values;
        this.keys = new short[i];
        this.values = (Object[]) new Object[i];
        this.maxSize = calcMaxSize(i);
        this.mask = i - 1;
        for (int i2 = 0; i2 < vArr2.length; i2++) {
            V v = vArr2[i2];
            if (v != null) {
                short s = sArr[i2];
                int hashIndex = hashIndex(s);
                while (true) {
                    vArr = this.values;
                    if (vArr[hashIndex] == null) {
                        break;
                    }
                    hashIndex = probeNext(hashIndex);
                }
                this.keys[hashIndex] = s;
                vArr[hashIndex] = v;
            }
        }
    }

    public String toString() {
        Object obj;
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.size * 4);
        sb.append('{');
        int i = 0;
        boolean z = true;
        while (true) {
            V[] vArr = this.values;
            if (i < vArr.length) {
                V v = vArr[i];
                if (v != null) {
                    if (!z) {
                        sb.append(", ");
                    }
                    sb.append(keyToString(this.keys[i]));
                    sb.append(SignatureVisitor.INSTANCEOF);
                    if (v == this) {
                        obj = "(this Map)";
                    } else {
                        obj = toExternal(v);
                    }
                    sb.append(obj);
                    z = false;
                }
                i++;
            } else {
                sb.append('}');
                return sb.toString();
            }
        }
    }

    /* access modifiers changed from: protected */
    public String keyToString(short s) {
        return Short.toString(s);
    }
}
