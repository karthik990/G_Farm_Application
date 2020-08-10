package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zze;
import com.google.firebase.auth.api.internal.zzdv;

public final class zzeg implements zzdv<zzeg, zze> {
    private String zzib;
    private String zzif;
    private String zzkh;
    private String zzrf;
    private boolean zzrg;
    private long zzrh;

    public final String getIdToken() {
        return this.zzib;
    }

    public final String zzs() {
        return this.zzkh;
    }

    public final boolean isNewUser() {
        return this.zzrg;
    }

    public final long zzt() {
        return this.zzrh;
    }

    public final zzjm<zze> zzee() {
        return zze.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zze) {
            zze zze = (zze) zzjc;
            this.zzrf = Strings.emptyToNull(zze.getLocalId());
            this.zzif = Strings.emptyToNull(zze.getEmail());
            this.zzib = Strings.emptyToNull(zze.getIdToken());
            this.zzkh = Strings.emptyToNull(zze.zzs());
            this.zzrg = zze.zzu();
            this.zzrh = zze.zzt();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of EmailLinkSigninResponse.");
    }
}
