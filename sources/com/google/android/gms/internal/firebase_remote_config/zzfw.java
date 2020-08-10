package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import org.slf4j.Marker;

public abstract class zzfw implements Serializable, Iterable<Byte> {
    public static final zzfw zzop = new zzgg(zzhk.zztt);
    private static final zzgc zzoq = (zzfr.zzet() ? new zzgh(null) : new zzga(null));
    private static final Comparator<zzfw> zzor = new zzfy();
    private int zziy = 0;

    zzfw() {
    }

    /* access modifiers changed from: private */
    public static int zza(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract int size();

    /* access modifiers changed from: protected */
    public abstract int zza(int i, int i2, int i3);

    /* access modifiers changed from: 0000 */
    public abstract void zza(zzfv zzfv) throws IOException;

    /* access modifiers changed from: protected */
    public abstract int zzb(int i, int i2, int i3);

    public abstract zzfw zzb(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void zzb(byte[] bArr, int i, int i2, int i3);

    /* access modifiers changed from: protected */
    public abstract String zzc(Charset charset);

    public abstract boolean zzew();

    /* access modifiers changed from: protected */
    public abstract int zzex();

    /* access modifiers changed from: protected */
    public abstract boolean zzey();

    public abstract byte zzv(int i);

    /* access modifiers changed from: 0000 */
    public abstract byte zzw(int i);

    /* renamed from: zzev */
    public zzgd iterator() {
        return new zzfx(this);
    }

    public static zzfw zzb(byte[] bArr, int i, int i2) {
        zzc(i, i + i2, bArr.length);
        return new zzgg(zzoq.zzc(bArr, i, i2));
    }

    static zzfw zza(byte[] bArr) {
        return new zzgg(bArr);
    }

    public static zzfw zzbl(String str) {
        return new zzgg(str.getBytes(zzhk.UTF_8));
    }

    public static zzfw zza(Iterable<zzfw> iterable) {
        int size = ((Collection) iterable).size();
        if (size == 0) {
            return zzop;
        }
        return zza(iterable.iterator(), size);
    }

    private static zzfw zza(Iterator<zzfw> it, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.format("length (%s) must be >= 1", new Object[]{Integer.valueOf(i)}));
        } else if (i == 1) {
            return (zzfw) it.next();
        } else {
            int i2 = i >>> 1;
            zzfw zza = zza(it, i2);
            zzfw zza2 = zza(it, i - i2);
            if (Integer.MAX_VALUE - zza.size() >= zza2.size()) {
                return zzjf.zza(zza, zza2);
            }
            int size = zza.size();
            int size2 = zza2.size();
            StringBuilder sb = new StringBuilder(53);
            sb.append("ByteString would be too long: ");
            sb.append(size);
            sb.append(Marker.ANY_NON_NULL_MARKER);
            sb.append(size2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @Deprecated
    public final void zza(byte[] bArr, int i, int i2, int i3) {
        zzc(0, i3 + 0, size());
        zzc(i2, i2 + i3, bArr.length);
        if (i3 > 0) {
            zzb(bArr, 0, i2, i3);
        }
    }

    public final String zzb(Charset charset) {
        return size() == 0 ? "" : zzc(charset);
    }

    public final int hashCode() {
        int i = this.zziy;
        if (i == 0) {
            int size = size();
            i = zzb(size, 0, size);
            if (i == 0) {
                i = 1;
            }
            this.zziy = i;
        }
        return i;
    }

    static zzge zzx(int i) {
        return new zzge(i, null);
    }

    /* access modifiers changed from: protected */
    public final int zzez() {
        return this.zziy;
    }

    static void zzc(int i, int i2) {
        if (((i2 - (i + 1)) | i) >= 0) {
            return;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(22);
            sb.append("Index < 0: ");
            sb.append(i);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder(40);
        sb2.append("Index > length: ");
        sb2.append(i);
        sb2.append(", ");
        sb2.append(i2);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }

    static int zzc(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder(37);
            sb3.append("End index: ");
            sb3.append(i2);
            sb3.append(" >= ");
            sb3.append(i3);
            throw new IndexOutOfBoundsException(sb3.toString());
        }
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", new Object[]{Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size())});
    }
}
