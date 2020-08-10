package com.flurry.sdk;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gy */
public final class C1852gy extends C1926jl {

    /* renamed from: a */
    public final C1587bd f1236a;

    /* renamed from: b */
    public final long f1237b;

    /* renamed from: c */
    public final long f1238c;

    /* renamed from: d */
    public final long f1239d;

    /* renamed from: e */
    public final C1586bc f1240e;

    /* renamed from: f */
    public final boolean f1241f;

    public C1852gy(C1577ba baVar) {
        this.f1236a = baVar.f610a;
        this.f1237b = baVar.f611b;
        this.f1238c = baVar.f612c;
        this.f1239d = baVar.f613d;
        this.f1240e = baVar.f614e;
        this.f1241f = baVar.f615f;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.session.timestamp", this.f1237b);
        a.put("fl.initial.timestamp", this.f1238c);
        a.put("fl.continue.session.millis", this.f1239d);
        a.put("fl.session.state", this.f1236a.f643d);
        a.put("fl.session.event", this.f1240e.name());
        a.put("fl.session.manual", this.f1241f);
        return a;
    }
}
