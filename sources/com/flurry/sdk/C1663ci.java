package com.flurry.sdk;

import com.flurry.sdk.C1638bx.C1641a;

/* renamed from: com.flurry.sdk.ci */
public final class C1663ci extends C1638bx {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public final C1949o<C1543ak> f824a = new C1949o<C1543ak>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            C1948n.m1229a().f1421g.unsubscribe(C1663ci.this.f824a);
            C1663ci.this.mo16327d();
        }
    };

    public C1663ci(C1671cp cpVar, C1641a aVar, C1653ca caVar, C1666ck ckVar) {
        super(cpVar, aVar, caVar, ckVar);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo16325b() {
        if (C1948n.m1229a().f1421g.mo16247c()) {
            mo16327d();
            return;
        }
        C1685cy.m756a("StreamingConfigFetcher", "Waiting for ID provider.");
        C1948n.m1229a().f1421g.subscribe(this.f824a);
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public final String mo16326c() {
        return C1948n.m1229a().f1422h.f444a;
    }
}
