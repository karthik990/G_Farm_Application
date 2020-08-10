package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzaf implements OnFailureListener {
    private final /* synthetic */ TaskCompletionSource zzuc;

    zzaf(zzac zzac, TaskCompletionSource taskCompletionSource) {
        this.zzuc = taskCompletionSource;
    }

    public final void onFailure(Exception exc) {
        this.zzuc.setException(exc);
        zzac.zzfl();
    }
}
