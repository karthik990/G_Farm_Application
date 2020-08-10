package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfg;
import com.google.firebase.auth.internal.zzt;
import java.util.List;

final class zzs implements zzez<zzek> {
    private final /* synthetic */ zzez zzls;
    private final /* synthetic */ zzes zzlu;
    private final /* synthetic */ zzt zzlv;

    zzs(zzt zzt, zzez zzez, zzes zzes) {
        this.zzlv = zzt;
        this.zzls = zzez;
        this.zzlu = zzes;
    }

    public final void zzbv(String str) {
        this.zzlv.zzla.onFailure(zzt.zzdc(str));
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        List zzer = ((zzek) obj).zzer();
        if (zzer == null || zzer.isEmpty()) {
            this.zzls.zzbv("No users.");
            return;
        }
        zzem zzem = (zzem) zzer.get(0);
        zzfg zzfg = new zzfg();
        zzfg.zzcq(this.zzlu.getAccessToken()).zzcv(this.zzlv.zzlw);
        this.zzlv.zzle.zza(this.zzlv.zzla, this.zzlu, zzem, zzfg, (zzew) this.zzls);
    }
}
