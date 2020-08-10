package com.flurry.sdk;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.hg */
public final class C1863hg extends C1926jl {

    /* renamed from: a */
    public final String f1255a;

    public C1863hg(String str) {
        if (str == null) {
            str = "";
        }
        this.f1255a = str;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        if (!TextUtils.isEmpty(this.f1255a)) {
            a.put("fl.timezone.value", this.f1255a);
        }
        return a;
    }
}
