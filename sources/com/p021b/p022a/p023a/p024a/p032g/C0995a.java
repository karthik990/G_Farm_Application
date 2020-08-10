package com.p021b.p022a.p023a.p024a.p032g;

import android.webkit.WebView;
import com.p021b.p022a.p023a.p024a.p026b.C0961a;
import com.p021b.p022a.p023a.p024a.p026b.C0964c;
import com.p021b.p022a.p023a.p024a.p026b.C0965d;
import com.p021b.p022a.p023a.p024a.p026b.C0969h;
import com.p021b.p022a.p023a.p024a.p026b.C0970i;
import com.p021b.p022a.p023a.p024a.p026b.p027a.C0962a;
import com.p021b.p022a.p023a.p024a.p028c.C0976c;
import com.p021b.p022a.p023a.p024a.p028c.C0977d;
import com.p021b.p022a.p023a.p024a.p030e.C0986a;
import com.p021b.p022a.p023a.p024a.p030e.C0987b;
import com.p021b.p022a.p023a.p024a.p030e.C0990d;
import com.p021b.p022a.p023a.p024a.p031f.C0994b;
import org.json.JSONArray;
import org.json.JSONObject;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.b.a.a.a.g.a */
public abstract class C0995a {

    /* renamed from: a */
    private C0994b f169a = new C0994b(null);

    /* renamed from: b */
    private C0961a f170b;

    /* renamed from: c */
    private C0962a f171c;

    /* renamed from: d */
    private C0996a f172d;

    /* renamed from: e */
    private double f173e;

    /* renamed from: com.b.a.a.a.g.a$a */
    enum C0996a {
        AD_STATE_IDLE,
        AD_STATE_VISIBLE,
        AD_STATE_HIDDEN
    }

    public C0995a() {
        mo11569i();
    }

    /* renamed from: a */
    public void mo11550a() {
    }

    /* renamed from: a */
    public void mo11551a(float f) {
        C0977d.m192a().mo11528a(mo11563c(), f);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo11552a(WebView webView) {
        this.f169a = new C0994b(webView);
    }

    /* renamed from: a */
    public void mo11553a(C0962a aVar) {
        this.f171c = aVar;
    }

    /* renamed from: a */
    public void mo11554a(C0961a aVar) {
        this.f170b = aVar;
    }

    /* renamed from: a */
    public void mo11555a(C0964c cVar) {
        C0977d.m192a().mo11533a(mo11563c(), cVar.mo11481c());
    }

    /* renamed from: a */
    public void mo11556a(C0970i iVar, C0965d dVar) {
        String g = iVar.mo11499g();
        JSONObject jSONObject = new JSONObject();
        String str = SettingsJsonConstants.APP_KEY;
        C0987b.m240a(jSONObject, "environment", str);
        C0987b.m240a(jSONObject, "adSessionType", dVar.mo11487f());
        C0987b.m240a(jSONObject, "deviceInfo", C0986a.m234d());
        JSONArray jSONArray = new JSONArray();
        jSONArray.put("clid");
        jSONArray.put("vlid");
        C0987b.m240a(jSONObject, "supports", jSONArray);
        JSONObject jSONObject2 = new JSONObject();
        C0987b.m240a(jSONObject2, "partnerName", dVar.mo11482a().mo11490a());
        C0987b.m240a(jSONObject2, "partnerVersion", dVar.mo11482a().mo11491b());
        C0987b.m240a(jSONObject, "omidNativeInfo", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        C0987b.m240a(jSONObject3, "libraryVersion", "1.2.0-Startapp");
        C0987b.m240a(jSONObject3, "appId", C0976c.m189a().mo11526b().getApplicationContext().getPackageName());
        C0987b.m240a(jSONObject, str, jSONObject3);
        if (dVar.mo11485d() != null) {
            C0987b.m240a(jSONObject, "customReferenceData", dVar.mo11485d());
        }
        JSONObject jSONObject4 = new JSONObject();
        for (C0969h hVar : dVar.mo11483b()) {
            C0987b.m240a(jSONObject4, hVar.mo11492a(), hVar.mo11494c());
        }
        C0977d.m192a().mo11530a(mo11563c(), g, jSONObject, jSONObject4);
    }

    /* renamed from: a */
    public void mo11557a(String str) {
        C0977d.m192a().mo11529a(mo11563c(), str, (JSONObject) null);
    }

    /* renamed from: a */
    public void mo11558a(String str, double d) {
        if (d > this.f173e) {
            this.f172d = C0996a.AD_STATE_VISIBLE;
            C0977d.m192a().mo11538c(mo11563c(), str);
        }
    }

    /* renamed from: a */
    public void mo11559a(String str, JSONObject jSONObject) {
        C0977d.m192a().mo11529a(mo11563c(), str, jSONObject);
    }

    /* renamed from: a */
    public void mo11560a(boolean z) {
        if (mo11566f()) {
            C0977d.m192a().mo11539d(mo11563c(), z ? "foregrounded" : "backgrounded");
        }
    }

    /* renamed from: b */
    public void mo11561b() {
        this.f169a.clear();
    }

    /* renamed from: b */
    public void mo11562b(String str, double d) {
        if (d > this.f173e && this.f172d != C0996a.AD_STATE_HIDDEN) {
            this.f172d = C0996a.AD_STATE_HIDDEN;
            C0977d.m192a().mo11538c(mo11563c(), str);
        }
    }

    /* renamed from: c */
    public WebView mo11563c() {
        return (WebView) this.f169a.get();
    }

    /* renamed from: d */
    public C0961a mo11564d() {
        return this.f170b;
    }

    /* renamed from: e */
    public C0962a mo11565e() {
        return this.f171c;
    }

    /* renamed from: f */
    public boolean mo11566f() {
        return this.f169a.get() != null;
    }

    /* renamed from: g */
    public void mo11567g() {
        C0977d.m192a().mo11527a(mo11563c());
    }

    /* renamed from: h */
    public void mo11568h() {
        C0977d.m192a().mo11536b(mo11563c());
    }

    /* renamed from: i */
    public void mo11569i() {
        this.f173e = C0990d.m252a();
        this.f172d = C0996a.AD_STATE_IDLE;
    }
}
