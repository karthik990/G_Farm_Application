package com.braintreepayments.api.models;

import android.text.TextUtils;
import com.braintreepayments.api.Json;
import org.json.JSONObject;

public class KountConfiguration {
    private static final String KOUNT_MERCHANT_ID_KEY = "kountMerchantId";
    private String mKountMerchantId;

    public static KountConfiguration fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        KountConfiguration kountConfiguration = new KountConfiguration();
        kountConfiguration.mKountMerchantId = Json.optString(jSONObject, KOUNT_MERCHANT_ID_KEY, "");
        return kountConfiguration;
    }

    public boolean isEnabled() {
        return !TextUtils.isEmpty(this.mKountMerchantId);
    }

    public String getKountMerchantId() {
        return this.mKountMerchantId;
    }
}
