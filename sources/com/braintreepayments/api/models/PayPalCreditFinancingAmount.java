package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import org.json.JSONObject;

public class PayPalCreditFinancingAmount implements Parcelable {
    public static final Creator<PayPalCreditFinancingAmount> CREATOR = new Creator<PayPalCreditFinancingAmount>() {
        public PayPalCreditFinancingAmount createFromParcel(Parcel parcel) {
            return new PayPalCreditFinancingAmount(parcel);
        }

        public PayPalCreditFinancingAmount[] newArray(int i) {
            return new PayPalCreditFinancingAmount[i];
        }
    };
    private static final String CURRENCY_KEY = "currency";
    private static final String VALUE_KEY = "value";
    private String mCurrency;
    private String mValue;

    public int describeContents() {
        return 0;
    }

    private PayPalCreditFinancingAmount() {
    }

    public static PayPalCreditFinancingAmount fromJson(JSONObject jSONObject) {
        PayPalCreditFinancingAmount payPalCreditFinancingAmount = new PayPalCreditFinancingAmount();
        if (jSONObject == null) {
            return payPalCreditFinancingAmount;
        }
        payPalCreditFinancingAmount.mCurrency = Json.optString(jSONObject, "currency", null);
        payPalCreditFinancingAmount.mValue = Json.optString(jSONObject, "value", null);
        return payPalCreditFinancingAmount;
    }

    public String getCurrency() {
        return this.mCurrency;
    }

    public String getValue() {
        return this.mValue;
    }

    public String toString() {
        return String.format("%s %s", new Object[]{this.mValue, this.mCurrency});
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCurrency);
        parcel.writeString(this.mValue);
    }

    private PayPalCreditFinancingAmount(Parcel parcel) {
        this.mCurrency = parcel.readString();
        this.mValue = parcel.readString();
    }
}
