package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfg;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.zzt;

final class zzad implements zzez<zzes> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;
    private final /* synthetic */ UserProfileChangeRequest zzlz;

    zzad(zzb zzb, UserProfileChangeRequest userProfileChangeRequest, zzdm zzdm) {
        this.zzle = zzb;
        this.zzlz = userProfileChangeRequest;
        this.zzla = zzdm;
    }

    public final void zzbv(String str) {
        this.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        zzes zzes = (zzes) obj;
        zzfg zzfg = new zzfg();
        zzfg.zzcq(zzes.getAccessToken());
        if (this.zzlz.zzde() || this.zzlz.getDisplayName() != null) {
            zzfg.zzct(this.zzlz.getDisplayName());
        }
        if (this.zzlz.zzdf() || this.zzlz.getPhotoUri() != null) {
            zzfg.zzcu(this.zzlz.zzam());
        }
        this.zzle.zza(this.zzla, zzes, zzfg, (zzew) this);
    }
}
