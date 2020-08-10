package com.esotericsoftware.kryo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntMap<V> {
    private static final int EMPTY = 0;
    private static final int PRIME2 = -1105259343;
    private static final int PRIME3 = -1262997959;
    private static final int PRIME4 = -825114047;
    int capacity;
    boolean hasZeroValue;
    private int hashShift;
    private boolean isBigTable;
    int[] keyTable;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;
    V[] valueTable;
    V zeroValue;

    public static class Entries<V> extends MapIterator<V> implements Iterable<Entry<V>>, Iterator<Entry<V>> {
        private Entry<V> entry = new Entry<>();

        public Iterator<Entry<V>> iterator() {
            return this;
        }

        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Entries(IntMap intMap) {
            super(intMap);
        }

        public Entry<V> next() {
            if (this.hasNext) {
                int[] iArr = this.map.keyTable;
                if (this.nextIndex == -1) {
                    Entry<V> entry2 = this.entry;
                    entry2.key = 0;
                    entry2.value = this.map.zeroValue;
                } else {
                    this.entry.key = iArr[this.nextIndex];
                    this.entry.value = this.map.valueTable[this.nextIndex];
                }
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

    public static class Entry<V> {
        public int key;
        public V value;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.key);
            sb.append("=");
            sb.append(this.value);
            return sb.toString();
        }
    }

    public static class Keys extends MapIterator {
        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Keys(IntMap intMap) {
            super(intMap);
        }

        public int next() {
            if (this.hasNext) {
                int i = this.nextIndex == -1 ? 0 : this.map.keyTable[this.nextIndex];
                this.currentIndex = this.nextIndex;
                findNextIndex();
                return i;
            }
            throw new NoSuchElementException();
        }

        public IntArray toArray() {
            IntArray intArray = new IntArray(true, this.map.size);
            while (this.hasNext) {
                intArray.add(next());
            }
            return intArray;
        }
    }

    private static class MapIterator<V> {
        static final int INDEX_ILLEGAL = -2;
        static final int INDEX_ZERO = -1;
        int currentIndex;
        public boolean hasNext;
        final IntMap<V> map;
        int nextIndex;

        public MapIterator(IntMap<V> intMap) {
            this.map = intMap;
            reset();
        }

        public void reset() {
            this.currentIndex = -2;
            this.nextIndex = -1;
            if (this.map.hasZeroValue) {
                this.hasNext = true;
            } else {
                findNextIndex();
            }
        }

        /* access modifiers changed from: 0000 */
        public void findNextIndex() {
            this.hasNext = false;
            int[] iArr = this.map.keyTable;
            int i = this.map.capacity + this.map.stashSize;
            do {
                int i2 = this.nextIndex + 1;
                this.nextIndex = i2;
                if (i2 >= i) {
                    return;
                }
            } while (iArr[this.nextIndex] == 0);
            this.hasNext = true;
        }

        public void remove() {
            if (this.currentIndex != -1 || !this.map.hasZeroValue) {
                int i = this.currentIndex;
                if (i < 0) {
                    throw new IllegalStateException("next must be called before remove.");
                } else if (i >= this.map.capacity) {
                    this.map.removeStashIndex(this.currentIndex);
                    this.nextIndex = this.currentIndex - 1;
                    findNextIndex();
                } else {
                    this.map.keyTable[this.currentIndex] = 0;
                    this.map.valueTable[this.currentIndex] = null;
                }
            } else {
                IntMap<V> intMap = this.map;
                intMap.zeroValue = null;
                intMap.hasZeroValue = false;
            }
            this.currentIndex = -2;
            this.map.size--;
        }
    }

    public static class Values<V> extends MapIterator<V> implements Iterable<V>, Iterator<V> {
        public Iterator<V> iterator() {
            return this;
        }

        public /* bridge */ /* synthetic */ void remove() {
            super.remove();
        }

        public /* bridge */ /* synthetic */ void reset() {
            super.reset();
        }

        public Values(IntMap<V> intMap) {
            super(intMap);
        }

        public boolean hasNext() {
            return this.hasNext;
        }

        public V next() {
            V v;
            if (this.hasNext) {
                if (this.nextIndex == -1) {
                    v = this.map.zeroValue;
                } else {
                    v = this.map.valueTable[this.nextIndex];
                }
                this.currentIndex = this.nextIndex;
                findNextIndex();
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
    }

    public IntMap() {
        this(32, 0.8f);
    }

    public IntMap(int i) {
        this(i, 0.8f);
    }

    public IntMap(int i, float f) {
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
                this.keyTable = new int[(this.capacity + this.stashCapacity)];
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

    public IntMap(IntMap<? extends V> intMap) {
        this(intMap.capacity, intMap.loadFactor);
        this.stashSize = intMap.stashSize;
        int[] iArr = intMap.keyTable;
        System.arraycopy(iArr, 0, this.keyTable, 0, iArr.length);
        V[] vArr = intMap.valueTable;
        System.arraycopy(vArr, 0, this.valueTable, 0, vArr.length);
        this.size = intMap.size;
        this.zeroValue = intMap.zeroValue;
        this.hasZeroValue = intMap.hasZeroValue;
    }

    public V put(int i, V v) {
        int i2;
        int i3 = i;
        V v2 = v;
        if (i3 == 0) {
            V v3 = this.zeroValue;
            this.zeroValue = v2;
            if (!this.hasZeroValue) {
                this.hasZeroValue = true;
                this.size++;
            }
            return v3;
        }
        int[] iArr = this.keyTable;
        int i4 = this.mask;
        boolean z = this.isBigTable;
        int i5 = i4 & i3;
        int i6 = iArr[i5];
        if (i6 == i3) {
            V[] vArr = this.valueTable;
            V v4 = vArr[i5];
            vArr[i5] = v2;
            return v4;
        }
        int hash2 = hash2(i);
        int i7 = iArr[hash2];
        if (i7 == i3) {
            V[] vArr2 = this.valueTable;
            V v5 = vArr2[hash2];
            vArr2[hash2] = v2;
            return v5;
        }
        int hash3 = hash3(i);
        int i8 = iArr[hash3];
        if (i8 == i3) {
            V[] vArr3 = this.valueTable;
            V v6 = vArr3[hash3];
            vArr3[hash3] = v2;
            return v6;
        }
        int i9 = -1;
        if (z) {
            i9 = hash4(i);
            i2 = iArr[i9];
            if (i2 == i3) {
                V[] vArr4 = this.valueTable;
                V v7 = vArr4[i9];
                vArr4[i9] = v2;
                return v7;
            }
        } else {
            i2 = -1;
        }
        int i10 = this.capacity;
        int i11 = this.stashSize + i10;
        while (i10 < i11) {
            if (iArr[i10] == i3) {
                V[] vArr5 = this.valueTable;
                V v8 = vArr5[i10];
                vArr5[i10] = v2;
                return v8;
            }
            i10++;
        }
        if (i6 == 0) {
            iArr[i5] = i3;
            this.valueTable[i5] = v2;
            int i12 = this.size;
            this.size = i12 + 1;
            if (i12 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return null;
        } else if (i7 == 0) {
            iArr[hash2] = i3;
            this.valueTable[hash2] = v2;
            int i13 = this.size;
            this.size = i13 + 1;
            if (i13 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return null;
        } else if (i8 == 0) {
            iArr[hash3] = i3;
            this.valueTable[hash3] = v2;
            int i14 = this.size;
            this.size = i14 + 1;
            if (i14 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return null;
        } else if (!z || i2 != 0) {
            push(i, v, i5, i6, hash2, i7, hash3, i8, i9, i2);
            return null;
        } else {
            iArr[i9] = i3;
            this.valueTable[i9] = v2;
            int i15 = this.size;
            this.size = i15 + 1;
            if (i15 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return null;
        }
    }

    public void putAll(IntMap<V> intMap) {
        Iterator it = intMap.entries().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            put(entry.key, entry.value);
        }
    }

    private void putResize(int i, V v) {
        int i2;
        int i3;
        if (i == 0) {
            this.zeroValue = v;
            this.hasZeroValue = true;
            return;
        }
        int i4 = i & this.mask;
        int[] iArr = this.keyTable;
        int i5 = iArr[i4];
        if (i5 == 0) {
            iArr[i4] = i;
            this.valueTable[i4] = v;
            int i6 = this.size;
            this.size = i6 + 1;
            if (i6 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return;
        }
        int hash2 = hash2(i);
        int[] iArr2 = this.keyTable;
        int i7 = iArr2[hash2];
        if (i7 == 0) {
            iArr2[hash2] = i;
            this.valueTable[hash2] = v;
            int i8 = this.size;
            this.size = i8 + 1;
            if (i8 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return;
        }
        int hash3 = hash3(i);
        int[] iArr3 = this.keyTable;
        int i9 = iArr3[hash3];
        if (i9 == 0) {
            iArr3[hash3] = i;
            this.valueTable[hash3] = v;
            int i10 = this.size;
            this.size = i10 + 1;
            if (i10 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return;
        }
        if (this.isBigTable) {
            int hash4 = hash4(i);
            int[] iArr4 = this.keyTable;
            int i11 = iArr4[hash4];
            if (i11 == 0) {
                iArr4[hash4] = i;
                this.valueTable[hash4] = v;
                int i12 = this.size;
                this.size = i12 + 1;
                if (i12 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i3 = hash4;
            i2 = i11;
        } else {
            i3 = -1;
            i2 = -1;
        }
        push(i, v, i4, i5, hash2, i7, hash3, i9, i3, i2);
    }

    private void push(int i, V v, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        int i10;
        int[] iArr = this.keyTable;
        V[] vArr = this.valueTable;
        int i11 = this.mask;
        boolean z = this.isBigTable;
        int i12 = this.pushIterations;
        int i13 = z ? 4 : 3;
        int i14 = i;
        V v2 = v;
        int i15 = i2;
        int i16 = i3;
        int i17 = i4;
        int i18 = i5;
        int i19 = i6;
        int i20 = i7;
        int i21 = i8;
        int i22 = i9;
        int i23 = 0;
        while (true) {
            int nextInt = ObjectMap.random.nextInt(i13);
            int i24 = i13;
            if (nextInt == 0) {
                V v3 = vArr[i15];
                iArr[i15] = i14;
                vArr[i15] = v2;
                v2 = v3;
                i14 = i16;
            } else if (nextInt == 1) {
                V v4 = vArr[i17];
                iArr[i17] = i14;
                vArr[i17] = v2;
                v2 = v4;
                i14 = i18;
            } else if (nextInt != 2) {
                V v5 = vArr[i21];
                iArr[i21] = i14;
                vArr[i21] = v2;
                i14 = i22;
                v2 = v5;
            } else {
                V v6 = vArr[i19];
                iArr[i19] = i14;
                vArr[i19] = v2;
                v2 = v6;
                i14 = i20;
            }
            i15 = i14 & i11;
            i16 = iArr[i15];
            if (i16 == 0) {
                iArr[i15] = i14;
                vArr[i15] = v2;
                int i25 = this.size;
                this.size = i25 + 1;
                if (i25 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i17 = hash2(i14);
            i18 = iArr[i17];
            if (i18 == 0) {
                iArr[i17] = i14;
                vArr[i17] = v2;
                int i26 = this.size;
                this.size = i26 + 1;
                if (i26 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i19 = hash3(i14);
            i20 = iArr[i19];
            if (i20 == 0) {
                iArr[i19] = i14;
                vArr[i19] = v2;
                int i27 = this.size;
                this.size = i27 + 1;
                if (i27 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            if (z) {
                int hash4 = hash4(i14);
                int i28 = iArr[hash4];
                if (i28 == 0) {
                    iArr[hash4] = i14;
                    vArr[hash4] = v2;
                    int i29 = this.size;
                    this.size = i29 + 1;
                    if (i29 >= this.threshold) {
                        resize(this.capacity << 1);
                    }
                    return;
                }
                int i30 = i28;
                i21 = hash4;
                i10 = i30;
            } else {
                i10 = i22;
            }
            int i31 = i23 + 1;
            if (i31 == i12) {
                putStash(i14, v2);
                return;
            }
            i23 = i31;
            i22 = i10;
            i13 = i24;
        }
    }

    private void putStash(int i, V v) {
        int i2 = this.stashSize;
        if (i2 == this.stashCapacity) {
            resize(this.capacity << 1);
            put(i, v);
            return;
        }
        int i3 = this.capacity + i2;
        this.keyTable[i3] = i;
        this.valueTable[i3] = v;
        this.stashSize = i2 + 1;
        this.size++;
    }

    public V get(int i) {
        if (i != 0) {
            int i2 = this.mask & i;
            if (this.keyTable[i2] != i) {
                i2 = hash2(i);
                if (this.keyTable[i2] != i) {
                    i2 = hash3(i);
                    if (this.keyTable[i2] != i) {
                        if (!this.isBigTable) {
                            return getStash(i, null);
                        }
                        i2 = hash4(i);
                        if (this.keyTable[i2] != i) {
                            return getStash(i, null);
                        }
                    }
                }
            }
            return this.valueTable[i2];
        } else if (!this.hasZeroValue) {
            return null;
        } else {
            return this.zeroValue;
        }
    }

    public V get(int i, V v) {
        if (i != 0) {
            int i2 = this.mask & i;
            if (this.keyTable[i2] != i) {
                i2 = hash2(i);
                if (this.keyTable[i2] != i) {
                    i2 = hash3(i);
                    if (this.keyTable[i2] != i) {
                        if (!this.isBigTable) {
                            return getStash(i, v);
                        }
                        i2 = hash4(i);
                        if (this.keyTable[i2] != i) {
                            return getStash(i, v);
                        }
                    }
                }
            }
            return this.valueTable[i2];
        } else if (!this.hasZeroValue) {
            return v;
        } else {
            return this.zeroValue;
        }
    }

    private V getStash(int i, V v) {
        int[] iArr = this.keyTable;
        int i2 = this.capacity;
        int i3 = this.stashSize + i2;
        while (i2 < i3) {
            if (iArr[i2] == i) {
                return this.valueTable[i2];
            }
            i2++;
        }
        return v;
    }

    public V remove(int i) {
        if (i != 0) {
            int i2 = this.mask & i;
            int[] iArr = this.keyTable;
            if (iArr[i2] == i) {
                iArr[i2] = 0;
                V[] vArr = this.valueTable;
                V v = vArr[i2];
                vArr[i2] = null;
                this.size--;
                return v;
            }
            int hash2 = hash2(i);
            int[] iArr2 = this.keyTable;
            if (iArr2[hash2] == i) {
                iArr2[hash2] = 0;
                V[] vArr2 = this.valueTable;
                V v2 = vArr2[hash2];
                vArr2[hash2] = null;
                this.size--;
                return v2;
            }
            int hash3 = hash3(i);
            int[] iArr3 = this.keyTable;
            if (iArr3[hash3] == i) {
                iArr3[hash3] = 0;
                V[] vArr3 = this.valueTable;
                V v3 = vArr3[hash3];
                vArr3[hash3] = null;
                this.size--;
                return v3;
            }
            if (this.isBigTable) {
                int hash4 = hash4(i);
                int[] iArr4 = this.keyTable;
                if (iArr4[hash4] == i) {
                    iArr4[hash4] = 0;
                    V[] vArr4 = this.valueTable;
                    V v4 = vArr4[hash4];
                    vArr4[hash4] = null;
                    this.size--;
                    return v4;
                }
            }
            return removeStash(i);
        } else if (!this.hasZeroValue) {
            return null;
        } else {
            V v5 = this.zeroValue;
            this.zeroValue = null;
            this.hasZeroValue = false;
            this.size--;
            return v5;
        }
    }

    /* access modifiers changed from: 0000 */
    public V removeStash(int i) {
        int[] iArr = this.keyTable;
        int i2 = this.capacity;
        int i3 = this.stashSize + i2;
        while (i2 < i3) {
            if (iArr[i2] == i) {
                V v = this.valueTable[i2];
                removeStashIndex(i2);
                this.size--;
                return v;
            }
            i2++;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void removeStashIndex(int i) {
        this.stashSize--;
        int i2 = this.capacity + this.stashSize;
        if (i < i2) {
            int[] iArr = this.keyTable;
            iArr[i] = iArr[i2];
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
        this.zeroValue = null;
        this.hasZeroValue = false;
        this.size = 0;
        resize(i);
    }

    public void clear() {
        int[] iArr = this.keyTable;
        V[] vArr = this.valueTable;
        int i = this.capacity + this.stashSize;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                iArr[i2] = 0;
                vArr[i2] = null;
                i = i2;
            } else {
                this.size = 0;
                this.stashSize = 0;
                this.zeroValue = null;
                this.hasZeroValue = false;
                return;
            }
        }
    }

    public boolean containsValue(Object obj, boolean z) {
        V[] vArr = this.valueTable;
        if (obj == null) {
            if (!this.hasZeroValue || this.zeroValue != null) {
                int[] iArr = this.keyTable;
                int i = this.capacity + this.stashSize;
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    } else if (iArr[i2] != 0 && vArr[i2] == null) {
                        return true;
                    } else {
                        i = i2;
                    }
                }
            } else {
                return true;
            }
        } else if (z) {
            if (obj != this.zeroValue) {
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
            } else {
                return true;
            }
        } else if (!this.hasZeroValue || !obj.equals(this.zeroValue)) {
            int i5 = this.capacity + this.stashSize;
            while (true) {
                int i6 = i5 - 1;
                if (i5 <= 0) {
                    break;
                } else if (obj.equals(vArr[i6])) {
                    return true;
                } else {
                    i5 = i6;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public boolean containsKey(int i) {
        if (i == 0) {
            return this.hasZeroValue;
        }
        if (this.keyTable[this.mask & i] != i) {
            if (this.keyTable[hash2(i)] != i) {
                if (this.keyTable[hash3(i)] != i) {
                    if (!this.isBigTable) {
                        return containsKeyStash(i);
                    }
                    if (this.keyTable[hash4(i)] != i) {
                        return containsKeyStash(i);
                    }
                }
            }
        }
        return true;
    }

    private boolean containsKeyStash(int i) {
        int[] iArr = this.keyTable;
        int i2 = this.capacity;
        int i3 = this.stashSize + i2;
        while (i2 < i3) {
            if (iArr[i2] == i) {
                return true;
            }
            i2++;
        }
        return false;
    }

    public int findKey(Object obj, boolean z, int i) {
        V[] vArr = this.valueTable;
        if (obj == null) {
            if (!this.hasZeroValue || this.zeroValue != null) {
                int[] iArr = this.keyTable;
                int i2 = this.capacity + this.stashSize;
                while (true) {
                    int i3 = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    } else if (iArr[i3] != 0 && vArr[i3] == null) {
                        return iArr[i3];
                    } else {
                        i2 = i3;
                    }
                }
            } else {
                return 0;
            }
        } else if (z) {
            if (obj != this.zeroValue) {
                int i4 = this.capacity + this.stashSize;
                while (true) {
                    int i5 = i4 - 1;
                    if (i4 <= 0) {
                        break;
                    } else if (vArr[i5] == obj) {
                        return this.keyTable[i5];
                    } else {
                        i4 = i5;
                    }
                }
            } else {
                return 0;
            }
        } else if (!this.hasZeroValue || !obj.equals(this.zeroValue)) {
            int i6 = this.capacity + this.stashSize;
            while (true) {
                int i7 = i6 - 1;
                if (i6 <= 0) {
                    break;
                } else if (obj.equals(vArr[i7])) {
                    return this.keyTable[i7];
                } else {
                    i6 = i7;
                }
            }
        } else {
            return 0;
        }
        return i;
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
        int[] iArr = this.keyTable;
        V[] vArr = this.valueTable;
        int i3 = this.stashCapacity;
        this.keyTable = new int[(i + i3)];
        this.valueTable = (Object[]) new Object[(i + i3)];
        int i4 = this.size;
        this.size = this.hasZeroValue ? 1 : 0;
        this.stashSize = 0;
        if (i4 > 0) {
            for (int i5 = 0; i5 < i2; i5++) {
                int i6 = iArr[i5];
                if (i6 != 0) {
                    putResize(i6, vArr[i5]);
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

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
            r7 = this;
            int r0 = r7.size
            if (r0 != 0) goto L_0x0007
            java.lang.String r0 = "[]"
            return r0
        L_0x0007:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 32
            r0.<init>(r1)
            r1 = 91
            r0.append(r1)
            int[] r1 = r7.keyTable
            V[] r2 = r7.valueTable
            int r3 = r1.length
            boolean r4 = r7.hasZeroValue
            r5 = 61
            if (r4 == 0) goto L_0x0029
            java.lang.String r4 = "0="
            r0.append(r4)
            V r4 = r7.zeroValue
            r0.append(r4)
            goto L_0x003f
        L_0x0029:
            int r4 = r3 + -1
            if (r3 <= 0) goto L_0x003e
            r3 = r1[r4]
            if (r3 != 0) goto L_0x0033
            r3 = r4
            goto L_0x0029
        L_0x0033:
            r0.append(r3)
            r0.append(r5)
            r3 = r2[r4]
            r0.append(r3)
        L_0x003e:
            r3 = r4
        L_0x003f:
            int r4 = r3 + -1
            if (r3 <= 0) goto L_0x0059
            r3 = r1[r4]
            if (r3 != 0) goto L_0x0048
            goto L_0x003e
        L_0x0048:
            java.lang.String r6 = ", "
            r0.append(r6)
            r0.append(r3)
            r0.append(r5)
            r3 = r2[r4]
            r0.append(r3)
            goto L_0x003e
        L_0x0059:
            r1 = 93
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.util.IntMap.toString():java.lang.String");
    }

    public Entries<V> entries() {
        return new Entries<>(this);
    }

    public Values<V> values() {
        return new Values<>(this);
    }

    public Keys keys() {
        return new Keys(this);
    }
}
