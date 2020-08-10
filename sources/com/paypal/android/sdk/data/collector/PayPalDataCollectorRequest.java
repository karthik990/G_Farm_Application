package com.paypal.android.sdk.data.collector;

import java.util.HashMap;

public class PayPalDataCollectorRequest {
    private HashMap<String, String> mAdditionalData;
    private String mApplicationGuid;
    private String mClientMetadataId;
    private boolean mDisableBeacon;

    public PayPalDataCollectorRequest setAdditionalData(HashMap<String, String> hashMap) {
        this.mAdditionalData = hashMap;
        return this;
    }

    public PayPalDataCollectorRequest setApplicationGuid(String str) {
        this.mApplicationGuid = str;
        return this;
    }

    public PayPalDataCollectorRequest setClientMetadataId(String str) {
        this.mClientMetadataId = str.substring(0, Math.min(str.length(), 32));
        return this;
    }

    public PayPalDataCollectorRequest setDisableBeacon(boolean z) {
        this.mDisableBeacon = z;
        return this;
    }

    public HashMap<String, String> getAdditionalData() {
        return this.mAdditionalData;
    }

    public String getApplicationGuid() {
        return this.mApplicationGuid;
    }

    public String getClientMetadataId() {
        return this.mClientMetadataId;
    }

    public boolean isDisableBeacon() {
        return this.mDisableBeacon;
    }
}
