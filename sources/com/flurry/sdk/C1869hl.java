package com.flurry.sdk;

import com.flurry.android.Consent;

/* renamed from: com.flurry.sdk.hl */
public final class C1869hl implements C1949o<Consent> {
    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        Consent consent = (Consent) obj;
        if (consent == null) {
            C1685cy.m768d("ConsentFrame", "Consent is null, do not send the frame.");
            return;
        }
        C1771fb.m926a().mo16467a(new C1894ih(new C1895ii(consent.isGdprScope(), consent.getConsentStrings())));
    }
}
