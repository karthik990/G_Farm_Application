package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzaq implements RemoteCall {
    private final zzar zzmo;

    zzaq(zzar zzar) {
        this.zzmo = zzar;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzmo.zza((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
