package com.braintreepayments.api.models;

import android.text.TextUtils;
import com.braintreepayments.api.Json;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class GraphQLConfiguration {
    private Set<String> mFeatures;
    private String mUrl;

    public static GraphQLConfiguration fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        GraphQLConfiguration graphQLConfiguration = new GraphQLConfiguration();
        graphQLConfiguration.mUrl = Json.optString(jSONObject, "url", "");
        graphQLConfiguration.mFeatures = parseJsonFeatures(jSONObject.optJSONArray("features"));
        return graphQLConfiguration;
    }

    public boolean isEnabled() {
        return !TextUtils.isEmpty(this.mUrl);
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean isFeatureEnabled(String str) {
        return isEnabled() && this.mFeatures.contains(str);
    }

    private static Set<String> parseJsonFeatures(JSONArray jSONArray) {
        HashSet hashSet = new HashSet();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                hashSet.add(jSONArray.optString(i, ""));
            }
        }
        return hashSet;
    }
}
