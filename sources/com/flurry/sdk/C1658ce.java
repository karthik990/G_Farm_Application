package com.flurry.sdk;

/* renamed from: com.flurry.sdk.ce */
public final class C1658ce {

    /* renamed from: a */
    private C1672cq f808a = new C1672cq();

    /* renamed from: b */
    private C1665cj f809b;

    /* renamed from: a */
    public final C1642by mo16349a(String str) {
        if (str != null) {
            C1665cj cjVar = this.f809b;
            if (cjVar != null) {
                return cjVar.mo16355a(str);
            }
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Variant: {");
        sb.append(this.f809b.toString());
        sb.append("}");
        return sb.toString();
    }
}
