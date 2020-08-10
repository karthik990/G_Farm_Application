package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzaw implements RemoteCall {
    private final zzax zzmu;

    zzaw(zzax zzax) {
        this.zzmu = zzax;
    }

    public final void accept(Object obj, Object obj2) {
        zzax zzax = this.zzmu;
        zzdp zzdp = (zzdp) obj;
        zzax.zzpu = new zzeu(zzax, (TaskCompletionSource) obj2);
        if (zzax.zzqh) {
            zzdp.zzeb().zzc(zzax.zzmv.getEmail(), zzax.zzmv.getPassword(), (zzdu) zzax.zzpq);
        } else {
            zzdp.zzeb().zza(zzax.zzmv, (zzdu) zzax.zzpq);
        }
    }
}
