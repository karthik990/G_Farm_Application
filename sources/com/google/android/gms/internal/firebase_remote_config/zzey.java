package com.google.android.gms.internal.firebase_remote_config;

import com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public final class zzey implements FirebaseRemoteConfigInfo {
    private final long zzlm;
    private final int zzln;
    private final FirebaseRemoteConfigSettings zzlo;

    private zzey(long j, int i, FirebaseRemoteConfigSettings firebaseRemoteConfigSettings) {
        this.zzlm = j;
        this.zzln = i;
        this.zzlo = firebaseRemoteConfigSettings;
    }

    public final long getFetchTimeMillis() {
        return this.zzlm;
    }

    public final int getLastFetchStatus() {
        return this.zzln;
    }

    public final FirebaseRemoteConfigSettings getConfigSettings() {
        return this.zzlo;
    }
}
