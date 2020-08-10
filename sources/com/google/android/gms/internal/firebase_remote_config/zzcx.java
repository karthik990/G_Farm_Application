package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public final class zzcx extends zzk {
    zzcx(zzcy zzcy) {
        super(zzcy);
    }

    /* access modifiers changed from: protected */
    public final void zza(zzf<?> zzf) throws IOException {
        super.zza((zzf) zzf);
    }

    static {
        Object[] objArr = {zza.VERSION};
        if (!(zza.zza.intValue() == 1 && zza.zzb.intValue() >= 15)) {
            throw new IllegalStateException(zzdz.zza("You are currently running with version %s of google-api-client. You need at least version 1.15 of google-api-client to run version 1.25.0-SNAPSHOT of the Firebase Remote Config API library.", objArr));
        }
    }
}
