package com.braintreepayments.api.interfaces;

import com.braintreepayments.api.models.PaymentMethodNonce;

public interface PaymentMethodNonceDeletedListener extends BraintreeListener {
    void onPaymentMethodNonceDeleted(PaymentMethodNonce paymentMethodNonce);
}
