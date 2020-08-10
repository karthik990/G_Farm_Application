package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.gtm.zza;
import java.util.Map;

final class zzl extends zzbq {

    /* renamed from: ID */
    private static final String f1584ID = zza.APP_VERSION_NAME.toString();
    private final Context zzrm;

    public zzl(Context context) {
        super(f1584ID, new String[0]);
        this.zzrm = context;
    }

    public final boolean zzgw() {
        return true;
    }

    public final com.google.android.gms.internal.gtm.zzl zzb(Map<String, com.google.android.gms.internal.gtm.zzl> map) {
        try {
            return zzgj.zzi(this.zzrm.getPackageManager().getPackageInfo(this.zzrm.getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            String packageName = this.zzrm.getPackageName();
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 25 + String.valueOf(message).length());
            sb.append("Package name ");
            sb.append(packageName);
            sb.append(" not found. ");
            sb.append(message);
            zzdi.zzav(sb.toString());
            return zzgj.zzkc();
        }
    }
}
