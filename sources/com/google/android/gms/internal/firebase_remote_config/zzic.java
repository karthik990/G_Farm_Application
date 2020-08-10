package com.google.android.gms.internal.firebase_remote_config;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzic extends zzfq<Long> implements zzhn<Long>, zzja, RandomAccess {
    private static final zzic zzuw;
    private int size;
    private long[] zzux;

    zzic() {
        this(new long[10], 0);
    }

    private zzic(long[] jArr, int i) {
        this.zzux = jArr;
        this.size = i;
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zzes();
        if (i2 >= i) {
            long[] jArr = this.zzux;
            System.arraycopy(jArr, i2, jArr, i, this.size - i2);
            this.size -= i2 - i;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzic)) {
            return super.equals(obj);
        }
        zzic zzic = (zzic) obj;
        if (this.size != zzic.size) {
            return false;
        }
        long[] jArr = zzic.zzux;
        for (int i = 0; i < this.size; i++) {
            if (this.zzux[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzhk.zzo(this.zzux[i2]);
        }
        return i;
    }

    public final long getLong(int i) {
        zzs(i);
        return this.zzux[i];
    }

    public final int size() {
        return this.size;
    }

    public final void zzp(long j) {
        zzk(this.size, j);
    }

    private final void zzk(int i, long j) {
        zzes();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                long[] jArr = this.zzux;
                if (i2 < jArr.length) {
                    System.arraycopy(jArr, i, jArr, i + 1, i2 - i);
                } else {
                    long[] jArr2 = new long[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(jArr, 0, jArr2, 0, i);
                    System.arraycopy(this.zzux, i, jArr2, i + 1, this.size - i);
                    this.zzux = jArr2;
                }
                this.zzux[i] = j;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzt(i));
    }

    public final boolean addAll(Collection<? extends Long> collection) {
        zzes();
        zzhk.checkNotNull(collection);
        if (!(collection instanceof zzic)) {
            return super.addAll(collection);
        }
        zzic zzic = (zzic) collection;
        int i = zzic.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            long[] jArr = this.zzux;
            if (i3 > jArr.length) {
                this.zzux = Arrays.copyOf(jArr, i3);
            }
            System.arraycopy(zzic.zzux, 0, this.zzux, this.size, zzic.size);
            this.size = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzes();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Long.valueOf(this.zzux[i]))) {
                long[] jArr = this.zzux;
                System.arraycopy(jArr, i + 1, jArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzs(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzt(i));
        }
    }

    private final String zzt(int i) {
        int i2 = this.size;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        long longValue = ((Long) obj).longValue();
        zzes();
        zzs(i);
        long[] jArr = this.zzux;
        long j = jArr[i];
        jArr[i] = longValue;
        return Long.valueOf(j);
    }

    public final /* synthetic */ Object remove(int i) {
        zzes();
        zzs(i);
        long[] jArr = this.zzux;
        long j = jArr[i];
        int i2 = this.size;
        if (i < i2 - 1) {
            System.arraycopy(jArr, i + 1, jArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Long.valueOf(j);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzk(i, ((Long) obj).longValue());
    }

    public final /* synthetic */ zzhn zzu(int i) {
        if (i >= this.size) {
            return new zzic(Arrays.copyOf(this.zzux, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(getLong(i));
    }

    static {
        zzic zzic = new zzic(new long[0], 0);
        zzuw = zzic;
        zzic.zzer();
    }
}
