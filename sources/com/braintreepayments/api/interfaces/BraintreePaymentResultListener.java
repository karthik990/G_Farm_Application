package com.braintreepayments.api.interfaces;

import com.braintreepayments.api.models.BraintreePaymentResult;

public interface BraintreePaymentResultListener extends BraintreeListener {
    void onBraintreePaymentResult(BraintreePaymentResult braintreePaymentResult);
}
