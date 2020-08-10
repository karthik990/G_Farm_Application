package com.braintreepayments.api;

import android.content.Intent;
import android.net.Uri;
import com.braintreepayments.api.exceptions.BraintreeException;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCallback;
import com.braintreepayments.api.internal.BraintreeHttpClient;
import com.braintreepayments.api.internal.ManifestValidator;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.CardBuilder;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.ThreeDSecureAuthenticationResponse;
import com.braintreepayments.api.models.ThreeDSecureLookup;
import com.braintreepayments.api.models.ThreeDSecureRequest;
import com.braintreepayments.api.threedsecure.ThreeDSecureWebViewActivity;
import org.json.JSONException;

public class ThreeDSecure {
    private static final String THREE_D_SECURE_ASSETS_PATH = "/mobile/three-d-secure-redirect/0.1.5";
    protected static boolean sWebViewOverride = false;

    public static void performVerification(final BraintreeFragment braintreeFragment, CardBuilder cardBuilder, final String str) {
        TokenizationClient.tokenize(braintreeFragment, cardBuilder, new PaymentMethodNonceCallback() {
            public void success(PaymentMethodNonce paymentMethodNonce) {
                ThreeDSecure.performVerification(braintreeFragment, paymentMethodNonce.getNonce(), str);
            }

            public void failure(Exception exc) {
                braintreeFragment.postCallback(exc);
            }
        });
    }

    public static void performVerification(BraintreeFragment braintreeFragment, String str, String str2) {
        performVerification(braintreeFragment, new ThreeDSecureRequest().nonce(str).amount(str2));
    }

    public static void performVerification(final BraintreeFragment braintreeFragment, final ThreeDSecureRequest threeDSecureRequest) {
        if (threeDSecureRequest.getAmount() == null || threeDSecureRequest.getNonce() == null) {
            braintreeFragment.postCallback((Exception) new InvalidArgumentException("The ThreeDSecureRequest nonce and amount cannot be null"));
        } else {
            braintreeFragment.waitForConfiguration(new ConfigurationListener() {
                public void onConfigurationFetched(Configuration configuration) {
                    if (!configuration.isThreeDSecureEnabled()) {
                        braintreeFragment.postCallback((Exception) new BraintreeException("Three D Secure is not enabled in the control panel"));
                        return;
                    }
                    final boolean z = ManifestValidator.isUrlSchemeDeclaredInAndroidManifest(braintreeFragment.getApplicationContext(), braintreeFragment.getReturnUrlScheme(), BraintreeBrowserSwitchActivity.class) && !ThreeDSecure.sWebViewOverride;
                    if (z || ManifestValidator.isActivityDeclaredInAndroidManifest(braintreeFragment.getApplicationContext(), ThreeDSecureWebViewActivity.class)) {
                        BraintreeHttpClient httpClient = braintreeFragment.getHttpClient();
                        StringBuilder sb = new StringBuilder();
                        sb.append("payment_methods/");
                        sb.append(threeDSecureRequest.getNonce());
                        sb.append("/three_d_secure/lookup");
                        httpClient.post(TokenizationClient.versionedPath(sb.toString()), threeDSecureRequest.build(), new HttpResponseCallback() {
                            public void success(String str) {
                                try {
                                    ThreeDSecureLookup fromJson = ThreeDSecureLookup.fromJson(str);
                                    if (fromJson.getAcsUrl() == null) {
                                        braintreeFragment.postCallback((PaymentMethodNonce) fromJson.getCardNonce());
                                    } else if (z) {
                                        ThreeDSecure.launchBrowserSwitch(braintreeFragment, fromJson);
                                    } else {
                                        ThreeDSecure.launchWebView(braintreeFragment, fromJson);
                                    }
                                } catch (JSONException e) {
                                    braintreeFragment.postCallback((Exception) e);
                                }
                            }

                            public void failure(Exception exc) {
                                braintreeFragment.postCallback(exc);
                            }
                        });
                        return;
                    }
                    braintreeFragment.postCallback((Exception) new BraintreeException("ThreeDSecureWebViewActivity is not declared in AndroidManifest.xml"));
                }
            });
        }
    }

    protected static void onActivityResult(BraintreeFragment braintreeFragment, int i, Intent intent) {
        ThreeDSecureAuthenticationResponse threeDSecureAuthenticationResponse;
        if (i == -1) {
            Uri data = intent.getData();
            if (data != null) {
                threeDSecureAuthenticationResponse = ThreeDSecureAuthenticationResponse.fromJson(data.getQueryParameter("auth_response"));
            } else {
                threeDSecureAuthenticationResponse = (ThreeDSecureAuthenticationResponse) intent.getParcelableExtra(ThreeDSecureWebViewActivity.EXTRA_THREE_D_SECURE_RESULT);
            }
            if (threeDSecureAuthenticationResponse.isSuccess()) {
                braintreeFragment.postCallback((PaymentMethodNonce) threeDSecureAuthenticationResponse.getCardNonce());
            } else if (threeDSecureAuthenticationResponse.getException() != null) {
                braintreeFragment.postCallback((Exception) new BraintreeException(threeDSecureAuthenticationResponse.getException()));
            } else {
                braintreeFragment.postCallback((Exception) new ErrorWithResponse(422, threeDSecureAuthenticationResponse.getErrors()));
            }
        }
    }

    /* access modifiers changed from: private */
    public static void launchWebView(BraintreeFragment braintreeFragment, ThreeDSecureLookup threeDSecureLookup) {
        braintreeFragment.startActivityForResult(new Intent(braintreeFragment.getApplicationContext(), ThreeDSecureWebViewActivity.class).putExtra(ThreeDSecureWebViewActivity.EXTRA_THREE_D_SECURE_LOOKUP, threeDSecureLookup), BraintreeRequestCodes.THREE_D_SECURE);
    }

    /* access modifiers changed from: private */
    public static void launchBrowserSwitch(BraintreeFragment braintreeFragment, ThreeDSecureLookup threeDSecureLookup) {
        StringBuilder sb = new StringBuilder();
        sb.append(braintreeFragment.getConfiguration().getAssetsUrl());
        sb.append(THREE_D_SECURE_ASSETS_PATH);
        String sb2 = sb.toString();
        String format = String.format("%s/redirect.html?redirect_url=%s://x-callback-url/braintree/threedsecure?", new Object[]{sb2, braintreeFragment.getReturnUrlScheme()});
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("/index.html");
        String str = "PaReq";
        String str2 = "MD";
        String str3 = "TermUrl";
        braintreeFragment.browserSwitch((int) BraintreeRequestCodes.THREE_D_SECURE, Uri.parse(sb3.toString()).buildUpon().appendQueryParameter("AcsUrl", threeDSecureLookup.getAcsUrl()).appendQueryParameter(str, threeDSecureLookup.getPareq()).appendQueryParameter(str2, threeDSecureLookup.getMd()).appendQueryParameter(str3, threeDSecureLookup.getTermUrl()).appendQueryParameter("ReturnUrl", format).build().toString());
    }
}
