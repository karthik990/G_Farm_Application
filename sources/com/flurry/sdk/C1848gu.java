package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gu */
public final class C1848gu extends C1926jl {

    /* renamed from: a */
    public final int f1227a;

    /* renamed from: b */
    public final String f1228b;

    /* renamed from: c */
    public final String f1229c;

    /* renamed from: d */
    public final String f1230d;

    /* renamed from: e */
    public final String f1231e;

    /* renamed from: f */
    public final String f1232f;

    /* renamed from: g */
    public final String f1233g;

    /* renamed from: h */
    public final int f1234h;

    public C1848gu(int i, String str, String str2, String str3, String str4, String str5, String str6, int i2) {
        this.f1341o = 2;
        if (i < 0) {
            i = -1;
        }
        this.f1227a = i;
        this.f1228b = str;
        this.f1229c = str2;
        this.f1230d = str3;
        this.f1231e = str4;
        this.f1232f = str5;
        this.f1233g = str6;
        this.f1234h = i2;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.network.status", this.f1227a);
        String str = this.f1228b;
        if (str != null) {
            a.put("fl.cellular.name", str);
            a.put("fl.cellular.operator", this.f1229c);
            a.put("fl.cellular.sim.operator", this.f1230d);
            a.put("fl.cellular.sim.id", this.f1231e);
            a.put("fl.cellular.sim.name", this.f1232f);
            a.put("fl.cellular.band", this.f1233g);
            a.put("fl.cellular.signal.strength", this.f1234h);
        }
        return a;
    }
}
