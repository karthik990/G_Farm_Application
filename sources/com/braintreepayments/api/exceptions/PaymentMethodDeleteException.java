package com.braintreepayments.api.exceptions;

import com.braintreepayments.api.models.PaymentMethodNonce;

public class PaymentMethodDeleteException extends Exception {
    private final PaymentMethodNonce mPaymentMethodNonce;

    public PaymentMethodDeleteException(PaymentMethodNonce paymentMethodNonce, Exception exc) {
        super(exc);
        this.mPaymentMethodNonce = paymentMethodNonce;
    }

    public PaymentMethodNonce getPaymentMethodNonce() {
        return this.mPaymentMethodNonce;
    }
}
