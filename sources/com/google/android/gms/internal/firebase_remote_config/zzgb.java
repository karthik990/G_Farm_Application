package com.google.android.gms.internal.firebase_remote_config;

final class zzgb extends zzgg {
    private final int zzot;
    private final int zzou;

    zzgb(byte[] bArr, int i, int i2) {
        super(bArr);
        zzc(i, i + i2, bArr.length);
        this.zzot = i;
        this.zzou = i2;
    }

    public final byte zzv(int i) {
        zzc(i, size());
        return this.zzow[this.zzot + i];
    }

    /* access modifiers changed from: 0000 */
    public final byte zzw(int i) {
        return this.zzow[this.zzot + i];
    }

    public final int size() {
        return this.zzou;
    }

    /* access modifiers changed from: protected */
    public final int zzfa() {
        return this.zzot;
    }

    /* access modifiers changed from: protected */
    public final void zzb(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzow, zzfa() + i, bArr, i2, i3);
    }
}
