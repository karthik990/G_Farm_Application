package com.google.firebase.auth.api.internal;

import com.google.firebase.auth.internal.zzt;

final class zzy implements zzez<Void> {
    private final /* synthetic */ zzdm zzla;

    zzy(zzb zzb, zzdm zzdm) {
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzla.zzea();
    }
}
