package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.iy */
public final class C1911iy extends C1926jl {

    /* renamed from: a */
    public final String f1306a;

    /* renamed from: b */
    public final Map<String, String> f1307b;

    public C1911iy(String str, Map<String, String> map) {
        this.f1306a = str;
        if (map == null) {
            map = new HashMap<>();
        }
        this.f1307b = map;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        JSONObject a2 = C1737ea.m878a(this.f1307b);
        a.put("fl.origin.attribute.name", this.f1306a);
        a.put("fl.origin.attribute.parameters", a2);
        return a;
    }
}
