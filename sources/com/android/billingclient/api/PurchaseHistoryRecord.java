package com.android.billingclient.api;

import android.text.TextUtils;
import com.anjlab.android.iab.p020v3.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseHistoryRecord {
    private final String mOriginalJson;
    private final JSONObject mParsedJson = new JSONObject(this.mOriginalJson);
    private final String mSignature;

    public PurchaseHistoryRecord(String str, String str2) throws JSONException {
        this.mOriginalJson = str;
        this.mSignature = str2;
    }

    public String getSku() {
        return this.mParsedJson.optString("productId");
    }

    public long getPurchaseTime() {
        return this.mParsedJson.optLong(Constants.RESPONSE_PURCHASE_TIME);
    }

    public String getPurchaseToken() {
        JSONObject jSONObject = this.mParsedJson;
        return jSONObject.optString("token", jSONObject.optString(Constants.RESPONSE_PURCHASE_TOKEN));
    }

    public String getDeveloperPayload() {
        return this.mParsedJson.optString("developerPayload");
    }

    public String getOriginalJson() {
        return this.mOriginalJson;
    }

    public String getSignature() {
        return this.mSignature;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PurchaseHistoryRecord. Json: ");
        sb.append(this.mOriginalJson);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PurchaseHistoryRecord)) {
            return false;
        }
        PurchaseHistoryRecord purchaseHistoryRecord = (PurchaseHistoryRecord) obj;
        if (!TextUtils.equals(this.mOriginalJson, purchaseHistoryRecord.getOriginalJson()) || !TextUtils.equals(this.mSignature, purchaseHistoryRecord.getSignature())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.mOriginalJson.hashCode();
    }
}
