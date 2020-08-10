package com.android.billingclient.api;

import org.json.JSONException;

public class PriceChangeFlowParams {
    /* access modifiers changed from: private */
    public SkuDetails skuDetails;

    public static class Builder {
        private SkuDetails skuDetails;

        public Builder setSkuDetails(SkuDetails skuDetails2) {
            this.skuDetails = skuDetails2;
            return this;
        }

        private Builder setSkuDetails(String str) {
            try {
                this.skuDetails = new SkuDetails(str);
                return this;
            } catch (JSONException unused) {
                throw new RuntimeException("Incorrect skuDetails JSON object!");
            }
        }

        public PriceChangeFlowParams build() {
            PriceChangeFlowParams priceChangeFlowParams = new PriceChangeFlowParams();
            SkuDetails skuDetails2 = this.skuDetails;
            if (skuDetails2 != null) {
                priceChangeFlowParams.skuDetails = skuDetails2;
                return priceChangeFlowParams;
            }
            throw new RuntimeException("SkuDetails must be set");
        }
    }

    public SkuDetails getSkuDetails() {
        return this.skuDetails;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
