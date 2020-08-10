package com.google.firebase.remoteconfig;

import com.google.android.gms.internal.firebase_remote_config.zzeo;
import com.google.android.gms.tasks.OnSuccessListener;

final /* synthetic */ class zza implements OnSuccessListener {
    private final FirebaseRemoteConfig zzjk;

    zza(FirebaseRemoteConfig firebaseRemoteConfig) {
        this.zzjk = firebaseRemoteConfig;
    }

    public final void onSuccess(Object obj) {
        this.zzjk.zza((zzeo) obj);
    }
}
