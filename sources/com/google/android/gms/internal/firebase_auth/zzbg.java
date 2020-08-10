package com.google.android.gms.internal.firebase_auth;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

final class zzbg extends zzay<Entry<K, V>> {
    private final /* synthetic */ zzbd zzhj;

    zzbg(zzbd zzbd) {
        this.zzhj = zzbd;
    }

    public final int size() {
        return this.zzhj.size;
    }

    public final /* synthetic */ Object get(int i) {
        zzaj.zza(i, this.zzhj.size);
        Object[] zzb = this.zzhj.zzhf;
        int i2 = i * 2;
        zzbd zzbd = this.zzhj;
        Object obj = zzb[i2];
        Object[] zzb2 = zzbd.zzhf;
        zzbd zzbd2 = this.zzhj;
        return new SimpleImmutableEntry(obj, zzb2[i2 + 1]);
    }
}
