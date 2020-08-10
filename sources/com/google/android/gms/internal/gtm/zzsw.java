package com.google.android.gms.internal.gtm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzsw {
    private static final zzsw zzbdr = new zzsw();
    private final zzta zzbds = new zzrz();
    private final ConcurrentMap<Class<?>, zzsz<?>> zzbdt = new ConcurrentHashMap();

    public static zzsw zzqs() {
        return zzbdr;
    }

    public final <T> zzsz<T> zzi(Class<T> cls) {
        String str = "messageType";
        zzre.zza(cls, str);
        zzsz<T> zzsz = (zzsz) this.zzbdt.get(cls);
        if (zzsz != null) {
            return zzsz;
        }
        zzsz<T> zzh = this.zzbds.zzh(cls);
        zzre.zza(cls, str);
        zzre.zza(zzh, "schema");
        zzsz zzsz2 = (zzsz) this.zzbdt.putIfAbsent(cls, zzh);
        return zzsz2 != null ? zzsz2 : zzh;
    }

    public final <T> zzsz<T> zzaf(T t) {
        return zzi(t.getClass());
    }

    private zzsw() {
    }
}
