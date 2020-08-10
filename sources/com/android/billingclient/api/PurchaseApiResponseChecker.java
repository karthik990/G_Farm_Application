package com.android.billingclient.api;

import android.os.Bundle;
import com.android.billingclient.util.BillingHelper;
import java.util.ArrayList;

final class PurchaseApiResponseChecker {
    PurchaseApiResponseChecker() {
    }

    static BillingResult checkPurchasesBundleValidity(Bundle bundle, String str, String str2) {
        BillingResult billingResult = BillingResults.INTERNAL_ERROR;
        if (bundle == null) {
            BillingHelper.logWarn(str, String.format("%s got null owned items list", new Object[]{str2}));
            return billingResult;
        }
        int responseCodeFromBundle = BillingHelper.getResponseCodeFromBundle(bundle, str);
        BillingResult build = BillingResult.newBuilder().setResponseCode(responseCodeFromBundle).setDebugMessage(BillingHelper.getDebugMessageFromBundle(bundle, str)).build();
        if (responseCodeFromBundle != 0) {
            BillingHelper.logWarn(str, String.format("%s failed. Response code: %s", new Object[]{str2, Integer.valueOf(responseCodeFromBundle)}));
            return build;
        }
        String str3 = BillingHelper.RESPONSE_INAPP_ITEM_LIST;
        if (bundle.containsKey(str3)) {
            String str4 = "INAPP_PURCHASE_DATA_LIST";
            if (bundle.containsKey(str4)) {
                String str5 = "INAPP_DATA_SIGNATURE_LIST";
                if (bundle.containsKey(str5)) {
                    ArrayList stringArrayList = bundle.getStringArrayList(str3);
                    ArrayList stringArrayList2 = bundle.getStringArrayList(str4);
                    ArrayList stringArrayList3 = bundle.getStringArrayList(str5);
                    if (stringArrayList == null) {
                        BillingHelper.logWarn(str, String.format("Bundle returned from %s contains null SKUs list.", new Object[]{str2}));
                        return billingResult;
                    } else if (stringArrayList2 == null) {
                        BillingHelper.logWarn(str, String.format("Bundle returned from %s contains null purchases list.", new Object[]{str2}));
                        return billingResult;
                    } else if (stringArrayList3 != null) {
                        return BillingResults.f83OK;
                    } else {
                        BillingHelper.logWarn(str, String.format("Bundle returned from %s contains null signatures list.", new Object[]{str2}));
                        return billingResult;
                    }
                }
            }
        }
        BillingHelper.logWarn(str, String.format("Bundle returned from %s doesn't contain required fields.", new Object[]{str2}));
        return billingResult;
    }
}
