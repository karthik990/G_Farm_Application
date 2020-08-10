package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzdr;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzcw implements RemoteCall {
    private final zzcx zzoi;

    zzcw(zzcx zzcx) {
        this.zzoi = zzcx;
    }

    public final void accept(Object obj, Object obj2) {
        zzcx zzcx = this.zzoi;
        zzdp zzdp = (zzdp) obj;
        zzcx.zzpu = new zzeu(zzcx, (TaskCompletionSource) obj2);
        if (zzcx.zzqh) {
            zzdp.zzeb().zze(zzcx.zzpr.zzcz(), zzcx.zzpq);
        } else {
            zzdp.zzeb().zza(new zzdr(zzcx.zzpr.zzcz()), (zzdu) zzcx.zzpq);
        }
    }
}
