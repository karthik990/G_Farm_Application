package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class zzaz implements zzch {
    private final zzax zzda;
    private final Set<String> zzdc;

    protected zzaz(zzba zzba) {
        this.zzda = zzba.zzda;
        this.zzdc = new HashSet(zzba.zzdd);
    }

    public final <T> T zza(InputStream inputStream, Charset charset, Class<T> cls) throws IOException {
        zzbb zza = this.zzda.zza(inputStream, charset);
        if (!this.zzdc.isEmpty()) {
            try {
                String str = "wrapper key(s) not found: %s";
                Object[] objArr = {this.zzdc};
                if (!((zza.zza(this.zzdc) == null || zza.zzbc() == zzbf.zzdk) ? false : true)) {
                    throw new IllegalArgumentException(zzdz.zza(str, objArr));
                }
            } catch (Throwable th) {
                zza.close();
                throw th;
            }
        }
        return zza.zza(cls, true, null);
    }

    public final zzax zzl() {
        return this.zzda;
    }

    public final Set<String> zzay() {
        return Collections.unmodifiableSet(this.zzdc);
    }
}
