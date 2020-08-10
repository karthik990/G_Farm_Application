package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.je */
public final class C1918je extends C1926jl {

    /* renamed from: a */
    public final Map<C1544al, String> f1311a;

    /* renamed from: b */
    public final boolean f1312b;

    public C1918je(Map<C1544al, String> map, boolean z) {
        this.f1311a = new HashMap(map);
        this.f1312b = z;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : this.f1311a.entrySet()) {
            jSONObject.put(((C1544al) entry.getKey()).name(), entry.getValue());
        }
        a.put("fl.reported.id", jSONObject);
        a.put("fl.ad.tracking", this.f1312b);
        return a;
    }
}
