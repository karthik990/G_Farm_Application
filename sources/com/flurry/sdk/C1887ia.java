package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ia */
public final class C1887ia extends C1926jl {

    /* renamed from: a */
    public final String f1279a;

    /* renamed from: b */
    public final String f1280b;

    /* renamed from: c */
    public final String f1281c;

    /* renamed from: d */
    public final String f1282d;

    /* renamed from: e */
    public final int f1283e;

    public C1887ia(String str, String str2, String str3, String str4) {
        this.f1279a = str;
        if (str2 == null) {
            str2 = "";
        }
        this.f1280b = str2;
        this.f1281c = str3;
        this.f1282d = str4;
        this.f1283e = 3;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        m1131a(a, "fl.app.version", this.f1279a);
        m1131a(a, "fl.app.version.override", this.f1280b);
        m1131a(a, "fl.app.version.code", this.f1281c);
        m1131a(a, "fl.bundle.id", this.f1282d);
        a.put("fl.build.environment", this.f1283e);
        return a;
    }

    /* renamed from: a */
    private static void m1131a(JSONObject jSONObject, String str, String str2) throws JSONException {
        if (str2 != null) {
            jSONObject.put(str, str2);
        }
    }
}
