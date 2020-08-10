package com.esotericsoftware.kryo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.objectweb.asm.signature.SignatureVisitor;

public class IdentityMap<K, V> {
    private static final int PRIME2 = -1105259343;
    private static final int PRIME3 = -1262997959;
    private static final int PRIME4 = -825114047;
    int capacity;
    private Entries entries;
    private int hashShift;
    private boolean isBigTable;
    K[] keyTable;
    private Keys keys;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;
    V[] valueTable;
    private Values values;

    public static class Entries<K, V> extends MapIterator<K, V> implements Iterable<Entry<K, V>>, Iterator<Entry<K, V>> {
        private Entry<K, V> entry = new Entry<>();

        public Iterator<Entry<K, V>> iterator() {
            return this;
        }

        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(IdentityMap<K, V> identityMap) {
            super(identityMap);
        }

        public Entry<K, V> next() {
            if (this.hasNext) {
                K[] kArr = this.map.keyTable;
                this.entry.key = kArr[this.nextIndex];
                this.entry.value = this.map.valueTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                findNextIndex();
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

        public Keys(IdentityMap<K, ?> identityMap) {
            super(identityMap);
        }

        public boolean hasNext() {
            return this.hasNext;
        }

        public K next() {
            K k = this.map.keyTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return k;
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
        final IdentityMap<K, V> map;
        int nextIndex;

        public MapIterator(IdentityMap<K, V> identityMap) {
            this.map = identityMap;
            reset();
        }

        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            findNextIndex();
        }

        /* access modifiers changed from: 0000 */
        public void findNextIndex() {
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
                    findNextIndex();
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

        public Values(IdentityMap<?, V> identityMap) {
            super(identityMap);
        }

        public boolean hasNext() {
            return this.hasNext;
        }

        public V next() {
            V v = this.map.valueTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            findNextIndex();
            return v;
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

    public IdentityMap() {
        this(32, 0.8f);
    }

    public IdentityMap(int i) {
        this(i, 0.8f);
    }

    public IdentityMap(int i, float f) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("initialCapacity must be >= 0: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i <= 1073741824) {
            this.capacity = ObjectMap.nextPowerOfTwo(i);
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

    public V put(K k, V v) {
        int i;
        K k2;
        K k3 = k;
        if (k3 != null) {
            K[] kArr = this.keyTable;
            int i2 = this.mask;
            boolean z = this.isBigTable;
            int identityHashCode = System.identityHashCode(k);
            int i3 = identityHashCode & i2;
            K k4 = kArr[i3];
            if (k4 == k3) {
                V[] vArr = this.valueTable;
                V v2 = vArr[i3];
                vArr[i3] = v;
                return v2;
            }
            int hash2 = hash2(identityHashCode);
            K k5 = kArr[hash2];
            if (k5 == k3) {
                V[] vArr2 = this.valueTable;
                V v3 = vArr2[hash2];
                vArr2[hash2] = v;
                return v3;
            }
            int hash3 = hash3(identityHashCode);
            K k6 = kArr[hash3];
            if (k6 == k3) {
                V[] vArr3 = this.valueTable;
                V v4 = vArr3[hash3];
                vArr3[hash3] = v;
                return v4;
            }
            if (z) {
                int hash4 = hash4(identityHashCode);
                K k7 = kArr[hash4];
                if (k7 == k3) {
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
                if (kArr[i4] == k3) {
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
        } else {
            throw new IllegalArgumentException("key cannot be null.");
        }
    }

    private void putResize(K k, V v) {
        Object obj;
        int i;
        int identityHashCode = System.identityHashCode(k);
        int i2 = identityHashCode & this.mask;
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
        int hash2 = hash2(identityHashCode);
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
        int hash3 = hash3(identityHashCode);
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
            int hash4 = hash4(identityHashCode);
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
            int nextInt = ObjectMap.random.nextInt(i8);
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
            int identityHashCode = System.identityHashCode(k7);
            i9 = identityHashCode & i6;
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
            i10 = hash2(identityHashCode);
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
            i11 = hash3(identityHashCode);
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
                i5 = hash4(identityHashCode);
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
            put(k, v);
            return;
        }
        int i2 = this.capacity + i;
        this.keyTable[i2] = k;
        this.valueTable[i2] = v;
        this.stashSize = i + 1;
        this.size++;
    }

    public V get(K k) {
        int identityHashCode = System.identityHashCode(k);
        int i = this.mask & identityHashCode;
        if (k != this.keyTable[i]) {
            i = hash2(identityHashCode);
            if (k != this.keyTable[i]) {
                i = hash3(identityHashCode);
                if (k != this.keyTable[i]) {
                    if (!this.isBigTable) {
                        return getStash(k, null);
                    }
                    i = hash4(identityHashCode);
                    if (k != this.keyTable[i]) {
                        return getStash(k, null);
                    }
                }
            }
        }
        return this.valueTable[i];
    }

    public V get(K k, V v) {
        int identityHashCode = System.identityHashCode(k);
        int i = this.mask & identityHashCode;
        if (k != this.keyTable[i]) {
            i = hash2(identityHashCode);
            if (k != this.keyTable[i]) {
                i = hash3(identityHashCode);
                if (k != this.keyTable[i]) {
                    if (!this.isBigTable) {
                        return getStash(k, v);
                    }
                    i = hash4(identityHashCode);
                    if (k != this.keyTable[i]) {
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
            if (kArr[i] == k) {
                return this.valueTable[i];
            }
            i++;
        }
        return v;
    }

    public V remove(K k) {
        int identityHashCode = System.identityHashCode(k);
        int i = this.mask & identityHashCode;
        K[] kArr = this.keyTable;
        if (kArr[i] == k) {
            kArr[i] = null;
            V[] vArr = this.valueTable;
            V v = vArr[i];
            vArr[i] = null;
            this.size--;
            return v;
        }
        int hash2 = hash2(identityHashCode);
        K[] kArr2 = this.keyTable;
        if (kArr2[hash2] == k) {
            kArr2[hash2] = null;
            V[] vArr2 = this.valueTable;
            V v2 = vArr2[hash2];
            vArr2[hash2] = null;
            this.size--;
            return v2;
        }
        int hash3 = hash3(identityHashCode);
        K[] kArr3 = this.keyTable;
        if (kArr3[hash3] == k) {
            kArr3[hash3] = null;
            V[] vArr3 = this.valueTable;
            V v3 = vArr3[hash3];
            vArr3[hash3] = null;
            this.size--;
            return v3;
        }
        if (this.isBigTable) {
            int hash4 = hash4(identityHashCode);
            K[] kArr4 = this.keyTable;
            if (kArr4[hash4] == k) {
                kArr4[hash4] = null;
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
            if (kArr[i] == k) {
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
                resize(ObjectMap.nextPowerOfTwo(i));
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
        int identityHashCode = System.identityHashCode(k);
        if (k != this.keyTable[this.mask & identityHashCode]) {
            if (k != this.keyTable[hash2(identityHashCode)]) {
                if (k != this.keyTable[hash3(identityHashCode)]) {
                    if (!this.isBigTable) {
                        return containsKeyStash(k);
                    }
                    if (k != this.keyTable[hash4(identityHashCode)]) {
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
            if (kArr[i] == k) {
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
            resize(ObjectMap.nextPowerOfTwo((int) (((float) i2) / this.loadFactor)));
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
            return "[]";
        }
        StringBuilder sb = new StringBuilder(32);
        sb.append('[');
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
                sb.append(']');
                return sb.toString();
            }
        }
    }

    public Entries<K, V> entries() {
        Entries entries2 = this.entries;
        if (entries2 == null) {
            this.entries = new Entries(this);
        } else {
            entries2.reset();
        }
        return this.entries;
    }

    public Values<V> values() {
        Values values2 = this.values;
        if (values2 == null) {
            this.values = new Values(this);
        } else {
            values2.reset();
        }
        return this.values;
    }

    public Keys<K> keys() {
        Keys keys2 = this.keys;
        if (keys2 == null) {
            this.keys = new Keys(this);
        } else {
            keys2.reset();
        }
        return this.keys;
    }
}
