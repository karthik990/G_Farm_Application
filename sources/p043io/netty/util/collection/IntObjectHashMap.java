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
import p043io.netty.util.collection.IntObjectMap.PrimitiveEntry;
import p043io.netty.util.internal.MathUtil;

/* renamed from: io.netty.util.collection.IntObjectHashMap */
public class IntObjectHashMap<V> implements IntObjectMap<V> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DEFAULT_CAPACITY = 8;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object NULL_VALUE = new Object();
    private final Iterable<PrimitiveEntry<V>> entries;
    /* access modifiers changed from: private */
    public final Set<Entry<Integer, V>> entrySet;
    private final Set<Integer> keySet;
    /* access modifiers changed from: private */
    public int[] keys;
    private final float loadFactor;
    private int mask;
    private int maxSize;
    /* access modifiers changed from: private */
    public int size;
    /* access modifiers changed from: private */
    public V[] values;

    /* renamed from: io.netty.util.collection.IntObjectHashMap$EntrySet */
    private final class EntrySet extends AbstractSet<Entry<Integer, V>> {
        private EntrySet() {
        }

        public Iterator<Entry<Integer, V>> iterator() {
            return new MapIterator();
        }

        public int size() {
            return IntObjectHashMap.this.size();
        }
    }

    /* renamed from: io.netty.util.collection.IntObjectHashMap$KeySet */
    private final class KeySet extends AbstractSet<Integer> {
        private KeySet() {
        }

        public int size() {
            return IntObjectHashMap.this.size();
        }

        public boolean contains(Object obj) {
            return IntObjectHashMap.this.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return IntObjectHashMap.this.remove(obj) != null;
        }

        public boolean retainAll(Collection<?> collection) {
            Iterator it = IntObjectHashMap.this.entries().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(Integer.valueOf(((PrimitiveEntry) it.next()).key()))) {
                    z = true;
                    it.remove();
                }
            }
            return z;
        }

        public void clear() {
            IntObjectHashMap.this.clear();
        }

        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                private final Iterator<Entry<Integer, V>> iter = IntObjectHashMap.this.entrySet.iterator();

                public boolean hasNext() {
                    return this.iter.hasNext();
                }

                public Integer next() {
                    return (Integer) ((Entry) this.iter.next()).getKey();
                }

                public void remove() {
                    this.iter.remove();
                }
            };
        }
    }

    /* renamed from: io.netty.util.collection.IntObjectHashMap$MapEntry */
    final class MapEntry implements Entry<Integer, V> {
        private final int entryIndex;

        MapEntry(int i) {
            this.entryIndex = i;
        }

        public Integer getKey() {
            verifyExists();
            return Integer.valueOf(IntObjectHashMap.this.keys[this.entryIndex]);
        }

        public V getValue() {
            verifyExists();
            return IntObjectHashMap.toExternal(IntObjectHashMap.this.values[this.entryIndex]);
        }

        public V setValue(V v) {
            verifyExists();
            V access$900 = IntObjectHashMap.toExternal(IntObjectHashMap.this.values[this.entryIndex]);
            IntObjectHashMap.this.values[this.entryIndex] = IntObjectHashMap.toInternal(v);
            return access$900;
        }

        private void verifyExists() {
            if (IntObjectHashMap.this.values[this.entryIndex] == null) {
                throw new IllegalStateException("The map entry has been removed");
            }
        }
    }

    /* renamed from: io.netty.util.collection.IntObjectHashMap$MapIterator */
    private final class MapIterator implements Iterator<Entry<Integer, V>> {
        private final PrimitiveIterator iter;

        private MapIterator() {
            this.iter = new PrimitiveIterator<>();
        }

        public boolean hasNext() {
            return this.iter.hasNext();
        }

        public Entry<Integer, V> next() {
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

    /* renamed from: io.netty.util.collection.IntObjectHashMap$PrimitiveIterator */
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
                if (i == IntObjectHashMap.this.values.length) {
                    return;
                }
            } while (IntObjectHashMap.this.values[this.nextIndex] == null);
        }

        public boolean hasNext() {
            if (this.nextIndex == -1) {
                scanNext();
            }
            return this.nextIndex != IntObjectHashMap.this.values.length;
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
                if (IntObjectHashMap.this.removeAt(i)) {
                    this.nextIndex = this.prevIndex;
                }
                this.prevIndex = -1;
                return;
            }
            throw new IllegalStateException("next must be called before each remove.");
        }

        public int key() {
            return IntObjectHashMap.this.keys[this.entryIndex];
        }

        public V value() {
            return IntObjectHashMap.toExternal(IntObjectHashMap.this.values[this.entryIndex]);
        }

        public void setValue(V v) {
            IntObjectHashMap.this.values[this.entryIndex] = IntObjectHashMap.toInternal(v);
        }
    }

    private static int hashCode(int i) {
        return i;
    }

    public IntObjectHashMap() {
        this(8, 0.5f);
    }

    public IntObjectHashMap(int i) {
        this(i, 0.5f);
    }

    public IntObjectHashMap(int i, float f) {
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
        this.keys = new int[safeFindNextPositivePowerOfTwo];
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

    public V get(int i) {
        int indexOf = indexOf(i);
        if (indexOf == -1) {
            return null;
        }
        return toExternal(this.values[indexOf]);
    }

    public V put(int i, V v) {
        int hashIndex = hashIndex(i);
        int i2 = hashIndex;
        do {
            V[] vArr = this.values;
            if (vArr[i2] == null) {
                this.keys[i2] = i;
                vArr[i2] = toInternal(v);
                growSize();
                return null;
            } else if (this.keys[i2] == i) {
                V v2 = vArr[i2];
                vArr[i2] = toInternal(v);
                return toExternal(v2);
            } else {
                i2 = probeNext(i2);
            }
        } while (i2 != hashIndex);
        throw new IllegalStateException("Unable to insert");
    }

    public void putAll(Map<? extends Integer, ? extends V> map) {
        if (map instanceof IntObjectHashMap) {
            IntObjectHashMap intObjectHashMap = (IntObjectHashMap) map;
            int i = 0;
            while (true) {
                V[] vArr = intObjectHashMap.values;
                if (i < vArr.length) {
                    V v = vArr[i];
                    if (v != null) {
                        put(intObjectHashMap.keys[i], v);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else {
            for (Entry entry : map.entrySet()) {
                put((Integer) entry.getKey(), (V) entry.getValue());
            }
        }
    }

    public V remove(int i) {
        int indexOf = indexOf(i);
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

    public boolean containsKey(int i) {
        return indexOf(i) >= 0;
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
                return IntObjectHashMap.this.size;
            }
        };
    }

    public int hashCode() {
        int i = this.size;
        for (int hashCode : this.keys) {
            i ^= hashCode(hashCode);
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntObjectMap)) {
            return false;
        }
        IntObjectMap intObjectMap = (IntObjectMap) obj;
        if (this.size != intObjectMap.size()) {
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
                Object obj2 = intObjectMap.get(this.keys[i]);
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

    public V put(Integer num, V v) {
        return put(objectToKey(num), v);
    }

    public V remove(Object obj) {
        return remove(objectToKey(obj));
    }

    public Set<Integer> keySet() {
        return this.keySet;
    }

    public Set<Entry<Integer, V>> entrySet() {
        return this.entrySet;
    }

    private int objectToKey(Object obj) {
        return ((Integer) obj).intValue();
    }

    private int indexOf(int i) {
        int hashIndex = hashIndex(i);
        int i2 = hashIndex;
        while (this.values[i2] != null) {
            if (i == this.keys[i2]) {
                return i2;
            }
            i2 = probeNext(i2);
            if (i2 == hashIndex) {
                return -1;
            }
        }
        return -1;
    }

    private int hashIndex(int i) {
        return hashCode(i) & this.mask;
    }

    private int probeNext(int i) {
        return (i + 1) & this.mask;
    }

    private void growSize() {
        this.size++;
        if (this.size > this.maxSize) {
            int[] iArr = this.keys;
            if (iArr.length != Integer.MAX_VALUE) {
                rehash(iArr.length << 1);
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
            int i4 = this.keys[i2];
            int hashIndex = hashIndex(i4);
            if ((i2 < hashIndex && (hashIndex <= i3 || i3 <= i2)) || (hashIndex <= i3 && i3 <= i2)) {
                int[] iArr = this.keys;
                iArr[i3] = i4;
                V[] vArr = this.values;
                vArr[i3] = v;
                iArr[i2] = 0;
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
        int[] iArr = this.keys;
        V[] vArr2 = this.values;
        this.keys = new int[i];
        this.values = (Object[]) new Object[i];
        this.maxSize = calcMaxSize(i);
        this.mask = i - 1;
        for (int i2 = 0; i2 < vArr2.length; i2++) {
            V v = vArr2[i2];
            if (v != null) {
                int i3 = iArr[i2];
                int hashIndex = hashIndex(i3);
                while (true) {
                    vArr = this.values;
                    if (vArr[hashIndex] == null) {
                        break;
                    }
                    hashIndex = probeNext(hashIndex);
                }
                this.keys[hashIndex] = i3;
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
    public String keyToString(int i) {
        return Integer.toString(i);
    }
}
