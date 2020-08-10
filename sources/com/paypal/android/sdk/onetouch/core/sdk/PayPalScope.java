package com.paypal.android.sdk.onetouch.core.sdk;

import com.google.android.gms.common.Scopes;

@Deprecated
public enum PayPalScope {
    FUTURE_PAYMENTS("https://uri.paypal.com/services/payments/futurepayments"),
    PROFILE(Scopes.PROFILE),
    PAYPAL_ATTRIBUTES("https://uri.paypal.com/services/paypalattributes"),
    OPENID(Scopes.OPEN_ID),
    EMAIL("email"),
    ADDRESS("address"),
    PHONE("phone");
    
    private String mScopeUri;

    private PayPalScope(String str) {
        this.mScopeUri = str;
    }

    public String getScopeUri() {
        return this.mScopeUri;
    }
}
