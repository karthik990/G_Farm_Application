package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.he */
public final class C1861he extends C1926jl {

    /* renamed from: a */
    public final Map<String, Map<String, String>> f1254a;

    public C1861he(Map<String, Map<String, String>> map) {
        this.f1254a = new HashMap(map);
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        Map<String, Map<String, String>> map = this.f1254a;
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                for (Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put((String) entry2.getKey(), entry2.getValue());
                    jSONObject2 = jSONObject3;
                }
                jSONObject.put((String) entry.getKey(), jSONObject2);
            }
        }
        a.put("fl.session.property", jSONObject);
        return a;
    }
}
