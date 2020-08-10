package com.flurry.sdk;

/* renamed from: com.flurry.sdk.cc */
public final class C1655cc {

    /* renamed from: a */
    public static final C1655cc f795a = new C1655cc(C1656a.SUCCEED, null);

    /* renamed from: b */
    public static final C1655cc f796b = new C1655cc(C1656a.NO_CHANGE, null);

    /* renamed from: c */
    String f797c;

    /* renamed from: d */
    C1656a f798d;

    /* renamed from: com.flurry.sdk.cc$a */
    public enum C1656a {
        SUCCEED(1),
        NO_CHANGE(0),
        IO(-1),
        NOT_VALID_JSON(-2),
        AUTHENTICATE(-3),
        UNKNOWN_CERTIFICATE(-4),
        OTHER(-5);
        

        /* renamed from: h */
        int f807h;

        private C1656a(int i) {
            this.f807h = i;
        }
    }

    public C1655cc(C1656a aVar, String str) {
        this.f798d = aVar;
        this.f797c = str;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[Error:");
        sb.append(this.f798d.name());
        sb.append("] ");
        sb.append(this.f797c);
        return sb.toString();
    }
}
