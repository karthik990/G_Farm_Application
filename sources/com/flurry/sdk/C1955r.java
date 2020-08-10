package com.flurry.sdk;

/* renamed from: com.flurry.sdk.r */
public final class C1955r {

    /* renamed from: a */
    public final C1950p f1444a;

    /* renamed from: b */
    public final C1950p f1445b;

    public C1955r(C1950p pVar, C1950p pVar2) {
        this.f1444a = pVar;
        this.f1445b = pVar2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Previous");
        sb.append(this.f1444a.toString());
        sb.append("Current");
        sb.append(this.f1445b.toString());
        return sb.toString();
    }
}
