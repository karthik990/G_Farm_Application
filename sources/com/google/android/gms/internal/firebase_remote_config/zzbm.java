package com.google.android.gms.internal.firebase_remote_config;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

public final class zzbm {
    private final Map<String, zzbn> zzeg = new zzbl();
    private final Map<Field, zzbn> zzeh = new zzbl();
    private final Object zzei;

    public zzbm(Object obj) {
        this.zzei = obj;
    }

    public final void zzbu() {
        for (Entry entry : this.zzeg.entrySet()) {
            ((Map) this.zzei).put((String) entry.getKey(), ((zzbn) entry.getValue()).zzbv());
        }
        for (Entry entry2 : this.zzeh.entrySet()) {
            zzby.zza((Field) entry2.getKey(), this.zzei, ((zzbn) entry2.getValue()).zzbv());
        }
    }

    public final void zza(Field field, Class<?> cls, Object obj) {
        zzbn zzbn = (zzbn) this.zzeh.get(field);
        if (zzbn == null) {
            zzbn = new zzbn(cls);
            this.zzeh.put(field, zzbn);
        }
        if (cls == zzbn.zzej) {
            zzbn.zzek.add(obj);
            return;
        }
        throw new IllegalArgumentException();
    }
}
