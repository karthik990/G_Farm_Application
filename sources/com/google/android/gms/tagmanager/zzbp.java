package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

final class zzbp extends zzbq {

    /* renamed from: ID */
    private static final String f1554ID = zza.EVENT.toString();
    private final zzfb zzaee;

    public zzbp(zzfb zzfb) {
        super(f1554ID, new String[0]);
        this.zzaee = zzfb;
    }

    public final boolean zzgw() {
        return false;
    }

    public final zzl zzb(Map<String, zzl> map) {
        String zzjf = this.zzaee.zzjf();
        if (zzjf == null) {
            return zzgj.zzkc();
        }
        return zzgj.zzi(zzjf);
    }
}
