package com.braintreepayments.api.models;

import com.braintreepayments.api.Json;
import org.json.JSONException;
import org.json.JSONObject;

public class PayPalPaymentResource {
    private static final String AGREEMENT_SETUP_KEY = "agreementSetup";
    private static final String APPROVAL_URL_KEY = "approvalUrl";
    private static final String PAYMENT_RESOURCE_KEY = "paymentResource";
    private static final String REDIRECT_URL_KEY = "redirectUrl";
    private String mRedirectUrl;

    public PayPalPaymentResource redirectUrl(String str) {
        this.mRedirectUrl = str;
        return this;
    }

    public String getRedirectUrl() {
        return this.mRedirectUrl;
    }

    public static PayPalPaymentResource fromJson(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        PayPalPaymentResource payPalPaymentResource = new PayPalPaymentResource();
        JSONObject optJSONObject = jSONObject.optJSONObject(PAYMENT_RESOURCE_KEY);
        String str2 = "";
        if (optJSONObject != null) {
            payPalPaymentResource.redirectUrl(Json.optString(optJSONObject, REDIRECT_URL_KEY, str2));
        } else {
            payPalPaymentResource.redirectUrl(Json.optString(jSONObject.optJSONObject(AGREEMENT_SETUP_KEY), APPROVAL_URL_KEY, str2));
        }
        return payPalPaymentResource;
    }
}
