package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbo.zze;

final class zzbbn implements zzbct {
    private static final zzbbn zzdts = new zzbbn();

    private zzbbn() {
    }

    public static zzbbn zzadc() {
        return zzdts;
    }

    public final boolean zza(Class<?> cls) {
        return zzbbo.class.isAssignableFrom(cls);
    }

    public final zzbcs zzb(Class<?> cls) {
        if (!zzbbo.class.isAssignableFrom(cls)) {
            String str = "Unsupported message type: ";
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        try {
            return (zzbcs) zzbbo.zzc(cls.asSubclass(zzbbo.class)).zza(zze.zzduc, (Object) null, (Object) null);
        } catch (Exception e) {
            String str2 = "Unable to get message info for ";
            String valueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), e);
        }
    }
}
