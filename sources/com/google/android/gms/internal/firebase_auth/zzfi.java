package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp.zzn;
import com.google.android.gms.internal.firebase_auth.zzp.zzn.zza;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzfi implements zzfd<zzn> {
    private String zzhy;
    private String zzif;
    private String zzig;
    private String zzjv;

    public zzfi(String str) {
        this.zzhy = str;
    }

    public zzfi(String str, String str2, String str3, String str4) {
        this.zzif = Preconditions.checkNotEmpty(str);
        this.zzig = Preconditions.checkNotEmpty(str2);
        this.zzjv = null;
        this.zzhy = str4;
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzaq = zzn.zzaq();
        String str = this.zzif;
        if (str != null) {
            zzaq.zzaw(str);
        }
        String str2 = this.zzig;
        if (str2 != null) {
            zzaq.zzax(str2);
        }
        String str3 = this.zzhy;
        if (str3 != null) {
            zzaq.zzay(str3);
        }
        return (zzn) ((zzhs) zzaq.zzih());
    }
}
