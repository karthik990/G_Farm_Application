package com.flurry.sdk;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gi */
public final class C1831gi extends C1926jl {

    /* renamed from: a */
    public List<C1832a> f1173a;

    /* renamed from: com.flurry.sdk.gi$a */
    public static class C1832a {

        /* renamed from: a */
        public final long f1174a;

        /* renamed from: b */
        public final int f1175b;

        public C1832a(long j, int i) {
            this.f1174a = j;
            this.f1175b = i;
        }
    }

    public C1831gi(List<C1832a> list) {
        this.f1173a = new ArrayList(list);
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.f1173a.size(); i++) {
            C1832a aVar = (C1832a) this.f1173a.get(i);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("fl.variant.id", aVar.f1174a);
            jSONObject.put("fl.variant.version", aVar.f1175b);
            jSONArray.put(jSONObject);
        }
        a.put("fl.variants", jSONArray);
        return a;
    }
}
