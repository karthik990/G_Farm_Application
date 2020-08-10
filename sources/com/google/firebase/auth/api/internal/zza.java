package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.auth.internal.zzt;

final class zza implements zzez<zzes> {
    private final /* synthetic */ zzdm zzla;

    zza(zzb zzb, zzdm zzdm) {
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzla.zzb((zzes) obj);
    }
}
