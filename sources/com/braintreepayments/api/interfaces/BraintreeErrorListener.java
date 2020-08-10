package com.braintreepayments.api.interfaces;

public interface BraintreeErrorListener extends BraintreeListener {
    void onError(Exception exc);
}
