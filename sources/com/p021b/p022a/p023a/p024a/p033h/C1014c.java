package com.p021b.p022a.p023a.p024a.p033h;

import com.p021b.p022a.p023a.p024a.p033h.p034a.C1006b.C1008b;
import com.p021b.p022a.p023a.p024a.p033h.p034a.C1009c;
import com.p021b.p022a.p023a.p024a.p033h.p034a.C1010d;
import com.p021b.p022a.p023a.p024a.p033h.p034a.C1011e;
import com.p021b.p022a.p023a.p024a.p033h.p034a.C1012f;
import java.util.HashSet;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.h.c */
public class C1014c implements C1008b {

    /* renamed from: a */
    private JSONObject f210a;

    /* renamed from: b */
    private final C1009c f211b;

    public C1014c(C1009c cVar) {
        this.f211b = cVar;
    }

    /* renamed from: a */
    public JSONObject mo11585a() {
        return this.f210a;
    }

    /* renamed from: a */
    public void mo11586a(JSONObject jSONObject) {
        this.f210a = jSONObject;
    }

    /* renamed from: a */
    public void mo11602a(JSONObject jSONObject, HashSet<String> hashSet, double d) {
        C1009c cVar = this.f211b;
        C1012f fVar = new C1012f(this, hashSet, jSONObject, d);
        cVar.mo11587b(fVar);
    }

    /* renamed from: b */
    public void mo11603b() {
        this.f211b.mo11587b(new C1010d(this));
    }

    /* renamed from: b */
    public void mo11604b(JSONObject jSONObject, HashSet<String> hashSet, double d) {
        C1009c cVar = this.f211b;
        C1011e eVar = new C1011e(this, hashSet, jSONObject, d);
        cVar.mo11587b(eVar);
    }
}
