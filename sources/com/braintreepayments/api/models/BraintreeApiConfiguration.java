package com.braintreepayments.api.models;

import android.text.TextUtils;
import com.braintreepayments.api.Json;
import org.json.JSONObject;

public class BraintreeApiConfiguration {
    private static final String ACCESS_TOKEN_KEY = "accessToken";
    private static final String URL_KEY = "url";
    private String mAccessToken;
    private String mUrl;

    static BraintreeApiConfiguration fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        BraintreeApiConfiguration braintreeApiConfiguration = new BraintreeApiConfiguration();
        String str = "";
        braintreeApiConfiguration.mAccessToken = Json.optString(jSONObject, ACCESS_TOKEN_KEY, str);
        braintreeApiConfiguration.mUrl = Json.optString(jSONObject, "url", str);
        return braintreeApiConfiguration;
    }

    public String getAccessToken() {
        return this.mAccessToken;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean isEnabled() {
        return !TextUtils.isEmpty(this.mAccessToken);
    }
}
