package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzj extends zzbq {

    /* renamed from: ID */
    private static final String f1582ID = zza.APP_NAME.toString();
    private final Context zzrm;

    public zzj(Context context) {
        super(f1582ID, new String[0]);
        this.zzrm = context;
    }

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        try {
            PackageManager packageManager = this.zzrm.getPackageManager();
            return zzgj.zzi(packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.zzrm.getPackageName(), 0)).toString());
        } catch (NameNotFoundException e) {
            zzdi.zza("App name is not found.", e);
            return zzgj.zzkc();
        }
    }
}
