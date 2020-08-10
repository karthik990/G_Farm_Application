package com.braintreepayments.api.interfaces;

import com.braintreepayments.api.models.PaymentMethodNonce;

public interface PaymentMethodNonceCallback {
    void failure(Exception exc);

    void success(PaymentMethodNonce paymentMethodNonce);
}
