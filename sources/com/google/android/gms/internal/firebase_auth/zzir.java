package com.google.android.gms.internal.firebase_auth;

final class zzir implements zziz {
    private zziz[] zzacj;

    zzir(zziz... zzizArr) {
        this.zzacj = zzizArr;
    }

    public final boolean zza(Class<?> cls) {
        for (zziz zza : this.zzacj) {
            if (zza.zza(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzja zzb(Class<?> cls) {
        zziz[] zzizArr;
        for (zziz zziz : this.zzacj) {
            if (zziz.zza(cls)) {
                return zziz.zzb(cls);
            }
        }
        String str = "No factory is available for message type: ";
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
