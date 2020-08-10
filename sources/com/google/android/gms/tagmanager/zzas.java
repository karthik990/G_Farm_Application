package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzas extends zzbq {

    /* renamed from: ID */
    private static final String f1547ID = zza.CUSTOM_VAR.toString();
    private static final String NAME = zzb.NAME.toString();
    private static final String zzafw = zzb.DEFAULT_VALUE.toString();
    private final DataLayer zzaed;

    public zzas(DataLayer dataLayer) {
        super(f1547ID, NAME);
        this.zzaed = dataLayer;
    }

    public final boolean zzgw() {
        return false;
    }

    public final zzl zzb(Map<String, zzl> map) {
        Object obj = this.zzaed.get(zzgj.zzc((zzl) map.get(NAME)));
        if (obj != null) {
            return zzgj.zzi(obj);
        }
        zzl zzl = (zzl) map.get(zzafw);
        if (zzl != null) {
            return zzl;
        }
        return zzgj.zzkc();
    }
}
