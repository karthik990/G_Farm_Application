package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.jl */
public abstract class C1926jl implements C1929jo {

    /* renamed from: o */
    protected int f1341o = 1;

    /* renamed from: a */
    public JSONObject mo16502a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("fl.frame.version", this.f1341o);
        return jSONObject;
    }
}
