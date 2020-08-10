package com.flurry.sdk;

import com.braintreepayments.api.models.BinData;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.io */
public final class C1901io extends C1926jl {

    /* renamed from: a */
    public int f1295a = 0;

    /* renamed from: b */
    public String f1296b = BinData.UNKNOWN;

    public C1901io(int i, String str) {
        this.f1295a = i;
        this.f1296b = str;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.flush.frame.code", this.f1295a);
        a.put("fl.flush.frame.reason", this.f1296b);
        return a;
    }
}
