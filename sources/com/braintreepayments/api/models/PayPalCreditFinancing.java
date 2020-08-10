package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

public class PayPalCreditFinancing implements Parcelable {
    private static final String CARD_AMOUNT_IMMUTABLE_KEY = "cardAmountImmutable";
    public static final Creator<PayPalCreditFinancing> CREATOR = new Creator<PayPalCreditFinancing>() {
        public PayPalCreditFinancing createFromParcel(Parcel parcel) {
            return new PayPalCreditFinancing(parcel);
        }

        public PayPalCreditFinancing[] newArray(int i) {
            return new PayPalCreditFinancing[i];
        }
    };
    private static final String MONTHLY_PAYMENT_KEY = "monthlyPayment";
    private static final String PAYER_ACCEPTANCE_KEY = "payerAcceptance";
    private static final String TERM_KEY = "term";
    private static final String TOTAL_COST_KEY = "totalCost";
    private static final String TOTAL_INTEREST_KEY = "totalInterest";
    private boolean mCardAmountImmutable;
    private PayPalCreditFinancingAmount mMonthlyPayment;
    private boolean mPayerAcceptance;
    private int mTerm;
    private PayPalCreditFinancingAmount mTotalCost;
    private PayPalCreditFinancingAmount mTotalInterest;

    public int describeContents() {
        return 0;
    }

    private PayPalCreditFinancing() {
    }

    public static PayPalCreditFinancing fromJson(JSONObject jSONObject) throws JSONException {
        PayPalCreditFinancing payPalCreditFinancing = new PayPalCreditFinancing();
        if (jSONObject == null) {
            return payPalCreditFinancing;
        }
        payPalCreditFinancing.mCardAmountImmutable = jSONObject.optBoolean(CARD_AMOUNT_IMMUTABLE_KEY, false);
        payPalCreditFinancing.mMonthlyPayment = PayPalCreditFinancingAmount.fromJson(jSONObject.getJSONObject(MONTHLY_PAYMENT_KEY));
        payPalCreditFinancing.mPayerAcceptance = jSONObject.optBoolean(PAYER_ACCEPTANCE_KEY, false);
        payPalCreditFinancing.mTerm = jSONObject.optInt("term", 0);
        payPalCreditFinancing.mTotalCost = PayPalCreditFinancingAmount.fromJson(jSONObject.getJSONObject(TOTAL_COST_KEY));
        payPalCreditFinancing.mTotalInterest = PayPalCreditFinancingAmount.fromJson(jSONObject.getJSONObject(TOTAL_INTEREST_KEY));
        return payPalCreditFinancing;
    }

    public int getTerm() {
        return this.mTerm;
    }

    public boolean isCardAmountImmutable() {
        return this.mCardAmountImmutable;
    }

    public PayPalCreditFinancingAmount getMonthlyPayment() {
        return this.mMonthlyPayment;
    }

    public boolean hasPayerAcceptance() {
        return this.mPayerAcceptance;
    }

    public PayPalCreditFinancingAmount getTotalCost() {
        return this.mTotalCost;
    }

    public PayPalCreditFinancingAmount getTotalInterest() {
        return this.mTotalInterest;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mCardAmountImmutable ? (byte) 1 : 0);
        parcel.writeParcelable(this.mMonthlyPayment, i);
        parcel.writeByte(this.mPayerAcceptance ? (byte) 1 : 0);
        parcel.writeInt(this.mTerm);
        parcel.writeParcelable(this.mTotalCost, i);
        parcel.writeParcelable(this.mTotalInterest, i);
    }

    private PayPalCreditFinancing(Parcel parcel) {
        boolean z = true;
        this.mCardAmountImmutable = parcel.readByte() != 0;
        this.mMonthlyPayment = (PayPalCreditFinancingAmount) parcel.readParcelable(PayPalCreditFinancingAmount.class.getClassLoader());
        if (parcel.readByte() == 0) {
            z = false;
        }
        this.mPayerAcceptance = z;
        this.mTerm = parcel.readInt();
        this.mTotalCost = (PayPalCreditFinancingAmount) parcel.readParcelable(PayPalCreditFinancingAmount.class.getClassLoader());
        this.mTotalInterest = (PayPalCreditFinancingAmount) parcel.readParcelable(PayPalCreditFinancingAmount.class.getClassLoader());
    }
}
