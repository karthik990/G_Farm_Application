package com.android.billingclient.util;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.Purchase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;

public final class BillingHelper {
    public static final String DEBUG_MESSAGE = "DEBUG_MESSAGE";
    public static final String EXTRA_PARAMS_DEVELOPER_PAYLOAD = "developerPayload";
    public static final String EXTRA_PARAMS_ENABLE_PENDING_PURCHASES = "enablePendingPurchases";
    public static final String EXTRA_PARAM_KEY_SKU_DETAILS_TOKEN = "skuDetailsToken";
    public static final String EXTRA_PARAM_KEY_SUBS_PRICE_CHANGE = "subs_price_change";
    public static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
    private static final String INTERNAL_ERROR = "An internal error occurred.";
    public static final String LIBRARY_VERSION_KEY = "playBillingLibraryVersion";
    public static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    public static final String RESPONSE_BUY_INTENT_KEY = "BUY_INTENT";
    public static final String RESPONSE_CODE = "RESPONSE_CODE";
    public static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
    public static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";
    private static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    public static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    private static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
    public static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
    public static final String RESPONSE_SUBS_MANAGEMENT_INTENT_KEY = "SUBS_MANAGEMENT_INTENT";
    private static final String TAG = "BillingHelper";

    public static void logVerbose(String str, String str2) {
        if (Log.isLoggable(str, 2)) {
            Log.v(str, str2);
        }
    }

    public static void logWarn(String str, String str2) {
        if (Log.isLoggable(str, 5)) {
            Log.w(str, str2);
        }
    }

    public static int getResponseCodeFromIntent(Intent intent, String str) {
        return getBillingResultFromIntent(intent, str).getResponseCode();
    }

    public static BillingResult getBillingResultFromIntent(Intent intent, String str) {
        if (intent != null) {
            return BillingResult.newBuilder().setResponseCode(getResponseCodeFromBundle(intent.getExtras(), str)).setDebugMessage(getDebugMessageFromBundle(intent.getExtras(), str)).build();
        }
        logWarn(TAG, "Got null intent!");
        return BillingResult.newBuilder().setResponseCode(6).setDebugMessage(INTERNAL_ERROR).build();
    }

    public static int getResponseCodeFromBundle(Bundle bundle, String str) {
        if (bundle == null) {
            logWarn(str, "Unexpected null bundle received!");
            return 6;
        }
        Object obj = bundle.get("RESPONSE_CODE");
        if (obj == null) {
            logVerbose(str, "getResponseCodeFromBundle() got null response code, assuming OK");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected type for bundle response code: ");
            sb.append(obj.getClass().getName());
            logWarn(str, sb.toString());
            return 6;
        }
    }

    public static String getDebugMessageFromBundle(Bundle bundle, String str) {
        String str2 = "";
        if (bundle == null) {
            logWarn(str, "Unexpected null bundle received!");
            return str2;
        }
        Object obj = bundle.get(DEBUG_MESSAGE);
        if (obj == null) {
            logVerbose(str, "getDebugMessageFromBundle() got null response code, assuming OK");
            return str2;
        } else if (obj instanceof String) {
            return (String) obj;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected type for debug message: ");
            sb.append(obj.getClass().getName());
            logWarn(str, sb.toString());
            return str2;
        }
    }

    public static List<Purchase> extractPurchases(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        ArrayList stringArrayList = bundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
        ArrayList stringArrayList2 = bundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
        ArrayList arrayList = new ArrayList();
        if (stringArrayList == null || stringArrayList2 == null) {
            String str = TAG;
            logWarn(str, "Couldn't find purchase lists, trying to find single data.");
            Purchase extractPurchase = extractPurchase(bundle.getString("INAPP_PURCHASE_DATA"), bundle.getString("INAPP_DATA_SIGNATURE"));
            if (extractPurchase == null) {
                logWarn(str, "Couldn't find single purchase data as well.");
                return null;
            }
            arrayList.add(extractPurchase);
        } else {
            int i = 0;
            while (i < stringArrayList.size() && i < stringArrayList2.size()) {
                Purchase extractPurchase2 = extractPurchase((String) stringArrayList.get(i), (String) stringArrayList2.get(i));
                if (extractPurchase2 != null) {
                    arrayList.add(extractPurchase2);
                }
                i++;
            }
        }
        return arrayList;
    }

    public static Bundle constructExtraParamsForLaunchBillingFlow(BillingFlowParams billingFlowParams, boolean z, boolean z2, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(LIBRARY_VERSION_KEY, str);
        if (billingFlowParams.getReplaceSkusProrationMode() != 0) {
            bundle.putInt(BillingFlowParams.EXTRA_PARAM_KEY_REPLACE_SKUS_PRORATION_MODE, billingFlowParams.getReplaceSkusProrationMode());
        }
        if (!TextUtils.isEmpty(billingFlowParams.getAccountId())) {
            bundle.putString(BillingFlowParams.EXTRA_PARAM_KEY_ACCOUNT_ID, billingFlowParams.getAccountId());
        }
        if (billingFlowParams.getVrPurchaseFlow()) {
            bundle.putBoolean("vr", true);
        }
        if (!TextUtils.isEmpty(billingFlowParams.getOldSku())) {
            bundle.putStringArrayList("skusToReplace", new ArrayList(Arrays.asList(new String[]{billingFlowParams.getOldSku()})));
        }
        if (!TextUtils.isEmpty(billingFlowParams.getDeveloperId())) {
            bundle.putString(BillingFlowParams.EXTRA_PARAM_KEY_DEVELOPER_ID, billingFlowParams.getDeveloperId());
        }
        if (z && z2) {
            bundle.putBoolean(EXTRA_PARAMS_ENABLE_PENDING_PURCHASES, true);
        }
        return bundle;
    }

    public static Bundle constructExtraParamsForQueryPurchases(boolean z, boolean z2, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(LIBRARY_VERSION_KEY, str);
        if (z && z2) {
            bundle.putBoolean(EXTRA_PARAMS_ENABLE_PENDING_PURCHASES, true);
        }
        return bundle;
    }

    public static Bundle constructExtraParamsForLoadRewardedSku(String str, int i, int i2, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(BillingFlowParams.EXTRA_PARAM_KEY_RSKU, str);
        bundle.putString(LIBRARY_VERSION_KEY, str2);
        if (i != 0) {
            bundle.putInt(BillingFlowParams.EXTRA_PARAM_CHILD_DIRECTED, i);
        }
        if (i2 != 0) {
            bundle.putInt(BillingFlowParams.EXTRA_PARAM_UNDER_AGE_OF_CONSENT, i);
        }
        return bundle;
    }

    public static Bundle constructExtraParamsForGetSkuDetails(boolean z, boolean z2, String str) {
        Bundle bundle = new Bundle();
        if (z) {
            bundle.putString(LIBRARY_VERSION_KEY, str);
        }
        if (z && z2) {
            bundle.putBoolean(EXTRA_PARAMS_ENABLE_PENDING_PURCHASES, true);
        }
        return bundle;
    }

    public static Bundle constructExtraParamsForConsume(ConsumeParams consumeParams, boolean z, String str) {
        Bundle bundle = new Bundle();
        if (z) {
            bundle.putString(LIBRARY_VERSION_KEY, str);
        }
        String developerPayload = consumeParams.getDeveloperPayload();
        if (z && !TextUtils.isEmpty(developerPayload)) {
            bundle.putString("developerPayload", developerPayload);
        }
        return bundle;
    }

    public static Bundle constructExtraParamsForAcknowledgePurchase(AcknowledgePurchaseParams acknowledgePurchaseParams, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(LIBRARY_VERSION_KEY, str);
        String developerPayload = acknowledgePurchaseParams.getDeveloperPayload();
        if (!TextUtils.isEmpty(developerPayload)) {
            bundle.putString("developerPayload", developerPayload);
        }
        return bundle;
    }

    private static Purchase extractPurchase(String str, String str2) {
        Purchase purchase = null;
        String str3 = TAG;
        if (str == null || str2 == null) {
            logWarn(str3, "Received a bad purchase data.");
            return null;
        }
        try {
            purchase = new Purchase(str, str2);
        } catch (JSONException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Got JSONException while parsing purchase data: ");
            sb.append(e);
            logWarn(str3, sb.toString());
        }
        return purchase;
    }
}
