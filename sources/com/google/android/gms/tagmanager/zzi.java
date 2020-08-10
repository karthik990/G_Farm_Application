package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzi extends zzbq {

    /* renamed from: ID */
    private static final String f1581ID = zza.APP_ID.toString();
    private final Context zzrm;

    public zzi(Context context) {
        super(f1581ID, new String[0]);
        this.zzrm = context;
    }

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        return zzgj.zzi(this.zzrm.getPackageName());
    }
}
