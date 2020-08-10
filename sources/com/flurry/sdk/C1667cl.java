package com.flurry.sdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/* renamed from: com.flurry.sdk.cl */
public final class C1667cl {
    /* renamed from: a */
    public static void m707a(C1666ck ckVar) {
        if (ckVar.mo16372e() != 0) {
            ArrayList<C1665cj> arrayList = new ArrayList<>(ckVar.mo16368b());
            Collections.sort(arrayList);
            ArrayList arrayList2 = new ArrayList();
            for (C1665cj cjVar : arrayList) {
                arrayList2.add(Integer.toString(cjVar.f827b));
            }
        }
    }

    /* renamed from: a */
    public static void m706a(int i, long j, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("exp_code", String.valueOf(i));
        hashMap.put("exp_ms", String.valueOf(j));
        if (str != null) {
            hashMap.put("exp_det", str);
        }
        if (C1685cy.m764c() <= 2) {
            C1685cy.m754a(2, "AnalyticsImpl", String.format("YWA event: %1$s {%2$s}", new Object[]{"expsdk_data", hashMap.toString()}));
        }
    }
}
