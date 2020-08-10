package com.braintreepayments.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.braintreepayments.api.exceptions.AppSwitchNotAvailableException;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCallback;
import com.braintreepayments.api.internal.AppHelper;
import com.braintreepayments.api.internal.BraintreeSharedPreferences;
import com.braintreepayments.api.internal.SignatureVerification;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.ClientToken;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.MetadataBuilder;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.VenmoAccountBuilder;
import com.braintreepayments.api.models.VenmoAccountNonce;
import com.braintreepayments.api.models.VenmoConfiguration;
import org.json.JSONException;
import org.json.JSONObject;

public class Venmo {
    static final String APP_SWITCH_ACTIVITY = "controller.SetupMerchantActivity";
    static final String CERTIFICATE_ISSUER = "CN=Andrew Kortina,OU=Engineering,O=Venmo,L=Philadelphia,ST=PA,C=US";
    static final String CERTIFICATE_SUBJECT = "CN=Andrew Kortina,OU=Engineering,O=Venmo,L=Philadelphia,ST=PA,C=US";
    static final String EXTRA_ACCESS_TOKEN = "com.braintreepayments.api.ACCESS_TOKEN";
    static final String EXTRA_BRAINTREE_DATA = "com.braintreepayments.api.EXTRA_BRAINTREE_DATA";
    static final String EXTRA_ENVIRONMENT = "com.braintreepayments.api.ENVIRONMENT";
    static final String EXTRA_MERCHANT_ID = "com.braintreepayments.api.MERCHANT_ID";
    static final String EXTRA_PAYMENT_METHOD_NONCE = "com.braintreepayments.api.EXTRA_PAYMENT_METHOD_NONCE";
    static final String EXTRA_USERNAME = "com.braintreepayments.api.EXTRA_USER_NAME";
    private static final String META_KEY = "_meta";
    static final String PACKAGE_NAME = "com.venmo";
    static final int PUBLIC_KEY_HASH_CODE = -129711843;
    private static final String VAULT_VENMO_KEY = "com.braintreepayments.api.Venmo.VAULT_VENMO_KEY";

    public static boolean isVenmoInstalled(Context context) {
        if (AppHelper.isIntentAvailable(context, getVenmoIntent())) {
            String str = "CN=Andrew Kortina,OU=Engineering,O=Venmo,L=Philadelphia,ST=PA,C=US";
            if (SignatureVerification.isSignatureValid(context, PACKAGE_NAME, str, str, PUBLIC_KEY_HASH_CODE)) {
                return true;
            }
        }
        return false;
    }

    public static void openVenmoAppPageInGooglePlay(BraintreeFragment braintreeFragment) {
        braintreeFragment.sendAnalyticsEvent("android.pay-with-venmo.app-store.invoked");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.venmo"));
        braintreeFragment.startActivity(intent);
    }

    private static Intent getVenmoIntent() {
        return new Intent().setComponent(new ComponentName(PACKAGE_NAME, "com.venmo.controller.SetupMerchantActivity"));
    }

    static Intent getLaunchIntent(VenmoConfiguration venmoConfiguration, String str, BraintreeFragment braintreeFragment) {
        Intent putExtra = getVenmoIntent().putExtra(EXTRA_MERCHANT_ID, str).putExtra(EXTRA_ACCESS_TOKEN, venmoConfiguration.getAccessToken()).putExtra(EXTRA_ENVIRONMENT, venmoConfiguration.getEnvironment());
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("_meta", new MetadataBuilder().sessionId(braintreeFragment.getSessionId()).integration(braintreeFragment.getIntegrationType()).version().build());
            putExtra.putExtra(EXTRA_BRAINTREE_DATA, jSONObject.toString());
        } catch (JSONException unused) {
        }
        return putExtra;
    }

    public static void authorizeAccount(final BraintreeFragment braintreeFragment, final boolean z, final String str) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                braintreeFragment.sendAnalyticsEvent("pay-with-venmo.selected");
                String str = str;
                if (TextUtils.isEmpty(str)) {
                    str = configuration.getPayWithVenmo().getMerchantId();
                }
                String str2 = !configuration.getPayWithVenmo().isAccessTokenValid() ? "Venmo is not enabled" : !Venmo.isVenmoInstalled(braintreeFragment.getApplicationContext()) ? "Venmo is not installed" : "";
                if (!TextUtils.isEmpty(str2)) {
                    braintreeFragment.postCallback((Exception) new AppSwitchNotAvailableException(str2));
                    braintreeFragment.sendAnalyticsEvent("pay-with-venmo.app-switch.failed");
                    return;
                }
                Venmo.persistVenmoVaultOption(z && (braintreeFragment.getAuthorization() instanceof ClientToken), braintreeFragment.getApplicationContext());
                braintreeFragment.startActivityForResult(Venmo.getLaunchIntent(configuration.getPayWithVenmo(), str, braintreeFragment), BraintreeRequestCodes.VENMO);
                braintreeFragment.sendAnalyticsEvent("pay-with-venmo.app-switch.started");
            }
        });
    }

    public static void authorizeAccount(BraintreeFragment braintreeFragment, boolean z) {
        authorizeAccount(braintreeFragment, z, null);
    }

    public static void authorizeAccount(BraintreeFragment braintreeFragment) {
        authorizeAccount(braintreeFragment, false, null);
    }

    /* access modifiers changed from: private */
    public static void persistVenmoVaultOption(boolean z, Context context) {
        BraintreeSharedPreferences.getSharedPreferences(context).edit().putBoolean(VAULT_VENMO_KEY, z).apply();
    }

    private static boolean shouldVault(Context context) {
        return BraintreeSharedPreferences.getSharedPreferences(context).getBoolean(VAULT_VENMO_KEY, false);
    }

    static void onActivityResult(BraintreeFragment braintreeFragment, int i, Intent intent) {
        if (i == -1) {
            braintreeFragment.sendAnalyticsEvent("pay-with-venmo.app-switch.success");
            String stringExtra = intent.getStringExtra(EXTRA_PAYMENT_METHOD_NONCE);
            if (!shouldVault(braintreeFragment.getApplicationContext()) || !(braintreeFragment.getAuthorization() instanceof ClientToken)) {
                String stringExtra2 = intent.getStringExtra(EXTRA_USERNAME);
                braintreeFragment.postCallback((PaymentMethodNonce) new VenmoAccountNonce(stringExtra, stringExtra2, stringExtra2));
                return;
            }
            vault(braintreeFragment, stringExtra);
        } else if (i == 0) {
            braintreeFragment.sendAnalyticsEvent("pay-with-venmo.app-switch.canceled");
        }
    }

    private static void vault(final BraintreeFragment braintreeFragment, String str) {
        TokenizationClient.tokenize(braintreeFragment, new VenmoAccountBuilder().nonce(str), new PaymentMethodNonceCallback() {
            public void success(PaymentMethodNonce paymentMethodNonce) {
                braintreeFragment.postCallback(paymentMethodNonce);
                braintreeFragment.sendAnalyticsEvent("pay-with-venmo.vault.success");
            }

            public void failure(Exception exc) {
                braintreeFragment.postCallback(exc);
                braintreeFragment.sendAnalyticsEvent("pay-with-venmo.vault.failed");
            }
        });
    }
}
