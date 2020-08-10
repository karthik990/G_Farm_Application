package com.flurry.sdk;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.jk */
public final class C1924jk extends C1926jl {

    /* renamed from: a */
    public final int f1327a;

    /* renamed from: b */
    public final long f1328b;

    /* renamed from: c */
    public final String f1329c;

    /* renamed from: d */
    public final List<String> f1330d;

    /* renamed from: e */
    public final C1925a f1331e;

    /* renamed from: com.flurry.sdk.jk$a */
    public enum C1925a {
        Set(1),
        Add(2),
        Remove(3),
        Clear(4),
        Assign(5),
        Flag(6),
        Unknown(0);
        
        /* access modifiers changed from: private */

        /* renamed from: h */
        public final int f1340h;

        private C1925a(int i) {
            this.f1340h = i;
        }
    }

    public C1924jk(int i, long j, String str, List<String> list, C1925a aVar) {
        this.f1327a = i;
        this.f1328b = j;
        this.f1329c = str;
        this.f1330d = list;
        this.f1331e = aVar;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.user.property.id", this.f1327a);
        a.put("fl.user.property.uptime", this.f1328b);
        a.put("fl.user.property.key", this.f1329c);
        List<String> list = this.f1330d;
        JSONArray jSONArray = new JSONArray();
        if (list != null && !list.isEmpty()) {
            for (String put : list) {
                jSONArray.put(put);
            }
        }
        a.put("fl.user.property.values", jSONArray);
        a.put("fl.user.property.call.type", this.f1331e.f1340h);
        return a;
    }
}
