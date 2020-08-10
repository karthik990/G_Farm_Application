package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.iq */
public final class C1903iq extends C1926jl {

    /* renamed from: a */
    public final long f1297a;

    public C1903iq(long j) {
        this.f1297a = j;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.frame.log.counter", this.f1297a);
        return a;
    }
}
