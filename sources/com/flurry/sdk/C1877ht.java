package com.flurry.sdk;

/* renamed from: com.flurry.sdk.ht */
public final class C1877ht {

    /* renamed from: a */
    protected static C1877ht f1259a;

    /* renamed from: b */
    public C1866hj f1260b = new C1866hj();

    /* renamed from: c */
    public C1879hv f1261c = new C1879hv();

    /* renamed from: d */
    public C1870hm f1262d = new C1870hm();

    /* renamed from: e */
    public C1871hn f1263e = new C1871hn();

    /* renamed from: f */
    public C1872ho f1264f = new C1872ho();

    /* renamed from: g */
    public C1873hp f1265g = new C1873hp();

    /* renamed from: h */
    public C1874hq f1266h = new C1874hq();

    /* renamed from: i */
    public C1875hr f1267i = new C1875hr();

    /* renamed from: j */
    public C1876hs f1268j = new C1876hs();

    /* renamed from: k */
    public C1880hw f1269k = new C1880hw();

    /* renamed from: l */
    public C1882hy f1270l = new C1882hy();

    /* renamed from: m */
    public C1869hl f1271m = new C1869hl();

    /* renamed from: n */
    public C1881hx f1272n = new C1881hx();

    /* renamed from: o */
    public C1878hu f1273o = new C1878hu();

    /* renamed from: p */
    public C1867hk f1274p = new C1867hk();

    private C1877ht() {
    }

    /* renamed from: a */
    public static synchronized C1877ht m1121a() {
        C1877ht htVar;
        synchronized (C1877ht.class) {
            if (f1259a == null) {
                f1259a = new C1877ht();
            }
            htVar = f1259a;
        }
        return htVar;
    }

    /* renamed from: b */
    public static synchronized void m1122b() {
        synchronized (C1877ht.class) {
            if (f1259a != null) {
                C1877ht htVar = f1259a;
                C1948n a = C1948n.m1229a();
                if (a != null) {
                    a.f1415a.unsubscribe(htVar.f1266h);
                    a.f1416b.unsubscribe(htVar.f1267i);
                    a.f1417c.unsubscribe(htVar.f1264f);
                    a.f1418d.unsubscribe(htVar.f1265g);
                    a.f1419e.unsubscribe(htVar.f1270l);
                    a.f1420f.unsubscribe(htVar.f1262d);
                    a.f1421g.unsubscribe(htVar.f1263e);
                    a.f1422h.unsubscribe(htVar.f1269k);
                    a.f1423i.unsubscribe(htVar.f1260b);
                    a.f1424j.unsubscribe(htVar.f1268j);
                    a.f1425k.unsubscribe(htVar.f1261c);
                    a.f1426l.unsubscribe(htVar.f1271m);
                    a.f1428n.unsubscribe(htVar.f1272n);
                    a.f1429o.unsubscribe(htVar.f1273o);
                    a.f1430p.unsubscribe(htVar.f1274p);
                }
                f1259a = null;
            }
        }
    }
}
