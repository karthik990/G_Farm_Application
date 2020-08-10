package com.android.billingclient.api;

import java.util.ArrayList;
import java.util.List;

public class SkuDetailsParams {
    /* access modifiers changed from: private */
    public String mSkuType;
    /* access modifiers changed from: private */
    public List<String> mSkusList;

    public static class Builder {
        private String mSkuType;
        private List<String> mSkusList;

        private Builder() {
        }

        public Builder setSkusList(List<String> list) {
            this.mSkusList = new ArrayList(list);
            return this;
        }

        public Builder setType(String str) {
            this.mSkuType = str;
            return this;
        }

        public SkuDetailsParams build() {
            SkuDetailsParams skuDetailsParams = new SkuDetailsParams();
            skuDetailsParams.mSkuType = this.mSkuType;
            skuDetailsParams.mSkusList = this.mSkusList;
            return skuDetailsParams;
        }
    }

    public String getSkuType() {
        return this.mSkuType;
    }

    public List<String> getSkusList() {
        return this.mSkusList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
