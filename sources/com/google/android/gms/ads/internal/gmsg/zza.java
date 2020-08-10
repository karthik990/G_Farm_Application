package com.google.android.gms.ads.internal.gmsg;

import android.os.Bundle;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.mobiroller.constants.ChatConstants;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@zzadh
public final class zza implements zzv<Object> {
    private final zzb zzbll;

    public zza(zzb zzb) {
        this.zzbll = zzb;
    }

    private static Bundle zzar(String str) {
        if (str == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            Bundle bundle = new Bundle();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                Object obj = jSONObject.get(str2);
                if (obj != null) {
                    if (obj instanceof Boolean) {
                        bundle.putBoolean(str2, ((Boolean) obj).booleanValue());
                    } else if (obj instanceof Double) {
                        bundle.putDouble(str2, ((Double) obj).doubleValue());
                    } else if (obj instanceof Integer) {
                        bundle.putInt(str2, ((Integer) obj).intValue());
                    } else if (obj instanceof Long) {
                        bundle.putLong(str2, ((Long) obj).longValue());
                    } else if (obj instanceof String) {
                        bundle.putString(str2, (String) obj);
                    } else {
                        String str3 = "Unsupported type for key:";
                        String valueOf = String.valueOf(str2);
                        zzakb.zzdk(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                    }
                }
            }
            return bundle;
        } catch (JSONException e) {
            zzakb.zzb("Failed to convert ad metadata to JSON.", e);
            return null;
        }
    }

    public final void zza(Object obj, Map<String, String> map) {
        if (this.zzbll != null) {
            String str = (String) map.get(PostalAddressParser.USER_ADDRESS_NAME_KEY);
            if (str == null) {
                zzakb.zzdj("Ad metadata with no name parameter.");
                str = "";
            }
            Bundle zzar = zzar((String) map.get(ChatConstants.ARG_USER_INFO));
            if (zzar == null) {
                zzakb.m1272e("Failed to convert ad metadata to Bundle.");
            } else {
                this.zzbll.zza(str, zzar);
            }
        }
    }
}
