package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzd;

final class zzhq implements zziz {
    private static final zzhq zzaae = new zzhq();

    private zzhq() {
    }

    public static zzhq zzib() {
        return zzaae;
    }

    public final boolean zza(Class<?> cls) {
        return zzhs.class.isAssignableFrom(cls);
    }

    public final zzja zzb(Class<?> cls) {
        if (!zzhs.class.isAssignableFrom(cls)) {
            String str = "Unsupported message type: ";
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        try {
            return (zzja) zzhs.zzd(cls.asSubclass(zzhs.class)).zza(zzd.zzaap, (Object) null, (Object) null);
        } catch (Exception e) {
            String str2 = "Unable to get message info for ";
            String valueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2), e);
        }
    }
}
