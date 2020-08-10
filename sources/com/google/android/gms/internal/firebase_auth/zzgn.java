package com.google.android.gms.internal.firebase_auth;

final class zzgn {
    private final byte[] buffer;
    private final zzha zzwc;

    private zzgn(int i) {
        this.buffer = new byte[i];
        this.zzwc = zzha.zzc(this.buffer);
    }

    public final zzgf zzgg() {
        this.zzwc.zzhj();
        return new zzgp(this.buffer);
    }

    public final zzha zzgh() {
        return this.zzwc;
    }

    /* synthetic */ zzgn(int i, zzgi zzgi) {
        this(i);
    }
}
