package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;

public abstract class zzgf implements Serializable, Iterable<Byte> {
    public static final zzgf zzvv = new zzgp(zzht.EMPTY_BYTE_ARRAY);
    private static final zzgl zzvw = (zzge.zzga() ? new zzgs(null) : new zzgj(null));
    private static final Comparator<zzgf> zzvy = new zzgh();
    private int zzvx = 0;

    zzgf() {
    }

    /* access modifiers changed from: private */
    public static int zza(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract int size();

    /* access modifiers changed from: protected */
    public abstract String zza(Charset charset);

    /* access modifiers changed from: 0000 */
    public abstract void zza(zzgg zzgg) throws IOException;

    /* access modifiers changed from: protected */
    public abstract int zzb(int i, int i2, int i3);

    public abstract zzgf zzd(int i, int i2);

    public abstract boolean zzgd();

    public abstract byte zzp(int i);

    /* access modifiers changed from: 0000 */
    public abstract byte zzq(int i);

    public static zzgf zza(byte[] bArr, int i, int i2) {
        zzc(i, i + i2, bArr.length);
        return new zzgp(zzvw.zzc(bArr, i, i2));
    }

    public static zzgf zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    static zzgf zzb(byte[] bArr) {
        return new zzgp(bArr);
    }

    public static zzgf zzdh(String str) {
        return new zzgp(str.getBytes(zzht.UTF_8));
    }

    public final String zzgc() {
        return size() == 0 ? "" : zza(zzht.UTF_8);
    }

    public final int hashCode() {
        int i = this.zzvx;
        if (i == 0) {
            int size = size();
            i = zzb(size, 0, size);
            if (i == 0) {
                i = 1;
            }
            this.zzvx = i;
        }
        return i;
    }

    static zzgn zzr(int i) {
        return new zzgn(i, null);
    }

    /* access modifiers changed from: protected */
    public final int zzge() {
        return this.zzvx;
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

    public /* synthetic */ Iterator iterator() {
        return new zzgi(this);
    }
}
