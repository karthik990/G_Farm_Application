package com.braintreepayments.api.exceptions;

public class VisaCheckoutNotAvailableException extends BraintreeException {
    public VisaCheckoutNotAvailableException(String str) {
        super(str);
    }
}
