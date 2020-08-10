package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzwh {
    private static final zzwh zzcbl = new zzwh();
    private final zzwm zzcbm = new zzvk();
    private final ConcurrentMap<Class<?>, zzwl<?>> zzcbn = new ConcurrentHashMap();

    public static zzwh zzxt() {
        return zzcbl;
    }

    public final <T> zzwl<T> zzi(Class<T> cls) {
        String str = "messageType";
        zzuq.zza(cls, str);
        zzwl<T> zzwl = (zzwl) this.zzcbn.get(cls);
        if (zzwl != null) {
            return zzwl;
        }
        zzwl<T> zzh = this.zzcbm.zzh(cls);
        zzuq.zza(cls, str);
        zzuq.zza(zzh, "schema");
        zzwl zzwl2 = (zzwl) this.zzcbn.putIfAbsent(cls, zzh);
        return zzwl2 != null ? zzwl2 : zzh;
    }

    public final <T> zzwl<T> zzak(T t) {
        return zzi(t.getClass());
    }

    private zzwh() {
    }
}
