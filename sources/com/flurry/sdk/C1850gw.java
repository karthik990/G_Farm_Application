package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gw */
public final class C1850gw extends C1926jl {

    /* renamed from: a */
    public final int f1235a;

    public C1850gw(int i) {
        this.f1235a = i;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.error.count", this.f1235a);
        return a;
    }
}
