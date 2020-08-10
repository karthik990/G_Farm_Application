package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzhi.zze;

final class zzhh implements zzin {
    private static final zzhh zzsv = new zzhh();

    private zzhh() {
    }

    public static zzhh zzgv() {
        return zzsv;
    }

    public final boolean zzg(Class<?> cls) {
        return zzhi.class.isAssignableFrom(cls);
    }

    public final zzim zzh(Class<?> cls) {
        if (!zzhi.class.isAssignableFrom(cls)) {
            String str = "Unsupported message type: ";
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        try {
            return (zzim) zzhi.zzi(cls.asSubclass(zzhi.class)).zzb(zze.zztf, null, null);
        } catch (Exception e) {
            String str2 = "Unable to get message info for ";
            String valueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), e);
        }
    }
}
