package com.flurry.sdk;

import java.util.Collections;

/* renamed from: com.flurry.sdk.hs */
final class C1876hs implements C1949o<C1570aw> {

    /* renamed from: a */
    private String f1258a;

    C1876hs() {
    }

    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        C1570aw awVar = (C1570aw) obj;
        String str = awVar.f595a;
        boolean z = awVar.f596b;
        if (str != null && !str.equals(this.f1258a) && z) {
            C1588be.m517a();
            C1588be.m519a("Log Notification Frame", Collections.emptyMap());
            this.f1258a = str;
            C1771fb.m926a().mo16467a(new C1908iv(new C1909iw(str, z)));
        }
    }
}
