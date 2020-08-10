package com.android.billingclient.api;

import android.text.TextUtils;
import com.android.billingclient.util.BillingHelper;
import com.anjlab.android.iab.p020v3.Constants;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SkuDetails {
    private final String mOriginalJson;
    private final JSONObject mParsedJson = new JSONObject(this.mOriginalJson);

    public static class SkuDetailsResult {
        private String mDebugMessage;
        private int mResponseCode;
        private List<SkuDetails> mSkuDetailsList;

        public SkuDetailsResult(int i, String str, List<SkuDetails> list) {
            this.mResponseCode = i;
            this.mDebugMessage = str;
            this.mSkuDetailsList = list;
        }

        public List<SkuDetails> getSkuDetailsList() {
            return this.mSkuDetailsList;
        }

        public int getResponseCode() {
            return this.mResponseCode;
        }

        public String getDebugMessage() {
            return this.mDebugMessage;
        }
    }

    public SkuDetails(String str) throws JSONException {
        this.mOriginalJson = str;
    }

    public String getOriginalJson() {
        return this.mOriginalJson;
    }

    public String getSku() {
        return this.mParsedJson.optString("productId");
    }

    public String getType() {
        return this.mParsedJson.optString("type");
    }

    public String getPrice() {
        return this.mParsedJson.optString("price");
    }

    public long getPriceAmountMicros() {
        return this.mParsedJson.optLong(Constants.RESPONSE_PRICE_MICROS);
    }

    public String getPriceCurrencyCode() {
        return this.mParsedJson.optString(Constants.RESPONSE_PRICE_CURRENCY);
    }

    public String getOriginalPrice() {
        String str = "original_price";
        if (this.mParsedJson.has(str)) {
            return this.mParsedJson.optString(str);
        }
        return getPrice();
    }

    public long getOriginalPriceAmountMicros() {
        String str = "original_price_micros";
        if (this.mParsedJson.has(str)) {
            return this.mParsedJson.optLong(str);
        }
        return getPriceAmountMicros();
    }

    public String getTitle() {
        return this.mParsedJson.optString("title");
    }

    public String getDescription() {
        return this.mParsedJson.optString(Constants.RESPONSE_DESCRIPTION);
    }

    public String getSubscriptionPeriod() {
        return this.mParsedJson.optString(Constants.RESPONSE_SUBSCRIPTION_PERIOD);
    }

    public String getFreeTrialPeriod() {
        return this.mParsedJson.optString(Constants.RESPONSE_FREE_TRIAL_PERIOD);
    }

    public String getIntroductoryPrice() {
        return this.mParsedJson.optString(Constants.RESPONSE_INTRODUCTORY_PRICE);
    }

    public long getIntroductoryPriceAmountMicros() {
        return this.mParsedJson.optLong(Constants.RESPONSE_INTRODUCTORY_PRICE_MICROS);
    }

    public String getIntroductoryPricePeriod() {
        return this.mParsedJson.optString(Constants.RESPONSE_INTRODUCTORY_PRICE_PERIOD);
    }

    public String getIntroductoryPriceCycles() {
        return this.mParsedJson.optString(Constants.RESPONSE_INTRODUCTORY_PRICE_CYCLES);
    }

    public String getIconUrl() {
        return this.mParsedJson.optString("iconUrl");
    }

    public boolean isRewarded() {
        return this.mParsedJson.has(BillingFlowParams.EXTRA_PARAM_KEY_RSKU);
    }

    /* access modifiers changed from: 0000 */
    public String getSkuDetailsToken() {
        return this.mParsedJson.optString(BillingHelper.EXTRA_PARAM_KEY_SKU_DETAILS_TOKEN);
    }

    /* access modifiers changed from: 0000 */
    public String rewardToken() {
        return this.mParsedJson.optString(BillingFlowParams.EXTRA_PARAM_KEY_RSKU);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SkuDetails: ");
        sb.append(this.mOriginalJson);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return TextUtils.equals(this.mOriginalJson, ((SkuDetails) obj).mOriginalJson);
    }

    public int hashCode() {
        return this.mOriginalJson.hashCode();
    }
}
