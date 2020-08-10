package com.flurry.sdk;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.is */
public final class C1905is extends C1926jl {

    /* renamed from: a */
    public final String f1298a;

    /* renamed from: b */
    public final List<String> f1299b;

    public C1905is(String str, List<String> list) {
        this.f1298a = str;
        this.f1299b = list;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        JSONArray jSONArray = new JSONArray();
        for (String put : this.f1299b) {
            jSONArray.put(put);
        }
        a.put("fl.launch.options.key", this.f1298a);
        a.put("fl.launch.options.values", jSONArray);
        return a;
    }
}
