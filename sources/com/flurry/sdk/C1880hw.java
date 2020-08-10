package com.flurry.sdk;

import com.flurry.sdk.C1772fc.C1773a;

/* renamed from: com.flurry.sdk.hw */
final class C1880hw implements C1949o<C1525ad> {
    C1880hw() {
    }

    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        C1525ad adVar = (C1525ad) obj;
        if (adVar == null) {
            C1685cy.m754a(2, "SessionInfoFrame", "Session info data is null, do not send the frame.");
        } else {
            C1601bl.m537a();
            int i = C1773a.AGENT_REPORT_TYPE_MAIN_DEVICE.f1053d;
            if (adVar.f471d != null && adVar.f471d.f515a) {
                i = C1773a.AGENT_REPORT_TYPE_INSTANT_APP.f1053d;
            }
            C1771fb.m926a().mo16467a(new C1921jh(new C1922ji(adVar.f468a, i, adVar.f469b, adVar.f470c)));
        }
        C1685cy.m754a(4, "SessionInfoObserver", "SessionInfoData".concat(String.valueOf(adVar)));
    }
}
