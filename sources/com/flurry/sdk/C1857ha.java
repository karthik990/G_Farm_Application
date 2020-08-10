package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ha */
public final class C1857ha extends C1926jl {

    /* renamed from: a */
    public final String f1250a;

    /* renamed from: b */
    public final String f1251b;

    public C1857ha(String str, String str2) {
        String str3 = "";
        if (str == null) {
            str = str3;
        }
        this.f1250a = str;
        if (str2 == null) {
            str2 = str3;
        }
        this.f1251b = str2;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.session.deeplink", this.f1251b);
        a.put("fl.session.origin.name", this.f1250a);
        return a;
    }
}
