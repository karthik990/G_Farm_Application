package com.braintreepayments.api;

import android.content.Context;
import android.text.TextUtils;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.internal.UUIDHelper;
import com.braintreepayments.api.models.ClientToken;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.kount.api.DataCollector.CompletionHandler;
import com.kount.api.DataCollector.Error;
import com.kount.api.DataCollector.LocationConfig;
import com.mobiroller.preview.BuildConfig;
import com.paypal.android.sdk.data.collector.InstallationIdentifier;
import com.paypal.android.sdk.data.collector.PayPalDataCollector;
import com.paypal.android.sdk.data.collector.PayPalDataCollectorRequest;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class DataCollector {
    private static final String BRAINTREE_MERCHANT_ID = "600000";
    private static final String CORRELATION_ID_KEY = "correlation_id";
    private static final String DEVICE_SESSION_ID_KEY = "device_session_id";
    private static final String FRAUD_MERCHANT_ID_KEY = "fraud_merchant_id";

    public static void collectDeviceData(BraintreeFragment braintreeFragment, BraintreeResponseListener<String> braintreeResponseListener) {
        collectDeviceData(braintreeFragment, (String) null, braintreeResponseListener);
    }

    public static void collectDeviceData(final BraintreeFragment braintreeFragment, final String str, final BraintreeResponseListener<String> braintreeResponseListener) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                final JSONObject jSONObject = new JSONObject();
                try {
                    String payPalClientMetadataId = DataCollector.getPayPalClientMetadataId(braintreeFragment.getApplicationContext());
                    if (!TextUtils.isEmpty(payPalClientMetadataId)) {
                        jSONObject.put(DataCollector.CORRELATION_ID_KEY, payPalClientMetadataId);
                    }
                } catch (JSONException unused) {
                }
                if (configuration.getKount().isEnabled()) {
                    final String str = str;
                    if (str == null) {
                        str = configuration.getKount().getKountMerchantId();
                    }
                    try {
                        final String formattedUUID = UUIDHelper.getFormattedUUID();
                        DataCollector.startDeviceCollector(braintreeFragment, str, formattedUUID, new BraintreeResponseListener<String>() {
                            public void onResponse(String str) {
                                try {
                                    jSONObject.put(DataCollector.DEVICE_SESSION_ID_KEY, formattedUUID);
                                    jSONObject.put(DataCollector.FRAUD_MERCHANT_ID_KEY, str);
                                } catch (JSONException unused) {
                                }
                                braintreeResponseListener.onResponse(jSONObject.toString());
                            }
                        });
                    } catch (ClassNotFoundException | NoClassDefFoundError | NumberFormatException unused2) {
                        braintreeResponseListener.onResponse(jSONObject.toString());
                    }
                } else {
                    braintreeResponseListener.onResponse(jSONObject.toString());
                }
            }
        });
    }

    public static void collectPayPalDeviceData(BraintreeFragment braintreeFragment, BraintreeResponseListener<String> braintreeResponseListener) {
        JSONObject jSONObject = new JSONObject();
        try {
            String payPalClientMetadataId = getPayPalClientMetadataId(braintreeFragment.getApplicationContext());
            if (!TextUtils.isEmpty(payPalClientMetadataId)) {
                jSONObject.put(CORRELATION_ID_KEY, payPalClientMetadataId);
            }
        } catch (JSONException unused) {
        }
        braintreeResponseListener.onResponse(jSONObject.toString());
    }

    @Deprecated
    public static String collectDeviceData(BraintreeFragment braintreeFragment) {
        return collectDeviceData(braintreeFragment, BRAINTREE_MERCHANT_ID);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|(1:6)|7|9) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0017 */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0025 A[Catch:{ JSONException -> 0x002a }] */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String collectDeviceData(com.braintreepayments.api.BraintreeFragment r3, java.lang.String r4) {
        /*
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r1 = com.braintreepayments.api.internal.UUIDHelper.getFormattedUUID()     // Catch:{ ClassNotFoundException | NoClassDefFoundError | NumberFormatException | JSONException -> 0x0017 }
            r2 = 0
            startDeviceCollector(r3, r4, r1, r2)     // Catch:{ ClassNotFoundException | NoClassDefFoundError | NumberFormatException | JSONException -> 0x0017 }
            java.lang.String r2 = "device_session_id"
            r0.put(r2, r1)     // Catch:{ ClassNotFoundException | NoClassDefFoundError | NumberFormatException | JSONException -> 0x0017 }
            java.lang.String r1 = "fraud_merchant_id"
            r0.put(r1, r4)     // Catch:{ ClassNotFoundException | NoClassDefFoundError | NumberFormatException | JSONException -> 0x0017 }
        L_0x0017:
            android.content.Context r3 = r3.getApplicationContext()     // Catch:{ JSONException -> 0x002a }
            java.lang.String r3 = getPayPalClientMetadataId(r3)     // Catch:{ JSONException -> 0x002a }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x002a }
            if (r4 != 0) goto L_0x002a
            java.lang.String r4 = "correlation_id"
            r0.put(r4, r3)     // Catch:{ JSONException -> 0x002a }
        L_0x002a:
            java.lang.String r3 = r0.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.braintreepayments.api.DataCollector.collectDeviceData(com.braintreepayments.api.BraintreeFragment, java.lang.String):java.lang.String");
    }

    @Deprecated
    public static String collectDeviceData(Context context, BraintreeFragment braintreeFragment) {
        return collectDeviceData(braintreeFragment);
    }

    @Deprecated
    public static String collectDeviceData(Context context, BraintreeFragment braintreeFragment, String str) {
        return collectDeviceData(braintreeFragment, str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        return com.paypal.android.sdk.data.collector.PayPalDataCollector.getClientMetadataId(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return "";
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0005 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getPayPalClientMetadataId(android.content.Context r0) {
        /*
            java.lang.String r0 = com.paypal.android.sdk.onetouch.core.PayPalOneTouchCore.getClientMetadataId(r0)     // Catch:{ NoClassDefFoundError -> 0x0005 }
            return r0
        L_0x0005:
            java.lang.String r0 = com.paypal.android.sdk.data.collector.PayPalDataCollector.getClientMetadataId(r0)     // Catch:{ NoClassDefFoundError -> 0x000a }
            return r0
        L_0x000a:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.braintreepayments.api.DataCollector.getPayPalClientMetadataId(android.content.Context):java.lang.String");
    }

    /* access modifiers changed from: private */
    public static void startDeviceCollector(final BraintreeFragment braintreeFragment, final String str, final String str2, final BraintreeResponseListener<String> braintreeResponseListener) throws ClassNotFoundException, NumberFormatException {
        braintreeFragment.sendAnalyticsEvent("data-collector.kount.started");
        Class.forName(com.kount.api.DataCollector.class.getName());
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                com.kount.api.DataCollector instance = com.kount.api.DataCollector.getInstance();
                instance.setContext(braintreeFragment.getApplicationContext());
                instance.setMerchantID(Integer.parseInt(str));
                instance.setLocationCollectorConfig(LocationConfig.COLLECT);
                instance.setEnvironment(DataCollector.getDeviceCollectorEnvironment(configuration.getEnvironment()));
                instance.collectForSession(str2, new CompletionHandler() {
                    public void completed(String str) {
                        braintreeFragment.sendAnalyticsEvent("data-collector.kount.succeeded");
                        if (braintreeResponseListener != null) {
                            braintreeResponseListener.onResponse(str);
                        }
                    }

                    public void failed(String str, Error error) {
                        braintreeFragment.sendAnalyticsEvent("data-collector.kount.failed");
                        if (braintreeResponseListener != null) {
                            braintreeResponseListener.onResponse(str);
                        }
                    }
                });
            }
        });
    }

    static void collectRiskData(final BraintreeFragment braintreeFragment, final PaymentMethodNonce paymentMethodNonce) {
        braintreeFragment.waitForConfiguration(new ConfigurationListener() {
            public void onConfigurationFetched(Configuration configuration) {
                if (configuration.getCardConfiguration().isFraudDataCollectionEnabled()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("rda_tenant", "bt_card");
                    hashMap.put("mid", configuration.getMerchantId());
                    if (braintreeFragment.getAuthorization() instanceof ClientToken) {
                        String customerId = ((ClientToken) braintreeFragment.getAuthorization()).getCustomerId();
                        if (customerId != null) {
                            hashMap.put("cid", customerId);
                        }
                    }
                    PayPalDataCollector.getClientMetadataId(braintreeFragment.getApplicationContext(), new PayPalDataCollectorRequest().setApplicationGuid(InstallationIdentifier.getInstallationGUID(braintreeFragment.getApplicationContext())).setClientMetadataId(paymentMethodNonce.getNonce()).setDisableBeacon(true).setAdditionalData(hashMap));
                }
            }
        });
    }

    static int getDeviceCollectorEnvironment(String str) {
        return BuildConfig.FLAVOR_server.equalsIgnoreCase(str) ? 2 : 1;
    }
}
