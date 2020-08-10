package com.braintreepayments.api.models;

import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class IdealRequest {
    private static final String AMOUNT_KEY = "amount";
    private static final String CURRENCY_KEY = "currency";
    private static final String ISSUER_KEY = "issuer";
    private static final String ORDER_ID_KEY = "order_id";
    private static final String REDIRECT_URL_KEY = "redirect_url";
    private static final String ROUTE_ID_KEY = "route_id";
    private String mAmount;
    private String mCurrency;
    private String mIssuerId;
    private String mOrderId;

    public IdealRequest orderId(String str) {
        this.mOrderId = str;
        return this;
    }

    public IdealRequest issuerId(String str) {
        this.mIssuerId = str;
        return this;
    }

    public IdealRequest amount(String str) {
        this.mAmount = str;
        return this;
    }

    public IdealRequest currency(String str) {
        this.mCurrency = str;
        return this;
    }

    public String build(String str, String str2) {
        try {
            return new JSONObject().put(ROUTE_ID_KEY, str2).put(ORDER_ID_KEY, this.mOrderId).put(ISSUER_KEY, this.mIssuerId).put(AMOUNT_KEY, this.mAmount).put("currency", this.mCurrency).put(REDIRECT_URL_KEY, str).toString();
        } catch (JSONException unused) {
            return new JSONObject().toString();
        }
    }
}
