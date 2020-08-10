package com.braintreepayments.api.interfaces;

public interface HttpResponseCallback {
    void failure(Exception exc);

    void success(String str);
}
