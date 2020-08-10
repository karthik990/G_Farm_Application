package com.braintreepayments.api.models;

import android.content.Context;
import com.braintreepayments.api.exceptions.BraintreeException;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class PayPalAccountBuilder extends PaymentMethodBuilder<PayPalAccountBuilder> {
    private static final String CORRELATION_ID_KEY = "correlationId";
    private static final String INTENT_KEY = "intent";
    private static final String MERCHANT_ACCOUNT_ID_KEY = "merchant_account_id";
    private static final String PAYPAL_ACCOUNT_KEY = "paypalAccount";
    private String mClientMetadataId;
    private String mIntent;
    private String mMerchantAccountId;
    private JSONObject mOneTouchCoreData = new JSONObject();

    /* access modifiers changed from: protected */
    public void buildGraphQL(Context context, JSONObject jSONObject, JSONObject jSONObject2) throws BraintreeException, JSONException {
    }

    public String getApiPath() {
        return "paypal_accounts";
    }

    public String getResponsePaymentMethodType() {
        return "PayPalAccount";
    }

    public PayPalAccountBuilder clientMetadataId(String str) {
        this.mClientMetadataId = str;
        return this;
    }

    public PayPalAccountBuilder oneTouchCoreData(JSONObject jSONObject) {
        if (jSONObject != null) {
            this.mOneTouchCoreData = jSONObject;
        }
        return this;
    }

    public PayPalAccountBuilder intent(String str) {
        this.mIntent = str;
        return this;
    }

    public PayPalAccountBuilder merchantAccountId(String str) {
        this.mMerchantAccountId = str;
        return this;
    }

    /* access modifiers changed from: protected */
    public void build(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        jSONObject2.put(CORRELATION_ID_KEY, this.mClientMetadataId);
        jSONObject2.put(INTENT_KEY, this.mIntent);
        Iterator keys = this.mOneTouchCoreData.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            jSONObject2.put(str, this.mOneTouchCoreData.get(str));
        }
        String str2 = this.mMerchantAccountId;
        if (str2 != null) {
            jSONObject.put(MERCHANT_ACCOUNT_ID_KEY, str2);
        }
        jSONObject.put(PAYPAL_ACCOUNT_KEY, jSONObject2);
    }
}
