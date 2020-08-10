package com.esotericsoftware.kryo.util;

import org.objectweb.asm.signature.SignatureVisitor;

public class IdentityObjectIntMap<K> {
    private static final int PRIME2 = -1105259343;
    private static final int PRIME3 = -1262997959;
    private static final int PRIME4 = -825114047;
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
    int[] valueTable;

    public IdentityObjectIntMap() {
        this(32, 0.8f);
    }

    public IdentityObjectIntMap(int i) {
        this(i, 0.8f);
    }

    public IdentityObjectIntMap(int i, float f) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("initialCapacity must be >= 0: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (this.capacity <= 1073741824) {
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
                this.valueTable = new int[this.keyTable.length];
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

    public void put(K k, int i) {
        int i2;
        K k2 = k;
        if (k2 != null) {
            K[] kArr = this.keyTable;
            int i3 = this.mask;
            boolean z = this.isBigTable;
            int identityHashCode = System.identityHashCode(k);
            int i4 = identityHashCode & i3;
            K k3 = kArr[i4];
            if (k2 == k3) {
                this.valueTable[i4] = i;
                return;
            }
            int hash2 = hash2(identityHashCode);
            K k4 = kArr[hash2];
            if (k2 == k4) {
                this.valueTable[hash2] = i;
                return;
            }
            int hash3 = hash3(identityHashCode);
            K k5 = kArr[hash3];
            if (k2 == k5) {
                this.valueTable[hash3] = i;
                return;
            }
            K k6 = null;
            if (z) {
                int hash4 = hash4(identityHashCode);
                K k7 = kArr[hash4];
                if (k2 == k7) {
                    this.valueTable[hash4] = i;
                    return;
                } else {
                    i2 = hash4;
                    k6 = k7;
                }
            } else {
                i2 = -1;
            }
            int i5 = this.capacity;
            int i6 = this.stashSize + i5;
            while (i5 < i6) {
                if (kArr[i5] == k2) {
                    this.valueTable[i5] = i;
                    return;
                }
                i5++;
            }
            if (k3 == null) {
                kArr[i4] = k2;
                this.valueTable[i4] = i;
                int i7 = this.size;
                this.size = i7 + 1;
                if (i7 >= this.threshold) {
                    resize(this.capacity << 1);
                }
            } else if (k4 == null) {
                kArr[hash2] = k2;
                this.valueTable[hash2] = i;
                int i8 = this.size;
                this.size = i8 + 1;
                if (i8 >= this.threshold) {
                    resize(this.capacity << 1);
                }
            } else if (k5 == null) {
                kArr[hash3] = k2;
                this.valueTable[hash3] = i;
                int i9 = this.size;
                this.size = i9 + 1;
                if (i9 >= this.threshold) {
                    resize(this.capacity << 1);
                }
            } else if (!z || k6 != null) {
                push(k, i, i4, k3, hash2, k4, hash3, k5, i2, k6);
            } else {
                kArr[i2] = k2;
                this.valueTable[i2] = i;
                int i10 = this.size;
                this.size = i10 + 1;
                if (i10 >= this.threshold) {
                    resize(this.capacity << 1);
                }
            }
        } else {
            throw new IllegalArgumentException("key cannot be null.");
        }
    }

    private void putResize(K k, int i) {
        Object obj;
        int i2;
        int identityHashCode = System.identityHashCode(k);
        int i3 = identityHashCode & this.mask;
        K[] kArr = this.keyTable;
        K k2 = kArr[i3];
        if (k2 == null) {
            kArr[i3] = k;
            this.valueTable[i3] = i;
            int i4 = this.size;
            this.size = i4 + 1;
            if (i4 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return;
        }
        int hash2 = hash2(identityHashCode);
        K[] kArr2 = this.keyTable;
        K k3 = kArr2[hash2];
        if (k3 == null) {
            kArr2[hash2] = k;
            this.valueTable[hash2] = i;
            int i5 = this.size;
            this.size = i5 + 1;
            if (i5 >= this.threshold) {
                resize(this.capacity << 1);
            }
            return;
        }
        int hash3 = hash3(identityHashCode);
        K[] kArr3 = this.keyTable;
        K k4 = kArr3[hash3];
        if (k4 == null) {
            kArr3[hash3] = k;
            this.valueTable[hash3] = i;
            int i6 = this.size;
            this.size = i6 + 1;
            if (i6 >= this.threshold) {
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
                this.valueTable[hash4] = i;
                int i7 = this.size;
                this.size = i7 + 1;
                if (i7 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i2 = hash4;
            obj = k5;
        } else {
            obj = null;
            i2 = -1;
        }
        push(k, i, i3, k2, hash2, k3, hash3, k4, i2, obj);
    }

    private void push(K k, int i, int i2, K k2, int i3, K k3, int i4, K k4, int i5, K k5) {
        K k6;
        int i6;
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        int i7 = this.mask;
        boolean z = this.isBigTable;
        int i8 = this.pushIterations;
        int i9 = z ? 4 : 3;
        K k7 = k;
        int i10 = i;
        int i11 = i2;
        K k8 = k2;
        int i12 = i3;
        K k9 = k3;
        int i13 = i4;
        K k10 = k4;
        int i14 = i5;
        K k11 = k5;
        int i15 = 0;
        while (true) {
            int nextInt = ObjectMap.random.nextInt(i9);
            int i16 = i9;
            if (nextInt == 0) {
                int i17 = iArr[i11];
                kArr[i11] = k7;
                iArr[i11] = i10;
                i10 = i17;
                k7 = k8;
            } else if (nextInt == 1) {
                int i18 = iArr[i12];
                kArr[i12] = k7;
                iArr[i12] = i10;
                i10 = i18;
                k7 = k9;
            } else if (nextInt != 2) {
                int i19 = iArr[i14];
                kArr[i14] = k7;
                iArr[i14] = i10;
                k7 = k11;
                i10 = i19;
            } else {
                int i20 = iArr[i13];
                kArr[i13] = k7;
                iArr[i13] = i10;
                i10 = i20;
                k7 = k10;
            }
            int identityHashCode = System.identityHashCode(k7);
            i11 = identityHashCode & i7;
            k8 = kArr[i11];
            if (k8 == null) {
                kArr[i11] = k7;
                iArr[i11] = i10;
                int i21 = this.size;
                this.size = i21 + 1;
                if (i21 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i12 = hash2(identityHashCode);
            k9 = kArr[i12];
            if (k9 == null) {
                kArr[i12] = k7;
                iArr[i12] = i10;
                int i22 = this.size;
                this.size = i22 + 1;
                if (i22 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            i13 = hash3(identityHashCode);
            k10 = kArr[i13];
            if (k10 == null) {
                kArr[i13] = k7;
                iArr[i13] = i10;
                int i23 = this.size;
                this.size = i23 + 1;
                if (i23 >= this.threshold) {
                    resize(this.capacity << 1);
                }
                return;
            }
            if (z) {
                i6 = hash4(identityHashCode);
                k6 = kArr[i6];
                if (k6 == null) {
                    kArr[i6] = k7;
                    iArr[i6] = i10;
                    int i24 = this.size;
                    this.size = i24 + 1;
                    if (i24 >= this.threshold) {
                        resize(this.capacity << 1);
                    }
                    return;
                }
            } else {
                i6 = i14;
                k6 = k11;
            }
            int i25 = i15 + 1;
            if (i25 == i8) {
                putStash(k7, i10);
                return;
            }
            i15 = i25;
            k11 = k6;
            i9 = i16;
            i14 = i6;
        }
    }

    private void putStash(K k, int i) {
        int i2 = this.stashSize;
        if (i2 == this.stashCapacity) {
            resize(this.capacity << 1);
            put(k, i);
            return;
        }
        int i3 = this.capacity + i2;
        this.keyTable[i3] = k;
        this.valueTable[i3] = i;
        this.stashSize = i2 + 1;
        this.size++;
    }

    public int get(K k, int i) {
        int identityHashCode = System.identityHashCode(k);
        int i2 = this.mask & identityHashCode;
        if (k != this.keyTable[i2]) {
            i2 = hash2(identityHashCode);
            if (k != this.keyTable[i2]) {
                i2 = hash3(identityHashCode);
                if (k != this.keyTable[i2]) {
                    if (!this.isBigTable) {
                        return getStash(k, i);
                    }
                    i2 = hash4(identityHashCode);
                    if (k != this.keyTable[i2]) {
                        return getStash(k, i);
                    }
                }
            }
        }
        return this.valueTable[i2];
    }

    private int getStash(K k, int i) {
        K[] kArr = this.keyTable;
        int i2 = this.capacity;
        int i3 = this.stashSize + i2;
        while (i2 < i3) {
            if (k == kArr[i2]) {
                return this.valueTable[i2];
            }
            i2++;
        }
        return i;
    }

    public int getAndIncrement(K k, int i, int i2) {
        int identityHashCode = System.identityHashCode(k);
        int i3 = this.mask & identityHashCode;
        if (k != this.keyTable[i3]) {
            i3 = hash2(identityHashCode);
            if (k != this.keyTable[i3]) {
                i3 = hash3(identityHashCode);
                if (k != this.keyTable[i3]) {
                    if (!this.isBigTable) {
                        return getAndIncrementStash(k, i, i2);
                    }
                    i3 = hash4(identityHashCode);
                    if (k != this.keyTable[i3]) {
                        return getAndIncrementStash(k, i, i2);
                    }
                }
            }
        }
        int[] iArr = this.valueTable;
        int i4 = iArr[i3];
        iArr[i3] = i2 + i4;
        return i4;
    }

    private int getAndIncrementStash(K k, int i, int i2) {
        K[] kArr = this.keyTable;
        int i3 = this.capacity;
        int i4 = this.stashSize + i3;
        while (i3 < i4) {
            if (k == kArr[i3]) {
                int[] iArr = this.valueTable;
                int i5 = iArr[i3];
                iArr[i3] = i2 + i5;
                return i5;
            }
            i3++;
        }
        put(k, i2 + i);
        return i;
    }

    public int remove(K k, int i) {
        int identityHashCode = System.identityHashCode(k);
        int i2 = this.mask & identityHashCode;
        K[] kArr = this.keyTable;
        if (k == kArr[i2]) {
            kArr[i2] = null;
            int i3 = this.valueTable[i2];
            this.size--;
            return i3;
        }
        int hash2 = hash2(identityHashCode);
        K[] kArr2 = this.keyTable;
        if (k == kArr2[hash2]) {
            kArr2[hash2] = null;
            int i4 = this.valueTable[hash2];
            this.size--;
            return i4;
        }
        int hash3 = hash3(identityHashCode);
        K[] kArr3 = this.keyTable;
        if (k == kArr3[hash3]) {
            kArr3[hash3] = null;
            int i5 = this.valueTable[hash3];
            this.size--;
            return i5;
        }
        if (this.isBigTable) {
            int hash4 = hash4(identityHashCode);
            K[] kArr4 = this.keyTable;
            if (k == kArr4[hash4]) {
                kArr4[hash4] = null;
                int i6 = this.valueTable[hash4];
                this.size--;
                return i6;
            }
        }
        return removeStash(k, i);
    }

    /* access modifiers changed from: 0000 */
    public int removeStash(K k, int i) {
        K[] kArr = this.keyTable;
        int i2 = this.capacity;
        int i3 = this.stashSize + i2;
        while (i2 < i3) {
            if (k == kArr[i2]) {
                int i4 = this.valueTable[i2];
                removeStashIndex(i2);
                this.size--;
                return i4;
            }
            i2++;
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public void removeStashIndex(int i) {
        this.stashSize--;
        int i2 = this.capacity + this.stashSize;
        if (i < i2) {
            K[] kArr = this.keyTable;
            kArr[i] = kArr[i2];
            int[] iArr = this.valueTable;
            iArr[i] = iArr[i2];
        }
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
        int i = this.capacity + this.stashSize;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                kArr[i2] = null;
                i = i2;
            } else {
                this.size = 0;
                this.stashSize = 0;
                return;
            }
        }
    }

    public boolean containsValue(int i) {
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        int i2 = this.capacity + this.stashSize;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return false;
            }
            if (kArr[i3] != null && iArr[i3] == i) {
                return true;
            }
            i2 = i3;
        }
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
            if (k == kArr[i]) {
                return true;
            }
            i++;
        }
        return false;
    }

    public K findKey(int i) {
        K[] kArr = this.keyTable;
        int[] iArr = this.valueTable;
        int i2 = this.capacity + this.stashSize;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return null;
            }
            if (kArr[i3] != null && iArr[i3] == i) {
                return kArr[i3];
            }
            i2 = i3;
        }
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
        int[] iArr = this.valueTable;
        int i3 = this.stashCapacity;
        this.keyTable = (Object[]) new Object[(i + i3)];
        this.valueTable = new int[(i + i3)];
        int i4 = this.size;
        this.size = 0;
        this.stashSize = 0;
        if (i4 > 0) {
            for (int i5 = 0; i5 < i2; i5++) {
                K k = kArr[i5];
                if (k != null) {
                    putResize(k, iArr[i5]);
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
        int[] iArr = this.valueTable;
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
                sb.append(iArr[i]);
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
                    sb.append(iArr[i2]);
                }
                i = i2;
            } else {
                sb.append('}');
                return sb.toString();
            }
        }
    }
}
