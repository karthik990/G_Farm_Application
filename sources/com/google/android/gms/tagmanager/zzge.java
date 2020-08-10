package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzge extends zzbq {

    /* renamed from: ID */
    private static final String f1577ID = zza.TIME.toString();

    public zzge() {
        super(f1577ID, new String[0]);
    }

    public final boolean zzgw() {
        return false;
    }

    public final zzl zzb(Map<String, zzl> map) {
        return zzgj.zzi(Long.valueOf(System.currentTimeMillis()));
    }
}
