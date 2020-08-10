package com.google.android.gms.internal.gtm;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzqm extends zzpo<Double> implements zzrj<Double>, zzsv, RandomAccess {
    private static final zzqm zzaxe;
    private int size;
    private double[] zzaxf;

    zzqm() {
        this(new double[10], 0);
    }

    private zzqm(double[] dArr, int i) {
        this.zzaxf = dArr;
        this.size = i;
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zzmz();
        if (i2 >= i) {
            double[] dArr = this.zzaxf;
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
        if (!(obj instanceof zzqm)) {
            return super.equals(obj);
        }
        zzqm zzqm = (zzqm) obj;
        if (this.size != zzqm.size) {
            return false;
        }
        double[] dArr = zzqm.zzaxf;
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(this.zzaxf[i]) != Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + zzre.zzz(Double.doubleToLongBits(this.zzaxf[i2]));
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zzd(double d) {
        zzc(this.size, d);
    }

    private final void zzc(int i, double d) {
        zzmz();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                double[] dArr = this.zzaxf;
                if (i2 < dArr.length) {
                    System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
                } else {
                    double[] dArr2 = new double[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(dArr, 0, dArr2, 0, i);
                    System.arraycopy(this.zzaxf, i, dArr2, i + 1, this.size - i);
                    this.zzaxf = dArr2;
                }
                this.zzaxf[i] = d;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzai(i));
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzmz();
        zzre.checkNotNull(collection);
        if (!(collection instanceof zzqm)) {
            return super.addAll(collection);
        }
        zzqm zzqm = (zzqm) collection;
        int i = zzqm.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            double[] dArr = this.zzaxf;
            if (i3 > dArr.length) {
                this.zzaxf = Arrays.copyOf(dArr, i3);
            }
            System.arraycopy(zzqm.zzaxf, 0, this.zzaxf, this.size, zzqm.size);
            this.size = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzmz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Double.valueOf(this.zzaxf[i]))) {
                double[] dArr = this.zzaxf;
                System.arraycopy(dArr, i + 1, dArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzah(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzai(i));
        }
    }

    private final String zzai(int i) {
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
        zzmz();
        zzah(i);
        double[] dArr = this.zzaxf;
        double d = dArr[i];
        dArr[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final /* synthetic */ Object remove(int i) {
        zzmz();
        zzah(i);
        double[] dArr = this.zzaxf;
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

    public final /* synthetic */ zzrj zzaj(int i) {
        if (i >= this.size) {
            return new zzqm(Arrays.copyOf(this.zzaxf, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzah(i);
        return Double.valueOf(this.zzaxf[i]);
    }

    static {
        zzqm zzqm = new zzqm(new double[0], 0);
        zzaxe = zzqm;
        zzqm.zzmi();
    }
}
