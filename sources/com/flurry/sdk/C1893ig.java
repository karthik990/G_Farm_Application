package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ig */
public final class C1893ig extends C1926jl {

    /* renamed from: a */
    public final boolean f1288a;

    public C1893ig(boolean z) {
        this.f1288a = z;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.ccpa.optout", this.f1288a);
        return a;
    }
}
