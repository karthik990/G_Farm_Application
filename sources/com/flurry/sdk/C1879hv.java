package com.flurry.sdk;

/* renamed from: com.flurry.sdk.hv */
public final class C1879hv implements C1949o<C1577ba> {
    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        C1577ba baVar = (C1577ba) obj;
        if (baVar == null) {
            C1685cy.m754a(2, "ReportingFrame", "Reporting data is null, do not send the frame.");
            return;
        }
        C1771fb.m926a().mo16467a(new C1851gx(new C1852gy(baVar)));
    }
}
