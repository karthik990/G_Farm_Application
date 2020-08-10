package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzcp;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzcc implements RemoteCall {
    private final zzcd zzns;

    zzcc(zzcd zzcd) {
        this.zzns = zzcd;
    }

    public final void accept(Object obj, Object obj2) {
        zzcd zzcd = this.zzns;
        zzdp zzdp = (zzdp) obj;
        zzcd.zzpu = new zzeu(zzcd, (TaskCompletionSource) obj2);
        if (zzcd.zzqh) {
            zzdp.zzeb().zzf(zzcd.zzpr.zzcz(), zzcd.zzpq);
        } else {
            zzdp.zzeb().zza(new zzcp(zzcd.zzpr.zzcz()), (zzdu) zzcd.zzpq);
        }
    }
}
