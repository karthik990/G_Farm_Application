package com.braintreepayments.api.models;

import android.content.ContentResolver;
import android.content.Context;
import android.text.TextUtils;
import com.braintreepayments.api.Json;
import com.braintreepayments.api.Venmo;
import org.json.JSONObject;

public class VenmoConfiguration {
    private static final String ACCESS_TOKEN_KEY = "accessToken";
    private static final String ENVIRONMENT_KEY = "environment";
    private static final String MERCHANT_ID_KEY = "merchantId";
    private String mAccessToken;
    private String mEnvironment;
    private String mMerchantId;

    @Deprecated
    public boolean isVenmoWhitelisted(ContentResolver contentResolver) {
        return true;
    }

    static VenmoConfiguration fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        VenmoConfiguration venmoConfiguration = new VenmoConfiguration();
        String str = "";
        venmoConfiguration.mAccessToken = Json.optString(jSONObject, ACCESS_TOKEN_KEY, str);
        venmoConfiguration.mEnvironment = Json.optString(jSONObject, ENVIRONMENT_KEY, str);
        venmoConfiguration.mMerchantId = Json.optString(jSONObject, MERCHANT_ID_KEY, str);
        return venmoConfiguration;
    }

    public String getAccessToken() {
        return this.mAccessToken;
    }

    public String getMerchantId() {
        return this.mMerchantId;
    }

    public String getEnvironment() {
        return this.mEnvironment;
    }

    public boolean isEnabled(Context context) {
        return isAccessTokenValid() && Venmo.isVenmoInstalled(context);
    }

    public boolean isAccessTokenValid() {
        return !TextUtils.isEmpty(this.mAccessToken);
    }
}
