package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzeg;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.firebase.auth.internal.zzt;
import com.twitter.sdk.android.core.internal.oauth.OAuthConstants;

final class zze implements zzez<zzeg> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;

    zze(zzb zzb, zzdm zzdm) {
        this.zzle = zzb;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzeg zzeg = (zzeg) obj;
        this.zzle.zza(new zzes(zzeg.zzs(), zzeg.getIdToken(), Long.valueOf(zzeg.zzt()), OAuthConstants.AUTHORIZATION_BEARER), null, null, Boolean.valueOf(zzeg.isNewUser()), null, this.zzla, this);
    }
}
