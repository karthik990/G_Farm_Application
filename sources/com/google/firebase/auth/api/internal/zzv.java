package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzeb;
import com.google.android.gms.internal.firebase_auth.zzfo;
import com.google.firebase.auth.internal.zzt;

final class zzv implements zzez<zzfo> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;

    zzv(zzb zzb, zzdm zzdm) {
        this.zzle = zzb;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzfo zzfo = (zzfo) obj;
        if (!zzfo.zzfd()) {
            this.zzle.zza(zzfo, this.zzla, (zzew) this);
        } else if (this.zzle.zzlc.zzed().booleanValue()) {
            this.zzla.zza(new zzeb(zzfo.zzbb(), zzfo.zzbc(), zzfo.zzdo()));
        } else {
            zzbv("REQUIRES_SECOND_FACTOR_AUTH");
        }
    }
}
