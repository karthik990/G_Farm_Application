package com.google.firebase.remoteconfig;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

final /* synthetic */ class zzb implements OnCompleteListener {
    private final FirebaseRemoteConfig zzjk;

    zzb(FirebaseRemoteConfig firebaseRemoteConfig) {
        this.zzjk = firebaseRemoteConfig;
    }

    public final void onComplete(Task task) {
        this.zzjk.zza(task);
    }
}
