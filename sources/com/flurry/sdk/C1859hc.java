package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.hc */
public final class C1859hc extends C1926jl {

    /* renamed from: a */
    public final String f1252a;

    /* renamed from: b */
    public final String f1253b;

    public C1859hc(String str, String str2) {
        String str3 = "";
        if (str == null) {
            str = str3;
        }
        this.f1252a = str;
        if (str2 == null) {
            str2 = str3;
        }
        this.f1253b = str2;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.session.property.param.name", this.f1252a);
        a.put("fl.session.property.param.value", this.f1253b);
        return a;
    }
}
