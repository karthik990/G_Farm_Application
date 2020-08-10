package com.google.android.gms.internal.firebase_remote_config;

final class zzif implements zzin {
    private zzin[] zzva;

    zzif(zzin... zzinArr) {
        this.zzva = zzinArr;
    }

    public final boolean zzg(Class<?> cls) {
        for (zzin zzg : this.zzva) {
            if (zzg.zzg(cls)) {
                return true;
            }
        }
        return false;
    }

    public final zzim zzh(Class<?> cls) {
        zzin[] zzinArr;
        for (zzin zzin : this.zzva) {
            if (zzin.zzg(cls)) {
                return zzin.zzh(cls);
            }
        }
        String str = "No factory is available for message type: ";
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
