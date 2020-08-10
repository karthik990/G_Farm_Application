package com.flurry.sdk;

/* renamed from: com.flurry.sdk.hp */
final class C1873hp implements C1949o<C1553aq> {
    C1873hp() {
    }

    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        C1553aq aqVar = (C1553aq) obj;
        String str = aqVar.f542a;
        String str2 = aqVar.f543b;
        if ((str == null || str.isEmpty()) && (str2 == null || str2.isEmpty())) {
            C1685cy.m754a(2, "LocaleFrame", "Locale is empty, do not send the frame.");
        } else {
            C1771fb.m926a().mo16467a(new C1845gr(new C1846gs(str, str2)));
        }
        StringBuilder sb = new StringBuilder("Locale language: ");
        sb.append(aqVar.f542a);
        sb.append(". Locale country: ");
        sb.append(aqVar.f543b);
        C1685cy.m754a(4, "LocaleObserver", sb.toString());
    }
}
