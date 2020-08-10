package com.google.firebase.auth;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final class zzr implements Continuation<GetTokenResult, Task<Void>> {
    private final /* synthetic */ FirebaseUser zzjg;
    private final /* synthetic */ ActionCodeSettings zzjh;

    zzr(FirebaseUser firebaseUser, ActionCodeSettings actionCodeSettings) {
        this.zzjg = firebaseUser;
        this.zzjh = actionCodeSettings;
    }

    public final /* synthetic */ Object then(Task task) throws Exception {
        return FirebaseAuth.getInstance(this.zzjg.zzcu()).zza(this.zzjh, ((GetTokenResult) task.getResult()).getToken());
    }
}
