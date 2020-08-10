package com.braintreepayments.api.interfaces;

import com.paypal.android.sdk.onetouch.core.Request;

public interface PayPalApprovalHandler {
    void handleApproval(Request request, PayPalApprovalCallback payPalApprovalCallback);
}
