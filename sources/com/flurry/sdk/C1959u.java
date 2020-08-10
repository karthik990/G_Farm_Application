package com.flurry.sdk;

import com.flurry.android.Consent;

/* renamed from: com.flurry.sdk.u */
public final class C1959u extends C1942m<Consent> {

    /* renamed from: a */
    public Consent f1451a = null;

    public C1959u() {
        super("ConsentProvider");
    }

    /* renamed from: a */
    public final void mo16534a(Consent consent) {
        this.f1451a = consent;
        notifyObservers(consent);
    }
}
