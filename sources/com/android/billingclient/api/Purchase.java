package com.android.billingclient.api;

import android.text.TextUtils;
import com.anjlab.android.iab.p020v3.Constants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    private final String mOriginalJson;
    private final JSONObject mParsedJson = new JSONObject(this.mOriginalJson);
    private final String mSignature;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PurchaseState {
        public static final int PENDING = 2;
        public static final int PURCHASED = 1;
        public static final int UNSPECIFIED_STATE = 0;
    }

    public static class PurchasesResult {
        private BillingResult mBillingResult;
        private List<Purchase> mPurchaseList;

        public PurchasesResult(BillingResult billingResult, List<Purchase> list) {
            this.mPurchaseList = list;
            this.mBillingResult = billingResult;
        }

        public BillingResult getBillingResult() {
            return this.mBillingResult;
        }

        public int getResponseCode() {
            return getBillingResult().getResponseCode();
        }

        public List<Purchase> getPurchasesList() {
            return this.mPurchaseList;
        }
    }

    public Purchase(String str, String str2) throws JSONException {
        this.mOriginalJson = str;
        this.mSignature = str2;
    }

    public String getOrderId() {
        return this.mParsedJson.optString("orderId");
    }

    public String getPackageName() {
        return this.mParsedJson.optString(Constants.RESPONSE_PACKAGE_NAME);
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

    public int getPurchaseState() {
        return this.mParsedJson.optInt(Constants.RESPONSE_PURCHASE_STATE, 1) != 4 ? 1 : 2;
    }

    public String getDeveloperPayload() {
        return this.mParsedJson.optString("developerPayload");
    }

    public boolean isAcknowledged() {
        return this.mParsedJson.optBoolean("acknowledged", true);
    }

    public boolean isAutoRenewing() {
        return this.mParsedJson.optBoolean(Constants.RESPONSE_AUTO_RENEWING);
    }

    public String getOriginalJson() {
        return this.mOriginalJson;
    }

    public String getSignature() {
        return this.mSignature;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Purchase. Json: ");
        sb.append(this.mOriginalJson);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Purchase)) {
            return false;
        }
        Purchase purchase = (Purchase) obj;
        if (!TextUtils.equals(this.mOriginalJson, purchase.getOriginalJson()) || !TextUtils.equals(this.mSignature, purchase.getSignature())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.mOriginalJson.hashCode();
    }
}
