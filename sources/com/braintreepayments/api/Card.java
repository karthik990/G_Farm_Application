package com.braintreepayments.api;

import com.braintreepayments.api.interfaces.PaymentMethodNonceCallback;
import com.braintreepayments.api.models.CardBuilder;
import com.braintreepayments.api.models.PaymentMethodNonce;

public class Card {
    public static void tokenize(final BraintreeFragment braintreeFragment, CardBuilder cardBuilder) {
        TokenizationClient.tokenize(braintreeFragment, cardBuilder, new PaymentMethodNonceCallback() {
            public void success(PaymentMethodNonce paymentMethodNonce) {
                DataCollector.collectRiskData(braintreeFragment, paymentMethodNonce);
                braintreeFragment.postCallback(paymentMethodNonce);
                braintreeFragment.sendAnalyticsEvent("card.nonce-received");
            }

            public void failure(Exception exc) {
                braintreeFragment.postCallback(exc);
                braintreeFragment.sendAnalyticsEvent("card.nonce-failed");
            }
        });
    }
}
