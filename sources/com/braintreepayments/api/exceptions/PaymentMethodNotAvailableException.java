package com.braintreepayments.api.exceptions;

public class PaymentMethodNotAvailableException extends BraintreeException {
    public PaymentMethodNotAvailableException(String str) {
        super(str);
    }
}
