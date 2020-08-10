package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.util.List;
import java.util.Map;

final class zzaz extends zzgh {

    /* renamed from: ID */
    private static final String f1548ID = zza.DATA_LAYER_WRITE.toString();
    private static final String VALUE = zzb.VALUE.toString();
    private static final String zzagi = zzb.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    private final DataLayer zzaed;

    public zzaz(DataLayer dataLayer) {
        super(f1548ID, VALUE);
        this.zzaed = dataLayer;
    }

    public final void zzd(Map<String, zzl> map) {
        zzl zzl = (zzl) map.get(VALUE);
        if (!(zzl == null || zzl == zzgj.zzjw())) {
            Object zzh = zzgj.zzh(zzl);
            if (zzh instanceof List) {
                for (Object next : (List) zzh) {
                    if (next instanceof Map) {
                        this.zzaed.push((Map) next);
                    }
                }
            }
        }
        zzl zzl2 = (zzl) map.get(zzagi);
        if (zzl2 != null && zzl2 != zzgj.zzjw()) {
            String zzc = zzgj.zzc(zzl2);
            if (zzc != zzgj.zzkb()) {
                this.zzaed.zzaq(zzc);
            }
        }
    }
}
