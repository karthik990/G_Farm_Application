package com.braintreepayments.api.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class CardConfiguration {
    private static final String COLLECT_DEVICE_DATA_KEY = "collectDeviceData";
    private static final String SUPPORTED_CARD_TYPES_KEY = "supportedCardTypes";
    private boolean mCollectFraudData = false;
    private final Set<String> mSupportedCardTypes = new HashSet();

    public static CardConfiguration fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        CardConfiguration cardConfiguration = new CardConfiguration();
        JSONArray optJSONArray = jSONObject.optJSONArray(SUPPORTED_CARD_TYPES_KEY);
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                cardConfiguration.mSupportedCardTypes.add(optJSONArray.optString(i, ""));
            }
        }
        cardConfiguration.mCollectFraudData = jSONObject.optBoolean(COLLECT_DEVICE_DATA_KEY, false);
        return cardConfiguration;
    }

    public Set<String> getSupportedCardTypes() {
        return Collections.unmodifiableSet(this.mSupportedCardTypes);
    }

    public boolean isFraudDataCollectionEnabled() {
        return this.mCollectFraudData;
    }
}
