package com.flurry.sdk;

import com.flurry.sdk.C1806fu.C1807a;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.flurry.sdk.fv */
public final class C1809fv implements C1806fu {

    /* renamed from: h */
    private static final Set<String> f1129h = new HashSet();

    /* renamed from: a */
    public final void mo16489a() {
    }

    /* renamed from: a */
    public final C1807a mo16488a(C1930jp jpVar) {
        if (!jpVar.mo16501a().equals(C1928jn.ORIGIN_ATTRIBUTE)) {
            return f1111a;
        }
        String str = ((C1911iy) jpVar.mo16519f()).f1306a;
        if (f1129h.size() < 10 || f1129h.contains(str)) {
            f1129h.add(str);
            return f1111a;
        }
        StringBuilder sb = new StringBuilder("MaxOrigins exceeded: ");
        sb.append(f1129h.size());
        C1685cy.m769e("OriginAttributeDropRule", sb.toString());
        return f1114d;
    }
}
