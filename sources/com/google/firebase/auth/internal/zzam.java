package com.google.firebase.auth.internal;

import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.internal.firebase_auth.zzbl;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class zzam {
    private static final Logger zzjt = new Logger("JSONParser", new String[0]);

    public static Map<String, Object> zzdd(String str) {
        Preconditions.checkNotEmpty(str);
        List zza = com.google.android.gms.internal.firebase_auth.zzam.zzd('.').zza((CharSequence) str);
        if (zza.size() < 2) {
            Logger logger = zzjt;
            String str2 = "Invalid idToken ";
            String valueOf = String.valueOf(str);
            logger.mo26592e(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), new Object[0]);
            return Collections.EMPTY_MAP;
        }
        try {
            Map<String, Object> zzde = zzde(new String(Base64Utils.decodeUrlSafeNoPadding((String) zza.get(1)), "UTF-8"));
            if (zzde == null) {
                zzde = Collections.EMPTY_MAP;
            }
            return zzde;
        } catch (UnsupportedEncodingException e) {
            zzjt.mo26591e("Unable to decode token", e, new Object[0]);
            return Collections.EMPTY_MAP;
        }
    }

    public static Map<String, Object> zzde(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject != JSONObject.NULL) {
                return zzc(jSONObject);
            }
            return null;
        } catch (Exception e) {
            Log.d("JSONParser", "Failed to parse JSONObject into Map.");
            throw new zzbl((Throwable) e);
        }
    }

    private static Map<String, Object> zzc(JSONObject jSONObject) throws JSONException {
        ArrayMap arrayMap = new ArrayMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            Object obj = jSONObject.get(str);
            if (obj instanceof JSONArray) {
                obj = zza((JSONArray) obj);
            } else if (obj instanceof JSONObject) {
                obj = zzc((JSONObject) obj);
            }
            arrayMap.put(str, obj);
        }
        return arrayMap;
    }

    private static List<Object> zza(JSONArray jSONArray) throws JSONException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            Object obj = jSONArray.get(i);
            if (obj instanceof JSONArray) {
                obj = zza((JSONArray) obj);
            } else if (obj instanceof JSONObject) {
                obj = zzc((JSONObject) obj);
            }
            arrayList.add(obj);
        }
        return arrayList;
    }
}
