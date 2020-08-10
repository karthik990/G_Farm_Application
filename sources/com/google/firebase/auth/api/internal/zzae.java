package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzfj;
import com.google.firebase.auth.internal.zzt;

final class zzae implements zzez<zzfj> {
    private final /* synthetic */ zzdm zzla;

    zzae(zzb zzb, zzdm zzdm) {
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzla.zzby(((zzfj) obj).getEmail());
    }
}
