package com.braintreepayments.api.interfaces;

public interface QueuedCallback {
    void run();

    boolean shouldRun();
}
