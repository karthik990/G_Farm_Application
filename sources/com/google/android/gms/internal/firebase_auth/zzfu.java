package com.google.android.gms.internal.firebase_auth;

import android.text.TextUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzp.zzu;
import com.google.firebase.auth.api.internal.zzdv;
import java.util.ArrayList;
import java.util.List;

public final class zzfu implements zzdv<zzfu, zzu> {
    private String zzib;
    private String zzif;
    private String zzjv;
    private String zzkc;
    private String zzkh;
    private String zzkx;
    private List<zzeu> zzky;
    private String zzrf;
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

    public final List<zzeu> zzbc() {
        return this.zzky;
    }

    public final String zzbb() {
        return this.zzkx;
    }

    public final boolean zzfd() {
        return !TextUtils.isEmpty(this.zzkx);
    }

    public final zzjm<zzu> zzee() {
        return zzu.zzm();
    }

    public final /* synthetic */ zzdv zza(zzjc zzjc) {
        if (zzjc instanceof zzu) {
            zzu zzu = (zzu) zzjc;
            this.zzrf = Strings.emptyToNull(zzu.getLocalId());
            this.zzif = Strings.emptyToNull(zzu.getEmail());
            this.zzjv = Strings.emptyToNull(zzu.getDisplayName());
            this.zzib = Strings.emptyToNull(zzu.getIdToken());
            this.zzkc = Strings.emptyToNull(zzu.zzam());
            this.zzkh = Strings.emptyToNull(zzu.zzs());
            this.zzrh = zzu.zzt();
            this.zzky = new ArrayList();
            for (zzr zza : zzu.zzbc()) {
                this.zzky.add(zzeu.zza(zza));
            }
            this.zzkx = zzu.zzbb();
            return this;
        }
        throw new IllegalArgumentException("The passed proto must be an instance of VerifyPasswordResponse.");
    }
}
