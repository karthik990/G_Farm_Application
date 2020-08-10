package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ja */
public final class C1914ja extends C1926jl {

    /* renamed from: a */
    public final boolean f1309a;

    public C1914ja(boolean z) {
        this.f1309a = z;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.report.previous.success", this.f1309a);
        return a;
    }
}
