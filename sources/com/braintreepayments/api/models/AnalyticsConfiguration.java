package com.braintreepayments.api.models;

import android.text.TextUtils;
import com.braintreepayments.api.Json;
import org.json.JSONException;
import org.json.JSONObject;

public class AnalyticsConfiguration {
    private static final String URL_KEY = "url";
    private String mUrl;

    public static AnalyticsConfiguration fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        AnalyticsConfiguration analyticsConfiguration = new AnalyticsConfiguration();
        analyticsConfiguration.mUrl = Json.optString(jSONObject, "url", null);
        return analyticsConfiguration;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject().put("url", this.mUrl);
        } catch (JSONException unused) {
            return new JSONObject();
        }
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean isEnabled() {
        return !TextUtils.isEmpty(this.mUrl);
    }
}
