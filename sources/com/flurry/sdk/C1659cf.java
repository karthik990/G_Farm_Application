package com.flurry.sdk;

/* renamed from: com.flurry.sdk.cf */
public final class C1659cf {

    /* renamed from: b */
    private static C1659cf f810b;

    /* renamed from: a */
    public C1644bz f811a = C1644bz.m630a();

    /* renamed from: a */
    public static synchronized C1659cf m673a() {
        C1659cf cfVar;
        synchronized (C1659cf.class) {
            if (f810b == null) {
                if (C1479a.m361i()) {
                    f810b = new C1659cf();
                } else {
                    throw new IllegalStateException("Flurry SDK must be initialized before starting config");
                }
            }
            cfVar = f810b;
        }
        return cfVar;
    }

    private C1659cf() {
    }

    /* renamed from: a */
    public final String mo16351a(String str, String str2, C1660cg cgVar) {
        return this.f811a.mo16337c().mo16324a(str, str2, cgVar);
    }
}
