package com.google.android.gms.internal.firebase_remote_config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzjb {
    private static final zzjb zzwd = new zzjb();
    private final zzjk zzwe = new zzid();
    private final ConcurrentMap<Class<?>, zzjj<?>> zzwf = new ConcurrentHashMap();

    public static zzjb zzik() {
        return zzwd;
    }

    public final <T> zzjj<T> zzk(Class<T> cls) {
        String str = "messageType";
        zzhk.zza(cls, str);
        zzjj<T> zzjj = (zzjj) this.zzwf.get(cls);
        if (zzjj != null) {
            return zzjj;
        }
        zzjj<T> zzj = this.zzwe.zzj(cls);
        zzhk.zza(cls, str);
        zzhk.zza(zzj, "schema");
        zzjj zzjj2 = (zzjj) this.zzwf.putIfAbsent(cls, zzj);
        return zzjj2 != null ? zzjj2 : zzj;
    }

    public final <T> zzjj<T> zzz(T t) {
        return zzk(t.getClass());
    }

    private zzjb() {
    }
}
