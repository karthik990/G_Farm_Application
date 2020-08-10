package com.google.android.gms.internal.ads;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzbdg {
    private static final zzbdg zzdxa = new zzbdg();
    private final zzbdn zzdxb;
    private final ConcurrentMap<Class<?>, zzbdm<?>> zzdxc = new ConcurrentHashMap();

    private zzbdg() {
        String[] strArr = {"com.google.protobuf.AndroidProto3SchemaFactory"};
        zzbdn zzbdn = null;
        for (int i = 0; i <= 0; i++) {
            zzbdn = zzeq(strArr[0]);
            if (zzbdn != null) {
                break;
            }
        }
        if (zzbdn == null) {
            zzbdn = new zzbcj();
        }
        this.zzdxb = zzbdn;
    }

    public static zzbdg zzaeo() {
        return zzdxa;
    }

    private static zzbdn zzeq(String str) {
        try {
            return (zzbdn) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }

    public final <T> zzbdm<T> zzab(T t) {
        return zze(t.getClass());
    }

    public final <T> zzbdm<T> zze(Class<T> cls) {
        String str = "messageType";
        zzbbq.zza(cls, str);
        zzbdm<T> zzbdm = (zzbdm) this.zzdxc.get(cls);
        if (zzbdm != null) {
            return zzbdm;
        }
        zzbdm<T> zzd = this.zzdxb.zzd(cls);
        zzbbq.zza(cls, str);
        zzbbq.zza(zzd, "schema");
        zzbdm zzbdm2 = (zzbdm) this.zzdxc.putIfAbsent(cls, zzd);
        return zzbdm2 != null ? zzbdm2 : zzd;
    }
}
