package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.internal.firebase_auth.zzbz;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzay implements RemoteCall {
    private final zzaz zzmw;

    zzay(zzaz zzaz) {
        this.zzmw = zzaz;
    }

    public final void accept(Object obj, Object obj2) {
        zzaz zzaz = this.zzmw;
        zzdp zzdp = (zzdp) obj;
        zzaz.zzpu = new zzeu(zzaz, (TaskCompletionSource) obj2);
        if (zzaz.zzqh) {
            zzdp.zzeb().zzg(zzaz.zzpr.zzcz(), zzaz.zzpq);
        } else {
            zzdp.zzeb().zza(new zzbz(zzaz.zzpr.zzcz()), (zzdu) zzaz.zzpq);
        }
    }
}
