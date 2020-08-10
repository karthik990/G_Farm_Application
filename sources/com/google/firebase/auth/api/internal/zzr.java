package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzfo;

final class zzr implements zzez<zzfo> {
    private final /* synthetic */ zzez zzls;
    private final /* synthetic */ zzo zzlt;

    zzr(zzo zzo, zzez zzez) {
        this.zzlt = zzo;
        this.zzls = zzez;
    }

    public final void zzbv(String str) {
        this.zzls.zzbv(str);
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        this.zzlt.zzle.zza((zzfo) obj, this.zzlt.zzla, (zzew) this);
    }
}
