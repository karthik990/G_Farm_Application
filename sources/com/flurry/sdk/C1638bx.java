package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.FlurryAgent;
import com.flurry.sdk.C1655cc.C1656a;
import com.flurry.sdk.C1661ch.C1662a;
import com.flurry.sdk.C1756ex.C1758a;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.mobiroller.constants.Constants;
import java.util.List;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.flurry.sdk.bx */
public abstract class C1638bx extends C1766f {

    /* renamed from: a */
    private static boolean f739a = false;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public C1655cc f740b;

    /* renamed from: d */
    private C1671cp f741d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public C1641a f742e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public C1653ca f743f;

    /* renamed from: g */
    private C1666ck f744g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public long f745h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public C1661ch f746i;

    /* renamed from: com.flurry.sdk.bx$a */
    public interface C1641a {
        /* renamed from: a */
        void mo16329a(C1655cc ccVar, boolean z);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public abstract void mo16325b();

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public abstract String mo16326c();

    public C1638bx(C1671cp cpVar, C1641a aVar, C1653ca caVar, C1666ck ckVar) {
        super("ConfigFetcher", C1756ex.m904a(C1758a.CONFIG));
        this.f741d = cpVar;
        this.f742e = aVar;
        this.f743f = caVar;
        this.f744g = ckVar;
    }

    /* renamed from: a */
    public final synchronized void mo16243a() {
        C1685cy.m756a("ConfigFetcher", "Starting Config fetch.");
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                C1638bx.this.f740b = C1655cc.f796b;
                C1638bx.this.f745h = System.currentTimeMillis();
                C1638bx.this.f746i = null;
                C1638bx.this.f743f.mo16345b();
                if (!C1638bx.m616c(C1638bx.this)) {
                    C1638bx.this.f742e.mo16329a(C1638bx.this.f740b, false);
                } else {
                    C1638bx.this.mo16325b();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public final synchronized void mo16327d() {
        C1685cy.m756a("ConfigFetcher", "Fetching Config data.");
        this.f741d.run();
        this.f740b = this.f741d.mo16381h();
        if (this.f740b == C1655cc.f795a) {
            C1685cy.m756a("ConfigFetcher", "Processing Config fetched data.");
            try {
                String str = this.f741d.f847h;
                C1685cy.m756a("ConfigFetcher", "JSON body: ".concat(String.valueOf(str)));
                JSONObject jSONObject = new JSONObject(str);
                String d = this.f741d.mo16377d();
                String c = mo16326c();
                String optString = jSONObject.optString("requestGuid");
                String optString2 = jSONObject.optString("apiKey");
                if (d.equals(optString)) {
                    if (c.equals(optString2)) {
                        List a = C1654cb.m668a(jSONObject);
                        long optLong = jSONObject.optLong("refreshInSeconds");
                        this.f744g.f833c = optLong;
                        if (!C1673cr.m729a(this.f743f.mo16347d()) || !this.f741d.mo16376c() || this.f744g.mo16369b(a)) {
                            this.f744g.mo16367a(a, this.f741d.mo16376c());
                            this.f740b = C1655cc.f795a;
                            C1666ck ckVar = this.f744g;
                            Context a2 = C1576b.m502a();
                            if (!this.f741d.mo16376c()) {
                                str = null;
                            }
                            if (str == null) {
                                JSONObject a3 = ckVar.mo16363a(ckVar.f831a, ckVar.f832b, false);
                                if (a3 != null) {
                                    str = a3.toString();
                                }
                            }
                            if (str != null) {
                                C1673cr.m726a(a2, str);
                            }
                            C1653ca caVar = this.f743f;
                            String g = this.f741d.mo16380g();
                            if (caVar.f790a != null) {
                                caVar.f790a.edit().putString("lastETag", g).apply();
                            }
                            C1653ca caVar2 = this.f743f;
                            String e = this.f741d.mo16378e();
                            if (caVar2.f790a != null) {
                                caVar2.f790a.edit().putString("lastKeyId", e).apply();
                            }
                            C1653ca caVar3 = this.f743f;
                            String f = this.f741d.mo16379f();
                            if (caVar3.f790a != null) {
                                caVar3.f790a.edit().putString("lastRSA", f).apply();
                            }
                        } else {
                            this.f740b = C1655cc.f796b;
                        }
                        f739a = true;
                        C1830gh.m1065a(this.f744g.mo16368b());
                        C1653ca caVar4 = this.f743f;
                        String c2 = this.f744g.mo16370c();
                        if (caVar4.f790a != null) {
                            C1685cy.m756a("ConfigMeta", "Save serialized variant IDs: ".concat(String.valueOf(c2)));
                            caVar4.f790a.edit().putString("com.flurry.sdk.variant_ids", c2).apply();
                        }
                        C1653ca caVar5 = this.f743f;
                        if (caVar5.f790a != null) {
                            caVar5.f790a.edit().putInt("appVersion", caVar5.f791b).apply();
                        }
                        this.f743f.mo16343a(System.currentTimeMillis());
                        C1653ca caVar6 = this.f743f;
                        long j = optLong * 1000;
                        if (j == 0) {
                            caVar6.f792c = 0;
                        } else if (j > Constants.REGISTRATION_EXPIRY_TIME_MS) {
                            caVar6.f792c = Constants.REGISTRATION_EXPIRY_TIME_MS;
                        } else if (j < DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS) {
                            caVar6.f792c = DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS;
                        } else {
                            caVar6.f792c = j;
                        }
                        if (caVar6.f790a != null) {
                            caVar6.f790a.edit().putLong("refreshFetch", caVar6.f792c).apply();
                        }
                        if (C1644bz.m634b() != null) {
                            C1644bz.m634b();
                            C1667cl.m707a(this.f744g);
                        }
                        this.f743f.mo16345b();
                        if (C1644bz.m634b() != null) {
                            C1644bz.m634b();
                            C1667cl.m706a(this.f740b.f798d.f807h, System.currentTimeMillis() - this.f745h, this.f740b.toString());
                        }
                        this.f742e.mo16329a(this.f740b, false);
                        return;
                    }
                }
                C1656a aVar = C1656a.AUTHENTICATE;
                StringBuilder sb = new StringBuilder("Guid: ");
                sb.append(d);
                sb.append(", payload: ");
                sb.append(optString);
                sb.append(" APIKey: ");
                sb.append(c);
                sb.append(", payload: ");
                sb.append(optString2);
                this.f740b = new C1655cc(aVar, sb.toString());
                StringBuilder sb2 = new StringBuilder("Authentication error: ");
                sb2.append(this.f740b);
                C1685cy.m762b("ConfigFetcher", sb2.toString());
                m619e();
            } catch (JSONException e2) {
                C1685cy.m757a("ConfigFetcher", "Json parse error", (Throwable) e2);
                this.f740b = new C1655cc(C1656a.NOT_VALID_JSON, e2.toString());
            } catch (Exception e3) {
                C1685cy.m757a("ConfigFetcher", "Fetch result error", (Throwable) e3);
                this.f740b = new C1655cc(C1656a.OTHER, e3.toString());
            }
        } else if (this.f740b == C1655cc.f796b) {
            this.f743f.mo16343a(System.currentTimeMillis());
            this.f743f.mo16345b();
            this.f742e.mo16329a(this.f740b, false);
        } else {
            String str2 = "ConfigFetcher";
            StringBuilder sb3 = new StringBuilder("fetch error:");
            sb3.append(this.f740b.toString());
            C1685cy.m754a(5, str2, sb3.toString());
            if (this.f746i == null && this.f740b.f798d == C1656a.UNKNOWN_CERTIFICATE) {
                FlurryAgent.onError("FlurryUnknownCertificate", this.f740b.f797c, "ConfigFetcher");
            }
            if (C1644bz.m634b() != null) {
                C1644bz.m634b();
                C1667cl.m706a(this.f740b.f798d.f807h, System.currentTimeMillis() - this.f745h, this.f740b.toString());
            }
            m619e();
        }
    }

    /* renamed from: e */
    private void m619e() {
        C1685cy.m756a("ConfigFetcher", "Retry fetching Config data.");
        C1661ch chVar = this.f746i;
        if (chVar == null) {
            this.f746i = new C1661ch(C1662a.values()[0]);
        } else {
            this.f746i = new C1661ch(chVar.f816a.mo16354a());
        }
        if (this.f746i.f816a == C1662a.ABANDON) {
            this.f742e.mo16329a(this.f740b, false);
            return;
        }
        this.f742e.mo16329a(this.f740b, true);
        this.f743f.mo16344a(new TimerTask() {
            public final void run() {
                C1638bx.this.mo16327d();
            }
        }, ((long) this.f746i.mo16353a()) * 1000);
    }

    /* renamed from: c */
    static /* synthetic */ boolean m616c(C1638bx bxVar) {
        if (!C1673cr.m728a(C1576b.m502a())) {
            return true;
        }
        StringBuilder sb = new StringBuilder("Compare version: current=");
        sb.append(bxVar.f743f.f791b);
        sb.append(", recorded=");
        sb.append(bxVar.f743f.mo16342a());
        String str = "ConfigFetcher";
        C1685cy.m756a(str, sb.toString());
        if (bxVar.f743f.mo16342a() < bxVar.f743f.f791b) {
            return true;
        }
        long j = bxVar.f743f.f792c;
        long j2 = 0;
        if (j != 0) {
            C1653ca caVar = bxVar.f743f;
            if (caVar.f790a != null) {
                j2 = caVar.f790a.getLong("lastFetch", 0);
            }
            if (System.currentTimeMillis() - j2 > j) {
                return true;
            }
        } else if (!f739a) {
            return true;
        }
        C1685cy.m756a(str, "It does not meet any criterias for data fetch.");
        return false;
    }
}
