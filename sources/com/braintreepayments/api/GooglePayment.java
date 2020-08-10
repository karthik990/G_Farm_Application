package com.braintreepayments.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.braintreepayments.api.exceptions.BraintreeException;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.GoogleApiClientException;
import com.braintreepayments.api.exceptions.GoogleApiClientException.ErrorType;
import com.braintreepayments.api.exceptions.GooglePaymentException;
import com.braintreepayments.api.googlepayment.C1102R;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.TokenizationParametersListener;
import com.braintreepayments.api.internal.ManifestValidator;
import com.braintreepayments.api.models.AndroidPayConfiguration;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.GooglePaymentCardNonce;
import com.braintreepayments.api.models.GooglePaymentRequest;
import com.braintreepayments.api.models.MetadataBuilder;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.TokenizationKey;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.Wallet.WalletOptions.Builder;
import com.mobiroller.preview.BuildConfig;
import java.util.ArrayList;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePayment {
    private static final String AMEX_NETWORK = "amex";
    private static final String DISCOVER_NETWORK = "discover";
    private static final String MASTERCARD_NETWORK = "mastercard";
    private static final String VISA_NETWORK = "visa";

    public static void isReadyToPay(final BraintreeFragment braintreeFragment, final BraintreeResponseListener<Boolean> braintreeResponseListener) {
        try {
            Class.forName(PaymentsClient.class.getName());
            braintreeFragment.waitForConfiguration(new ConfigurationListener() {
                public void onConfigurationFetched(Configuration configuration) {
                    if (!configuration.getAndroidPay().isEnabled(braintreeFragment.getApplicationContext())) {
                        braintreeResponseListener.onResponse(Boolean.valueOf(false));
                        return;
                    }
                    if (braintreeFragment.getActivity() == null) {
                        braintreeFragment.postCallback((Exception) new GoogleApiClientException(ErrorType.NotAttachedToActivity, 1));
                    }
                    Wallet.getPaymentsClient(braintreeFragment.getActivity(), new Builder().setEnvironment(GooglePayment.getEnvironment(configuration.getAndroidPay())).build()).isReadyToPay(IsReadyToPayRequest.newBuilder().addAllowedPaymentMethod(1).addAllowedPaymentMethod(2).build()).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        public void onComplete(Task<Boolean> task) {
                            try {
                                braintreeResponseListener.onResponse(task.getResult(ApiException.class));
                            } catch (ApiException unused) {
                                braintreeResponseListener.onResponse(Boolean.valueOf(false));
                            }
                        }
                    });
                }
            });
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
            braintreeResponseListener.onResponse(Boolean.valueOf(false));
        }
    }

    public static void getTokenizationParameters(final BraintreeFragment braintreeFragment, final TokenizationParametersListener tokenizationParametersListener) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                tokenizationParametersListener.onResult(GooglePayment.getTokenizationParameters(braintreeFragment), GooglePayment.getAllowedCardNetworks(braintreeFragment));
            }
        });
    }

    public static void requestPayment(final BraintreeFragment braintreeFragment, final GooglePaymentRequest googlePaymentRequest) {
        braintreeFragment.sendAnalyticsEvent("google-payment.selected");
        String str = "google-payment.failed";
        if (!validateManifest(braintreeFragment.getApplicationContext())) {
            braintreeFragment.postCallback((Exception) new BraintreeException("GooglePaymentActivity was not found in the Android manifest, or did not have a theme of R.style.bt_transparent_activity"));
            braintreeFragment.sendAnalyticsEvent(str);
        } else if (googlePaymentRequest == null || googlePaymentRequest.getTransactionInfo() == null) {
            braintreeFragment.postCallback((Exception) new BraintreeException("Cannot pass null TransactionInfo to requestPayment"));
            braintreeFragment.sendAnalyticsEvent(str);
        } else {
            braintreeFragment.waitForConfiguration(new ConfigurationListener() {
                public void onConfigurationFetched(Configuration configuration) {
                    PaymentDataRequest.Builder paymentMethodTokenizationParameters = PaymentDataRequest.newBuilder().setTransactionInfo(googlePaymentRequest.getTransactionInfo()).addAllowedPaymentMethod(1).addAllowedPaymentMethod(2).setPaymentMethodTokenizationParameters(GooglePayment.getTokenizationParameters(braintreeFragment));
                    CardRequirements.Builder addAllowedCardNetworks = CardRequirements.newBuilder().addAllowedCardNetworks(GooglePayment.getAllowedCardNetworks(braintreeFragment));
                    if (googlePaymentRequest.getAllowPrepaidCards() != null) {
                        addAllowedCardNetworks.setAllowPrepaidCards(googlePaymentRequest.getAllowPrepaidCards().booleanValue());
                    }
                    if (googlePaymentRequest.getBillingAddressFormat() != null) {
                        addAllowedCardNetworks.setBillingAddressFormat(googlePaymentRequest.getBillingAddressFormat().intValue());
                    }
                    if (googlePaymentRequest.isBillingAddressRequired() != null) {
                        addAllowedCardNetworks.setBillingAddressRequired(googlePaymentRequest.isBillingAddressRequired().booleanValue());
                    }
                    paymentMethodTokenizationParameters.setCardRequirements(addAllowedCardNetworks.build());
                    if (googlePaymentRequest.isEmailRequired() != null) {
                        paymentMethodTokenizationParameters.setEmailRequired(googlePaymentRequest.isEmailRequired().booleanValue());
                    }
                    if (googlePaymentRequest.isPhoneNumberRequired() != null) {
                        paymentMethodTokenizationParameters.setPhoneNumberRequired(googlePaymentRequest.isPhoneNumberRequired().booleanValue());
                    }
                    if (googlePaymentRequest.isShippingAddressRequired() != null) {
                        paymentMethodTokenizationParameters.setShippingAddressRequired(googlePaymentRequest.isShippingAddressRequired().booleanValue());
                    }
                    if (googlePaymentRequest.getShippingAddressRequirements() != null) {
                        paymentMethodTokenizationParameters.setShippingAddressRequirements(googlePaymentRequest.getShippingAddressRequirements());
                    }
                    if (googlePaymentRequest.isUiRequired() != null) {
                        paymentMethodTokenizationParameters.setUiRequired(googlePaymentRequest.isUiRequired().booleanValue());
                    }
                    braintreeFragment.sendAnalyticsEvent("google-payment.started");
                    String str = "com.braintreepayments.api.EXTRA_PAYMENT_DATA_REQUEST";
                    braintreeFragment.startActivityForResult(new Intent(braintreeFragment.getApplicationContext(), GooglePaymentActivity.class).putExtra("com.braintreepayments.api.EXTRA_ENVIRONMENT", GooglePayment.getEnvironment(configuration.getAndroidPay())).putExtra(str, paymentMethodTokenizationParameters.build()), BraintreeRequestCodes.GOOGLE_PAYMENT);
                }
            });
        }
    }

    public static void tokenize(BraintreeFragment braintreeFragment, PaymentData paymentData) {
        try {
            braintreeFragment.postCallback((PaymentMethodNonce) GooglePaymentCardNonce.fromPaymentData(paymentData));
            braintreeFragment.sendAnalyticsEvent("google-payment.nonce-received");
        } catch (NullPointerException | JSONException unused) {
            braintreeFragment.sendAnalyticsEvent("google-payment.failed");
            try {
                braintreeFragment.postCallback((Exception) ErrorWithResponse.fromJson(paymentData.getPaymentMethodToken().getToken()));
            } catch (NullPointerException | JSONException e) {
                braintreeFragment.postCallback(e);
            }
        }
    }

    static void onActivityResult(BraintreeFragment braintreeFragment, int i, Intent intent) {
        if (i == -1) {
            braintreeFragment.sendAnalyticsEvent("google-payment.authorized");
            tokenize(braintreeFragment, PaymentData.getFromIntent(intent));
        } else if (i == 1) {
            braintreeFragment.sendAnalyticsEvent("google-payment.failed");
            braintreeFragment.postCallback((Exception) new GooglePaymentException("An error was encountered during the Google Payments flow. See the status object in this exception for more details.", AutoResolveHelper.getStatusFromIntent(intent)));
        } else if (i == 0) {
            braintreeFragment.sendAnalyticsEvent("google-payment.canceled");
        }
    }

    static int getEnvironment(AndroidPayConfiguration androidPayConfiguration) {
        return BuildConfig.FLAVOR_server.equals(androidPayConfiguration.getEnvironment()) ? 1 : 3;
    }

    static PaymentMethodTokenizationParameters getTokenizationParameters(BraintreeFragment braintreeFragment) {
        String str;
        JSONObject build = new MetadataBuilder().integration(braintreeFragment.getIntegrationType()).sessionId(braintreeFragment.getSessionId()).version().build();
        try {
            str = build.getString(ClientCookie.VERSION_ATTR);
        } catch (JSONException unused) {
            str = "2.16.0";
        }
        String str2 = "braintree:authorizationFingerprint";
        String str3 = "braintree:metadata";
        PaymentMethodTokenizationParameters.Builder addParameter = PaymentMethodTokenizationParameters.newBuilder().setPaymentMethodTokenizationType(1).addParameter("gateway", "braintree").addParameter("braintree:merchantId", braintreeFragment.getConfiguration().getMerchantId()).addParameter(str2, braintreeFragment.getConfiguration().getAndroidPay().getGoogleAuthorizationFingerprint()).addParameter("braintree:apiVersion", "v1").addParameter("braintree:sdkVersion", str).addParameter(str3, build.toString());
        if (braintreeFragment.getAuthorization() instanceof TokenizationKey) {
            addParameter.addParameter("braintree:clientKey", braintreeFragment.getAuthorization().getBearer());
        }
        return addParameter.build();
    }

    static ArrayList<Integer> getAllowedCardNetworks(BraintreeFragment braintreeFragment) {
        String[] supportedNetworks;
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String str : braintreeFragment.getConfiguration().getAndroidPay().getSupportedNetworks()) {
            char c = 65535;
            switch (str.hashCode()) {
                case -2038717326:
                    if (str.equals(MASTERCARD_NETWORK)) {
                        c = 1;
                        break;
                    }
                    break;
                case 2997727:
                    if (str.equals(AMEX_NETWORK)) {
                        c = 2;
                        break;
                    }
                    break;
                case 3619905:
                    if (str.equals(VISA_NETWORK)) {
                        c = 0;
                        break;
                    }
                    break;
                case 273184745:
                    if (str.equals(DISCOVER_NETWORK)) {
                        c = 3;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                arrayList.add(Integer.valueOf(5));
            } else if (c == 1) {
                arrayList.add(Integer.valueOf(4));
            } else if (c == 2) {
                arrayList.add(Integer.valueOf(1));
            } else if (c == 3) {
                arrayList.add(Integer.valueOf(2));
            }
        }
        return arrayList;
    }

    private static boolean validateManifest(Context context) {
        ActivityInfo activityInfo = ManifestValidator.getActivityInfo(context, GooglePaymentActivity.class);
        return activityInfo != null && activityInfo.getThemeResource() == C1102R.C1108style.bt_transparent_activity;
    }
}
