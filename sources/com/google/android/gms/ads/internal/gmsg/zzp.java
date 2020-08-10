package com.google.android.gms.ads.internal.gmsg;

import com.google.android.gms.internal.ads.zzakb;
import java.util.Map;

final class zzp implements zzv<Object> {
    zzp() {
    }

    public final void zza(Object obj, Map<String, String> map) {
        String valueOf = String.valueOf((String) map.get("string"));
        String str = "Received log message: ";
        zzakb.zzdj(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
