package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzfd;
import com.google.firebase.auth.internal.zzt;

final class zzl implements zzez<Object> {
    private final /* synthetic */ zzdm zzla;

    zzl(zzb zzb, zzdm zzdm) {
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzla.zza((zzfd) null);
    }
}
