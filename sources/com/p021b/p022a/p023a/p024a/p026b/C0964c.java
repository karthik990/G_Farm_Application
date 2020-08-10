package com.p021b.p022a.p023a.p024a.p026b;

import com.p021b.p022a.p023a.p024a.p030e.C0987b;
import com.p021b.p022a.p023a.p024a.p030e.C0991e;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.b.c */
public class C0964c {

    /* renamed from: a */
    private final C0967f f101a;

    /* renamed from: b */
    private final C0967f f102b;

    /* renamed from: c */
    private final boolean f103c;

    private C0964c(C0967f fVar, C0967f fVar2, boolean z) {
        this.f101a = fVar;
        if (fVar2 == null) {
            this.f102b = C0967f.NONE;
        } else {
            this.f102b = fVar2;
        }
        this.f103c = z;
    }

    /* renamed from: a */
    public static C0964c m123a(C0967f fVar, C0967f fVar2, boolean z) {
        C0991e.m256a((Object) fVar, "Impression owner is null");
        C0991e.m254a(fVar);
        return new C0964c(fVar, fVar2, z);
    }

    /* renamed from: a */
    public boolean mo11479a() {
        return C0967f.NATIVE == this.f101a;
    }

    /* renamed from: b */
    public boolean mo11480b() {
        return C0967f.NATIVE == this.f102b;
    }

    /* renamed from: c */
    public JSONObject mo11481c() {
        JSONObject jSONObject = new JSONObject();
        C0987b.m240a(jSONObject, "impressionOwner", this.f101a);
        C0987b.m240a(jSONObject, "videoEventsOwner", this.f102b);
        C0987b.m240a(jSONObject, "isolateVerificationScripts", Boolean.valueOf(this.f103c));
        return jSONObject;
    }
}
