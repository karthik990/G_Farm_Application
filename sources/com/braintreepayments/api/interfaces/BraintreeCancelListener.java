package com.braintreepayments.api.interfaces;

public interface BraintreeCancelListener extends BraintreeListener {
    void onCancel(int i);
}
