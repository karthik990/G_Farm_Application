package com.esotericsoftware.kryo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import org.objectweb.asm.signature.SignatureVisitor;

public class ObjectMap<K, V> {
    private static final int PRIME2 = -1105259343;
    private static final int PRIME3 = -1262997959;
    private static final int PRIME4 = -825114047;
    static Random random = new Random();
    int capacity;
    private int hashShift;
    private boolean isBigTable;
    K[] keyTable;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;
    V[] valueTable;

    public static class Entries<K, V> extends MapIterator<K, V> implements Iterable<Entry<K, V>>, Iterator<Entry<K, V>> {
        Entry<K, V> entry = new Entry<>();

        public Iterator<Entry<K, V>> iterator() {
            return this;
        }

        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(ObjectMap<K, V> objectMap) {
            super(objectMap);
        }

        public Entry<K, V> next() {
            if (this.hasNext) {
                K[] kArr = this.map.keyTable;
                this.entry.key = kArr[this.nextIndex];
                this.entry.value = this.map.valueTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                advance();
                return this.entry;
            }
            throw new NoSuchElementException();
        }

        public boolean hasNext() {
            return this.hasNext;
        }
    }

    public static class Entry<K, V> {
        public K key;
        public V value;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.key);
            sb.append("=");
            sb.append(this.value);
            return sb.toString();
        }
    }

    public static class Keys<K> extends MapIterator<K, Object> implements Iterable<K>, Iterator<K> {
        public Iterator<K> iterator() {
            return this;
        }

        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(ObjectMap<K, ?> objectMap) {
            super(objectMap);
        }

        public boolean hasNext() {
            return this.hasNext;
        }

        public K next() {
            if (this.hasNext) {
                K k = this.map.keyTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                advance();
                return k;
            }
            throw new NoSuchElementException();
        }

        public ArrayList<K> toArray() {
            ArrayList<K> arrayList = new ArrayList<>(this.map.size);
            while (this.hasNext) {
                arrayList.add(next());
            }
            return arrayList;
        }
    }

    private static class MapIterator<K, V> {
        int currentIndex;
        public boolean hasNext;
        final ObjectMap<K, V> map;
        int nextIndex;

        public MapIterator(ObjectMap<K, V> objectMap) {
            this.map = objectMap;
            reset();
        }

        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            advance();
        }

        /* access modifiers changed from: 0000 */
        public void advance() {
            this.hasNext = false;
            K[] kArr = this.map.keyTable;
            int i = this.map.capacity + this.map.stashSize;
            do {
                int i2 = this.nextIndex + 1;
                this.nextIndex = i2;
                if (i2 >= i) {
                    return;
                }
            } while (kArr[this.nextIndex] == null);
            this.hasNext = true;
        }

        public void remove() {
            int i = this.currentIndex;
            if (i >= 0) {
                if (i >= this.map.capacity) {
                    this.map.removeStashIndex(this.currentIndex);
                    this.nextIndex = this.currentIndex - 1;
                    advance();
                } else {
                    this.map.keyTable[this.currentIndex] = null;
                    this.map.valueTable[this.currentIndex] = null;
                }
                this.currentIndex = -1;
                this.map.size--;
                return;
            }
            throw new IllegalStateException("next must be called before remove.");
        }
    }

    public static class Values<V> extends MapIterator<Object, V> implements Iterable<V>, Iterator<V> {
        public Iterator<V> iterator() {
            return this;
        }

        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(ObjectMap<?, V> objectMap) {
            super(objectMap);
        }

        public boolean hasNext() {
            return this.hasNext;
        }

        public V next() {
            if (this.hasNext) {
                V v = this.map.valueTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                advance();
                return v;
            }
            throw new NoSuchElementException();
        }

        public ArrayList<V> toArray() {
            ArrayList<V> arrayList = new ArrayList<>(this.map.size);
            while (this.hasNext) {
                arrayList.add(next());
            }
            return arrayList;
        }

        public void toArray(ArrayList<V> arrayList) {
            while (this.hasNext) {
                arrayList.add(next());
            }
        }
    }

    public static int nextPowerOfTwo(int i) {
        if (i == 0) {
            return 1;
        }
        int i2 = i - 1;
        int i3 = i2 | (i2 >> 1);
        int i4 = i3 | (i3 >> 2);
        int i5 = i4 | (i4 >> 4);
        int i6 = i5 | (i5 >> 8);
        return (i6 | (i6 >> 16)) + 1;
    }

    public ObjectMap() {
        this(32, 0.8f);
    }

    public ObjectMap(int i) {
        this(i, 0.8f);
    }

    public ObjectMap(int i, float f) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("initialCapacity must be >= 0: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i <= 1073741824) {
            this.capacity = nextPowerOfTwo(i);
            if (f > 0.0f) {
                this.loadFactor = f;
                this.isBigTable = (this.capacity >>> 16) != 0;
                int i2 = this.capacity;
                this.threshold = (int) (((float) i2) * f);
                this.mask = i2 - 1;
                this.hashShift = 31 - Integer.numberOfTrailingZeros(i2);
                this.stashCapacity = Math.max(3, ((int) Math.ceil(Math.log((double) this.capacity))) * 2);
                this.pushIterations = Math.max(Math.min(this.capacity, 8), ((int) Math.sqrt((double) this.capacity)) / 8);
                this.keyTable = (Object[]) new Object[(this.capacity + this.stashCapacity)];
                this.valueTable = (Object[]) new Object[this.keyTable.length];
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("loadFactor must be > 0: ");
            sb2.append(f);
            throw new IllegalArgumentException(sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("initialCapacity is too large: ");
            sb3.append(i);
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    public ObjectMap(ObjectMap<? extends K, ? extends V> objectMap) {
        this(objectMap.capacity, objectMap.loadFactor);
        this.stashSize = objectMap.stashSize;
        K[] kArr = objectMap.keyTable;
        System.arraycopy(kArr, 0, this.keyTable, 0, kArr.length);
        V[] vArr = objectMap.valueTable;
        System.arraycopy(vArr, 0, this.valueTable, 0, vArr.length);
        this.size = objectMap.size;
    }

    public V put(K k, V v) {
        if (k != null) {
            return put_internal(k, v);
        }
        throw new IllegalArgumentException("key cannot be null.");
    }

    private V put_internal(K k, V v) {
        int i;
        K k2;
        K k3 = k;
        K[] kArr = this.keyTable;
        int i2 = this.mask;
        boolean z = this.isBigTable;
        int hashCode = k.hashCode();
        int i3 = hashCode & i2;
        K k4 = kArr[i3];
        if (k3.equals(k4)) {
            V[] vArr = this.valueTable;
            V v2 = vArr[i3];
            vArr[i3] = v;
            return v2;
        }
        int hash2 = hash2(hashCode);
        K k5 = kArr[hash2];
        if (k3.equals(k5)) {
            V[] vArr2 = this.valueTable;
            V v3 = vArr2[hash2];
            vArr2[hash2] = v;
            return v3;
        }
        int hash3 = hash3(hashCode);
        K k6 = kArr[hash3];
        if (k3.equals(k6)) {
            V[] vArr3 = this.valueTable;
            V v4 = vArr3[hash3];
            vArr3[hash3] = v;
            return v4;
        }
        if (z) {
            int hash4 = hash4(hashCode);
            K k7 = kArr[hash4];
            if (k3.equals(k7)) {
                V[] vArr4 = this.valueTable;
                V v5 = vArr4[hash4];
                vArr4[hash4] = v;
                return v5;
            }
            i = hash4;
            k2 = k7;
        } else {
            k2 = null;
            i = -1;
        }
        int i4 = this.capacity;
        int i5 = this.stashSize + i4;
        while (i4 < i5) {
            if (k3.equals(kArr[i4])) {
                V[] vArr5 = this.valueTable;
                V v6 = vArr5[i4];
                vArr5[i4] = v;
                return v6;
            }
            i4++;
        }
        if (k4 == null) {
            kArr[i3] = k3;
            this.valueTable[i3] = v;
            int i6 = this.size;
            this.size = i6 + 1;
            if (i6 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return null;
        } else if (k5 == null) {
            kArr[hash2] = k3;
            this.valueTable[hash2] = v;
            int i7 = this.size;
            this.size = i7 + 1;
            if (i7 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return null;
        } else if (k6 == null) {
            kArr[hash3] = k3;
            this.valueTable[hash3] = v;
            int i8 = this.size;
            this.size = i8 + 1;
            if (i8 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return null;
        } else if (!z || k2 != null) {
            push(k, v, i3, k4, hash2, k5, hash3, k6, i, k2);
            return null;
        } else {
            kArr[i] = k3;
            this.valueTable[i] = v;
            int i9 = this.size;
            this.size = i9 + 1;
            if (i9 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return null;
        }
    }

    public void putAll(ObjectMap<K, V> objectMap) {
        ensureCapacity(objectMap.size);
        Iterator it = objectMap.entries().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            put(entry.key, entry.value);
        }
    }

    private void putResize(K k, V v) {
        Object obj;
        int i;
        int hashCode = k.hashCode();
        int i2 = hashCode & this.mask;
        K[] kArr = this.keyTable;
        K k2 = kArr[i2];
        if (k2 == null) {
            kArr[i2] = k;
            this.valueTable[i2] = v;
            int i3 = this.size;
            this.size = i3 + 1;
            if (i3 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return;
        }
        int hash2 = hash2(hashCode);
        K[] kArr2 = this.keyTable;
        K k3 = kArr2[hash2];
        if (k3 == null) {
            kArr2[hash2] = k;
            this.valueTable[hash2] = v;
            int i4 = this.size;
            this.size = i4 + 1;
            if (i4 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return;
        }
        int hash3 = hash3(hashCode);
        K[] kArr3 = this.keyTable;
        K k4 = kArr3[hash3];
        if (k4 == null) {
            kArr3[hash3] = k;
            this.valueTable[hash3] = v;
            int i5 = this.size;
            this.size = i5 + 1;
            if (i5 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return;
        }
        if (this.isBigTable) {
            int hash4 = hash4(hashCode);
            K[] kArr4 = this.keyTable;
            K k5 = kArr4[hash4];
            if (k5 == null) {
                kArr4[hash4] = k;
                this.valueTable[hash4] = v;
                int i6 = this.size;
                this.size = i6 + 1;
                if (i6 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i = hash4;
            obj = k5;
        } else {
            obj = null;
            i = -1;
        }
        push(k, v, i2, k2, hash2, k3, hash3, k4, i, obj);
    }

    private void push(K k, V v, int i, K k2, int i2, K k3, int i3, K k4, int i4, K k5) {
        K k6;
        int i5;
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        int i6 = this.mask;
        boolean z = this.isBigTable;
        int i7 = this.pushIterations;
        int i8 = z ? 4 : 3;
        K k7 = k;
        V v2 = v;
        int i9 = i;
        K k8 = k2;
        int i10 = i2;
        K k9 = k3;
        int i11 = i3;
        K k10 = k4;
        int i12 = i4;
        K k11 = k5;
        int i13 = 0;
        while (true) {
            int nextInt = random.nextInt(i8);
            int i14 = i8;
            if (nextInt == 0) {
                V v3 = vArr[i9];
                kArr[i9] = k7;
                vArr[i9] = v2;
                v2 = v3;
                k7 = k8;
            } else if (nextInt == 1) {
                V v4 = vArr[i10];
                kArr[i10] = k7;
                vArr[i10] = v2;
                v2 = v4;
                k7 = k9;
            } else if (nextInt != 2) {
                V v5 = vArr[i12];
                kArr[i12] = k7;
                vArr[i12] = v2;
                k7 = k11;
                v2 = v5;
            } else {
                V v6 = vArr[i11];
                kArr[i11] = k7;
                vArr[i11] = v2;
                v2 = v6;
                k7 = k10;
            }
            int hashCode = k7.hashCode();
            i9 = hashCode & i6;
            k8 = kArr[i9];
            if (k8 == null) {
                kArr[i9] = k7;
                vArr[i9] = v2;
                int i15 = this.size;
                this.size = i15 + 1;
                if (i15 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i10 = hash2(hashCode);
            k9 = kArr[i10];
            if (k9 == null) {
                kArr[i10] = k7;
                vArr[i10] = v2;
                int i16 = this.size;
                this.size = i16 + 1;
                if (i16 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i11 = hash3(hashCode);
            k10 = kArr[i11];
            if (k10 == null) {
                kArr[i11] = k7;
                vArr[i11] = v2;
                int i17 = this.size;
                this.size = i17 + 1;
                if (i17 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            if (z) {
                i5 = hash4(hashCode);
                k6 = kArr[i5];
                if (k6 == null) {
                    kArr[i5] = k7;
                    vArr[i5] = v2;
                    int i18 = this.size;
                    this.size = i18 + 1;
                    if (i18 >= this.threshold) {
                        resize(this.capacity << 1);
                    }
                    return;
                }
            } else {
                i5 = i12;
                k6 = k11;
            }
            int i19 = i13 + 1;
            if (i19 == i7) {
                putStash(k7, v2);
                return;
            }
            i13 = i19;
            k11 = k6;
            i8 = i14;
            i12 = i5;
        }
    }

    private void putStash(K k, V v) {
        int i = this.stashSize;
        if (i == this.stashCapacity) {
            resize(this.capacity << 1);
            put_internal(k, v);
            return;
        }
        int i2 = this.capacity + i;
        this.keyTable[i2] = k;
        this.valueTable[i2] = v;
        this.stashSize = i + 1;
        this.size++;
    }

    public V get(K k) {
        int hashCode = k.hashCode();
        int i = this.mask & hashCode;
        if (!k.equals(this.keyTable[i])) {
            i = hash2(hashCode);
            if (!k.equals(this.keyTable[i])) {
                i = hash3(hashCode);
                if (!k.equals(this.keyTable[i])) {
                    if (!this.isBigTable) {
                        return getStash(k);
                    }
                    i = hash4(hashCode);
                    if (!k.equals(this.keyTable[i])) {
                        return getStash(k);
                    }
                }
            }
        }
        return this.valueTable[i];
    }

    private V getStash(K k) {
        K[] kArr = this.keyTable;
        int i = this.capacity;
        int i2 = this.stashSize + i;
        while (i < i2) {
            if (k.equals(kArr[i])) {
                return this.valueTable[i];
            }
            i++;
        }
        return null;
    }

    public V get(K k, V v) {
        int hashCode = k.hashCode();
        int i = this.mask & hashCode;
        if (!k.equals(this.keyTable[i])) {
            i = hash2(hashCode);
            if (!k.equals(this.keyTable[i])) {
                i = hash3(hashCode);
                if (!k.equals(this.keyTable[i])) {
                    if (!this.isBigTable) {
                        return getStash(k, v);
                    }
                    i = hash4(hashCode);
                    if (!k.equals(this.keyTable[i])) {
                        return getStash(k, v);
                    }
                }
            }
        }
        return this.valueTable[i];
    }

    private V getStash(K k, V v) {
        K[] kArr = this.keyTable;
        int i = this.capacity;
        int i2 = this.stashSize + i;
        while (i < i2) {
            if (k.equals(kArr[i])) {
                return this.valueTable[i];
            }
            i++;
        }
        return v;
    }

    public V remove(K k) {
        int hashCode = k.hashCode();
        int i = this.mask & hashCode;
        if (k.equals(this.keyTable[i])) {
            this.keyTable[i] = null;
            V[] vArr = this.valueTable;
            V v = vArr[i];
            vArr[i] = null;
            this.size--;
            return v;
        }
        int hash2 = hash2(hashCode);
        if (k.equals(this.keyTable[hash2])) {
            this.keyTable[hash2] = null;
            V[] vArr2 = this.valueTable;
            V v2 = vArr2[hash2];
            vArr2[hash2] = null;
            this.size--;
            return v2;
        }
        int hash3 = hash3(hashCode);
        if (k.equals(this.keyTable[hash3])) {
            this.keyTable[hash3] = null;
            V[] vArr3 = this.valueTable;
            V v3 = vArr3[hash3];
            vArr3[hash3] = null;
            this.size--;
            return v3;
        }
        if (this.isBigTable) {
            int hash4 = hash4(hashCode);
            if (k.equals(this.keyTable[hash4])) {
                this.keyTable[hash4] = null;
                V[] vArr4 = this.valueTable;
                V v4 = vArr4[hash4];
                vArr4[hash4] = null;
                this.size--;
                return v4;
            }
        }
        return removeStash(k);
    }

    /* access modifiers changed from: 0000 */
    public V removeStash(K k) {
        K[] kArr = this.keyTable;
        int i = this.capacity;
        int i2 = this.stashSize + i;
        while (i < i2) {
            if (k.equals(kArr[i])) {
                V v = this.valueTable[i];
                removeStashIndex(i);
                this.size--;
                return v;
            }
            i++;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void removeStashIndex(int i) {
        this.stashSize--;
        int i2 = this.capacity + this.stashSize;
        if (i < i2) {
            K[] kArr = this.keyTable;
            kArr[i] = kArr[i2];
            V[] vArr = this.valueTable;
            vArr[i] = vArr[i2];
            vArr[i2] = null;
            return;
        }
        this.valueTable[i] = null;
    }

    public void shrink(int i) {
        if (i >= 0) {
            int i2 = this.size;
            if (i2 > i) {
                i = i2;
            }
            if (this.capacity > i) {
                resize(nextPowerOfTwo(i));
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("maximumCapacity must be >= 0: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public void clear(int i) {
        if (this.capacity <= i) {
            clear();
            return;
        }
        this.size = 0;
        resize(i);
    }

    public void clear() {
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        int i = this.capacity + this.stashSize;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                kArr[i2] = null;
                vArr[i2] = null;
                i = i2;
            } else {
                this.size = 0;
                this.stashSize = 0;
                return;
            }
        }
    }

    public boolean containsValue(Object obj, boolean z) {
        V[] vArr = this.valueTable;
        if (obj != null) {
            if (!z) {
                int i = this.capacity + this.stashSize;
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    } else if (obj.equals(vArr[i2])) {
                        return true;
                    } else {
                        i = i2;
                    }
                }
            } else {
                int i3 = this.capacity + this.stashSize;
                while (true) {
                    int i4 = i3 - 1;
                    if (i3 <= 0) {
                        break;
                    } else if (vArr[i4] == obj) {
                        return true;
                    } else {
                        i3 = i4;
                    }
                }
            }
        } else {
            K[] kArr = this.keyTable;
            int i5 = this.capacity + this.stashSize;
            while (true) {
                int i6 = i5 - 1;
                if (i5 <= 0) {
                    break;
                } else if (kArr[i6] != null && vArr[i6] == null) {
                    return true;
                } else {
                    i5 = i6;
                }
            }
        }
        return false;
    }

    public boolean containsKey(K k) {
        int hashCode = k.hashCode();
        if (!k.equals(this.keyTable[this.mask & hashCode])) {
            if (!k.equals(this.keyTable[hash2(hashCode)])) {
                if (!k.equals(this.keyTable[hash3(hashCode)])) {
                    if (!this.isBigTable) {
                        return containsKeyStash(k);
                    }
                    if (!k.equals(this.keyTable[hash4(hashCode)])) {
                        return containsKeyStash(k);
                    }
                }
            }
        }
        return true;
    }

    private boolean containsKeyStash(K k) {
        K[] kArr = this.keyTable;
        int i = this.capacity;
        int i2 = this.stashSize + i;
        while (i < i2) {
            if (k.equals(kArr[i])) {
                return true;
            }
            i++;
        }
        return false;
    }

    public K findKey(Object obj, boolean z) {
        V[] vArr = this.valueTable;
        if (obj != null) {
            if (!z) {
                int i = this.capacity + this.stashSize;
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    } else if (obj.equals(vArr[i2])) {
                        return this.keyTable[i2];
                    } else {
                        i = i2;
                    }
                }
            } else {
                int i3 = this.capacity + this.stashSize;
                while (true) {
                    int i4 = i3 - 1;
                    if (i3 <= 0) {
                        break;
                    } else if (vArr[i4] == obj) {
                        return this.keyTable[i4];
                    } else {
                        i3 = i4;
                    }
                }
            }
        } else {
            K[] kArr = this.keyTable;
            int i5 = this.capacity + this.stashSize;
            while (true) {
                int i6 = i5 - 1;
                if (i5 <= 0) {
                    break;
                } else if (kArr[i6] != null && vArr[i6] == null) {
                    return kArr[i6];
                } else {
                    i5 = i6;
                }
            }
        }
        return null;
    }

    public void ensureCapacity(int i) {
        int i2 = this.size + i;
        if (i2 >= this.threshold) {
            resize(nextPowerOfTwo((int) (((float) i2) / this.loadFactor)));
        }
    }

    private void resize(int i) {
        int i2 = this.capacity + this.stashSize;
        this.capacity = i;
        this.threshold = (int) (((float) i) * this.loadFactor);
        this.mask = i - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(i);
        double d = (double) i;
        this.stashCapacity = Math.max(3, ((int) Math.ceil(Math.log(d))) * 2);
        this.pushIterations = Math.max(Math.min(i, 8), ((int) Math.sqrt(d)) / 8);
        this.isBigTable = (this.capacity >>> 16) != 0;
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        int i3 = this.stashCapacity;
        this.keyTable = (Object[]) new Object[(i + i3)];
        this.valueTable = (Object[]) new Object[(i + i3)];
        int i4 = this.size;
        this.size = 0;
        this.stashSize = 0;
        if (i4 > 0) {
            for (int i5 = 0; i5 < i2; i5++) {
                K k = kArr[i5];
                if (k != null) {
                    putResize(k, vArr[i5]);
                }
            }
        }
    }

    private int hash2(int i) {
        int i2 = i * PRIME2;
        return (i2 ^ (i2 >>> this.hashShift)) & this.mask;
    }

    private int hash3(int i) {
        int i2 = i * PRIME3;
        return (i2 ^ (i2 >>> this.hashShift)) & this.mask;
    }

    private int hash4(int i) {
        int i2 = i * PRIME4;
        return (i2 ^ (i2 >>> this.hashShift)) & this.mask;
    }

    public String toString() {
        int i;
        if (this.size == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(32);
        sb.append('{');
        K[] kArr = this.keyTable;
        V[] vArr = this.valueTable;
        int length = kArr.length;
        while (true) {
            i = length - 1;
            if (length <= 0) {
                break;
            }
            K k = kArr[i];
            if (k != null) {
                sb.append(k);
                sb.append(SignatureVisitor.INSTANCEOF);
                sb.append(vArr[i]);
                break;
            }
            length = i;
        }
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                K k2 = kArr[i2];
                if (k2 != null) {
                    sb.append(", ");
                    sb.append(k2);
                    sb.append(SignatureVisitor.INSTANCEOF);
                    sb.append(vArr[i2]);
                }
                i = i2;
            } else {
                sb.append('}');
                return sb.toString();
            }
        }
    }

    public Entries<K, V> entries() {
        return new Entries<>(this);
    }

    public Values<V> values() {
        return new Values<>(this);
    }

    public Keys<K> keys() {
        return new Keys<>(this);
    }
}
