package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzhi.zzd;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class zzgv {
    private static volatile boolean zzpu = false;
    private static final Class<?> zzpv = zzgi();
    static final zzgv zzpw = new zzgv(true);
    private final Map<zzgw, zzd<?, ?>> zzpx;

    private static Class<?> zzgi() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzgv zzgj() {
        return zzgu.zzgh();
    }

    public final <ContainingType extends zzio> zzd<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return (zzd) this.zzpx.get(new zzgw(containingtype, i));
    }

    zzgv() {
        this.zzpx = new HashMap();
    }

    private zzgv(boolean z) {
        this.zzpx = Collections.emptyMap();
    }
}
