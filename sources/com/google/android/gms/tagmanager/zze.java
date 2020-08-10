package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zze extends zzbq {

    /* renamed from: ID */
    private static final String f1566ID = zza.ADVERTISER_ID.toString();
    private final zza zzadt;

    public zze(Context context) {
        this(zza.zzf(context));
    }

    public final boolean zzgw() {
        return false;
    }

    private zze(zza zza) {
        super(f1566ID, new String[0]);
        this.zzadt = zza;
        this.zzadt.zzgq();
    }

    public final zzl zzb(Map<String, zzl> map) {
        String zzgq = this.zzadt.zzgq();
        return zzgq == null ? zzgj.zzkc() : zzgj.zzi(zzgq);
    }
}
