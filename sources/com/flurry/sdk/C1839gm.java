package com.flurry.sdk;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.gm */
public final class C1839gm extends C1926jl {

    /* renamed from: a */
    public final String f1209a;

    /* renamed from: b */
    public int f1210b;

    /* renamed from: c */
    public final Map<String, String> f1211c;

    /* renamed from: d */
    public final Map<String, String> f1212d;

    /* renamed from: e */
    public final boolean f1213e;

    /* renamed from: f */
    public final boolean f1214f;

    /* renamed from: g */
    public long f1215g;

    /* renamed from: h */
    public final C1840a f1216h;

    /* renamed from: i */
    public final long f1217i;

    /* renamed from: j */
    public final long f1218j;

    /* renamed from: com.flurry.sdk.gm$a */
    public enum C1840a {
        CUSTOM_EVENT,
        PURCHASE_EVENT
    }

    public C1839gm(String str, int i, C1840a aVar, Map<String, String> map, Map<String, String> map2, List<String> list, boolean z, boolean z2, long j, long j2) {
        this.f1209a = C1734dz.m874b(C1734dz.m867a(str));
        this.f1210b = i;
        this.f1216h = aVar;
        this.f1211c = map != null ? m1082a(map, list) : new HashMap<>();
        this.f1212d = map2 != null ? m1082a(map2, list) : new HashMap<>();
        this.f1213e = z;
        this.f1214f = z2;
        this.f1217i = j;
        this.f1218j = j2;
        this.f1215g = 0;
    }

    public C1839gm(String str, int i, Map<String, String> map, Map<String, String> map2, long j, long j2, long j3) {
        this.f1209a = str;
        this.f1210b = i;
        this.f1216h = C1840a.CUSTOM_EVENT;
        this.f1211c = map;
        this.f1212d = map2;
        this.f1213e = true;
        this.f1214f = false;
        this.f1217i = j;
        this.f1218j = j2;
        this.f1215g = j3;
    }

    /* renamed from: a */
    public final JSONObject mo16502a() throws JSONException {
        JSONObject a = super.mo16502a();
        a.put("fl.event.name", this.f1209a);
        a.put("fl.event.id", this.f1210b);
        a.put("fl.event.type", this.f1216h.toString());
        a.put("fl.event.timed", this.f1213e);
        a.put("fl.timed.event.starting", this.f1214f);
        long j = this.f1215g;
        if (j > 0) {
            a.put("fl.timed.event.duration", j);
        }
        a.put("fl.event.timestamp", this.f1217i);
        a.put("fl.event.uptime", this.f1218j);
        a.put("fl.event.user.parameters", C1737ea.m878a(this.f1211c));
        a.put("fl.event.flurry.parameters", C1737ea.m878a(this.f1212d));
        return a;
    }

    /* renamed from: a */
    private static Map<String, String> m1082a(Map<String, String> map, List<String> list) {
        String str;
        String str2;
        HashMap hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            if (!list.contains(entry.getKey())) {
                str = C1734dz.m874b((String) entry.getKey());
                str2 = C1734dz.m874b((String) entry.getValue());
            } else {
                str = C1734dz.m874b((String) entry.getKey());
                str2 = (String) entry.getValue();
            }
            if (!TextUtils.isEmpty(str)) {
                hashMap.put(str, str2);
            }
        }
        return hashMap;
    }
}
