package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzf extends zzbq {

    /* renamed from: ID */
    private static final String f1573ID = zza.ADVERTISING_TRACKING_ENABLED.toString();
    private final zza zzadt;

    public zzf(Context context) {
        this(zza.zzf(context));
    }

    public final boolean zzgw() {
        return false;
    }

    private zzf(zza zza) {
        super(f1573ID, new String[0]);
        this.zzadt = zza;
    }

    public final zzl zzb(Map<String, zzl> map) {
        return zzgj.zzi(Boolean.valueOf(!this.zzadt.isLimitAdTrackingEnabled()));
    }
}
