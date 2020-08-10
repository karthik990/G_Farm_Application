package com.braintreepayments.api.models;

import android.content.Context;
import com.braintreepayments.api.exceptions.BraintreeException;
import org.json.JSONException;
import org.json.JSONObject;

public class VenmoAccountBuilder extends PaymentMethodBuilder<VenmoAccountBuilder> {
    private final String NONCE_KEY = "nonce";
    private final String VENMO_ACCOUNT_KEY = "venmoAccount";
    private String mNonce;

    /* access modifiers changed from: protected */
    public void buildGraphQL(Context context, JSONObject jSONObject, JSONObject jSONObject2) throws BraintreeException, JSONException {
    }

    public String getApiPath() {
        return "venmo_accounts";
    }

    public String getResponsePaymentMethodType() {
        return "VenmoAccount";
    }

    public VenmoAccountBuilder nonce(String str) {
        this.mNonce = str;
        return this;
    }

    /* access modifiers changed from: protected */
    public void build(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        jSONObject2.put("nonce", this.mNonce);
        jSONObject.put("venmoAccount", jSONObject2);
    }
}
