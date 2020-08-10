package com.flurry.sdk;

/* renamed from: com.flurry.sdk.ip */
public final class C1902ip extends C1927jm {
    private C1902ip(C1929jo joVar) {
        super(joVar);
    }

    /* renamed from: a */
    public final C1928jn mo16501a() {
        return C1928jn.FRAME_COUNTER;
    }

    /* renamed from: b */
    public static C1902ip m1151b() {
        String str = "frame.counter";
        long b = C1775fe.m939b(str, 0) + 1;
        C1775fe.m936a(str, b);
        return new C1902ip(new C1903iq(b));
    }
}
