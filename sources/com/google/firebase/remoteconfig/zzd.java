package com.google.firebase.remoteconfig;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

final /* synthetic */ class zzd implements OnCompleteListener {
    private final FirebaseRemoteConfig zzjk;

    zzd(FirebaseRemoteConfig firebaseRemoteConfig) {
        this.zzjk = firebaseRemoteConfig;
    }

    public final void onComplete(Task task) {
        this.zzjk.zza(task);
    }
}
