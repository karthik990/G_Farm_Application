package com.flurry.sdk;

import com.flurry.sdk.C1519ac.C1524a;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.ji */
public final class C1922ji extends C1926jl {

    /* renamed from: a */
    public final int f1318a = 3;

    /* renamed from: b */
    public final int f1319b = 313;

    /* renamed from: c */
    public final String f1320c;

    /* renamed from: d */
    public final int f1321d;

    /* renamed from: e */
    public final boolean f1322e;

    /* renamed from: f */
    public final C1524a f1323f;

    public C1922ji(String str, int i, boolean z, C1524a aVar) {
        this.f1320c = str;
        this.f1321d = i;
        this.f1322e = z;
        this.f1323f = aVar;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.agent.version", this.f1319b);
        a.put("fl.agent.platform", this.f1318a);
        a.put("fl.apikey", this.f1320c);
        a.put("fl.agent.report.key", this.f1321d);
        a.put("fl.background.session.metrics", this.f1322e);
        a.put("fl.play.service.availability", this.f1323f.f467i);
        return a;
    }
}
