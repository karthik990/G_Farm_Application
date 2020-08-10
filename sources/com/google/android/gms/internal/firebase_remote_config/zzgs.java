package com.google.android.gms.internal.firebase_remote_config;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzgs extends zzfq<Double> implements zzhn<Double>, zzja, RandomAccess {
    private static final zzgs zzpr;
    private int size;
    private double[] zzps;

    zzgs() {
        this(new double[10], 0);
    }

    private zzgs(double[] dArr, int i) {
        this.zzps = dArr;
        this.size = i;
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zzes();
        if (i2 >= i) {
            double[] dArr = this.zzps;
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
        if (!(obj instanceof zzgs)) {
            return super.equals(obj);
        }
        zzgs zzgs = (zzgs) obj;
        if (this.size != zzgs.size) {
            return false;
        }
        double[] dArr = zzgs.zzps;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzps[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzhk.zzo(Double.doubleToLongBits(this.zzps[i2]));
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zze(double d) {
        zzc(this.size, d);
    }

    private final void zzc(int i, double d) {
        zzes();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                double[] dArr = this.zzps;
                if (i2 < dArr.length) {
                    System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
                } else {
                    double[] dArr2 = new double[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(dArr, 0, dArr2, 0, i);
                    System.arraycopy(this.zzps, i, dArr2, i + 1, this.size - i);
                    this.zzps = dArr2;
                }
                this.zzps[i] = d;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzt(i));
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzes();
        zzhk.checkNotNull(collection);
        if (!(collection instanceof zzgs)) {
            return super.addAll(collection);
        }
        zzgs zzgs = (zzgs) collection;
        int i = zzgs.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            double[] dArr = this.zzps;
            if (i3 > dArr.length) {
                this.zzps = Arrays.copyOf(dArr, i3);
            }
            System.arraycopy(zzgs.zzps, 0, this.zzps, this.size, zzgs.size);
            this.size = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzes();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzps[i]))) {
                double[] dArr = this.zzps;
                System.arraycopy(dArr, i + 1, dArr, i, (this.size - i) - 1);
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
        double doubleValue = ((Double) obj).doubleValue();
        zzes();
        zzs(i);
        double[] dArr = this.zzps;
        double d = dArr[i];
        dArr[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final /* synthetic */ Object remove(int i) {
        zzes();
        zzs(i);
        double[] dArr = this.zzps;
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

    public final /* synthetic */ zzhn zzu(int i) {
        if (i >= this.size) {
            return new zzgs(Arrays.copyOf(this.zzps, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzs(i);
        return Double.valueOf(this.zzps[i]);
    }

    static {
        zzgs zzgs = new zzgs(new double[0], 0);
        zzpr = zzgs;
        zzgs.zzer();
    }
}
