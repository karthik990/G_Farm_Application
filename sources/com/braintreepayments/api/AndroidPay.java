package com.braintreepayments.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.braintreepayments.api.exceptions.AndroidPayException;
import com.braintreepayments.api.exceptions.BraintreeException;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.TokenizationParametersListener;
import com.braintreepayments.api.internal.ManifestValidator;
import com.braintreepayments.api.models.AndroidPayCardNonce;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.Wallet;
import java.util.ArrayList;
import org.json.JSONException;

@Deprecated
public class AndroidPay {
    @Deprecated
    public static void isReadyToPay(final BraintreeFragment braintreeFragment, final BraintreeResponseListener<Boolean> braintreeResponseListener) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                if (!configuration.getAndroidPay().isEnabled(braintreeFragment.getApplicationContext())) {
                    braintreeResponseListener.onResponse(Boolean.valueOf(false));
                } else {
                    braintreeFragment.getGoogleApiClient(new BraintreeResponseListener<GoogleApiClient>() {
                        public void onResponse(GoogleApiClient googleApiClient) {
                            Wallet.Payments.isReadyToPay(googleApiClient).setResultCallback(new ResultCallback<BooleanResult>() {
                                public void onResult(BooleanResult booleanResult) {
                                    braintreeResponseListener.onResponse(Boolean.valueOf(booleanResult.getStatus().isSuccess() && booleanResult.getValue()));
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Deprecated
    public static void getTokenizationParameters(final BraintreeFragment braintreeFragment, final TokenizationParametersListener tokenizationParametersListener) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                tokenizationParametersListener.onResult(GooglePayment.getTokenizationParameters(braintreeFragment), GooglePayment.getAllowedCardNetworks(braintreeFragment));
            }
        });
    }

    @Deprecated
    public static void requestAndroidPay(BraintreeFragment braintreeFragment, Cart cart, boolean z, boolean z2, ArrayList<CountrySpecification> arrayList) {
        braintreeFragment.sendAnalyticsEvent("android-pay.selected");
        String str = "android-pay.failed";
        if (!validateManifest(braintreeFragment.getApplicationContext())) {
            braintreeFragment.postCallback((Exception) new BraintreeException("AndroidPayActivity was not found in the Android manifest, or did not have a theme of R.style.bt_transparent_activity"));
            braintreeFragment.sendAnalyticsEvent(str);
        } else if (cart == null) {
            braintreeFragment.postCallback((Exception) new BraintreeException("Cannot pass null cart to performMaskedWalletRequest"));
            braintreeFragment.sendAnalyticsEvent(str);
        } else {
            final BraintreeFragment braintreeFragment2 = braintreeFragment;
            final Cart cart2 = cart;
            final boolean z3 = z;
            final boolean z4 = z2;
            final ArrayList<CountrySpecification> arrayList2 = arrayList;
            C10223 r2 = new ConfigurationListener() {
                public void onConfigurationFetched(Configuration configuration) {
                    braintreeFragment2.sendAnalyticsEvent("android-pay.started");
                    String str = "com.braintreepayments.api.EXTRA_MERCHANT_NAME";
                    String str2 = "com.braintreepayments.api.EXTRA_CART";
                    String str3 = "com.braintreepayments.api.EXTRA_TOKENIZATION_PARAMETERS";
                    String str4 = "com.braintreepayments.api.EXTRA_ALLOWED_CARD_NETWORKS";
                    String str5 = "com.braintreepayments.api.EXTRA_SHIPPING_ADDRESS_REQUIRED";
                    String str6 = "com.braintreepayments.api.EXTRA_PHONE_NUMBER_REQUIRED";
                    String str7 = "com.braintreepayments.api.EXTRA_ALLOWED_COUNTRIES";
                    braintreeFragment2.startActivityForResult(new Intent(braintreeFragment2.getApplicationContext(), AndroidPayActivity.class).putExtra("com.braintreepayments.api.EXTRA_ENVIRONMENT", GooglePayment.getEnvironment(configuration.getAndroidPay())).putExtra(str, configuration.getAndroidPay().getDisplayName()).putExtra(str2, cart2).putExtra(str3, GooglePayment.getTokenizationParameters(braintreeFragment2)).putIntegerArrayListExtra(str4, GooglePayment.getAllowedCardNetworks(braintreeFragment2)).putExtra(str5, z3).putExtra(str6, z4).putParcelableArrayListExtra(str7, arrayList2).putExtra("com.braintreepayments.api.EXTRA_REQUEST_TYPE", 1), BraintreeRequestCodes.ANDROID_PAY);
                }
            };
            braintreeFragment.waitForConfiguration(r2);
        }
    }

    @Deprecated
    public static void changePaymentMethod(final BraintreeFragment braintreeFragment, final AndroidPayCardNonce androidPayCardNonce) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                braintreeFragment.sendAnalyticsEvent("android-pay.change-masked-wallet");
                String str = "com.braintreepayments.api.EXTRA_GOOGLE_TRANSACTION_ID";
                String str2 = "com.braintreepayments.api.EXTRA_CART";
                braintreeFragment.startActivityForResult(new Intent(braintreeFragment.getApplicationContext(), AndroidPayActivity.class).putExtra("com.braintreepayments.api.EXTRA_ENVIRONMENT", GooglePayment.getEnvironment(configuration.getAndroidPay())).putExtra(str, androidPayCardNonce.getGoogleTransactionId()).putExtra(str2, androidPayCardNonce.getCart()).putExtra("com.braintreepayments.api.EXTRA_REQUEST_TYPE", 2), BraintreeRequestCodes.ANDROID_PAY);
            }
        });
    }

    @Deprecated
    public static void tokenize(BraintreeFragment braintreeFragment, FullWallet fullWallet) {
        tokenize(braintreeFragment, fullWallet, null);
    }

    @Deprecated
    public static void tokenize(BraintreeFragment braintreeFragment, FullWallet fullWallet, Cart cart) {
        try {
            braintreeFragment.postCallback((PaymentMethodNonce) AndroidPayCardNonce.fromFullWallet(fullWallet, cart));
            braintreeFragment.sendAnalyticsEvent("android-pay.nonce-received");
        } catch (JSONException unused) {
            braintreeFragment.sendAnalyticsEvent("android-pay.failed");
            try {
                braintreeFragment.postCallback((Exception) ErrorWithResponse.fromJson(fullWallet.getPaymentMethodToken().getToken()));
            } catch (JSONException e) {
                braintreeFragment.postCallback((Exception) e);
            }
        }
    }

    static void onActivityResult(BraintreeFragment braintreeFragment, int i, Intent intent) {
        if (i == -1) {
            String str = "com.google.android.gms.wallet.EXTRA_FULL_WALLET";
            if (intent.hasExtra(str)) {
                braintreeFragment.sendAnalyticsEvent("android-pay.authorized");
                tokenize(braintreeFragment, intent.getParcelableExtra(str), intent.getParcelableExtra("com.braintreepayments.api.EXTRA_CART"));
            }
        } else if (i == 0) {
            braintreeFragment.sendAnalyticsEvent("android-pay.canceled");
        } else {
            if (intent != null) {
                String str2 = "com.braintreepayments.api.EXTRA_ERROR";
                if (intent.hasExtra(str2)) {
                    braintreeFragment.postCallback((Exception) new AndroidPayException(intent.getStringExtra(str2)));
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Android Pay error code: ");
                    sb.append(intent.getIntExtra("com.google.android.gms.wallet.EXTRA_ERROR_CODE", -1));
                    sb.append(" see https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants for more details");
                    braintreeFragment.postCallback((Exception) new AndroidPayException(sb.toString()));
                }
            }
            braintreeFragment.sendAnalyticsEvent("android-pay.failed");
        }
    }

    private static boolean validateManifest(Context context) {
        ActivityInfo activityInfo = ManifestValidator.getActivityInfo(context, AndroidPayActivity.class);
        return activityInfo != null && activityInfo.getThemeResource() == C1074R.C1080style.bt_transparent_activity;
    }
}
