package com.flurry.sdk;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.hi */
public final class C1865hi extends C1926jl {

    /* renamed from: a */
    public final String f1256a;

    public C1865hi(String str) {
        if (str == null) {
            str = "";
        }
        this.f1256a = str;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        if (!TextUtils.isEmpty(this.f1256a)) {
            a.put("fl.demo.userid", this.f1256a);
        }
        return a;
    }
}
