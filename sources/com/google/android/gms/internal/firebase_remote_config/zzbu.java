package com.google.android.gms.internal.firebase_remote_config;

import java.util.Locale;
import java.util.Map.Entry;

final class zzbu implements Entry<String, Object> {
    private Object zzfg;
    private final zzby zzfh;
    private final /* synthetic */ zzbt zzfi;

    zzbu(zzbt zzbt, zzby zzby, Object obj) {
        this.zzfi = zzbt;
        this.zzfh = zzby;
        this.zzfg = zzds.checkNotNull(obj);
    }

    public final Object getValue() {
        return this.zzfg;
    }

    public final Object setValue(Object obj) {
        Object obj2 = this.zzfg;
        this.zzfg = zzds.checkNotNull(obj);
        this.zzfh.zzb(this.zzfi.zzff, obj);
        return obj2;
    }

    public final int hashCode() {
        return ((String) getKey()).hashCode() ^ getValue().hashCode();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        return ((String) getKey()).equals(entry.getKey()) && getValue().equals(entry.getValue());
    }

    public final /* synthetic */ Object getKey() {
        String name = this.zzfh.getName();
        return this.zzfi.zzar.zzbw() ? name.toLowerCase(Locale.US) : name;
    }
}
