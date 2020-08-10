package com.flurry.android;

import java.util.Map;

public abstract class Consent {
    protected Map<String, String> consentStrings;
    protected boolean isGdprScope;

    public boolean isGdprScope() {
        return this.isGdprScope;
    }

    public Map<String, String> getConsentStrings() {
        return this.consentStrings;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Consent consent = (Consent) obj;
            if (this.isGdprScope == consent.isGdprScope()) {
                Map<String, String> map = this.consentStrings;
                return map == null ? consent.getConsentStrings() == null : map.equals(consent.getConsentStrings());
            }
        }
    }

    public int hashCode() {
        int i = (this.isGdprScope ? 1 : 0) * true;
        Map<String, String> map = this.consentStrings;
        return i + (map != null ? map.hashCode() : 0);
    }
}
