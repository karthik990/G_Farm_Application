package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfm;
import com.google.android.gms.internal.firebase_auth.zzfo;
import com.google.firebase.auth.internal.zzt;

final class zzo implements zzez<zzes> {
    final /* synthetic */ zzdm zzla;
    final /* synthetic */ zzb zzle;
    private final /* synthetic */ zzfm zzlp;

    zzo(zzb zzb, zzfm zzfm, zzdm zzdm) {
        this.zzle = zzb;
        this.zzlp = zzfm;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzes zzes = (zzes) obj;
        if (this.zzle.zzlc.zzec().booleanValue() && this.zzle.zzld.zzej()) {
            this.zzlp.zzr(this.zzle.zzlc.zzec().booleanValue());
        }
        this.zzlp.zzcy(zzes.getAccessToken());
        this.zzle.zzlb.zza(this.zzlp, (zzez<zzfo>) new zzr<zzfo>(this, this));
    }
}
