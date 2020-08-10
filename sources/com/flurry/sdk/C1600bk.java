package com.flurry.sdk;

/* renamed from: com.flurry.sdk.bk */
public final class C1600bk {

    /* renamed from: a */
    private static C1600bk f667a;

    private C1600bk() {
    }

    /* renamed from: a */
    public static synchronized C1600bk m536a() {
        C1600bk bkVar;
        synchronized (C1600bk.class) {
            if (f667a == null) {
                f667a = new C1600bk();
            }
            bkVar = f667a;
        }
        return bkVar;
    }
}
