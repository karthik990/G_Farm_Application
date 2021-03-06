package com.google.android.gms.internal.firebase_remote_config;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzfu extends zzfq<Boolean> implements zzhn<Boolean>, zzja, RandomAccess {
    private static final zzfu zzon;
    private int size;
    private boolean[] zzoo;

    zzfu() {
        this(new boolean[10], 0);
    }

    private zzfu(boolean[] zArr, int i) {
        this.zzoo = zArr;
        this.size = i;
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zzes();
        if (i2 >= i) {
            boolean[] zArr = this.zzoo;
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
        if (!(obj instanceof zzfu)) {
            return super.equals(obj);
        }
        zzfu zzfu = (zzfu) obj;
        if (this.size != zzfu.size) {
            return false;
        }
        boolean[] zArr = zzfu.zzoo;
        for (int i = 0; i < this.size; i++) {
            if (this.zzoo[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzhk.zzg(this.zzoo[i2]);
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
        zzes();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                boolean[] zArr = this.zzoo;
                if (i2 < zArr.length) {
                    System.arraycopy(zArr, i, zArr, i + 1, i2 - i);
                } else {
                    boolean[] zArr2 = new boolean[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(zArr, 0, zArr2, 0, i);
                    System.arraycopy(this.zzoo, i, zArr2, i + 1, this.size - i);
                    this.zzoo = zArr2;
                }
                this.zzoo[i] = z;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzt(i));
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzes();
        zzhk.checkNotNull(collection);
        if (!(collection instanceof zzfu)) {
            return super.addAll(collection);
        }
        zzfu zzfu = (zzfu) collection;
        int i = zzfu.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            boolean[] zArr = this.zzoo;
            if (i3 > zArr.length) {
                this.zzoo = Arrays.copyOf(zArr, i3);
            }
            System.arraycopy(zzfu.zzoo, 0, this.zzoo, this.size, zzfu.size);
            this.size = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzes();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Boolean.valueOf(this.zzoo[i]))) {
                boolean[] zArr = this.zzoo;
                System.arraycopy(zArr, i + 1, zArr, i, (this.size - i) - 1);
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
        boolean booleanValue = ((Boolean) obj).booleanValue();
        zzes();
        zzs(i);
        boolean[] zArr = this.zzoo;
        boolean z = zArr[i];
        zArr[i] = booleanValue;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ Object remove(int i) {
        zzes();
        zzs(i);
        boolean[] zArr = this.zzoo;
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

    public final /* synthetic */ zzhn zzu(int i) {
        if (i >= this.size) {
            return new zzfu(Arrays.copyOf(this.zzoo, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzs(i);
        return Boolean.valueOf(this.zzoo[i]);
    }

    static {
        zzfu zzfu = new zzfu(new boolean[0], 0);
        zzon = zzfu;
        zzfu.zzer();
    }
}
