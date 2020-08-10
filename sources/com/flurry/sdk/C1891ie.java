package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ie */
public final class C1891ie extends C1926jl {

    /* renamed from: a */
    public final int f1286a;

    /* renamed from: b */
    public final int f1287b;

    public C1891ie(int i, int i2) {
        if (i < 0) {
            i = C1950p.UNKNOWN.f1435d;
        }
        this.f1287b = i;
        if (i2 < 0) {
            i2 = C1950p.UNKNOWN.f1435d;
        }
        this.f1286a = i2;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.app.current.state", this.f1286a);
        a.put("fl.app.previous.state", this.f1287b);
        return a;
    }
}
