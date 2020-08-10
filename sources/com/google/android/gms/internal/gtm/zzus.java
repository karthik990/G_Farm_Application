package com.google.android.gms.internal.gtm;

public final class zzus implements Cloneable {
    private static final zzut zzbhe = new zzut();
    private int mSize;
    private boolean zzbhf;
    private int[] zzbhg;
    private zzut[] zzbhh;

    zzus() {
        this(10);
    }

    private zzus(int i) {
        this.zzbhf = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzbhg = new int[idealIntArraySize];
        this.zzbhh = new zzut[idealIntArraySize];
        this.mSize = 0;
    }

    /* access modifiers changed from: 0000 */
    public final zzut zzcd(int i) {
        int zzcf = zzcf(i);
        if (zzcf >= 0) {
            zzut[] zzutArr = this.zzbhh;
            if (zzutArr[zzcf] != zzbhe) {
                return zzutArr[zzcf];
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final void zza(int i, zzut zzut) {
        int zzcf = zzcf(i);
        if (zzcf >= 0) {
            this.zzbhh[zzcf] = zzut;
            return;
        }
        int i2 = zzcf ^ -1;
        if (i2 < this.mSize) {
            zzut[] zzutArr = this.zzbhh;
            if (zzutArr[i2] == zzbhe) {
                this.zzbhg[i2] = i;
                zzutArr[i2] = zzut;
                return;
            }
        }
        int i3 = this.mSize;
        if (i3 >= this.zzbhg.length) {
            int idealIntArraySize = idealIntArraySize(i3 + 1);
            int[] iArr = new int[idealIntArraySize];
            zzut[] zzutArr2 = new zzut[idealIntArraySize];
            int[] iArr2 = this.zzbhg;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            zzut[] zzutArr3 = this.zzbhh;
            System.arraycopy(zzutArr3, 0, zzutArr2, 0, zzutArr3.length);
            this.zzbhg = iArr;
            this.zzbhh = zzutArr2;
        }
        int i4 = this.mSize;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.zzbhg;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            zzut[] zzutArr4 = this.zzbhh;
            System.arraycopy(zzutArr4, i2, zzutArr4, i5, this.mSize - i2);
        }
        this.zzbhg[i2] = i;
        this.zzbhh[i2] = zzut;
        this.mSize++;
    }

    /* access modifiers changed from: 0000 */
    public final int size() {
        return this.mSize;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    /* access modifiers changed from: 0000 */
    public final zzut zzce(int i) {
        return this.zzbhh[i];
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzus)) {
            return false;
        }
        zzus zzus = (zzus) obj;
        int i = this.mSize;
        if (i != zzus.mSize) {
            return false;
        }
        int[] iArr = this.zzbhg;
        int[] iArr2 = zzus.zzbhg;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            } else if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            zzut[] zzutArr = this.zzbhh;
            zzut[] zzutArr2 = zzus.zzbhh;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                } else if (!zzutArr[i4].equals(zzutArr2[i4])) {
                    z2 = false;
                    break;
                } else {
                    i4++;
                }
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzbhg[i2]) * 31) + this.zzbhh[i2].hashCode();
        }
        return i;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        return i2 / 4;
    }

    private final int zzcf(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzbhg[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return i3 ^ -1;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        zzus zzus = new zzus(i);
        System.arraycopy(this.zzbhg, 0, zzus.zzbhg, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            zzut[] zzutArr = this.zzbhh;
            if (zzutArr[i2] != null) {
                zzus.zzbhh[i2] = (zzut) zzutArr[i2].clone();
            }
        }
        zzus.mSize = i;
        return zzus;
    }
}
