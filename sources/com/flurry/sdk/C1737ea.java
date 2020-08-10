package com.flurry.sdk;

import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ea */
public final class C1737ea {
    /* renamed from: a */
    public static JSONObject m878a(Map<String, String> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
        }
        return jSONObject;
    }
}
