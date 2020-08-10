package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.im */
public final class C1899im extends C1926jl {

    /* renamed from: a */
    public final boolean f1293a = true;

    /* renamed from: b */
    public final String f1294b;

    public C1899im(String str) {
        this.f1294b = str;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.background.enabled", this.f1293a);
        a.put("fl.sdk.version.code", this.f1294b);
        return a;
    }
}
