package com.flurry.sdk;

/* renamed from: com.flurry.sdk.bg */
public final class C1590bg extends C1942m<C1589bf> {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public C1545am f648a;

    /* renamed from: b */
    private C1578bb f649b;

    /* renamed from: d */
    private C1546an f650d;

    /* renamed from: e */
    private C1949o<C1577ba> f651e = new C1949o<C1577ba>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            final C1577ba baVar = (C1577ba) obj;
            C1590bg.this.runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    C1589bf bfVar;
                    if (baVar.f614e.equals(C1586bc.SESSION_START)) {
                        bfVar = new C1589bf(true, C1590bg.this.f648a);
                    } else {
                        bfVar = new C1589bf(false, C1590bg.this.f648a);
                    }
                    C1590bg.this.notifyObservers(bfVar);
                }
            });
        }
    };

    /* renamed from: f */
    private C1949o<C1545am> f652f = new C1949o<C1545am>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            final C1545am amVar = (C1545am) obj;
            C1590bg.this.runAsync(new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    C1685cy.m754a(3, "SessionPropertyProvider", "Receive instant app data");
                    C1590bg.this.f648a = amVar;
                }
            });
        }
    };

    public C1590bg(C1578bb bbVar, C1546an anVar) {
        super("SessionPropertyProvider");
        this.f649b = bbVar;
        this.f649b.subscribe(this.f651e);
        this.f650d = anVar;
        this.f650d.subscribe(this.f652f);
    }

    public final void destroy() {
        super.destroy();
        this.f649b.unsubscribe(this.f651e);
        this.f650d.unsubscribe(this.f652f);
    }
}
