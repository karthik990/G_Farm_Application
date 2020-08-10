package com.braintreepayments.api;

import android.net.Uri;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.models.AmericanExpressRewardsBalance;
import com.braintreepayments.api.models.Configuration;
import org.json.JSONException;

public class AmericanExpress {
    /* access modifiers changed from: private */
    public static final String AMEX_REWARDS_BALANCE_PATH = TokenizationClient.versionedPath("payment_methods/amex_rewards_balance");

    public static void getRewardsBalance(final BraintreeFragment braintreeFragment, final String str, final String str2) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                String str = "currencyIsoCode";
                String uri = Uri.parse(AmericanExpress.AMEX_REWARDS_BALANCE_PATH).buildUpon().appendQueryParameter("paymentMethodNonce", str).appendQueryParameter(str, str2).build().toString();
                braintreeFragment.sendAnalyticsEvent("amex.rewards-balance.start");
                braintreeFragment.getHttpClient().get(uri, new HttpResponseCallback() {
                    public void success(String str) {
                        braintreeFragment.sendAnalyticsEvent("amex.rewards-balance.success");
                        try {
                            braintreeFragment.postAmericanExpressCallback(AmericanExpressRewardsBalance.fromJson(str));
                        } catch (JSONException e) {
                            braintreeFragment.sendAnalyticsEvent("amex.rewards-balance.parse.failed");
                            braintreeFragment.postCallback((Exception) e);
                        }
                    }

                    public void failure(Exception exc) {
                        braintreeFragment.postCallback(exc);
                        braintreeFragment.sendAnalyticsEvent("amex.rewards-balance.error");
                    }
                });
            }
        });
    }
}
