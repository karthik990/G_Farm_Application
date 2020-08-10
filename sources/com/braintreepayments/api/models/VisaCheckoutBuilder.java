package com.braintreepayments.api.models;

import android.content.Context;
import com.visa.checkout.VisaPaymentSummary;
import org.json.JSONException;
import org.json.JSONObject;

public class VisaCheckoutBuilder extends PaymentMethodBuilder<VisaCheckoutBuilder> {
    private static final String CALL_ID = "callId";
    private static final String ENCRYPTED_KEY = "encryptedKey";
    private static final String ENCRYPTED_PAYMENT_DATA = "encryptedPaymentData";
    private static final String VISA_CHECKOUT_KEY = "visaCheckoutCard";
    private String mCallId;
    private String mEncryptedKey;
    private String mEncryptedPaymentData;

    /* access modifiers changed from: protected */
    public void buildGraphQL(Context context, JSONObject jSONObject, JSONObject jSONObject2) {
    }

    public String getApiPath() {
        return "visa_checkout_cards";
    }

    public String getResponsePaymentMethodType() {
        return "VisaCheckoutCard";
    }

    public VisaCheckoutBuilder(VisaPaymentSummary visaPaymentSummary) {
        if (visaPaymentSummary != null) {
            this.mCallId = visaPaymentSummary.getCallId();
            this.mEncryptedKey = visaPaymentSummary.getEncKey();
            this.mEncryptedPaymentData = visaPaymentSummary.getEncPaymentData();
        }
    }

    /* access modifiers changed from: protected */
    public void build(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        jSONObject2.put(CALL_ID, this.mCallId);
        jSONObject2.put(ENCRYPTED_KEY, this.mEncryptedKey);
        jSONObject2.put(ENCRYPTED_PAYMENT_DATA, this.mEncryptedPaymentData);
        jSONObject.put(VISA_CHECKOUT_KEY, jSONObject2);
    }
}
