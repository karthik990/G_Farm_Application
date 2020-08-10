package com.flurry.sdk;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gs */
public final class C1846gs extends C1926jl {

    /* renamed from: a */
    public final String f1225a;

    /* renamed from: b */
    public final String f1226b;

    public C1846gs(String str, String str2) {
        String str3 = "";
        if (str == null) {
            str = str3;
        }
        this.f1225a = str;
        if (str2 == null) {
            str2 = str3;
        }
        this.f1226b = str2;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        if (!TextUtils.isEmpty(this.f1225a)) {
            a.put("fl.language", this.f1225a);
        }
        if (!TextUtils.isEmpty(this.f1226b)) {
            a.put("fl.country", this.f1226b);
        }
        return a;
    }
}
