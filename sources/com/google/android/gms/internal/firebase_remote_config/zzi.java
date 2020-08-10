package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public class zzi implements zzj {
    private final String key;
    private final String zzae;

    public zzi() {
        this(null);
    }

    public zzi(String str) {
        this(str, null);
    }

    private zzi(String str, String str2) {
        this.key = str;
        this.zzae = null;
    }

    public final void zza(zzf<?> zzf) throws IOException {
        String str = this.key;
        if (str != null) {
            zzf.put("key", str);
        }
    }
}
