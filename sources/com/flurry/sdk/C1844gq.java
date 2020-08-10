package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gq */
public final class C1844gq extends C1926jl {

    /* renamed from: a */
    public final int f1224a;

    public C1844gq(int i) {
        this.f1224a = i;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        int i = this.f1224a;
        if (i != Integer.MIN_VALUE) {
            a.put("fl.demo.gender", i);
        }
        return a;
    }
}
