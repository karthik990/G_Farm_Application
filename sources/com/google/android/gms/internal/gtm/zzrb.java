package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc.zze;

final class zzrb implements zzsj {
    private static final zzrb zzbaj = new zzrb();

    private zzrb() {
    }

    public static zzrb zzpc() {
        return zzbaj;
    }

    public final boolean zze(Class<?> cls) {
        return zzrc.class.isAssignableFrom(cls);
    }

    public final zzsi zzf(Class<?> cls) {
        if (!zzrc.class.isAssignableFrom(cls)) {
            String str = "Unsupported message type: ";
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        try {
            return (zzsi) zzrc.zzg(cls.asSubclass(zzrc.class)).zza(zze.zzbat, (Object) null, (Object) null);
        } catch (Exception e) {
            String str2 = "Unable to get message info for ";
            String valueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), e);
        }
    }
}
