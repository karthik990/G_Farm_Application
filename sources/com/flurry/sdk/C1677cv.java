package com.flurry.sdk;

import java.util.Comparator;

/* renamed from: com.flurry.sdk.cv */
public final class C1677cv implements Comparator<Runnable> {
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        Runnable runnable = (Runnable) obj2;
        int a = m742a((Runnable) obj);
        int a2 = m742a(runnable);
        if (a < a2) {
            return -1;
        }
        return a > a2 ? 1 : 0;
    }

    /* renamed from: a */
    private static int m742a(Runnable runnable) {
        if (runnable == null) {
            return Integer.MAX_VALUE;
        }
        if (runnable instanceof C1678cw) {
            C1739ec ecVar = (C1739ec) ((C1678cw) runnable).mo16387a();
            if (ecVar != null) {
                return ecVar.f997p;
            }
            return Integer.MAX_VALUE;
        } else if (runnable instanceof C1739ec) {
            return ((C1739ec) runnable).f997p;
        } else {
            StringBuilder sb = new StringBuilder("Unknown runnable class: ");
            sb.append(runnable.getClass().getName());
            C1685cy.m754a(6, "PriorityComparator", sb.toString());
            return Integer.MAX_VALUE;
        }
    }
}
