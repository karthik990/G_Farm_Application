package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzp.zzh;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.api.internal.zzfd;

public final class zzeq implements zzfd<zzh> {
    private String zzhy;
    private String zzib;
    private String zzif;
    private ActionCodeSettings zzkk;
    private String zzku;
    private String zzru;

    public zzeq(zzfw zzfw) {
        this.zzru = zzc(zzfw);
    }

    private zzeq(zzfw zzfw, ActionCodeSettings actionCodeSettings, String str, String str2, String str3, String str4) {
        this.zzru = zzc((zzfw) Preconditions.checkNotNull(zzfw));
        this.zzkk = (ActionCodeSettings) Preconditions.checkNotNull(actionCodeSettings);
        this.zzif = null;
        this.zzku = str2;
        this.zzib = str3;
        this.zzhy = null;
    }

    public static zzeq zza(ActionCodeSettings actionCodeSettings, String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(actionCodeSettings);
        zzeq zzeq = new zzeq(zzfw.VERIFY_AND_CHANGE_EMAIL, actionCodeSettings, null, str2, str, null);
        return zzeq;
    }

    public final zzeq zzcj(String str) {
        this.zzif = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzeq zzck(String str) {
        this.zzib = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzeq zza(ActionCodeSettings actionCodeSettings) {
        this.zzkk = (ActionCodeSettings) Preconditions.checkNotNull(actionCodeSettings);
        return this;
    }

    public final zzeq zzcl(String str) {
        this.zzhy = str;
        return this;
    }

    public final ActionCodeSettings zzdj() {
        return this.zzkk;
    }

    private static String zzc(zzfw zzfw) {
        int i = zzet.zzry[zzfw.ordinal()];
        if (i == 1) {
            return "PASSWORD_RESET";
        }
        if (i == 2) {
            return "VERIFY_EMAIL";
        }
        if (i != 3) {
            return i != 4 ? "REQUEST_TYPE_UNSET_ENUM_VALUE" : "VERIFY_BEFORE_UPDATE_EMAIL";
        }
        return "EMAIL_SIGNIN";
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ com.google.android.gms.internal.firebase_auth.zzjc zzeq() {
        /*
            r6 = this;
            com.google.android.gms.internal.firebase_auth.zzp$zzh$zza r0 = com.google.android.gms.internal.firebase_auth.zzp.zzh.zzaa()
            java.lang.String r1 = r6.zzru
            int r2 = r1.hashCode()
            r3 = 3
            r4 = 2
            r5 = 1
            switch(r2) {
                case -1452371317: goto L_0x002f;
                case -1341836234: goto L_0x0025;
                case -1288726400: goto L_0x001b;
                case 870738373: goto L_0x0011;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x0039
        L_0x0011:
            java.lang.String r2 = "EMAIL_SIGNIN"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0039
            r1 = 2
            goto L_0x003a
        L_0x001b:
            java.lang.String r2 = "VERIFY_BEFORE_UPDATE_EMAIL"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0039
            r1 = 3
            goto L_0x003a
        L_0x0025:
            java.lang.String r2 = "VERIFY_EMAIL"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0039
            r1 = 1
            goto L_0x003a
        L_0x002f:
            java.lang.String r2 = "PASSWORD_RESET"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0039
            r1 = 0
            goto L_0x003a
        L_0x0039:
            r1 = -1
        L_0x003a:
            if (r1 == 0) goto L_0x004e
            if (r1 == r5) goto L_0x004b
            if (r1 == r4) goto L_0x0048
            if (r1 == r3) goto L_0x0045
            com.google.android.gms.internal.firebase_auth.zzfw r1 = com.google.android.gms.internal.firebase_auth.zzfw.OOB_REQ_TYPE_UNSPECIFIED
            goto L_0x0050
        L_0x0045:
            com.google.android.gms.internal.firebase_auth.zzfw r1 = com.google.android.gms.internal.firebase_auth.zzfw.VERIFY_AND_CHANGE_EMAIL
            goto L_0x0050
        L_0x0048:
            com.google.android.gms.internal.firebase_auth.zzfw r1 = com.google.android.gms.internal.firebase_auth.zzfw.EMAIL_SIGNIN
            goto L_0x0050
        L_0x004b:
            com.google.android.gms.internal.firebase_auth.zzfw r1 = com.google.android.gms.internal.firebase_auth.zzfw.VERIFY_EMAIL
            goto L_0x0050
        L_0x004e:
            com.google.android.gms.internal.firebase_auth.zzfw r1 = com.google.android.gms.internal.firebase_auth.zzfw.PASSWORD_RESET
        L_0x0050:
            com.google.android.gms.internal.firebase_auth.zzp$zzh$zza r0 = r0.zza(r1)
            java.lang.String r1 = r6.zzif
            if (r1 == 0) goto L_0x005b
            r0.zzp(r1)
        L_0x005b:
            java.lang.String r1 = r6.zzku
            if (r1 == 0) goto L_0x0062
            r0.zzq(r1)
        L_0x0062:
            java.lang.String r1 = r6.zzib
            if (r1 == 0) goto L_0x0069
            r0.zzr(r1)
        L_0x0069:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            if (r1 == 0) goto L_0x00e4
            boolean r1 = r1.getAndroidInstallApp()
            com.google.android.gms.internal.firebase_auth.zzp$zzh$zza r1 = r0.zza(r1)
            com.google.firebase.auth.ActionCodeSettings r2 = r6.zzkk
            boolean r2 = r2.canHandleCodeInApp()
            r1.zzb(r2)
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.getUrl()
            if (r1 == 0) goto L_0x008f
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.getUrl()
            r0.zzs(r1)
        L_0x008f:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.getIOSBundle()
            if (r1 == 0) goto L_0x00a0
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.getIOSBundle()
            r0.zzt(r1)
        L_0x00a0:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.zzck()
            if (r1 == 0) goto L_0x00b1
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.zzck()
            r0.zzu(r1)
        L_0x00b1:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.getAndroidPackageName()
            if (r1 == 0) goto L_0x00c2
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.getAndroidPackageName()
            r0.zzv(r1)
        L_0x00c2:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.getAndroidMinimumVersion()
            if (r1 == 0) goto L_0x00d3
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.getAndroidMinimumVersion()
            r0.zzw(r1)
        L_0x00d3:
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.zzcm()
            if (r1 == 0) goto L_0x00e4
            com.google.firebase.auth.ActionCodeSettings r1 = r6.zzkk
            java.lang.String r1 = r1.zzcm()
            r0.zzy(r1)
        L_0x00e4:
            java.lang.String r1 = r6.zzhy
            if (r1 == 0) goto L_0x00eb
            r0.zzx(r1)
        L_0x00eb:
            com.google.android.gms.internal.firebase_auth.zzjc r0 = r0.zzih()
            com.google.android.gms.internal.firebase_auth.zzhs r0 = (com.google.android.gms.internal.firebase_auth.zzhs) r0
            com.google.android.gms.internal.firebase_auth.zzp$zzh r0 = (com.google.android.gms.internal.firebase_auth.zzp.zzh) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzeq.zzeq():com.google.android.gms.internal.firebase_auth.zzjc");
    }
}
