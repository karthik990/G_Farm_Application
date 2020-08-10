package com.flurry.sdk;

import com.flurry.sdk.C1831gi.C1832a;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.flurry.sdk.gh */
public final class C1830gh extends C1927jm {
    private C1830gh(C1929jo joVar) {
        super(joVar);
    }

    /* renamed from: a */
    public final C1928jn mo16501a() {
        return C1928jn.VARIANT_IDS;
    }

    /* renamed from: a */
    public static void m1065a(List<C1665cj> list) {
        if (list == null || list.size() == 0) {
            C1685cy.m768d("VariantIdsFrame", "Variant list is empty, do not send the frame.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (C1665cj cjVar : list) {
            arrayList.add(new C1832a((long) cjVar.f827b, cjVar.f828c));
        }
        C1771fb.m926a().mo16467a(new C1830gh(new C1831gi(arrayList)));
    }
}
