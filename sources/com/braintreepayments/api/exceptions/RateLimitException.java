package com.braintreepayments.api.exceptions;

public class RateLimitException extends Exception {
    public RateLimitException(String str) {
        super(str);
    }
}
