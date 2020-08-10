package com.google.firebase.auth;

import android.net.Uri;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_auth.zzaz;
import java.util.Set;

public final class zzb {
    private static final zzaz<String, Integer> zzhz = zzaz.zzb("recoverEmail", Integer.valueOf(2), "resetPassword", Integer.valueOf(0), "signIn", Integer.valueOf(4), "verifyEmail", Integer.valueOf(1));
    private final String zzht;
    private final String zzhu;
    private final String zzhv;
    private final String zzhw;
    private final String zzhx;
    private final String zzhy;

    private zzb(String str) {
        String str2 = "apiKey";
        this.zzht = zza(str, str2);
        String str3 = "oobCode";
        this.zzhu = zza(str, str3);
        String str4 = "mode";
        this.zzhv = zza(str, str4);
        if (this.zzht == null || this.zzhu == null || this.zzhv == null) {
            throw new IllegalArgumentException(String.format("%s, %s and %s are required in a valid action code URL", new Object[]{str2, str3, str4}));
        }
        this.zzhw = zza(str, "continueUrl");
        this.zzhx = zza(str, "languageCode");
        this.zzhy = zza(str, "tenantId");
    }

    public static zzb zzbr(String str) {
        Preconditions.checkNotEmpty(str);
        try {
            return new zzb(str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public final int getOperation() {
        return ((Integer) zzhz.getOrDefault(this.zzhv, Integer.valueOf(3))).intValue();
    }

    public final String zzcn() {
        return this.zzhu;
    }

    public final String zzba() {
        return this.zzhy;
    }

    private static String zza(String str, String str2) {
        String str3 = "link";
        Uri parse = Uri.parse(str);
        try {
            Set queryParameterNames = parse.getQueryParameterNames();
            if (queryParameterNames.contains(str2)) {
                return parse.getQueryParameter(str2);
            }
            if (queryParameterNames.contains(str3)) {
                return Uri.parse(parse.getQueryParameter(str3)).getQueryParameter(str2);
            }
            return null;
        } catch (UnsupportedOperationException unused) {
        }
    }
}
