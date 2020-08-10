package com.flurry.sdk;

import com.flurry.sdk.C1806fu.C1807a;
import com.flurry.sdk.C1806fu.C1808b;

/* renamed from: com.flurry.sdk.fs */
public final class C1804fs implements C1806fu {

    /* renamed from: h */
    private int f1107h = 0;

    /* renamed from: a */
    public final C1807a mo16488a(C1930jp jpVar) {
        if (jpVar.mo16501a().equals(C1928jn.FLUSH_FRAME)) {
            return new C1807a(C1808b.DO_NOT_DROP, new C1849gv(new C1850gw(this.f1107h)));
        }
        if (!jpVar.mo16501a().equals(C1928jn.ANALYTICS_ERROR)) {
            return f1111a;
        }
        if (C1963y.UNCAUGHT_EXCEPTION_ID.f1467c.equals(((C1836gk) jpVar.mo16519f()).f1188b)) {
            return f1111a;
        }
        int i = this.f1107h;
        this.f1107h = i + 1;
        if (i >= 50) {
            return f1117g;
        }
        return f1111a;
    }

    /* renamed from: a */
    public final void mo16489a() {
        this.f1107h = 0;
    }
}
