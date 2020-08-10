package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class zzef extends zzbq {
    private static final String zzags = zzb.ARG0.toString();
    private static final String zzaio = zzb.ARG1.toString();

    public zzef(String str) {
        super(str, zzags, zzaio);
    }

    /* access modifiers changed from: protected */
    public abstract boolean zza(zzl zzl, zzl zzl2, Map<String, zzl> map);

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        Iterator it = map.values().iterator();
        do {
            boolean z = false;
            if (!it.hasNext()) {
                zzl zzl = (zzl) map.get(zzags);
                zzl zzl2 = (zzl) map.get(zzaio);
                if (!(zzl == null || zzl2 == null)) {
                    z = zza(zzl, zzl2, map);
                }
                return zzgj.zzi(Boolean.valueOf(z));
            }
        } while (((zzl) it.next()) != zzgj.zzkc());
        return zzgj.zzi(Boolean.valueOf(false));
    }

    public final /* bridge */ /* synthetic */ Set zzig() {
        return super.zzig();
    }

    public final /* bridge */ /* synthetic */ String zzif() {
        return super.zzif();
    }
}
