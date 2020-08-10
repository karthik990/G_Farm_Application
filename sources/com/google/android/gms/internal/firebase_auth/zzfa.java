package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp.zzi;
import com.google.android.gms.internal.firebase_auth.zzp.zzi.zza;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzfa implements zzfd<zzi> {
    private final String zzhu;
    private final String zzhy;
    private final String zzkd;

    public zzfa(String str, String str2, String str3) {
        this.zzhu = Preconditions.checkNotEmpty(str);
        this.zzkd = str2;
        this.zzhy = str3;
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzag = zzi.zzac().zzag(this.zzhu);
        String str = this.zzkd;
        if (str != null) {
            zzag.zzah(str);
        }
        String str2 = this.zzhy;
        if (str2 != null) {
            zzag.zzai(str2);
        }
        return (zzi) ((zzhs) zzag.zzih());
    }
}
