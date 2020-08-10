package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzm;
import com.google.firebase.auth.api.internal.zzdv;
import java.util.List;

public final class zzfj implements zzdv<zzfj, zzm> {
    private String zzib;
    private String zzif;
    private String zzig;
    private String zzjv;
    private String zzkc;
    private String zzkh;
    private long zzrh;
    private zzey zzrq;
    private Boolean zzsl;

    public final String getIdToken() {
        return this.zzib;
    }

    public final String zzs() {
        return this.zzkh;
    }

    public final long zzt() {
        return this.zzrh;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final List<zzew> zzes() {
        zzey zzey = this.zzrq;
        if (zzey != null) {
            return zzey.zzes();
        }
        return null;
    }

    public final zzjm<zzm> zzee() {
        return zzm.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzm) {
            zzm zzm = (zzm) zzjc;
            this.zzif = Strings.emptyToNull(zzm.getEmail());
            this.zzig = Strings.emptyToNull(zzm.zzan());
            this.zzsl = Boolean.valueOf(zzm.zzao());
            this.zzjv = Strings.emptyToNull(zzm.getDisplayName());
            this.zzkc = Strings.emptyToNull(zzm.zzam());
            this.zzrq = zzey.zze(zzm.zzal());
            this.zzib = Strings.emptyToNull(zzm.getIdToken());
            this.zzkh = Strings.emptyToNull(zzm.zzs());
            this.zzrh = zzm.zzt();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of SetAccountInfoResponse.");
    }
}
