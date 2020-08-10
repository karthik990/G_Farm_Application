package com.braintreepayments.api.exceptions;

public class ConfigurationException extends BraintreeException {
    public ConfigurationException(String str, Throwable th) {
        super(str, th);
    }

    public ConfigurationException(String str) {
        super(str);
    }
}
