package com.google.android.gms.internal.firebase_auth;

final class zzgm extends zzgp {
    private final int zzwa;
    private final int zzwb;

    zzgm(byte[] bArr, int i, int i2) {
        super(bArr);
        zzc(i, i + i2, bArr.length);
        this.zzwa = i;
        this.zzwb = i2;
    }

    public final byte zzp(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzwd[this.zzwa + i];
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
    public final byte zzq(int i) {
        return this.zzwd[this.zzwa + i];
    }

    public final int size() {
        return this.zzwb;
    }

    /* access modifiers changed from: protected */
    public final int zzgf() {
        return this.zzwa;
    }
}
