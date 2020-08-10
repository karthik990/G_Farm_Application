package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ge */
public final class C1827ge extends C1926jl {

    /* renamed from: a */
    public final Long f1172a;

    public C1827ge(Long l) {
        this.f1172a = l;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        if (this.f1172a.longValue() != Long.MIN_VALUE) {
            a.put("fl.demo.birthdate", this.f1172a);
        }
        return a;
    }
}
