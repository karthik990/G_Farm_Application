package com.braintreepayments.api.interfaces;

import com.braintreepayments.api.models.Configuration;

public interface ConfigurationListener extends BraintreeListener {
    void onConfigurationFetched(Configuration configuration);
}
