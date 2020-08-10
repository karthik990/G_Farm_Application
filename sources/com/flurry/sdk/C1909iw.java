package com.flurry.sdk;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.iw */
public final class C1909iw extends C1926jl {

    /* renamed from: a */
    public final String f1304a;

    /* renamed from: b */
    public final boolean f1305b;

    public C1909iw(String str, boolean z) {
        this.f1304a = str;
        this.f1305b = z;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        if (!TextUtils.isEmpty(this.f1304a)) {
            a.put("fl.notification.key", this.f1304a);
        }
        a.put("fl.notification.enabled", this.f1305b);
        return a;
    }
}
