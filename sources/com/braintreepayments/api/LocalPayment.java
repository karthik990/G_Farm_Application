package com.braintreepayments.api;

import android.content.Intent;
import androidx.core.p012os.EnvironmentCompat;
import com.braintreepayments.api.exceptions.BraintreeException;
import com.braintreepayments.api.exceptions.ConfigurationException;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.LocalPaymentRequest;
import com.braintreepayments.api.models.LocalPaymentResult;
import com.braintreepayments.api.models.MetadataBuilder;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.paypal.android.sdk.data.collector.PayPalDataCollector;
import org.json.JSONException;
import org.json.JSONObject;

public class LocalPayment {
    private static final String LOCAL_PAYMENT_CANCEL = "local-payment-cancel";
    private static final String LOCAL_PAYMENT_SUCCESSS = "local-payment-success";
    /* access modifiers changed from: private */
    public static String sMerchantAccountId;
    /* access modifiers changed from: private */
    public static String sPaymentType;

    public static void startPayment(final BraintreeFragment braintreeFragment, final LocalPaymentRequest localPaymentRequest, final BraintreeResponseListener<LocalPaymentRequest> braintreeResponseListener) {
        if (localPaymentRequest == null) {
            braintreeFragment.postCallback((Exception) new BraintreeException("A LocalPaymentRequest is required."));
        } else if (localPaymentRequest.getApprovalUrl() != null || localPaymentRequest.getPaymentId() != null) {
            braintreeFragment.postCallback((Exception) new BraintreeException("LocalPaymentRequest is invalid, appovalUrl and paymentId should not be set."));
        } else if (localPaymentRequest.getPaymentType() == null || localPaymentRequest.getAmount() == null) {
            braintreeFragment.postCallback((Exception) new BraintreeException("LocalPaymentRequest is invalid, paymentType and amount are required."));
        } else if (braintreeResponseListener == null) {
            braintreeFragment.postCallback((Exception) new BraintreeException("BraintreeResponseListener<LocalPaymentRequest> is required."));
        } else {
            braintreeFragment.waitForConfiguration(new ConfigurationListener() {
                public void onConfigurationFetched(Configuration configuration) {
                    if (!configuration.getPayPal().isEnabled()) {
                        braintreeFragment.postCallback((Exception) new ConfigurationException("Local payments are not enabled for this merchant."));
                        return;
                    }
                    LocalPayment.sMerchantAccountId = localPaymentRequest.getMerchantAccountId();
                    LocalPayment.sPaymentType = localPaymentRequest.getPaymentType();
                    StringBuilder sb = new StringBuilder();
                    sb.append(braintreeFragment.getReturnUrlScheme());
                    String str = "://";
                    sb.append(str);
                    sb.append(LocalPayment.LOCAL_PAYMENT_SUCCESSS);
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(braintreeFragment.getReturnUrlScheme());
                    sb3.append(str);
                    sb3.append(LocalPayment.LOCAL_PAYMENT_CANCEL);
                    String sb4 = sb3.toString();
                    BraintreeFragment braintreeFragment = braintreeFragment;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(LocalPayment.paymentTypeForAnalytics());
                    sb5.append(".local-payment.start-payment.selected");
                    braintreeFragment.sendAnalyticsEvent(sb5.toString());
                    braintreeFragment.getHttpClient().post("/v1/paypal_hermes/create_payment_resource", localPaymentRequest.build(sb2, sb4), new HttpResponseCallback() {
                        public void success(String str) {
                            String str2 = "paymentResource";
                            try {
                                JSONObject jSONObject = new JSONObject(str);
                                localPaymentRequest.approvalUrl(jSONObject.getJSONObject(str2).getString("redirectUrl"));
                                localPaymentRequest.paymentId(jSONObject.getJSONObject(str2).getString("paymentToken"));
                                BraintreeFragment braintreeFragment = braintreeFragment;
                                StringBuilder sb = new StringBuilder();
                                sb.append(LocalPayment.paymentTypeForAnalytics());
                                sb.append(".local-payment.create.succeeded");
                                braintreeFragment.sendAnalyticsEvent(sb.toString());
                                braintreeResponseListener.onResponse(localPaymentRequest);
                            } catch (JSONException e) {
                                failure(e);
                            }
                        }

                        public void failure(Exception exc) {
                            BraintreeFragment braintreeFragment = braintreeFragment;
                            StringBuilder sb = new StringBuilder();
                            sb.append(LocalPayment.paymentTypeForAnalytics());
                            sb.append(".local-payment.webswitch.initiate.failed");
                            braintreeFragment.sendAnalyticsEvent(sb.toString());
                            braintreeFragment.postCallback(exc);
                        }
                    });
                }
            });
        }
    }

    public static void approvePayment(BraintreeFragment braintreeFragment, LocalPaymentRequest localPaymentRequest) {
        braintreeFragment.browserSwitch((int) BraintreeRequestCodes.LOCAL_PAYMENT, localPaymentRequest.getApprovalUrl());
        StringBuilder sb = new StringBuilder();
        sb.append(paymentTypeForAnalytics());
        sb.append(".local-payment.webswitch.initiate.succeeded");
        braintreeFragment.sendAnalyticsEvent(sb.toString());
    }

    static void onActivityResult(final BraintreeFragment braintreeFragment, int i, Intent intent) {
        if (intent == null || intent.getData() == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(paymentTypeForAnalytics());
            sb.append(".local-payment.webswitch-response.invalid");
            braintreeFragment.sendAnalyticsEvent(sb.toString());
            braintreeFragment.postCallback((Exception) new BraintreeException("LocalPayment encountered an error, return URL is invalid."));
        } else {
            String uri = intent.getData().toString();
            if (uri.toLowerCase().contains(LOCAL_PAYMENT_CANCEL.toLowerCase())) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(paymentTypeForAnalytics());
                sb2.append(".local-payment.webswitch.canceled");
                braintreeFragment.sendAnalyticsEvent(sb2.toString());
                braintreeFragment.postCancelCallback(BraintreeRequestCodes.LOCAL_PAYMENT);
                return;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("merchant_account_id", sMerchantAccountId);
                jSONObject.put("paypal_account", new JSONObject().put("intent", PayPalRequest.INTENT_SALE).put("response", new JSONObject().put("webURL", uri)).put("options", new JSONObject().put("validate", false)).put("response_type", "web").put("correlation_id", PayPalDataCollector.getClientMetadataId(braintreeFragment.getApplicationContext())));
                jSONObject.put(MetadataBuilder.META_KEY, new JSONObject().put(Param.SOURCE, "client").put("integration", braintreeFragment.getIntegrationType()).put("sessionId", braintreeFragment.getSessionId()));
                braintreeFragment.getHttpClient().post("/v1/payment_methods/paypal_accounts", jSONObject.toString(), new HttpResponseCallback() {
                    public void success(String str) {
                        try {
                            LocalPaymentResult fromJson = LocalPaymentResult.fromJson(str);
                            BraintreeFragment braintreeFragment = braintreeFragment;
                            StringBuilder sb = new StringBuilder();
                            sb.append(LocalPayment.paymentTypeForAnalytics());
                            sb.append(".local-payment.tokenize.succeeded");
                            braintreeFragment.sendAnalyticsEvent(sb.toString());
                            braintreeFragment.postCallback((PaymentMethodNonce) fromJson);
                        } catch (JSONException e) {
                            failure(e);
                        }
                    }

                    public void failure(Exception exc) {
                        BraintreeFragment braintreeFragment = braintreeFragment;
                        StringBuilder sb = new StringBuilder();
                        sb.append(LocalPayment.paymentTypeForAnalytics());
                        sb.append(".local-payment.tokenize.failed");
                        braintreeFragment.sendAnalyticsEvent(sb.toString());
                        braintreeFragment.postCallback(exc);
                    }
                });
            } catch (JSONException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public static String paymentTypeForAnalytics() {
        String str = sPaymentType;
        return str != null ? str : EnvironmentCompat.MEDIA_UNKNOWN;
    }
}
