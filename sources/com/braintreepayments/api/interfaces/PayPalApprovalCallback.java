package com.braintreepayments.api.interfaces;

import android.content.Intent;

public interface PayPalApprovalCallback {
    void onCancel();

    void onComplete(Intent intent);
}
