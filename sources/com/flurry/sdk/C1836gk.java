package com.flurry.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gk */
public final class C1836gk extends C1926jl {

    /* renamed from: a */
    public final int f1187a;

    /* renamed from: b */
    public final String f1188b;

    /* renamed from: c */
    public final long f1189c;

    /* renamed from: d */
    public final String f1190d;

    /* renamed from: e */
    public final String f1191e;

    /* renamed from: f */
    public final String f1192f;

    /* renamed from: g */
    public final int f1193g;

    /* renamed from: h */
    public final int f1194h;

    /* renamed from: i */
    public final String f1195i;

    /* renamed from: j */
    public final String f1196j;

    /* renamed from: k */
    public final Map<String, String> f1197k;

    /* renamed from: l */
    public final Map<String, String> f1198l;

    /* renamed from: m */
    public int f1199m;

    /* renamed from: n */
    public List<C1960v> f1200n;

    public C1836gk(int i, String str, long j, String str2, String str3, String str4, int i2, int i3, Map<String, String> map, Map<String, String> map2, int i4, List<C1960v> list, String str5, String str6) {
        this.f1187a = i;
        this.f1188b = str;
        this.f1189c = j;
        String str7 = "";
        if (str2 == null) {
            str2 = str7;
        }
        this.f1190d = str2;
        if (str3 == null) {
            str3 = str7;
        }
        this.f1191e = str3;
        if (str4 == null) {
            str4 = str7;
        }
        this.f1192f = str4;
        this.f1193g = i2;
        this.f1194h = i3;
        if (map == null) {
            map = new HashMap<>();
        }
        this.f1197k = map;
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        this.f1198l = map2;
        this.f1199m = i4;
        if (list == null) {
            list = new ArrayList<>();
        }
        this.f1200n = list;
        this.f1195i = str5 != null ? C1734dz.m874b(str5) : str7;
        if (str6 != null) {
            str7 = str6;
        }
        this.f1196j = str7;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.error.id", this.f1187a);
        a.put("fl.error.name", this.f1188b);
        a.put("fl.error.timestamp", this.f1189c);
        a.put("fl.error.message", this.f1190d);
        a.put("fl.error.class", this.f1191e);
        a.put("fl.error.type", this.f1193g);
        a.put("fl.crash.report", this.f1192f);
        a.put("fl.crash.platform", this.f1194h);
        a.put("fl.error.user.crash.parameter", C1737ea.m878a(this.f1198l));
        a.put("fl.error.sdk.crash.parameter", C1737ea.m878a(this.f1197k));
        a.put("fl.breadcrumb.version", this.f1199m);
        JSONArray jSONArray = new JSONArray();
        List<C1960v> list = this.f1200n;
        if (list != null) {
            for (C1960v vVar : list) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("fl.breadcrumb.message", vVar.f1453a);
                jSONObject.put("fl.breadcrumb.timestamp", vVar.f1454b);
                jSONArray.put(jSONObject);
            }
        }
        a.put("fl.breadcrumb", jSONArray);
        a.put("fl.nativecrash.minidump", this.f1195i);
        a.put("fl.nativecrash.logcat", this.f1196j);
        return a;
    }
}
