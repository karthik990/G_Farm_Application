package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public final class zzab {
    private final zzac zzbq;
    private final zzah zzo;

    zzab(zzah zzah, zzac zzac) {
        this.zzo = zzah;
        this.zzbq = zzac;
    }

    public final zzaa zza(String str, zzs zzs, zzt zzt) throws IOException {
        zzaa zzaa = new zzaa(this.zzo, null);
        zzac zzac = this.zzbq;
        if (zzac != null) {
            zzac.zza(zzaa);
        }
        zzaa.zzw(str);
        zzaa.zza(zzs);
        if (zzt != null) {
            zzaa.zza(zzt);
        }
        return zzaa;
    }
}
