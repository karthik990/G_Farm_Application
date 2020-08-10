package com.braintreepayments.api;

import android.content.Intent;
import com.braintreepayments.api.exceptions.BraintreeException;
import com.braintreepayments.api.exceptions.ConfigurationException;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCallback;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.VisaCheckoutBuilder;
import com.braintreepayments.api.models.VisaCheckoutConfiguration;
import com.mobiroller.preview.BuildConfig;
import com.visa.checkout.Profile.ProfileBuilder;
import com.visa.checkout.PurchaseInfo.PurchaseInfoBuilder;
import com.visa.checkout.VisaCheckoutSdk;
import com.visa.checkout.VisaPaymentSummary;
import java.util.List;

public class VisaCheckout {
    public static void createProfileBuilder(final BraintreeFragment braintreeFragment, final BraintreeResponseListener<ProfileBuilder> braintreeResponseListener) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                VisaCheckoutConfiguration visaCheckout = configuration.getVisaCheckout();
                if (!(VisaCheckout.isVisaCheckoutSDKAvailable() && configuration.getVisaCheckout().isEnabled())) {
                    braintreeFragment.postCallback((Exception) new ConfigurationException("Visa Checkout is not enabled."));
                    return;
                }
                String apiKey = visaCheckout.getApiKey();
                List acceptedCardBrands = visaCheckout.getAcceptedCardBrands();
                ProfileBuilder profileBuilder = new ProfileBuilder(apiKey, BuildConfig.FLAVOR_server.equals(configuration.getEnvironment()) ? "https://secure.checkout.visa.com" : "https://sandbox.secure.checkout.visa.com");
                profileBuilder.setCardBrands((String[]) acceptedCardBrands.toArray(new String[acceptedCardBrands.size()]));
                profileBuilder.setDataLevel("FULL");
                profileBuilder.setExternalClientId(visaCheckout.getExternalClientId());
                braintreeResponseListener.onResponse(profileBuilder);
            }
        });
    }

    public static void authorize(BraintreeFragment braintreeFragment, PurchaseInfoBuilder purchaseInfoBuilder) {
        Intent checkoutIntent = VisaCheckoutSdk.getCheckoutIntent(braintreeFragment.getActivity(), purchaseInfoBuilder.build());
        braintreeFragment.sendAnalyticsEvent("visacheckout.initiate.started");
        braintreeFragment.startActivityForResult(checkoutIntent, BraintreeRequestCodes.VISA_CHECKOUT);
    }

    static boolean isVisaCheckoutSDKAvailable() {
        try {
            Class.forName("com.visa.checkout.VisaCheckoutSdk");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    static void onActivityResult(BraintreeFragment braintreeFragment, int i, Intent intent) {
        if (i == 0) {
            braintreeFragment.sendAnalyticsEvent("visacheckout.result.cancelled");
        } else if (i != -1 || intent == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Visa Checkout responded with an invalid resultCode: ");
            sb.append(i);
            braintreeFragment.postCallback((Exception) new BraintreeException(sb.toString()));
            braintreeFragment.sendAnalyticsEvent("visacheckout.result.failed");
        } else {
            tokenize(braintreeFragment, intent.getParcelableExtra("payment_summary"));
            braintreeFragment.sendAnalyticsEvent("visacheckout.result.succeeded");
        }
    }

    static void tokenize(final BraintreeFragment braintreeFragment, VisaPaymentSummary visaPaymentSummary) {
        TokenizationClient.tokenize(braintreeFragment, new VisaCheckoutBuilder(visaPaymentSummary), new PaymentMethodNonceCallback() {
            public void success(PaymentMethodNonce paymentMethodNonce) {
                braintreeFragment.postCallback(paymentMethodNonce);
                braintreeFragment.sendAnalyticsEvent("visacheckout.tokenize.succeeded");
            }

            public void failure(Exception exc) {
                braintreeFragment.postCallback(exc);
                braintreeFragment.sendAnalyticsEvent("visacheckout.tokenize.failed");
            }
        });
    }
}
