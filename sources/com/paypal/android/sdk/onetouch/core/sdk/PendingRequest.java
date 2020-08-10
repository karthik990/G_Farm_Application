package com.paypal.android.sdk.onetouch.core.sdk;

import android.content.Intent;
import com.paypal.android.sdk.onetouch.core.enums.RequestTarget;

public class PendingRequest {
    private final String mClientMetadataId;
    private final Intent mIntent;
    private final RequestTarget mRequestTarget;
    private final boolean mSuccess;

    public PendingRequest(boolean z, RequestTarget requestTarget, String str, Intent intent) {
        this.mSuccess = z;
        this.mRequestTarget = requestTarget;
        this.mClientMetadataId = str;
        this.mIntent = intent;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public RequestTarget getRequestTarget() {
        return this.mRequestTarget;
    }

    public String getClientMetadataId() {
        return this.mClientMetadataId;
    }

    public Intent getIntent() {
        return this.mIntent;
    }
}
