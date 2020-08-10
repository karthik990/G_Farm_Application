package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

final class zzg implements zzag {
    private final /* synthetic */ zzaa zzaa;
    private final /* synthetic */ zzf zzab;
    private final /* synthetic */ zzag zzz;

    zzg(zzf zzf, zzag zzag, zzaa zzaa2) {
        this.zzab = zzf;
        this.zzz = zzag;
        this.zzaa = zzaa2;
    }

    public final void zzb(zzad zzad) throws IOException {
        zzag zzag = this.zzz;
        if (zzag != null) {
            zzag.zzb(zzad);
        }
        if (!zzad.zzae() && this.zzaa.zzac()) {
            throw this.zzab.zza(zzad);
        }
    }
}
