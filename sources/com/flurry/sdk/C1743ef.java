package com.flurry.sdk;

import com.flurry.android.Consent;
import java.util.Map;

/* renamed from: com.flurry.sdk.ef */
public abstract class C1743ef extends Consent {

    /* renamed from: a */
    private boolean f1000a;

    public C1743ef(boolean z, boolean z2, Map<String, String> map) {
        this.f1000a = z;
        this.isGdprScope = z2;
        this.consentStrings = map;
    }

    public boolean isLICNEnabled() {
        return this.f1000a;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && this.f1000a == ((C1743ef) obj).isLICNEnabled();
    }

    public int hashCode() {
        return (super.hashCode() * 31) + (this.f1000a ? 1 : 0);
    }
}
