package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzl;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class zzbq {
    private final Set<String> zzagw;
    private final String zzqr;

    public abstract zzl zzb(Map<String, zzl> map);

    public abstract boolean zzgw();

    public String zzif() {
        return this.zzqr;
    }

    public zzbq(String str, String... strArr) {
        this.zzqr = str;
        this.zzagw = new HashSet(strArr.length);
        for (String add : strArr) {
            this.zzagw.add(add);
        }
    }

    public Set<String> zzig() {
        return this.zzagw;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zza(Set<String> set) {
        return set.containsAll(this.zzagw);
    }
}
