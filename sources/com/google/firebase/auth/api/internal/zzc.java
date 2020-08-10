package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzeb;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfu;
import com.google.firebase.auth.internal.zzt;
import com.twitter.sdk.android.core.internal.oauth.OAuthConstants;

final class zzc implements zzez<zzfu> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;

    zzc(zzb zzb, zzdm zzdm) {
        this.zzle = zzb;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzfu zzfu = (zzfu) obj;
        if (!zzfu.zzfd()) {
            this.zzle.zza(new zzes(zzfu.zzs(), zzfu.getIdToken(), Long.valueOf(zzfu.zzt()), OAuthConstants.AUTHORIZATION_BEARER), null, null, Boolean.valueOf(false), null, this.zzla, this);
        } else if (this.zzle.zzlc.zzed().booleanValue()) {
            this.zzla.zza(new zzeb(zzfu.zzbb(), zzfu.zzbc(), null));
        } else {
            zzbv("REQUIRES_SECOND_FACTOR_AUTH");
        }
    }
}
