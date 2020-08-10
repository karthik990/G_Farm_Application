package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.flurry.sdk.hu */
public final class C1878hu implements C1949o<C1574ay> {
    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        C1574ay ayVar = (C1574ay) obj;
        String str = ayVar.f605a;
        String str2 = ayVar.f606b;
        Map map = ayVar.f607c;
        if (map == null) {
            map = new HashMap();
        }
        map.put("fl.origin.attribute.version", str2);
        if (map.size() > 10) {
            int size = map.size();
            C1685cy.m768d("OriginAttributeFrame", "MaxOriginParams exceeded: ".concat(String.valueOf(size)));
            map.clear();
            map.put("fl.parameter.limit.exceeded", String.valueOf(size));
        }
        C1771fb.m926a().mo16467a(new C1910ix(new C1911iy(str, map)));
        StringBuilder sb = new StringBuilder("Origin attribute name: ");
        sb.append(ayVar.f605a);
        sb.append(". Origin attribute version: ");
        sb.append(ayVar.f606b);
        sb.append(". Origin attribute params: ");
        sb.append(ayVar.f607c);
        C1685cy.m754a(4, "OriginAttributeObserver", sb.toString());
    }
}
