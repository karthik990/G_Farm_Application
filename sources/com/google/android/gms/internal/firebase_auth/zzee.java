package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp.zzc;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzee implements zzfd<zzc> {
    private String zzib;

    public zzee(String str) {
        this.zzib = Preconditions.checkNotEmpty(str);
    }

    public final /* synthetic */ zzjc zzeq() {
        return (zzc) ((zzhs) zzc.zzo().zzg(this.zzib).zzih());
    }
}
