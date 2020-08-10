package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzde implements RemoteCall {
    private final zzdf zzom;

    zzde(zzdf zzdf) {
        this.zzom = zzdf;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzom.zzad((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
