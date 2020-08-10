package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzo;
import com.google.firebase.auth.api.internal.zzdv;

public final class zzfl implements zzdv<zzfl, zzo> {
    private String zzib;
    private String zzif;
    private String zzjv;
    private String zzkh;
    private long zzrh;

    public final String getIdToken() {
        return this.zzib;
    }

    public final String zzs() {
        return this.zzkh;
    }

    public final long zzt() {
        return this.zzrh;
    }

    public final zzjm<zzo> zzee() {
        return zzo.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzo) {
            zzo zzo = (zzo) zzjc;
            this.zzib = Strings.emptyToNull(zzo.getIdToken());
            this.zzjv = Strings.emptyToNull(zzo.getDisplayName());
            this.zzif = Strings.emptyToNull(zzo.getEmail());
            this.zzkh = Strings.emptyToNull(zzo.zzs());
            this.zzrh = zzo.zzt();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of SignUpNewUserResponse.");
    }
}
