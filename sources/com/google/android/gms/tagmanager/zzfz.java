package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;

abstract class zzfz extends zzef {
    public zzfz(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public abstract boolean zza(String str, String str2, Map<String, zzl> map);

    /* access modifiers changed from: protected */
    public final boolean zza(zzl zzl, zzl zzl2, Map<String, zzl> map) {
        String zzc = zzgj.zzc(zzl);
        String zzc2 = zzgj.zzc(zzl2);
        if (zzc == zzgj.zzkb() || zzc2 == zzgj.zzkb()) {
            return false;
        }
        return zza(zzc, zzc2, map);
    }
}
