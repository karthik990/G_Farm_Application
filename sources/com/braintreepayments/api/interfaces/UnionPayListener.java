package com.braintreepayments.api.interfaces;

import com.braintreepayments.api.models.UnionPayCapabilities;

public interface UnionPayListener extends BraintreeListener {
    void onCapabilitiesFetched(UnionPayCapabilities unionPayCapabilities);

    void onSmsCodeSent(String str, boolean z);
}
