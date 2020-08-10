package com.braintreepayments.api.interfaces;

import com.braintreepayments.api.models.AmericanExpressRewardsBalance;

public interface AmericanExpressListener extends BraintreeListener {
    void onRewardsBalanceFetched(AmericanExpressRewardsBalance americanExpressRewardsBalance);
}
