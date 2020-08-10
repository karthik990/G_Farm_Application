package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.jg */
public final class C1920jg extends C1926jl {

    /* renamed from: a */
    public final long f1314a;

    /* renamed from: b */
    public final long f1315b;

    /* renamed from: c */
    public final long f1316c;

    /* renamed from: d */
    public final int f1317d;

    public C1920jg(long j, long j2, long j3, int i) {
        this.f1314a = j;
        this.f1315b = j2;
        this.f1316c = j3;
        this.f1317d = i;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.session.id", this.f1314a);
        a.put("fl.session.elapsed.start.time", this.f1315b);
        long j = this.f1316c;
        if (j >= this.f1315b) {
            a.put("fl.session.elapsed.end.time", j);
        }
        a.put("fl.session.id.current.state", this.f1317d);
        return a;
    }
}
