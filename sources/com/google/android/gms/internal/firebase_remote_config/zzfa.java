package com.google.android.gms.internal.firebase_remote_config;

import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public final class zzfa {
    private long zzlp;
    private int zzlq;
    private FirebaseRemoteConfigSettings zzlr;

    private zzfa() {
    }

    public final zzfa zzc(long j) {
        this.zzlp = j;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public final zzfa zzn(int i) {
        this.zzlq = i;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public final zzfa zza(FirebaseRemoteConfigSettings firebaseRemoteConfigSettings) {
        this.zzlr = firebaseRemoteConfigSettings;
        return this;
    }

    public final zzey zzdd() {
        zzey zzey = new zzey(this.zzlp, this.zzlq, this.zzlr);
        return zzey;
    }
}
