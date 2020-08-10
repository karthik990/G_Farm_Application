package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzeh;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.internal.zzt;

final class zzf implements zzez<zzes> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;
    private final /* synthetic */ EmailAuthCredential zzlf;

    zzf(zzb zzb, EmailAuthCredential emailAuthCredential, zzdm zzdm) {
        this.zzle = zzb;
        this.zzlf = emailAuthCredential;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzle.zza(new zzeh(this.zzlf, ((zzes) obj).getAccessToken()), this.zzla);
    }
}
