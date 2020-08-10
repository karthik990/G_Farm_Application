package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.go */
public final class C1842go extends C1926jl {

    /* renamed from: a */
    public final int f1222a;

    /* renamed from: b */
    public final boolean f1223b;

    public C1842go(int i, boolean z) {
        this.f1222a = i;
        this.f1223b = z;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.event.count", this.f1222a);
        a.put("fl.event.set.complete", this.f1223b);
        return a;
    }
}
