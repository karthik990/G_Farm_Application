package com.flurry.sdk;

import com.flurry.sdk.C1806fu.C1807a;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.flurry.sdk.fw */
public final class C1810fw implements C1806fu {

    /* renamed from: h */
    protected static final Set<String> f1130h = new HashSet();

    /* renamed from: a */
    public final C1807a mo16488a(C1930jp jpVar) {
        if (!jpVar.mo16501a().equals(C1928jn.SESSION_PROPERTIES_PARAMS)) {
            return f1111a;
        }
        String str = ((C1859hc) jpVar.mo16519f()).f1252a;
        if (f1130h.size() < 10 || f1130h.contains(str)) {
            f1130h.add(str);
            return f1111a;
        }
        C1685cy.m769e("SessionPropertiesParamsDropRule", "MaxSessionPropertiesParams exceeded: 10");
        return f1114d;
    }

    /* renamed from: a */
    public final void mo16489a() {
        f1130h.clear();
    }
}
