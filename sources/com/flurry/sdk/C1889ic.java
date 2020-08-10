package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ic */
public final class C1889ic extends C1926jl {

    /* renamed from: a */
    public final int f1285a;

    public C1889ic(int i) {
        if (i < 0) {
            i = 0;
        }
        this.f1285a = i;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.app.orientation", this.f1285a);
        return a;
    }
}
