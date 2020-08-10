package com.braintreepayments.api;

import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Build.VERSION;
import com.braintreepayments.api.exceptions.BraintreeException;
import com.braintreepayments.api.exceptions.PaymentMethodDeleteException;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import com.braintreepayments.api.internal.GraphQLQueryHelper;
import com.braintreepayments.api.models.ClientToken;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.MetadataBuilder;
import com.braintreepayments.api.models.PaymentMethodNonce;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMethod {
    protected static final String CLIENT_SDK_META_DATA = "clientSdkMetadata";
    protected static final String INPUT = "input";
    protected static final String SINGLE_USE_TOKEN_ID = "singleUseTokenId";
    protected static final String VARIABLES = "variables";

    public static void getPaymentMethodNonces(final BraintreeFragment braintreeFragment, boolean z) {
        String str = "session_id";
        final Uri build = Uri.parse(TokenizationClient.versionedPath("payment_methods")).buildUpon().appendQueryParameter("default_first", String.valueOf(z)).appendQueryParameter(str, braintreeFragment.getSessionId()).build();
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                braintreeFragment.getHttpClient().get(build.toString(), new HttpResponseCallback() {
                    public void success(String str) {
                        try {
                            braintreeFragment.postCallback(PaymentMethodNonce.parsePaymentMethodNonces(str));
                            braintreeFragment.sendAnalyticsEvent("get-payment-methods.succeeded");
                        } catch (JSONException e) {
                            braintreeFragment.postCallback((Exception) e);
                            braintreeFragment.sendAnalyticsEvent("get-payment-methods.failed");
                        }
                    }

                    public void failure(Exception exc) {
                        braintreeFragment.postCallback(exc);
                        braintreeFragment.sendAnalyticsEvent("get-payment-methods.failed");
                    }
                });
            }
        });
    }

    public static void getPaymentMethodNonces(BraintreeFragment braintreeFragment) {
        getPaymentMethodNonces(braintreeFragment, false);
    }

    public static void deletePaymentMethod(final BraintreeFragment braintreeFragment, final PaymentMethodNonce paymentMethodNonce) {
        if (!(braintreeFragment.getAuthorization() instanceof ClientToken)) {
            braintreeFragment.postCallback((Exception) new BraintreeException("A client token with a customer id must be used to delete a payment method nonce."));
        } else if (VERSION.SDK_INT < 21) {
            braintreeFragment.postCallback((Exception) new BraintreeException("Payment Method Nonce deletion is not supported for API < 21"));
        } else {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            try {
                jSONObject.put(CLIENT_SDK_META_DATA, new MetadataBuilder().sessionId(braintreeFragment.getSessionId()).source("client").integration(braintreeFragment.getIntegrationType()).build());
                jSONObject.put("query", GraphQLQueryHelper.getQuery(braintreeFragment.getApplicationContext(), C1074R.raw.delete_payment_method_mutation));
                jSONObject3.put(SINGLE_USE_TOKEN_ID, paymentMethodNonce.getNonce());
                jSONObject2.put("input", jSONObject3);
                jSONObject.put("variables", jSONObject2);
                jSONObject.put(Keys.OPERATION_NAME, "DeletePaymentMethodFromSingleUseToken");
            } catch (NotFoundException | IOException | JSONException unused) {
                braintreeFragment.postCallback((Exception) new BraintreeException("Unable to read GraphQL query"));
            }
            braintreeFragment.getGraphQLHttpClient().post(jSONObject.toString(), new HttpResponseCallback() {
                public void success(String str) {
                    braintreeFragment.postPaymentMethodDeletedCallback(paymentMethodNonce);
                    braintreeFragment.sendAnalyticsEvent("delete-payment-methods.succeeded");
                }

                public void failure(Exception exc) {
                    braintreeFragment.postCallback((Exception) new PaymentMethodDeleteException(paymentMethodNonce, exc));
                    braintreeFragment.sendAnalyticsEvent("delete-payment-methods.failed");
                }
            });
        }
    }
}
