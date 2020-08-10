package com.google.android.gms.internal.firebase_auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class zzho extends zzgb<Float> implements zzhz<Float>, zzjl, RandomAccess {
    private static final zzho zzzw;
    private int size;
    private float[] zzzx;

    zzho() {
        this(new float[10], 0);
    }

    private zzho(float[] fArr, int i) {
        this.zzzx = fArr;
        this.size = i;
    }

    /* access modifiers changed from: protected */
    public final void removeRange(int i, int i2) {
        zzfz();
        if (i2 >= i) {
            float[] fArr = this.zzzx;
            System.arraycopy(fArr, i2, fArr, i, this.size - i2);
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
        if (!(obj instanceof zzho)) {
            return super.equals(obj);
        }
        zzho zzho = (zzho) obj;
        if (this.size != zzho.size) {
            return false;
        }
        float[] fArr = zzho.zzzx;
        for (int i = 0; i < this.size; i++) {
            if (Float.floatToIntBits(this.zzzx[i]) != Float.floatToIntBits(fArr[i])) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + Float.floatToIntBits(this.zzzx[i2]);
        }
        return i;
    }

    public final int size() {
        return this.size;
    }

    public final void zzc(float f) {
        zzc(this.size, f);
    }

    private final void zzc(int i, float f) {
        zzfz();
        if (i >= 0) {
            int i2 = this.size;
            if (i <= i2) {
                float[] fArr = this.zzzx;
                if (i2 < fArr.length) {
                    System.arraycopy(fArr, i, fArr, i + 1, i2 - i);
                } else {
                    float[] fArr2 = new float[(((i2 * 3) / 2) + 1)];
                    System.arraycopy(fArr, 0, fArr2, 0, i);
                    System.arraycopy(this.zzzx, i, fArr2, i + 1, this.size - i);
                    this.zzzx = fArr2;
                }
                this.zzzx[i] = f;
                this.size++;
                this.modCount++;
                return;
            }
        }
        throw new IndexOutOfBoundsException(zzn(i));
    }

    public final boolean addAll(Collection<? extends Float> collection) {
        zzfz();
        zzht.checkNotNull(collection);
        if (!(collection instanceof zzho)) {
            return super.addAll(collection);
        }
        zzho zzho = (zzho) collection;
        int i = zzho.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            float[] fArr = this.zzzx;
            if (i3 > fArr.length) {
                this.zzzx = Arrays.copyOf(fArr, i3);
            }
            System.arraycopy(zzho.zzzx, 0, this.zzzx, this.size, zzho.size);
            this.size = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzfz();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Float.valueOf(this.zzzx[i]))) {
                float[] fArr = this.zzzx;
                System.arraycopy(fArr, i + 1, fArr, i, (this.size - i) - 1);
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
        float floatValue = ((Float) obj).floatValue();
        zzfz();
        zzm(i);
        float[] fArr = this.zzzx;
        float f = fArr[i];
        fArr[i] = floatValue;
        return Float.valueOf(f);
    }

    public final /* synthetic */ Object remove(int i) {
        zzfz();
        zzm(i);
        float[] fArr = this.zzzx;
        float f = fArr[i];
        int i2 = this.size;
        if (i < i2 - 1) {
            System.arraycopy(fArr, i + 1, fArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Float.valueOf(f);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc(i, ((Float) obj).floatValue());
    }

    public final /* synthetic */ zzhz zzo(int i) {
        if (i >= this.size) {
            return new zzho(Arrays.copyOf(this.zzzx, i), this.size);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzm(i);
        return Float.valueOf(this.zzzx[i]);
    }

    static {
        zzho zzho = new zzho(new float[0], 0);
        zzzw = zzho;
        zzho.zzfy();
    }
}
