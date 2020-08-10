package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzhu extends zzgb<Integer> implements zzhx, zzjl, RandomAccess {
    private static final zzhu zzabd;
    private int size;
    private int[] zzabe;

    public static zzhu zziq() {
        return zzabd;
    }

    zzhu() {
        this(new int[10], 0);
    }

    private zzhu(int[] iArr, int i) {
        this.zzabe = iArr;
        this.size = i;
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zzfz();
        if (i2 >= i) {
            int[] iArr = this.zzabe;
            System.arraycopy(iArr, i2, iArr, i, this.size - i2);
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
        if (!(obj instanceof zzhu)) {
            return super.equals(obj);
        }
        zzhu zzhu = (zzhu) obj;
        if (this.size != zzhu.size) {
            return false;
        }
        int[] iArr = zzhu.zzabe;
        for (int i = 0; i < this.size; i++) {
            if (this.zzabe[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzabe[i2];
        }
        return i;
    }

    /* renamed from: zzav */
    public final zzhx zzo(int i) {
        if (i >= this.size) {
            return new zzhu(Arrays.copyOf(this.zzabe, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final int getInt(int i) {
        zzm(i);
        return this.zzabe[i];
    }

    public final int size() {
        return this.size;
    }

    public final void zzaw(int i) {
        zzr(this.size, i);
    }

    private final void zzr(int i, int i2) {
        zzfz();
        if (i >= 0) {
            int i3 = this.size;
            if (i <= i3) {
                int[] iArr = this.zzabe;
                if (i3 < iArr.length) {
                    System.arraycopy(iArr, i, iArr, i + 1, i3 - i);
                } else {
                    int[] iArr2 = new int[(((i3 * 3) / 2) + 1)];
                    System.arraycopy(iArr, 0, iArr2, 0, i);
                    System.arraycopy(this.zzabe, i, iArr2, i + 1, this.size - i);
                    this.zzabe = iArr2;
                }
                this.zzabe[i] = i2;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzn(i));
    }

    public final boolean addAll(Collection<? extends Integer> collection) {
        zzfz();
        zzht.checkNotNull(collection);
        if (!(collection instanceof zzhu)) {
            return super.addAll(collection);
        }
        zzhu zzhu = (zzhu) collection;
        int i = zzhu.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            int[] iArr = this.zzabe;
            if (i3 > iArr.length) {
                this.zzabe = Arrays.copyOf(iArr, i3);
            }
            System.arraycopy(zzhu.zzabe, 0, this.zzabe, this.size, zzhu.size);
            this.size = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzfz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzabe[i]))) {
                int[] iArr = this.zzabe;
                System.arraycopy(iArr, i + 1, iArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzm(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzn(i));
        }
    }

    private final String zzn(int i) {
        int i2 = this.size;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zzfz();
        zzm(i);
        int[] iArr = this.zzabe;
        int i2 = iArr[i];
        iArr[i] = intValue;
        return Integer.valueOf(i2);
    }

    public final /* synthetic */ Object remove(int i) {
        zzfz();
        zzm(i);
        int[] iArr = this.zzabe;
        int i2 = iArr[i];
        int i3 = this.size;
        if (i < i3 - 1) {
            System.arraycopy(iArr, i + 1, iArr, i, (i3 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzr(i, ((Integer) obj).intValue());
    }

    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    static {
        zzhu zzhu = new zzhu(new int[0], 0);
        zzabd = zzhu;
        zzhu.zzfy();
    }
}
