package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzel;
import com.google.android.gms.internal.firebase_auth.zzes;

final class zzt implements zzez<zzes> {
    final /* synthetic */ zzdm zzla;
    final /* synthetic */ zzb zzle;
    final /* synthetic */ String zzlw;

    zzt(zzb zzb, String str, zzdm zzdm) {
        this.zzle = zzb;
        this.zzlw = str;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(com.google.firebase.auth.internal.zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzes zzes = (zzes) obj;
        this.zzle.zzlb.zza(new zzel(zzes.getAccessToken()), (zzez<zzek>) new zzs<zzek>(this, this, zzes));
    }
}
