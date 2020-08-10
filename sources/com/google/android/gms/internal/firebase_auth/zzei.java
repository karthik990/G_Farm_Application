package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzlg.zza;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzei implements zzfd<zza> {
    private String zzri;
    private String zzrj;
    private final String zzrk;

    public zzei(String str) {
        this(str, null);
    }

    private zzei(String str, String str2) {
        this.zzri = zzej.REFRESH_TOKEN.toString();
        this.zzrj = Preconditions.checkNotEmpty(str);
        this.zzrk = null;
    }

    public final /* synthetic */ zzjc zzeq() {
        return (zza) ((zzhs) zza.zzkz().zzdn(this.zzri).zzdo(this.zzrj).zzih());
    }
}
