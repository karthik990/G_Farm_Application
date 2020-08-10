package com.braintreepayments.api.models;

import org.json.JSONObject;

public class UnionPayConfiguration {
    private static final String ENABLED = "enabled";
    private boolean mEnabled;

    static UnionPayConfiguration fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        UnionPayConfiguration unionPayConfiguration = new UnionPayConfiguration();
        unionPayConfiguration.mEnabled = jSONObject.optBoolean(ENABLED, false);
        return unionPayConfiguration;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }
}
