package com.flurry.sdk;

/* renamed from: com.flurry.sdk.hx */
public final class C1881hx implements C1949o<C1589bf> {
    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        C1589bf bfVar = (C1589bf) obj;
        C1685cy.m754a(3, "SessionPropertyObserver", "Log session property frame");
        if (bfVar == null) {
            C1685cy.m754a(2, "SessionPropertiesFrame", "Session property data is null, do not send the frame.");
            return;
        }
        C1771fb.m926a().mo16467a(new C1860hd(new C1861he(C1860hd.m1102a(bfVar))));
    }
}
