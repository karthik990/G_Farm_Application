package com.google.android.gms.internal.firebase_remote_config;

import com.google.api.client.googleapis.MethodOverride;
import java.io.IOException;

public final class zzb implements zzac, zzw {
    private final boolean zzd;

    public zzb() {
        this(false);
    }

    private zzb(boolean z) {
        this.zzd = false;
    }

    public final void zza(zzaa zzaa) {
        zzaa.zza((zzw) this);
    }

    public final void zzb(zzaa zzaa) throws IOException {
        String requestMethod = zzaa.getRequestMethod();
        String str = "POST";
        boolean z = true;
        String str2 = "GET";
        if (requestMethod.equals(str) || ((!requestMethod.equals(str2) || zzaa.zzu().zzp().length() <= 2048) && zzaa.zzt().zzz(requestMethod))) {
            z = false;
        }
        if (z) {
            String requestMethod2 = zzaa.getRequestMethod();
            zzaa.zzw(str);
            zzaa.zzy().zzb(MethodOverride.HEADER, requestMethod2);
            if (requestMethod2.equals(str2)) {
                zzaa.zza((zzt) new zzam((zzs) zzaa.zzu().clone()));
                zzaa.zzu().clear();
            } else if (zzaa.zzv() == null) {
                zzaa.zza((zzt) new zzp());
            }
        }
    }
}
