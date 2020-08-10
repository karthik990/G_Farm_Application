package com.flurry.sdk;

/* renamed from: com.flurry.sdk.bj */
public final class C1599bj {

    /* renamed from: a */
    private static C1599bj f666a;

    private C1599bj() {
    }

    /* renamed from: a */
    public static synchronized C1599bj m535a() {
        C1599bj bjVar;
        synchronized (C1599bj.class) {
            if (f666a == null) {
                f666a = new C1599bj();
            }
            bjVar = f666a;
        }
        return bjVar;
    }
}
