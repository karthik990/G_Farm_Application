package com.android.billingclient.api;

public final class BillingResult {
    /* access modifiers changed from: private */
    public String mDebugMessage;
    /* access modifiers changed from: private */
    public int mResponseCode;

    public static class Builder {
        private String mDebugMessage;
        private int mResponseCode;

        private Builder() {
        }

        public Builder setResponseCode(int i) {
            this.mResponseCode = i;
            return this;
        }

        public Builder setDebugMessage(String str) {
            this.mDebugMessage = str;
            return this;
        }

        public BillingResult build() {
            BillingResult billingResult = new BillingResult();
            billingResult.mResponseCode = this.mResponseCode;
            billingResult.mDebugMessage = this.mDebugMessage;
            return billingResult;
        }
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public String getDebugMessage() {
        return this.mDebugMessage;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
