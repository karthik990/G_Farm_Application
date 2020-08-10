package com.google.android.gms.internal.gtm;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class zzsg implements zzsf {
    zzsg() {
    }

    public final Map<?, ?> zzx(Object obj) {
        return (zzse) obj;
    }

    public final zzsd<?, ?> zzac(Object obj) {
        throw new NoSuchMethodError();
    }

    public final Map<?, ?> zzy(Object obj) {
        return (zzse) obj;
    }

    public final boolean zzz(Object obj) {
        return !((zzse) obj).isMutable();
    }

    public final Object zzaa(Object obj) {
        ((zzse) obj).zzmi();
        return obj;
    }

    public final Object zzab(Object obj) {
        return zzse.zzqf().zzqg();
    }

    public final Object zzc(Object obj, Object obj2) {
        zzse zzse = (zzse) obj;
        zzse zzse2 = (zzse) obj2;
        if (!zzse2.isEmpty()) {
            if (!zzse.isMutable()) {
                zzse = zzse.zzqg();
            }
            zzse.zza(zzse2);
        }
        return zzse;
    }

    public final int zzb(int i, Object obj, Object obj2) {
        zzse zzse = (zzse) obj;
        if (zzse.isEmpty()) {
            return 0;
        }
        Iterator it = zzse.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Entry entry = (Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
