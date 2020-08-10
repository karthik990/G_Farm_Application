package com.google.android.gms.internal.firebase_remote_config;

import java.util.AbstractMap;
import java.util.Set;

final class zzbt extends AbstractMap<String, Object> {
    final zzbq zzar;
    final Object zzff;

    zzbt(Object obj, boolean z) {
        this.zzff = obj;
        this.zzar = zzbq.zza(obj.getClass(), z);
        if (!(!this.zzar.isEnum())) {
            throw new IllegalArgumentException();
        }
    }

    public final boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    public final Object get(Object obj) {
        if (!(obj instanceof String)) {
            return null;
        }
        zzby zzae = this.zzar.zzae((String) obj);
        if (zzae == null) {
            return null;
        }
        return zzae.zzh(this.zzff);
    }

    public final /* synthetic */ Set entrySet() {
        return new zzbw(this);
    }

    public final /* synthetic */ Object put(Object obj, Object obj2) {
        String str = (String) obj;
        zzby zzae = this.zzar.zzae(str);
        String valueOf = String.valueOf(str);
        String str2 = "no field of key ";
        String concat = valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2);
        if (zzae != null) {
            Object zzh = zzae.zzh(this.zzff);
            zzae.zzb(this.zzff, zzds.checkNotNull(obj2));
            return zzh;
        }
        throw new NullPointerException(String.valueOf(concat));
    }
}
