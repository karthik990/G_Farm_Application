package com.google.firebase.auth.api.internal;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class zzdc implements RemoteCall {
    private final zzdd zzol;

    zzdc(zzdd zzdd) {
        this.zzol = zzdd;
    }

    public final void accept(Object obj, Object obj2) {
        this.zzol.zzac((zzdp) obj, (TaskCompletionSource) obj2);
    }
}
