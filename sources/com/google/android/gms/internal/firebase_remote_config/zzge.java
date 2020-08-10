package com.google.android.gms.internal.firebase_remote_config;

final class zzge {
    private final byte[] buffer;
    private final zzgp zzov;

    private zzge(int i) {
        this.buffer = new byte[i];
        this.zzov = zzgp.zzb(this.buffer);
    }

    public final zzfw zzfb() {
        if (this.zzov.zzgd() == 0) {
            return new zzgg(this.buffer);
        }
        throw new IllegalStateException("Did not write as much data as expected.");
    }

    public final zzgp zzfc() {
        return this.zzov;
    }

    /* synthetic */ zzge(int i, zzfx zzfx) {
        this(i);
    }
}
