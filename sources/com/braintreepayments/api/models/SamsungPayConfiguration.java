package com.braintreepayments.api.models;

import com.braintreepayments.api.Json;
import com.braintreepayments.api.internal.ClassHelper;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SamsungPayConfiguration {
    private static final String DISPLAY_NAME_KEY = "displayName";
    private static final String ENVIRONMENT = "environment";
    private static final String SAMSUNG_AUTHORIZATION_KEY = "samsungAuthorization";
    private static final String SAMSUNG_PAY_CLASSNAME = "com.braintreepayments.api.SamsungPay";
    private static final String SERVICE_ID_KEY = "serviceId";
    private static final String SUPPORTED_CARD_BRANDS_KEY = "supportedCardBrands";
    private String mEnvironment;
    private String mMerchantDisplayName;
    private String mSamsungAuthorization;
    private String mServiceId;
    private Set<String> mSupportedCardBrands = new HashSet();

    static SamsungPayConfiguration fromJson(JSONObject jSONObject) {
        SamsungPayConfiguration samsungPayConfiguration = new SamsungPayConfiguration();
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String str = "";
        samsungPayConfiguration.mMerchantDisplayName = Json.optString(jSONObject, DISPLAY_NAME_KEY, str);
        samsungPayConfiguration.mServiceId = Json.optString(jSONObject, SERVICE_ID_KEY, str);
        try {
            JSONArray jSONArray = jSONObject.getJSONArray(SUPPORTED_CARD_BRANDS_KEY);
            for (int i = 0; i < jSONArray.length(); i++) {
                samsungPayConfiguration.mSupportedCardBrands.add(jSONArray.getString(i));
            }
        } catch (JSONException unused) {
        }
        samsungPayConfiguration.mSamsungAuthorization = Json.optString(jSONObject, SAMSUNG_AUTHORIZATION_KEY, str);
        samsungPayConfiguration.mEnvironment = Json.optString(jSONObject, ENVIRONMENT, str);
        return samsungPayConfiguration;
    }

    public boolean isEnabled() {
        return !"".equals(this.mSamsungAuthorization) && ClassHelper.isClassAvailable(SAMSUNG_PAY_CLASSNAME);
    }

    public String getMerchantDisplayName() {
        return this.mMerchantDisplayName;
    }

    public String getServiceId() {
        return this.mServiceId;
    }

    public Set<String> getSupportedCardBrands() {
        return this.mSupportedCardBrands;
    }

    public String getSamsungAuthorization() {
        return this.mSamsungAuthorization;
    }

    public String getEnvironment() {
        return this.mEnvironment;
    }
}
