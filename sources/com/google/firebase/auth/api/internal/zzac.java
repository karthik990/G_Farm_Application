package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfg;
import com.google.firebase.auth.internal.zzt;

final class zzac implements zzez<zzes> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;
    private final /* synthetic */ String zzlq;

    zzac(zzb zzb, String str, zzdm zzdm) {
        this.zzle = zzb;
        this.zzlq = str;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzes zzes = (zzes) obj;
        zzfg zzfg = new zzfg();
        zzfg.zzcq(zzes.getAccessToken()).zzcr(this.zzlq);
        this.zzle.zza(this.zzla, zzes, zzfg, (zzew) this);
    }
}
