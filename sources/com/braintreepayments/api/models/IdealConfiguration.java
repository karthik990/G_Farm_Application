package com.braintreepayments.api.models;

import android.text.TextUtils;
import com.braintreepayments.api.Json;
import org.json.JSONObject;

public class IdealConfiguration {
    private static final String ASSETS_URL_KEY = "assetsUrl";
    private static final String ROUTE_ID_KEY = "routeId";
    private String mAssetsUrl;
    private String mRouteId;

    static IdealConfiguration fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        IdealConfiguration idealConfiguration = new IdealConfiguration();
        String str = "";
        idealConfiguration.mRouteId = Json.optString(jSONObject, ROUTE_ID_KEY, str);
        idealConfiguration.mAssetsUrl = Json.optString(jSONObject, ASSETS_URL_KEY, str);
        return idealConfiguration;
    }

    public String getRouteId() {
        return this.mRouteId;
    }

    public boolean isEnabled() {
        return !TextUtils.isEmpty(this.mRouteId);
    }

    public String getAssetsUrl() {
        return this.mAssetsUrl;
    }
}
