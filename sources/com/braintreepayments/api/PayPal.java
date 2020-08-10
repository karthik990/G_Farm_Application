package com.braintreepayments.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Base64;
import androidx.core.p012os.EnvironmentCompat;
import com.braintreepayments.api.exceptions.BraintreeException;
import com.braintreepayments.api.exceptions.BrowserSwitchException;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.interfaces.PayPalApprovalCallback;
import com.braintreepayments.api.interfaces.PayPalApprovalHandler;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCallback;
import com.braintreepayments.api.internal.BraintreeSharedPreferences;
import com.braintreepayments.api.internal.ManifestValidator;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.ClientToken;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.PayPalAccountBuilder;
import com.braintreepayments.api.models.PayPalAccountNonce;
import com.braintreepayments.api.models.PayPalPaymentResource;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.PostalAddress;
import com.braintreepayments.api.models.PostalAddressParser;
import com.paypal.android.sdk.onetouch.core.AuthorizationRequest;
import com.paypal.android.sdk.onetouch.core.BillingAgreementRequest;
import com.paypal.android.sdk.onetouch.core.CheckoutRequest;
import com.paypal.android.sdk.onetouch.core.PayPalOneTouchCore;
import com.paypal.android.sdk.onetouch.core.Request;
import com.paypal.android.sdk.onetouch.core.Result;
import com.paypal.android.sdk.onetouch.core.enums.RequestTarget;
import com.paypal.android.sdk.onetouch.core.enums.ResultType;
import com.paypal.android.sdk.onetouch.core.network.EnvironmentManager;
import com.paypal.android.sdk.onetouch.core.sdk.PayPalScope;
import com.paypal.android.sdk.onetouch.core.sdk.PendingRequest;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class PayPal {
    private static final String ADDRESS_OVERRIDE_KEY = "address_override";
    private static final String AMOUNT_KEY = "amount";
    private static final String AUTHORIZATION_FINGERPRINT_KEY = "authorization_fingerprint";
    private static final String CANCEL_URL_KEY = "cancel_url";
    private static final String CREATE_SINGLE_PAYMENT_ENDPOINT = "paypal_hermes/create_payment_resource";
    private static final String CURRENCY_ISO_CODE_KEY = "currency_iso_code";
    private static final String DESCRIPTION_KEY = "description";
    private static final String DISPLAY_NAME_KEY = "brand_name";
    private static final String EXPERIENCE_PROFILE_KEY = "experience_profile";
    private static final String INTENT_KEY = "intent";
    private static final String LANDING_PAGE_TYPE_KEY = "landing_page_type";
    private static final String LOCALE_CODE_KEY = "locale_code";
    private static final String MERCHANT_ACCOUNT_ID = "merchant_account_id";
    private static final String NO_SHIPPING_KEY = "no_shipping";
    private static final String OFFER_CREDIT_KEY = "offer_paypal_credit";
    @Deprecated
    private static final String PAYLOAD_CLIENT_TOKEN_KEY = "client_token";
    private static final String PAYPAL_REQUEST_KEY = "com.braintreepayments.api.PayPal.PAYPAL_REQUEST_KEY";
    private static final String REQUEST_KEY = "com.braintreepayments.api.PayPal.REQUEST_KEY";
    private static final String REQUEST_TYPE_KEY = "com.braintreepayments.api.PayPal.REQUEST_TYPE_KEY";
    private static final String RETURN_URL_KEY = "return_url";
    @Deprecated
    public static final String SCOPE_ADDRESS = PayPalScope.ADDRESS.getScopeUri();
    @Deprecated
    public static final String SCOPE_EMAIL = PayPalScope.EMAIL.getScopeUri();
    @Deprecated
    public static final String SCOPE_FUTURE_PAYMENTS = PayPalScope.FUTURE_PAYMENTS.getScopeUri();
    private static final String SETUP_BILLING_AGREEMENT_ENDPOINT = "paypal_hermes/setup_billing_agreement";
    private static final String SHIPPING_ADDRESS_KEY = "shipping_address";
    private static final String TOKENIZATION_KEY = "client_key";
    private static final String USER_ACTION_KEY = "useraction";
    @Deprecated
    protected static boolean sFuturePaymentsOverride = false;

    /* renamed from: com.braintreepayments.api.PayPal$7 */
    static /* synthetic */ class C10707 {
        static final /* synthetic */ int[] $SwitchMap$com$paypal$android$sdk$onetouch$core$enums$ResultType = new int[ResultType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.paypal.android.sdk.onetouch.core.enums.ResultType[] r0 = com.paypal.android.sdk.onetouch.core.enums.ResultType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$paypal$android$sdk$onetouch$core$enums$ResultType = r0
                int[] r0 = $SwitchMap$com$paypal$android$sdk$onetouch$core$enums$ResultType     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.paypal.android.sdk.onetouch.core.enums.ResultType r1 = com.paypal.android.sdk.onetouch.core.enums.ResultType.Error     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$paypal$android$sdk$onetouch$core$enums$ResultType     // Catch:{ NoSuchFieldError -> 0x001f }
                com.paypal.android.sdk.onetouch.core.enums.ResultType r1 = com.paypal.android.sdk.onetouch.core.enums.ResultType.Cancel     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$paypal$android$sdk$onetouch$core$enums$ResultType     // Catch:{ NoSuchFieldError -> 0x002a }
                com.paypal.android.sdk.onetouch.core.enums.ResultType r1 = com.paypal.android.sdk.onetouch.core.enums.ResultType.Success     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.braintreepayments.api.PayPal.C10707.<clinit>():void");
        }
    }

    @Deprecated
    public static void authorizeAccount(BraintreeFragment braintreeFragment) {
        authorizeAccount(braintreeFragment, null);
    }

    @Deprecated
    public static void authorizeAccount(final BraintreeFragment braintreeFragment, final List<String> list) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                if (!configuration.isPayPalEnabled()) {
                    braintreeFragment.postCallback((Exception) new BraintreeException("PayPal is not enabled"));
                } else if (!PayPal.isManifestValid(braintreeFragment)) {
                    braintreeFragment.sendAnalyticsEvent("paypal.invalid-manifest");
                    braintreeFragment.postCallback((Exception) new BraintreeException("BraintreeBrowserSwitchActivity missing, incorrectly configured in AndroidManifest.xml or another app defines the same browser switch url as this app. See https://developers.braintreepayments.com/guides/client-sdk/android/v2#browser-switch for the correct configuration"));
                } else if (!configuration.getPayPal().shouldUseBillingAgreement() || PayPal.sFuturePaymentsOverride) {
                    braintreeFragment.sendAnalyticsEvent("paypal.future-payments.selected");
                    AuthorizationRequest authorizationRequest = PayPal.getAuthorizationRequest(braintreeFragment);
                    List<String> list = list;
                    if (list != null) {
                        for (String withScopeValue : list) {
                            authorizationRequest.withScopeValue(withScopeValue);
                        }
                    }
                    PayPal.startPayPal(braintreeFragment, authorizationRequest, null);
                } else {
                    PayPal.requestBillingAgreement(braintreeFragment, new PayPalRequest());
                }
            }
        });
    }

    public static void requestBillingAgreement(BraintreeFragment braintreeFragment, PayPalRequest payPalRequest) {
        requestBillingAgreement(braintreeFragment, payPalRequest, null);
    }

    public static void requestBillingAgreement(BraintreeFragment braintreeFragment, PayPalRequest payPalRequest, PayPalApprovalHandler payPalApprovalHandler) {
        if (payPalRequest.getAmount() == null) {
            braintreeFragment.sendAnalyticsEvent("paypal.billing-agreement.selected");
            if (payPalRequest.shouldOfferCredit()) {
                braintreeFragment.sendAnalyticsEvent("paypal.billing-agreement.credit.offered");
            }
            requestOneTimePayment(braintreeFragment, payPalRequest, true, payPalApprovalHandler);
            return;
        }
        braintreeFragment.postCallback((Exception) new BraintreeException("There must be no amount specified for the Billing Agreement flow"));
    }

    public static void requestOneTimePayment(BraintreeFragment braintreeFragment, PayPalRequest payPalRequest) {
        requestOneTimePayment(braintreeFragment, payPalRequest, null);
    }

    public static void requestOneTimePayment(BraintreeFragment braintreeFragment, PayPalRequest payPalRequest, PayPalApprovalHandler payPalApprovalHandler) {
        if (payPalRequest.getAmount() != null) {
            braintreeFragment.sendAnalyticsEvent("paypal.one-time-payment.selected");
            if (payPalRequest.shouldOfferCredit()) {
                braintreeFragment.sendAnalyticsEvent("paypal.single-payment.credit.offered");
            }
            requestOneTimePayment(braintreeFragment, payPalRequest, false, payPalApprovalHandler);
            return;
        }
        braintreeFragment.postCallback((Exception) new BraintreeException("An amount must be specified for the Single Payment flow."));
    }

    private static void requestOneTimePayment(final BraintreeFragment braintreeFragment, final PayPalRequest payPalRequest, final boolean z, final PayPalApprovalHandler payPalApprovalHandler) {
        final C10652 r0 = new HttpResponseCallback() {
            public void success(String str) {
                Request request;
                try {
                    String builder = Uri.parse(PayPalPaymentResource.fromJson(str).getRedirectUrl()).buildUpon().appendQueryParameter(PayPal.USER_ACTION_KEY, payPalRequest.getUserAction()).toString();
                    if (z) {
                        request = PayPal.getBillingAgreementRequest(braintreeFragment, builder);
                    } else {
                        request = PayPal.getCheckoutRequest(braintreeFragment, builder);
                    }
                    PayPal.startPayPal(braintreeFragment, request, payPalApprovalHandler);
                } catch (JSONException e) {
                    braintreeFragment.postCallback((Exception) e);
                }
            }

            public void failure(Exception exc) {
                braintreeFragment.postCallback(exc);
            }
        };
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                if (!configuration.isPayPalEnabled()) {
                    braintreeFragment.postCallback((Exception) new BraintreeException("PayPal is not enabled"));
                } else if (!PayPal.isManifestValid(braintreeFragment)) {
                    braintreeFragment.sendAnalyticsEvent("paypal.invalid-manifest");
                    braintreeFragment.postCallback((Exception) new BraintreeException("BraintreeBrowserSwitchActivity missing, incorrectly configured in AndroidManifest.xml or another app defines the same browser switch url as this app. See https://developers.braintreepayments.com/guides/client-sdk/android/v2#browser-switch for the correct configuration"));
                } else {
                    try {
                        PayPal.persistPayPalRequest(braintreeFragment.getApplicationContext(), payPalRequest);
                        PayPal.createPaymentResource(braintreeFragment, payPalRequest, z, r0);
                    } catch (BraintreeException | ErrorWithResponse | JSONException e) {
                        braintreeFragment.postCallback(e);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void createPaymentResource(BraintreeFragment braintreeFragment, PayPalRequest payPalRequest, boolean z, HttpResponseCallback httpResponseCallback) throws JSONException, ErrorWithResponse, BraintreeException {
        JSONObject jSONObject;
        String currencyCode = payPalRequest.getCurrencyCode();
        if (currencyCode == null) {
            currencyCode = braintreeFragment.getConfiguration().getPayPal().getCurrencyIsoCode();
        }
        CheckoutRequest checkoutRequest = getCheckoutRequest(braintreeFragment, null);
        JSONObject put = new JSONObject().put(RETURN_URL_KEY, checkoutRequest.getSuccessUrl()).put(CANCEL_URL_KEY, checkoutRequest.getCancelUrl()).put(OFFER_CREDIT_KEY, payPalRequest.shouldOfferCredit());
        if (braintreeFragment.getAuthorization() instanceof ClientToken) {
            put.put(AUTHORIZATION_FINGERPRINT_KEY, braintreeFragment.getAuthorization().getBearer());
        } else {
            put.put(TOKENIZATION_KEY, braintreeFragment.getAuthorization().getBearer());
        }
        if (!z) {
            put.put(AMOUNT_KEY, payPalRequest.getAmount()).put(CURRENCY_ISO_CODE_KEY, currencyCode).put(INTENT_KEY, payPalRequest.getIntent());
        } else if (!TextUtils.isEmpty(payPalRequest.getBillingAgreementDescription())) {
            put.put("description", payPalRequest.getBillingAgreementDescription());
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(NO_SHIPPING_KEY, !payPalRequest.isShippingAddressRequired());
        jSONObject2.put(LANDING_PAGE_TYPE_KEY, payPalRequest.getLandingPageType());
        String displayName = payPalRequest.getDisplayName();
        if (TextUtils.isEmpty(displayName)) {
            displayName = braintreeFragment.getConfiguration().getPayPal().getDisplayName();
        }
        jSONObject2.put(DISPLAY_NAME_KEY, displayName);
        if (payPalRequest.getLocaleCode() != null) {
            jSONObject2.put(LOCALE_CODE_KEY, payPalRequest.getLocaleCode());
        }
        PostalAddress shippingAddressOverride = payPalRequest.getShippingAddressOverride();
        String str = ADDRESS_OVERRIDE_KEY;
        if (shippingAddressOverride != null) {
            jSONObject2.put(str, !payPalRequest.isShippingAddressEditable());
            if (z) {
                jSONObject = new JSONObject();
                put.put(SHIPPING_ADDRESS_KEY, jSONObject);
            } else {
                jSONObject = put;
            }
            PostalAddress shippingAddressOverride2 = payPalRequest.getShippingAddressOverride();
            jSONObject.put(PostalAddressParser.LINE_1_KEY, shippingAddressOverride2.getStreetAddress());
            jSONObject.put(PostalAddressParser.LINE_2_KEY, shippingAddressOverride2.getExtendedAddress());
            jSONObject.put(PostalAddressParser.LOCALITY_KEY, shippingAddressOverride2.getLocality());
            jSONObject.put(PostalAddressParser.REGION_KEY, shippingAddressOverride2.getRegion());
            jSONObject.put(PostalAddressParser.POSTAL_CODE_UNDERSCORE_KEY, shippingAddressOverride2.getPostalCode());
            jSONObject.put(PostalAddressParser.COUNTRY_CODE_UNDERSCORE_KEY, shippingAddressOverride2.getCountryCodeAlpha2());
            jSONObject.put(PostalAddressParser.RECIPIENT_NAME_UNDERSCORE_KEY, shippingAddressOverride2.getRecipientName());
        } else {
            jSONObject2.put(str, false);
        }
        if (payPalRequest.getMerchantAccountId() != null) {
            put.put(MERCHANT_ACCOUNT_ID, payPalRequest.getMerchantAccountId());
        }
        put.put(EXPERIENCE_PROFILE_KEY, jSONObject2);
        String str2 = z ? SETUP_BILLING_AGREEMENT_ENDPOINT : CREATE_SINGLE_PAYMENT_ENDPOINT;
        StringBuilder sb = new StringBuilder();
        sb.append("/v1/");
        sb.append(str2);
        braintreeFragment.getHttpClient().post(sb.toString(), put.toString(), httpResponseCallback);
    }

    /* access modifiers changed from: private */
    public static void startPayPal(final BraintreeFragment braintreeFragment, Request request, PayPalApprovalHandler payPalApprovalHandler) {
        PayPalApprovalCallback payPalApprovalCallback;
        persistRequest(braintreeFragment.getApplicationContext(), request);
        if (payPalApprovalHandler == null) {
            payPalApprovalHandler = getDefaultApprovalHandler(braintreeFragment);
            payPalApprovalCallback = null;
        } else {
            payPalApprovalCallback = new PayPalApprovalCallback() {
                public void onComplete(Intent intent) {
                    PayPal.onActivityResult(braintreeFragment, -1, intent);
                }

                public void onCancel() {
                    braintreeFragment.postCancelCallback(BraintreeRequestCodes.PAYPAL);
                }
            };
        }
        payPalApprovalHandler.handleApproval(request, payPalApprovalCallback);
    }

    private static PayPalApprovalHandler getDefaultApprovalHandler(final BraintreeFragment braintreeFragment) {
        return new PayPalApprovalHandler() {
            public void handleApproval(Request request, PayPalApprovalCallback payPalApprovalCallback) {
                PendingRequest startIntent = PayPalOneTouchCore.getStartIntent(braintreeFragment.getApplicationContext(), request);
                if (startIntent.isSuccess() && startIntent.getRequestTarget() == RequestTarget.wallet) {
                    PayPal.sendAnalyticsForPayPal(braintreeFragment, request, true, RequestTarget.wallet);
                    braintreeFragment.startActivityForResult(startIntent.getIntent(), BraintreeRequestCodes.PAYPAL);
                } else if (!startIntent.isSuccess() || startIntent.getRequestTarget() != RequestTarget.browser) {
                    PayPal.sendAnalyticsForPayPal(braintreeFragment, request, false, null);
                } else {
                    PayPal.sendAnalyticsForPayPal(braintreeFragment, request, true, RequestTarget.browser);
                    braintreeFragment.browserSwitch((int) BraintreeRequestCodes.PAYPAL, startIntent.getIntent());
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public static void sendAnalyticsForPayPal(BraintreeFragment braintreeFragment, Request request, boolean z, RequestTarget requestTarget) {
        String str;
        String paymentTypeForRequest = paymentTypeForRequest(request);
        String str2 = requestTarget == RequestTarget.wallet ? "appswitch" : "webswitch";
        if (z) {
            str = String.format("%s.%s.started", new Object[]{paymentTypeForRequest, str2});
        } else {
            str = String.format("%s.initiate.failed", new Object[]{paymentTypeForRequest});
        }
        braintreeFragment.sendAnalyticsEvent(str);
    }

    protected static void onActivityResult(BraintreeFragment braintreeFragment, int i, Intent intent) {
        Request persistedRequest = getPersistedRequest(braintreeFragment.getApplicationContext());
        if (i != -1 || intent == null || persistedRequest == null) {
            String paymentTypeForRequest = persistedRequest != null ? paymentTypeForRequest(persistedRequest) : EnvironmentCompat.MEDIA_UNKNOWN;
            StringBuilder sb = new StringBuilder();
            sb.append(paymentTypeForRequest);
            sb.append(".canceled");
            braintreeFragment.sendAnalyticsEvent(sb.toString());
            if (i != 0) {
                braintreeFragment.postCancelCallback(BraintreeRequestCodes.PAYPAL);
                return;
            }
            return;
        }
        boolean isAppSwitch = isAppSwitch(intent);
        Result parseResponse = PayPalOneTouchCore.parseResponse(braintreeFragment.getApplicationContext(), persistedRequest, intent);
        int i2 = C10707.$SwitchMap$com$paypal$android$sdk$onetouch$core$enums$ResultType[parseResponse.getResultType().ordinal()];
        if (i2 == 1) {
            braintreeFragment.postCallback((Exception) new BrowserSwitchException(parseResponse.getError().getMessage()));
            sendAnalyticsEventForSwitchResult(braintreeFragment, persistedRequest, isAppSwitch, "failed");
        } else if (i2 == 2) {
            sendAnalyticsEventForSwitchResult(braintreeFragment, persistedRequest, isAppSwitch, "canceled");
            braintreeFragment.postCancelCallback(BraintreeRequestCodes.PAYPAL);
        } else if (i2 == 3) {
            onSuccess(braintreeFragment, intent, persistedRequest, parseResponse);
            sendAnalyticsEventForSwitchResult(braintreeFragment, persistedRequest, isAppSwitch, "succeeded");
        }
    }

    private static void onSuccess(final BraintreeFragment braintreeFragment, Intent intent, Request request, Result result) {
        TokenizationClient.tokenize(braintreeFragment, parseResponse(getPersistedPayPalRequest(braintreeFragment.getApplicationContext()), request, result, intent), new PaymentMethodNonceCallback() {
            public void success(PaymentMethodNonce paymentMethodNonce) {
                if ((paymentMethodNonce instanceof PayPalAccountNonce) && ((PayPalAccountNonce) paymentMethodNonce).getCreditFinancing() != null) {
                    braintreeFragment.sendAnalyticsEvent("paypal.credit.accepted");
                }
                braintreeFragment.postCallback(paymentMethodNonce);
            }

            public void failure(Exception exc) {
                braintreeFragment.postCallback(exc);
            }
        });
    }

    private static PayPalAccountBuilder parseResponse(PayPalRequest payPalRequest, Request request, Result result, Intent intent) {
        String str = "code";
        String str2 = "response";
        String str3 = "client";
        PayPalAccountBuilder clientMetadataId = new PayPalAccountBuilder().clientMetadataId(request.getClientMetadataId());
        if (!(payPalRequest == null || payPalRequest.getMerchantAccountId() == null)) {
            clientMetadataId.merchantAccountId(payPalRequest.getMerchantAccountId());
        }
        if ((request instanceof CheckoutRequest) && payPalRequest != null) {
            clientMetadataId.intent(payPalRequest.getIntent());
        }
        if (isAppSwitch(intent)) {
            clientMetadataId.source("paypal-app");
        } else {
            clientMetadataId.source("paypal-browser");
        }
        JSONObject response = result.getResponse();
        try {
            JSONObject jSONObject = response.getJSONObject(str3);
            JSONObject jSONObject2 = response.getJSONObject(str2);
            if (EnvironmentManager.MOCK.equalsIgnoreCase(jSONObject.getString(str3)) && jSONObject2.getString(str) != null && !(request instanceof CheckoutRequest)) {
                JSONObject jSONObject3 = new JSONObject();
                StringBuilder sb = new StringBuilder();
                sb.append("fake-code:");
                sb.append(((AuthorizationRequest) request).getScopeString());
                response.put(str2, jSONObject3.put(str, sb.toString()));
            }
        } catch (JSONException unused) {
        }
        clientMetadataId.oneTouchCoreData(response);
        return clientMetadataId;
    }

    private static void sendAnalyticsEventForSwitchResult(BraintreeFragment braintreeFragment, Request request, boolean z, String str) {
        braintreeFragment.sendAnalyticsEvent(String.format("%s.%s.%s", new Object[]{paymentTypeForRequest(request), z ? "appswitch" : "webswitch", str}));
    }

    static CheckoutRequest getCheckoutRequest(BraintreeFragment braintreeFragment, String str) {
        CheckoutRequest approvalURL = ((CheckoutRequest) populateRequestData(braintreeFragment, new CheckoutRequest())).approvalURL(str);
        if (str != null) {
            String queryParameter = Uri.parse(str).getQueryParameter("token");
            if (queryParameter != null) {
                approvalURL.pairingId(braintreeFragment.getApplicationContext(), queryParameter);
            }
        }
        return approvalURL;
    }

    static BillingAgreementRequest getBillingAgreementRequest(BraintreeFragment braintreeFragment, String str) {
        BillingAgreementRequest approvalURL = ((BillingAgreementRequest) populateRequestData(braintreeFragment, new BillingAgreementRequest())).approvalURL(str);
        if (str != null) {
            String queryParameter = Uri.parse(str).getQueryParameter("ba_token");
            if (queryParameter != null) {
                approvalURL.pairingId(braintreeFragment.getApplicationContext(), queryParameter);
            }
        }
        return approvalURL;
    }

    @Deprecated
    static AuthorizationRequest getAuthorizationRequest(BraintreeFragment braintreeFragment) {
        return ((AuthorizationRequest) populateRequestData(braintreeFragment, new AuthorizationRequest(braintreeFragment.getApplicationContext()))).privacyUrl(braintreeFragment.getConfiguration().getPayPal().getPrivacyUrl()).userAgreementUrl(braintreeFragment.getConfiguration().getPayPal().getUserAgreementUrl()).withScopeValue(SCOPE_FUTURE_PAYMENTS).withScopeValue(SCOPE_EMAIL).withAdditionalPayloadAttribute(PAYLOAD_CLIENT_TOKEN_KEY, braintreeFragment.getAuthorization().toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0035  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T extends com.paypal.android.sdk.onetouch.core.Request> T populateRequestData(com.braintreepayments.api.BraintreeFragment r6, T r7) {
        /*
            com.braintreepayments.api.models.Configuration r0 = r6.getConfiguration()
            com.braintreepayments.api.models.PayPalConfiguration r0 = r0.getPayPal()
            java.lang.String r1 = r0.getEnvironment()
            int r2 = r1.hashCode()
            r3 = -1548612125(0xffffffffa3b20de3, float:-1.930468E-17)
            java.lang.String r4 = "live"
            r5 = 1
            if (r2 == r3) goto L_0x0026
            r3 = 3322092(0x32b0ec, float:4.655242E-39)
            if (r2 == r3) goto L_0x001e
            goto L_0x0030
        L_0x001e:
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0030
            r1 = 0
            goto L_0x0031
        L_0x0026:
            java.lang.String r2 = "offline"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0030
            r1 = 1
            goto L_0x0031
        L_0x0030:
            r1 = -1
        L_0x0031:
            java.lang.String r2 = "mock"
            if (r1 == 0) goto L_0x003d
            if (r1 == r5) goto L_0x003c
            java.lang.String r4 = r0.getEnvironment()
            goto L_0x003d
        L_0x003c:
            r4 = r2
        L_0x003d:
            java.lang.String r0 = r0.getClientId()
            if (r0 != 0) goto L_0x004b
            boolean r1 = r2.equals(r4)
            if (r1 == 0) goto L_0x004b
            java.lang.String r0 = "FAKE-PAYPAL-CLIENT-ID"
        L_0x004b:
            com.paypal.android.sdk.onetouch.core.Request r1 = r7.environment(r4)
            com.paypal.android.sdk.onetouch.core.Request r0 = r1.clientId(r0)
            java.lang.String r1 = r6.getReturnUrlScheme()
            java.lang.String r2 = "cancel"
            com.paypal.android.sdk.onetouch.core.Request r0 = r0.cancelUrl(r1, r2)
            java.lang.String r6 = r6.getReturnUrlScheme()
            java.lang.String r1 = "success"
            r0.successUrl(r6, r1)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.braintreepayments.api.PayPal.populateRequestData(com.braintreepayments.api.BraintreeFragment, com.paypal.android.sdk.onetouch.core.Request):com.paypal.android.sdk.onetouch.core.Request");
    }

    private static boolean isAppSwitch(Intent intent) {
        return intent.getData() == null;
    }

    /* access modifiers changed from: private */
    public static void persistPayPalRequest(Context context, PayPalRequest payPalRequest) {
        Parcel obtain = Parcel.obtain();
        payPalRequest.writeToParcel(obtain, 0);
        BraintreeSharedPreferences.getSharedPreferences(context).edit().putString(PAYPAL_REQUEST_KEY, Base64.encodeToString(obtain.marshall(), 0)).apply();
    }

    private static void persistRequest(Context context, Request request) {
        Parcel obtain = Parcel.obtain();
        request.writeToParcel(obtain, 0);
        BraintreeSharedPreferences.getSharedPreferences(context).edit().putString(REQUEST_KEY, Base64.encodeToString(obtain.marshall(), 0)).putString(REQUEST_TYPE_KEY, request.getClass().getSimpleName()).apply();
    }

    /* JADX INFO: finally extract failed */
    private static PayPalRequest getPersistedPayPalRequest(Context context) {
        String str = PAYPAL_REQUEST_KEY;
        SharedPreferences sharedPreferences = BraintreeSharedPreferences.getSharedPreferences(context);
        try {
            byte[] decode = Base64.decode(sharedPreferences.getString(str, ""), 0);
            Parcel obtain = Parcel.obtain();
            obtain.unmarshall(decode, 0, decode.length);
            obtain.setDataPosition(0);
            PayPalRequest payPalRequest = (PayPalRequest) PayPalRequest.CREATOR.createFromParcel(obtain);
            sharedPreferences.edit().remove(str).apply();
            return payPalRequest;
        } catch (Exception unused) {
            sharedPreferences.edit().remove(str).apply();
            return null;
        } catch (Throwable th) {
            sharedPreferences.edit().remove(str).apply();
            throw th;
        }
    }

    private static Request getPersistedRequest(Context context) {
        Request request;
        String str = "";
        String str2 = REQUEST_TYPE_KEY;
        String str3 = REQUEST_KEY;
        SharedPreferences sharedPreferences = BraintreeSharedPreferences.getSharedPreferences(context);
        try {
            byte[] decode = Base64.decode(sharedPreferences.getString(str3, str), 0);
            Parcel obtain = Parcel.obtain();
            obtain.unmarshall(decode, 0, decode.length);
            obtain.setDataPosition(0);
            String string = sharedPreferences.getString(str2, str);
            if (AuthorizationRequest.class.getSimpleName().equals(string)) {
                request = (Request) AuthorizationRequest.CREATOR.createFromParcel(obtain);
            } else if (BillingAgreementRequest.class.getSimpleName().equals(string)) {
                request = (Request) BillingAgreementRequest.CREATOR.createFromParcel(obtain);
            } else {
                if (CheckoutRequest.class.getSimpleName().equals(string)) {
                    request = (Request) CheckoutRequest.CREATOR.createFromParcel(obtain);
                }
                sharedPreferences.edit().remove(str3).remove(str2).apply();
                return null;
            }
            sharedPreferences.edit().remove(str3).remove(str2).apply();
            return request;
        } catch (Exception unused) {
        } catch (Throwable th) {
            sharedPreferences.edit().remove(str3).remove(str2).apply();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public static boolean isManifestValid(BraintreeFragment braintreeFragment) {
        return ManifestValidator.isUrlSchemeDeclaredInAndroidManifest(braintreeFragment.getApplicationContext(), braintreeFragment.getReturnUrlScheme(), BraintreeBrowserSwitchActivity.class);
    }

    private static String paymentTypeForRequest(Request request) {
        if (request instanceof BillingAgreementRequest) {
            return "paypal-billing-agreement";
        }
        return request instanceof CheckoutRequest ? "paypal-single-payment" : "paypal-future-payments";
    }
}
