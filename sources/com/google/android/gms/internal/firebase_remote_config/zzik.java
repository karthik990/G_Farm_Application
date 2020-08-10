package com.google.android.gms.internal.firebase_remote_config;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class zzik implements zzij {
    zzik() {
    }

    public final Map<?, ?> zzq(Object obj) {
        return (zzii) obj;
    }

    public final zzih<?, ?> zzv(Object obj) {
        throw new NoSuchMethodError();
    }

    public final Map<?, ?> zzr(Object obj) {
        return (zzii) obj;
    }

    public final boolean zzs(Object obj) {
        return !((zzii) obj).isMutable();
    }

    public final Object zzt(Object obj) {
        ((zzii) obj).zzer();
        return obj;
    }

    public final Object zzu(Object obj) {
        return zzii.zzhx().zzhy();
    }

    public final Object zzd(Object obj, Object obj2) {
        zzii zzii = (zzii) obj;
        zzii zzii2 = (zzii) obj2;
        if (!zzii2.isEmpty()) {
            if (!zzii.isMutable()) {
                zzii = zzii.zzhy();
            }
            zzii.zza(zzii2);
        }
        return zzii;
    }

    public final int zzc(int i, Object obj, Object obj2) {
        zzii zzii = (zzii) obj;
        if (zzii.isEmpty()) {
            return 0;
        }
        Iterator it = zzii.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Entry entry = (Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
