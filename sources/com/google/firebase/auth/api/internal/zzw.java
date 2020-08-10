package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzee;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.auth.internal.zzt;

final class zzw implements zzez<zzes> {
    final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;

    zzw(zzb zzb, zzdm zzdm) {
        this.zzle = zzb;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzle.zzlb.zza(new zzee(((zzes) obj).getAccessToken()), (zzez<Void>) new zzz<Void>(this, this));
    }
}
