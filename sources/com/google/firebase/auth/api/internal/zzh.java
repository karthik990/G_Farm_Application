package com.google.firebase.auth.api.internal;

import com.google.android.gms.internal.firebase_auth.zzek;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfg;
import java.util.List;

final class zzh implements zzez<zzek> {
    private final /* synthetic */ zzdm zzla;
    private final /* synthetic */ zzb zzle;
    private final /* synthetic */ zzfg zzlg;
    private final /* synthetic */ zzes zzli;
    private final /* synthetic */ zzew zzlj;

    zzh(zzb zzb, zzew zzew, zzdm zzdm, zzes zzes, zzfg zzfg) {
        this.zzle = zzb;
        this.zzlj = zzew;
        this.zzla = zzdm;
        this.zzli = zzes;
        this.zzlg = zzfg;
    }

    public final void zzbv(String str) {
        this.zzlj.zzbv(str);
    }

    public final /* synthetic */ void onSuccess(Object obj) {
        List zzer = ((zzek) obj).zzer();
        if (zzer == null || zzer.isEmpty()) {
            this.zzlj.zzbv("No users");
        } else {
            this.zzle.zza(this.zzla, this.zzli, (zzem) zzer.get(0), this.zzlg, this.zzlj);
        }
    }
}
