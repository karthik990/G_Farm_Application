package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ik */
public final class C1897ik extends C1926jl {

    /* renamed from: a */
    public final Map<String, String> f1291a;

    public C1897ik(Map<String, String> map) {
        this.f1291a = new HashMap(map);
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.device.properties", C1737ea.m878a(this.f1291a));
        return a;
    }
}
