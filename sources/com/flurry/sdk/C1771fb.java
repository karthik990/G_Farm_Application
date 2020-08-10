package com.flurry.sdk;

/* renamed from: com.flurry.sdk.fb */
public final class C1771fb {

    /* renamed from: a */
    static C1771fb f1044a;

    /* renamed from: b */
    public C1780fi f1045b = new C1780fi();

    /* renamed from: c */
    public C1935ju f1046c;

    /* renamed from: d */
    public C1931jq f1047d;

    private C1771fb() {
        C1931jq jqVar;
        int a = C1687d.m770a();
        if (a == 0 || a == 2) {
            jqVar = C1687d.m777c() ? new C1764ez() : new C1761ey();
        } else {
            jqVar = null;
        }
        this.f1047d = jqVar;
        this.f1046c = new C1935ju(this.f1047d);
    }

    /* renamed from: a */
    public static synchronized C1771fb m926a() {
        C1771fb fbVar;
        synchronized (C1771fb.class) {
            if (f1044a == null) {
                f1044a = new C1771fb();
            }
            fbVar = f1044a;
        }
        return fbVar;
    }

    /* renamed from: a */
    public final void mo16467a(C1930jp jpVar) {
        if (jpVar != null) {
            this.f1045b.mo16470a(jpVar);
        } else {
            C1685cy.m754a(5, "StreamingManager", "sendFrameToStreaming failed -- message is null");
        }
    }

    /* renamed from: b */
    public static void m927b() {
        C1771fb fbVar = f1044a;
        if (fbVar != null) {
            C1935ju juVar = fbVar.f1046c;
            if (juVar != null) {
                juVar.f1389a.stopWatching();
                juVar.f1390b = null;
                fbVar.f1046c = null;
            }
            C1780fi fiVar = fbVar.f1045b;
            if (fiVar != null) {
                fiVar.f1059a.mo16480b();
                fiVar.f1059a = null;
                fbVar.f1045b = null;
            }
            f1044a = null;
        }
    }
}
