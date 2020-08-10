package com.google.android.gms.internal.gtm;

final class zzqa {
    private final byte[] buffer;
    private final zzqj zzawd;

    private zzqa(int i) {
        this.buffer = new byte[i];
        this.zzawd = zzqj.zzg(this.buffer);
    }

    public final zzps zzng() {
        if (this.zzawd.zzoi() == 0) {
            return new zzqc(this.buffer);
        }
        throw new IllegalStateException("Did not write as much data as expected.");
    }

    public final zzqj zznh() {
        return this.zzawd;
    }

    /* synthetic */ zzqa(int i, zzpt zzpt) {
        this(i);
    }
}
