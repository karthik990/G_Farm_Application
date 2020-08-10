package com.flurry.sdk;

/* renamed from: com.flurry.sdk.n */
public final class C1948n {

    /* renamed from: q */
    private static C1948n f1414q;

    /* renamed from: a */
    public C1558at f1415a = new C1558at(this.f1423i);

    /* renamed from: b */
    public C1563av f1416b = new C1563av(this.f1423i);

    /* renamed from: c */
    public C1552ap f1417c = new C1552ap();

    /* renamed from: d */
    public C1554ar f1418d = new C1554ar();

    /* renamed from: e */
    public C1595bh f1419e = new C1595bh();

    /* renamed from: f */
    public C1518ab f1420f = new C1518ab();

    /* renamed from: g */
    public C1529ag f1421g = new C1529ag(this.f1423i);

    /* renamed from: h */
    public C1519ac f1422h = new C1519ac(this.f1427m, this.f1423i);

    /* renamed from: i */
    public C1951q f1423i = new C1951q(this.f1417c);

    /* renamed from: j */
    public C1571ax f1424j = new C1571ax(this.f1423i);

    /* renamed from: k */
    public C1578bb f1425k = new C1578bb(this.f1423i);

    /* renamed from: l */
    public C1959u f1426l = new C1959u();

    /* renamed from: m */
    public C1546an f1427m = new C1546an(this.f1417c);

    /* renamed from: n */
    public C1590bg f1428n = new C1590bg(this.f1425k, this.f1427m);

    /* renamed from: o */
    public C1575az f1429o = new C1575az();

    /* renamed from: p */
    public C1958t f1430p = new C1958t();

    private C1948n() {
        this.f1415a.start();
        this.f1417c.start();
        this.f1418d.start();
        this.f1419e.start();
        this.f1420f.start();
        this.f1427m.start();
        this.f1423i.start();
        this.f1421g.start();
        this.f1422h.start();
        this.f1424j.start();
        this.f1416b.start();
        this.f1425k.start();
        this.f1426l.start();
        this.f1428n.start();
        this.f1429o.start();
        this.f1430p.start();
    }

    /* renamed from: a */
    public static synchronized C1948n m1229a() {
        C1948n nVar;
        synchronized (C1948n.class) {
            if (f1414q == null) {
                f1414q = new C1948n();
            }
            nVar = f1414q;
        }
        return nVar;
    }

    /* renamed from: b */
    public static synchronized void m1230b() {
        synchronized (C1948n.class) {
            if (f1414q != null) {
                C1948n nVar = f1414q;
                nVar.f1415a.destroy();
                nVar.f1417c.destroy();
                nVar.f1418d.destroy();
                nVar.f1419e.destroy();
                nVar.f1420f.destroy();
                nVar.f1427m.destroy();
                nVar.f1423i.destroy();
                nVar.f1421g.destroy();
                nVar.f1422h.destroy();
                nVar.f1424j.destroy();
                nVar.f1416b.destroy();
                nVar.f1425k.destroy();
                nVar.f1426l.destroy();
                nVar.f1428n.destroy();
                nVar.f1429o.destroy();
                nVar.f1430p.destroy();
                nVar.f1415a = null;
                nVar.f1417c = null;
                nVar.f1418d = null;
                nVar.f1419e = null;
                nVar.f1420f = null;
                nVar.f1427m = null;
                nVar.f1423i = null;
                nVar.f1421g = null;
                nVar.f1422h = null;
                nVar.f1424j = null;
                nVar.f1416b = null;
                nVar.f1425k = null;
                nVar.f1426l = null;
                nVar.f1428n = null;
                nVar.f1429o = null;
                nVar.f1430p = null;
                f1414q = null;
            }
        }
    }
}
