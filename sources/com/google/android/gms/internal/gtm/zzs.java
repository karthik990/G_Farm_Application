package com.google.android.gms.internal.gtm;

import com.google.android.gms.analytics.zzi;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zzs extends zzi<zzs> {
    private Map<Integer, String> zzuh = new HashMap(4);

    public final String toString() {
        HashMap hashMap = new HashMap();
        for (Entry entry : this.zzuh.entrySet()) {
            String valueOf = String.valueOf(entry.getKey());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 9);
            sb.append("dimension");
            sb.append(valueOf);
            hashMap.put(sb.toString(), entry.getValue());
        }
        return zza((Object) hashMap);
    }

    public final Map<Integer, String> zzbk() {
        return Collections.unmodifiableMap(this.zzuh);
    }

    public final /* synthetic */ void zzb(zzi zzi) {
        ((zzs) zzi).zzuh.putAll(this.zzuh);
    }
}
