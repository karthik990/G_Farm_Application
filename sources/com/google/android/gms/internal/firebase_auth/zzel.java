package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp.zzf;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzel implements zzfd<zzf> {
    private String zzib;

    public zzel(String str) {
        this.zzib = Preconditions.checkNotEmpty(str);
    }

    public final /* synthetic */ zzjc zzeq() {
        return (zzf) ((zzhs) zzf.zzw().zzo(this.zzib).zzih());
    }
}
