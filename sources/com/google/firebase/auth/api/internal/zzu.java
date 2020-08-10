package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzel;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.auth.internal.zzt;

final class zzu implements zzez<zzes> {
    final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;

    zzu(zzb zzb, zzdm zzdm) {
        this.zzle = zzb;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzes zzes = (zzes) obj;
        this.zzle.zzlb.zza(new zzel(zzes.getAccessToken()), (zzez<zzek>) new zzx<zzek>(this, this, zzes));
    }
}
