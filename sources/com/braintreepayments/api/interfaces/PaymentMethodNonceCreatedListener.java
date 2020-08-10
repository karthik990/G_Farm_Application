package com.braintreepayments.api.interfaces;

import com.braintreepayments.api.models.PaymentMethodNonce;

public interface PaymentMethodNonceCreatedListener extends BraintreeListener {
    void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce);
}
