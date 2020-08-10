package com.google.android.gms.internal.ads;

import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import com.google.android.gms.ads.internal.gmsg.zzv;
import java.util.Map;

public final class zzafi implements zzv<Object> {
    public final void zza(Object obj, Map<String, String> map) {
        String str = (String) map.get("request_id");
        String valueOf = String.valueOf((String) map.get(Keys.ERRORS));
        String str2 = "Invalid request: ";
        zzakb.zzdk(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        zzafa.zzcgg.zzat(str);
    }
}
