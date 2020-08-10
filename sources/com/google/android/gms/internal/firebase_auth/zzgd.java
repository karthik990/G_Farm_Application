package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzgd extends zzgb<Boolean> implements zzhz<Boolean>, zzjl, RandomAccess {
    private static final zzgd zzvr;
    private int size;
    private boolean[] zzvs;

    zzgd() {
        this(new boolean[10], 0);
    }

    private zzgd(boolean[] zArr, int i) {
        this.zzvs = zArr;
        this.size = i;
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zzfz();
        if (i2 >= i) {
            boolean[] zArr = this.zzvs;
            System.arraycopy(zArr, i2, zArr, i, this.size - i2);
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
        if (!(obj instanceof zzgd)) {
            return super.equals(obj);
        }
        zzgd zzgd = (zzgd) obj;
        if (this.size != zzgd.size) {
            return false;
        }
        boolean[] zArr = zzgd.zzvs;
        for (int i = 0; i < this.size; i++) {
            if (this.zzvs[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzht.zzv(this.zzvs[i2]);
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void addBoolean(boolean z) {
        zza(this.size, z);
    }

    private final void zza(int i, boolean z) {
        zzfz();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                boolean[] zArr = this.zzvs;
                if (i2 < zArr.length) {
                    System.arraycopy(zArr, i, zArr, i + 1, i2 - i);
                } else {
                    boolean[] zArr2 = new boolean[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(zArr, 0, zArr2, 0, i);
                    System.arraycopy(this.zzvs, i, zArr2, i + 1, this.size - i);
                    this.zzvs = zArr2;
                }
                this.zzvs[i] = z;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzn(i));
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzfz();
        zzht.checkNotNull(collection);
        if (!(collection instanceof zzgd)) {
            return super.addAll(collection);
        }
        zzgd zzgd = (zzgd) collection;
        int i = zzgd.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            boolean[] zArr = this.zzvs;
            if (i3 > zArr.length) {
                this.zzvs = Arrays.copyOf(zArr, i3);
            }
            System.arraycopy(zzgd.zzvs, 0, this.zzvs, this.size, zzgd.size);
            this.size = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzfz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzvs[i]))) {
                boolean[] zArr = this.zzvs;
                System.arraycopy(zArr, i + 1, zArr, i, (this.size - i) - 1);
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
        boolean booleanValue = ((Boolean) obj).booleanValue();
        zzfz();
        zzm(i);
        boolean[] zArr = this.zzvs;
        boolean z = zArr[i];
        zArr[i] = booleanValue;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ Object remove(int i) {
        zzfz();
        zzm(i);
        boolean[] zArr = this.zzvs;
        boolean z = zArr[i];
        int i2 = this.size;
        if (i < i2 - 1) {
            System.arraycopy(zArr, i + 1, zArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Boolean) obj).booleanValue());
    }

    public final /* synthetic */ zzhz zzo(int i) {
        if (i >= this.size) {
            return new zzgd(Arrays.copyOf(this.zzvs, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzm(i);
        return Boolean.valueOf(this.zzvs[i]);
    }

    static {
        zzgd zzgd = new zzgd(new boolean[0], 0);
        zzvr = zzgd;
        zzgd.zzfy();
    }
}
