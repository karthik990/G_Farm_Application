package com.google.android.gms.internal.gtm;

final class zzpx extends zzqc {
    private final int zzawb;
    private final int zzawc;

    zzpx(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzawb = i;
        this.zzawc = i2;
    }

    public final byte zzak(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzawe[this.zzawb + i];
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
        sb2.append(size);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }

    /* access modifiers changed from: 0000 */
    public final byte zzal(int i) {
        return this.zzawe[this.zzawb + i];
    }

    public final int size() {
        return this.zzawc;
    }

    /* access modifiers changed from: protected */
    public final int zznf() {
        return this.zzawb;
    }
}
