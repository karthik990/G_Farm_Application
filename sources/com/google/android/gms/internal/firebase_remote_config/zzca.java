package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public final class zzca {
    /* JADX INFO: finally extract failed */
    public static long zzb(zzcl zzcl) throws IOException {
        zzbo zzbo = new zzbo();
        try {
            zzcl.writeTo(zzbo);
            zzbo.close();
            return zzbo.zzel;
        } catch (Throwable th) {
            zzbo.close();
            throw th;
        }
    }
}
