package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzhb extends zzgb<Double> implements zzhz<Double>, zzjl, RandomAccess {
    private static final zzhb zzwy;
    private int size;
    private double[] zzwz;

    zzhb() {
        this(new double[10], 0);
    }

    private zzhb(double[] dArr, int i) {
        this.zzwz = dArr;
        this.size = i;
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zzfz();
        if (i2 >= i) {
            double[] dArr = this.zzwz;
            System.arraycopy(dArr, i2, dArr, i, this.size - i2);
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
        if (!(obj instanceof zzhb)) {
            return super.equals(obj);
        }
        zzhb zzhb = (zzhb) obj;
        if (this.size != zzhb.size) {
            return false;
        }
        double[] dArr = zzhb.zzwz;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzwz[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzht.zzk(Double.doubleToLongBits(this.zzwz[i2]));
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zzc(double d) {
        zzc(this.size, d);
    }

    private final void zzc(int i, double d) {
        zzfz();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                double[] dArr = this.zzwz;
                if (i2 < dArr.length) {
                    System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
                } else {
                    double[] dArr2 = new double[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(dArr, 0, dArr2, 0, i);
                    System.arraycopy(this.zzwz, i, dArr2, i + 1, this.size - i);
                    this.zzwz = dArr2;
                }
                this.zzwz[i] = d;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzn(i));
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzfz();
        zzht.checkNotNull(collection);
        if (!(collection instanceof zzhb)) {
            return super.addAll(collection);
        }
        zzhb zzhb = (zzhb) collection;
        int i = zzhb.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            double[] dArr = this.zzwz;
            if (i3 > dArr.length) {
                this.zzwz = Arrays.copyOf(dArr, i3);
            }
            System.arraycopy(zzhb.zzwz, 0, this.zzwz, this.size, zzhb.size);
            this.size = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzfz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzwz[i]))) {
                double[] dArr = this.zzwz;
                System.arraycopy(dArr, i + 1, dArr, i, (this.size - i) - 1);
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
        double doubleValue = ((Double) obj).doubleValue();
        zzfz();
        zzm(i);
        double[] dArr = this.zzwz;
        double d = dArr[i];
        dArr[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final /* synthetic */ Object remove(int i) {
        zzfz();
        zzm(i);
        double[] dArr = this.zzwz;
        double d = dArr[i];
        int i2 = this.size;
        if (i < i2 - 1) {
            System.arraycopy(dArr, i + 1, dArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Double.valueOf(d);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Double) obj).doubleValue());
    }

    public final /* synthetic */ zzhz zzo(int i) {
        if (i >= this.size) {
            return new zzhb(Arrays.copyOf(this.zzwz, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzm(i);
        return Double.valueOf(this.zzwz[i]);
    }

    static {
        zzhb zzhb = new zzhb(new double[0], 0);
        zzwy = zzhb;
        zzhb.zzfy();
    }
}
