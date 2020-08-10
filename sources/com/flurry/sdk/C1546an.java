package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.flurry.sdk.C1550ao.C1551a;
import com.google.android.instantapps.InstantApps;

/* renamed from: com.flurry.sdk.an */
public final class C1546an extends C1942m<C1545am> {

    /* renamed from: a */
    public String f517a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public C1552ap f518b;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f519d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public String f520e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public C1949o<C1550ao> f521f = new C1949o<C1550ao>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            final C1550ao aoVar = (C1550ao) obj;
            C1546an.this.runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    if (C1546an.this.f520e == null && aoVar.f526a.equals(C1551a.CREATED)) {
                        C1546an.this.f520e = aoVar.f527b.getString("activity_name");
                        C1546an.this.mo16264b();
                        C1546an.this.f518b.unsubscribe(C1546an.this.f521f);
                    }
                }
            });
        }
    };

    public C1546an(C1552ap apVar) {
        super("InstantAppProvider");
        this.f518b = apVar;
        this.f518b.subscribe(this.f521f);
    }

    public final void start() {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                Context a = C1576b.m502a();
                String str = "InstantAppProvider";
                if (a == null) {
                    C1685cy.m754a(6, str, "Context is null");
                    return;
                }
                try {
                    Class.forName("com.google.android.instantapps.InstantApps");
                    C1546an.this.f519d = InstantApps.isInstantApp(a);
                    StringBuilder sb = new StringBuilder("isInstantApp: ");
                    sb.append(String.valueOf(C1546an.this.f519d));
                    C1685cy.m754a(3, str, sb.toString());
                } catch (ClassNotFoundException unused) {
                    C1685cy.m754a(3, str, "isInstantApps dependency is not added");
                }
                C1546an.this.mo16264b();
            }
        });
    }

    /* renamed from: a */
    public final String mo16243a() {
        if (!this.f519d) {
            return null;
        }
        return !TextUtils.isEmpty(this.f517a) ? this.f517a : this.f520e;
    }

    /* renamed from: b */
    public final void mo16264b() {
        if (!this.f519d || mo16243a() != null) {
            boolean z = this.f519d;
            notifyObservers(new C1545am(z, z ? mo16243a() : null));
            return;
        }
        C1685cy.m754a(3, "InstantAppProvider", "Fetching instant app name");
    }

    public final void destroy() {
        super.destroy();
        this.f518b.unsubscribe(this.f521f);
    }
}
