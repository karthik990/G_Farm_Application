package com.flurry.sdk;

import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ii */
public final class C1895ii extends C1926jl {

    /* renamed from: a */
    public boolean f1289a;

    /* renamed from: b */
    public Map<String, String> f1290b;

    public C1895ii(boolean z, Map<String, String> map) {
        this.f1289a = z;
        this.f1290b = map;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.consent.isGdprScope", this.f1289a);
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : this.f1290b.entrySet()) {
            jSONObject.put((String) entry.getKey(), entry.getValue());
        }
        a.put("fl.consent.strings", jSONObject);
        return a;
    }
}
