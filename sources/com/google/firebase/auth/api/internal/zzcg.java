package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzcg implements RemoteCall {
    private final zzch zznv;

    zzcg(zzch zzch) {
        this.zznv = zzch;
    }

    public final void accept(Object obj, Object obj2) {
        this.zznv.zzs((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
