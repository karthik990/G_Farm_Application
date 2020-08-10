package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BraintreePaymentResult implements Parcelable {
    public static final Creator<BraintreePaymentResult> CREATOR = new Creator<BraintreePaymentResult>() {
        public BraintreePaymentResult createFromParcel(Parcel parcel) {
            return new BraintreePaymentResult(parcel);
        }

        public BraintreePaymentResult[] newArray(int i) {
            return new BraintreePaymentResult[i];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    public BraintreePaymentResult() {
    }

    protected BraintreePaymentResult(Parcel parcel) {
    }
}
