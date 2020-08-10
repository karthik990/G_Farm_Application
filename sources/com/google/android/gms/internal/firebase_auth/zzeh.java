package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.internal.firebase_auth.zzp.zzd;
import com.google.android.gms.internal.firebase_auth.zzp.zzd.zza;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.api.internal.zzfd;
import com.google.firebase.auth.zzb;

public final class zzeh implements zzfd<zzd> {
    private static final Logger zzjt = new Logger("EmailLinkSignInRequest", new String[0]);
    private final String zzib;
    private final String zzif;
    private final String zzih;

    public zzeh(EmailAuthCredential emailAuthCredential, String str) {
        this.zzif = Preconditions.checkNotEmpty(emailAuthCredential.getEmail());
        this.zzih = Preconditions.checkNotEmpty(emailAuthCredential.zzco());
        this.zzib = str;
    }

    public final /* synthetic */ zzjc zzeq() {
        zza zzj = zzd.zzq().zzj(this.zzif);
        zzb zzbr = zzb.zzbr(this.zzih);
        String str = null;
        String zzcn = zzbr != null ? zzbr.zzcn() : null;
        if (zzbr != null) {
            str = zzbr.zzba();
        }
        if (zzcn != null) {
            zzj.zzi(zzcn);
        }
        if (str != null) {
            zzj.zzl(str);
        }
        String str2 = this.zzib;
        if (str2 != null) {
            zzj.zzk(str2);
        }
        return (zzd) ((zzhs) zzj.zzih());
    }
}
