package com.flurry.sdk;

/* renamed from: com.flurry.sdk.ib */
public final class C1888ib extends C1927jm {

    /* renamed from: a */
    private static int f1284a;

    private C1888ib(C1929jo joVar) {
        super(joVar);
    }

    /* renamed from: a */
    public final C1928jn mo16501a() {
        return C1928jn.APP_ORIENTATION;
    }

    /* renamed from: a */
    public static void m1133a(int i) {
        if (i != 0 && f1284a != i) {
            f1284a = i;
            C1771fb.m926a().mo16467a(new C1888ib(new C1889ic(i)));
        }
    }
}
