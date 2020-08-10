package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfl;
import com.google.firebase.auth.internal.zzt;
import com.twitter.sdk.android.core.internal.oauth.OAuthConstants;

final class zzd implements zzez<zzfl> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;

    zzd(zzb zzb, zzdm zzdm) {
        this.zzle = zzb;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzfl zzfl = (zzfl) obj;
        this.zzle.zza(new zzes(zzfl.zzs(), zzfl.getIdToken(), Long.valueOf(zzfl.zzt()), OAuthConstants.AUTHORIZATION_BEARER), null, null, Boolean.valueOf(true), null, this.zzla, this);
    }
}
