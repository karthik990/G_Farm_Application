package com.google.firebase.auth.internal;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzab implements OnFailureListener {
    private final /* synthetic */ TaskCompletionSource zzuc;

    zzab(zzac zzac, TaskCompletionSource taskCompletionSource) {
        this.zzuc = taskCompletionSource;
    }

    public final void onFailure(Exception exc) {
        this.zzuc.setException(exc);
        zzac.zzfl();
    }
}
