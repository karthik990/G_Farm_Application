package com.flurry.sdk;

import android.text.TextUtils;
import com.google.android.gms.common.GoogleApiAvailability;

/* renamed from: com.flurry.sdk.ac */
public final class C1519ac extends C1942m<C1525ad> {

    /* renamed from: a */
    public String f444a;

    /* renamed from: b */
    public String f445b;

    /* renamed from: d */
    public boolean f446d = false;

    /* renamed from: e */
    public boolean f447e = false;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public C1545am f448f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public C1949o<C1545am> f449g = new C1949o<C1545am>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            final C1545am amVar = (C1545am) obj;
            C1519ac.this.runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    StringBuilder sb = new StringBuilder("isInstantApp: ");
                    sb.append(amVar.f515a);
                    C1685cy.m754a(3, "FlurryProvider", sb.toString());
                    C1519ac.this.f448f = amVar;
                    C1519ac.m405a(C1519ac.this);
                    C1519ac.this.f450h.unsubscribe(C1519ac.this.f449g);
                }
            });
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: h */
    public C1546an f450h;

    /* renamed from: i */
    private C1951q f451i;

    /* renamed from: j */
    private C1949o<C1955r> f452j = new C1949o<C1955r>() {
        /* renamed from: a */
        public final /* bridge */ /* synthetic */ void mo16242a(Object obj) {
            C1519ac.m405a(C1519ac.this);
        }
    };

    /* renamed from: com.flurry.sdk.ac$a */
    public enum C1524a {
        UNAVAILABLE(-2),
        UNKNOWN(-1),
        SUCCESS(0),
        SERVICE_MISSING(1),
        SERVICE_UPDATING(2),
        SERVICE_VERSION_UPDATE_REQUIRED(3),
        SERVICE_DISABLED(4),
        SERVICE_INVALID(5);
        

        /* renamed from: i */
        public int f467i;

        private C1524a(int i) {
            this.f467i = i;
        }
    }

    public C1519ac(C1546an anVar, C1951q qVar) {
        super("FlurryProvider");
        this.f450h = anVar;
        this.f450h.subscribe(this.f449g);
        this.f451i = qVar;
        this.f451i.subscribe(this.f452j);
    }

    /* renamed from: a */
    private static C1524a m402a() {
        try {
            int isGooglePlayServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(C1576b.m502a());
            if (isGooglePlayServicesAvailable == 0) {
                return C1524a.SUCCESS;
            }
            if (isGooglePlayServicesAvailable == 1) {
                return C1524a.SERVICE_MISSING;
            }
            if (isGooglePlayServicesAvailable == 2) {
                return C1524a.SERVICE_VERSION_UPDATE_REQUIRED;
            }
            if (isGooglePlayServicesAvailable == 3) {
                return C1524a.SERVICE_DISABLED;
            }
            if (isGooglePlayServicesAvailable == 9) {
                return C1524a.SERVICE_INVALID;
            }
            if (isGooglePlayServicesAvailable != 18) {
                return C1524a.UNAVAILABLE;
            }
            return C1524a.SERVICE_UPDATING;
        } catch (Exception | NoClassDefFoundError unused) {
            C1685cy.m754a(3, "FlurryProvider", "Error retrieving Google Play Services Availability. This probably means google play services is unavailable.");
            return C1524a.UNAVAILABLE;
        }
    }

    public final void destroy() {
        super.destroy();
        this.f450h.unsubscribe(this.f449g);
        this.f451i.unsubscribe(this.f452j);
    }

    /* renamed from: a */
    static /* synthetic */ void m405a(C1519ac acVar) {
        if (!TextUtils.isEmpty(acVar.f444a) && acVar.f448f != null) {
            acVar.notifyObservers(new C1525ad(C1598bi.m531a().mo16288b(), acVar.f446d, m402a(), acVar.f448f));
        }
    }

    /* renamed from: d */
    static /* synthetic */ void m408d(C1519ac acVar) {
        String str = "FlurryProvider";
        if (TextUtils.isEmpty(acVar.f444a)) {
            C1685cy.m754a(6, str, "Streaming API Key is invalid");
            return;
        }
        String str2 = "prev_streaming_api_key";
        int b = C1775fe.m938b(str2, 0);
        int hashCode = C1775fe.m940b("api_key", "").hashCode();
        int hashCode2 = acVar.f444a.hashCode();
        if (!(b == hashCode2 || hashCode == hashCode2)) {
            C1685cy.m754a(3, str, "Streaming API key is refreshed");
            C1775fe.m935a(str2, hashCode2);
            C1578bb bbVar = C1948n.m1229a().f1425k;
            C1685cy.m754a(3, "ReportingProvider", "Reset initial timestamp.");
            bbVar.runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    C1578bb.this.f621g = Long.MIN_VALUE;
                }
            });
        }
    }
}
