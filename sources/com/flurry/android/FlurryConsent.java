package com.flurry.android;

import java.util.Map;

public final class FlurryConsent extends Consent {
    public FlurryConsent(boolean z, Map<String, String> map) {
        this.isGdprScope = z;
        this.consentStrings = map;
    }
}
