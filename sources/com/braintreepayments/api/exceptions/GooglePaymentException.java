package com.braintreepayments.api.exceptions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;

public class GooglePaymentException extends BraintreeException implements Parcelable {
    public static final Creator<GooglePaymentException> CREATOR = new Creator<GooglePaymentException>() {
        public GooglePaymentException createFromParcel(Parcel parcel) {
            return new GooglePaymentException(parcel);
        }

        public GooglePaymentException[] newArray(int i) {
            return new GooglePaymentException[i];
        }
    };
    private Status mStatus;

    public int describeContents() {
        return 0;
    }

    public GooglePaymentException(String str, Status status) {
        super(str);
        this.mStatus = status;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
        parcel.writeParcelable(this.mStatus, 0);
    }

    protected GooglePaymentException(Parcel parcel) {
        super(parcel.readString());
        this.mStatus = (Status) parcel.readParcelable(Status.class.getClassLoader());
    }
}
