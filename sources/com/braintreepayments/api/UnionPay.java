package com.braintreepayments.api;

import android.net.Uri;
import com.braintreepayments.api.exceptions.ConfigurationException;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCallback;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.UnionPayCapabilities;
import com.braintreepayments.api.models.UnionPayCardBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class UnionPay {
    /* access modifiers changed from: private */
    public static final String UNIONPAY_CAPABILITIES_PATH = TokenizationClient.versionedPath("payment_methods/credit_cards/capabilities");
    private static final String UNIONPAY_ENROLLMENT_ID_KEY = "unionPayEnrollmentId";
    /* access modifiers changed from: private */
    public static final String UNIONPAY_ENROLLMENT_PATH = TokenizationClient.versionedPath("union_pay_enrollments");
    private static final String UNIONPAY_SMS_REQUIRED_KEY = "smsCodeRequired";

    public static void fetchCapabilities(final BraintreeFragment braintreeFragment, final String str) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                if (!configuration.getUnionPay().isEnabled()) {
                    braintreeFragment.postCallback((Exception) new ConfigurationException("UnionPay is not enabled"));
                    return;
                }
                braintreeFragment.getHttpClient().get(Uri.parse(UnionPay.UNIONPAY_CAPABILITIES_PATH).buildUpon().appendQueryParameter("creditCard[number]", str).build().toString(), new HttpResponseCallback() {
                    public void success(String str) {
                        braintreeFragment.postCallback(UnionPayCapabilities.fromJson(str));
                        braintreeFragment.sendAnalyticsEvent("union-pay.capabilities-received");
                    }

                    public void failure(Exception exc) {
                        braintreeFragment.postCallback(exc);
                        braintreeFragment.sendAnalyticsEvent("union-pay.capabilities-failed");
                    }
                });
            }
        });
    }

    public static void enroll(final BraintreeFragment braintreeFragment, final UnionPayCardBuilder unionPayCardBuilder) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                if (!configuration.getUnionPay().isEnabled()) {
                    braintreeFragment.postCallback((Exception) new ConfigurationException("UnionPay is not enabled"));
                    return;
                }
                try {
                    braintreeFragment.getHttpClient().post(UnionPay.UNIONPAY_ENROLLMENT_PATH, unionPayCardBuilder.buildEnrollment().toString(), new HttpResponseCallback() {
                        public void success(String str) {
                            try {
                                JSONObject jSONObject = new JSONObject(str);
                                braintreeFragment.postUnionPayCallback(jSONObject.getString(UnionPay.UNIONPAY_ENROLLMENT_ID_KEY), jSONObject.getBoolean(UnionPay.UNIONPAY_SMS_REQUIRED_KEY));
                                braintreeFragment.sendAnalyticsEvent("union-pay.enrollment-succeeded");
                            } catch (JSONException e) {
                                failure(e);
                            }
                        }

                        public void failure(Exception exc) {
                            braintreeFragment.postCallback(exc);
                            braintreeFragment.sendAnalyticsEvent("union-pay.enrollment-failed");
                        }
                    });
                } catch (JSONException e) {
                    braintreeFragment.postCallback((Exception) e);
                }
            }
        });
    }

    public static void tokenize(final BraintreeFragment braintreeFragment, UnionPayCardBuilder unionPayCardBuilder) {
        TokenizationClient.tokenize(braintreeFragment, unionPayCardBuilder, new PaymentMethodNonceCallback() {
            public void success(PaymentMethodNonce paymentMethodNonce) {
                braintreeFragment.postCallback(paymentMethodNonce);
                braintreeFragment.sendAnalyticsEvent("union-pay.nonce-received");
            }

            public void failure(Exception exc) {
                braintreeFragment.postCallback(exc);
                braintreeFragment.sendAnalyticsEvent("union-pay.nonce-failed");
            }
        });
    }
}
