package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.wallet.ShippingAddressRequirements;
import com.google.android.gms.wallet.TransactionInfo;

public class GooglePaymentRequest implements Parcelable {
    public static final Creator<GooglePaymentRequest> CREATOR = new Creator<GooglePaymentRequest>() {
        public GooglePaymentRequest createFromParcel(Parcel parcel) {
            return new GooglePaymentRequest(parcel);
        }

        public GooglePaymentRequest[] newArray(int i) {
            return new GooglePaymentRequest[i];
        }
    };
    private Boolean mAllowPrepaidCards = null;
    private Integer mBillingAddressFormat;
    private Boolean mBillingAddressRequired = null;
    private Boolean mEmailRequired = null;
    private Boolean mPhoneNumberRequired = null;
    private Boolean mShippingAddressRequired = null;
    private ShippingAddressRequirements mShippingAddressRequirements;
    private TransactionInfo mTransactionInfo;
    private Boolean mUiRequired = null;

    public int describeContents() {
        return 0;
    }

    public GooglePaymentRequest() {
    }

    public GooglePaymentRequest transactionInfo(TransactionInfo transactionInfo) {
        this.mTransactionInfo = transactionInfo;
        return this;
    }

    public GooglePaymentRequest emailRequired(boolean z) {
        this.mEmailRequired = Boolean.valueOf(z);
        return this;
    }

    public GooglePaymentRequest phoneNumberRequired(boolean z) {
        this.mPhoneNumberRequired = Boolean.valueOf(z);
        return this;
    }

    public GooglePaymentRequest billingAddressRequired(boolean z) {
        this.mBillingAddressRequired = Boolean.valueOf(z);
        return this;
    }

    public GooglePaymentRequest billingAddressFormat(int i) {
        this.mBillingAddressFormat = Integer.valueOf(i);
        return this;
    }

    public GooglePaymentRequest shippingAddressRequired(boolean z) {
        this.mShippingAddressRequired = Boolean.valueOf(z);
        return this;
    }

    public GooglePaymentRequest shippingAddressRequirements(ShippingAddressRequirements shippingAddressRequirements) {
        this.mShippingAddressRequirements = shippingAddressRequirements;
        return this;
    }

    public GooglePaymentRequest allowPrepaidCards(boolean z) {
        this.mAllowPrepaidCards = Boolean.valueOf(z);
        return this;
    }

    public GooglePaymentRequest uiRequired(boolean z) {
        this.mUiRequired = Boolean.valueOf(z);
        return this;
    }

    public TransactionInfo getTransactionInfo() {
        return this.mTransactionInfo;
    }

    public Boolean isEmailRequired() {
        return this.mEmailRequired;
    }

    public Boolean isPhoneNumberRequired() {
        return this.mPhoneNumberRequired;
    }

    public Boolean isBillingAddressRequired() {
        return this.mBillingAddressRequired;
    }

    public Integer getBillingAddressFormat() {
        return this.mBillingAddressFormat;
    }

    public Boolean isShippingAddressRequired() {
        return this.mShippingAddressRequired;
    }

    public ShippingAddressRequirements getShippingAddressRequirements() {
        return this.mShippingAddressRequirements;
    }

    public Boolean getAllowPrepaidCards() {
        return this.mAllowPrepaidCards;
    }

    public Boolean isUiRequired() {
        return this.mUiRequired;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mTransactionInfo, i);
        Boolean bool = this.mEmailRequired;
        int i2 = 2;
        int i3 = bool == null ? 0 : bool.booleanValue() ? 1 : 2;
        parcel.writeByte((byte) i3);
        Boolean bool2 = this.mPhoneNumberRequired;
        int i4 = bool2 == null ? 0 : bool2.booleanValue() ? 1 : 2;
        parcel.writeByte((byte) i4);
        Boolean bool3 = this.mBillingAddressRequired;
        int i5 = bool3 == null ? 0 : bool3.booleanValue() ? 1 : 2;
        parcel.writeByte((byte) i5);
        if (this.mBillingAddressFormat == null) {
            parcel.writeByte(0);
        } else {
            parcel.writeByte(1);
            parcel.writeInt(this.mBillingAddressFormat.intValue());
        }
        Boolean bool4 = this.mShippingAddressRequired;
        int i6 = bool4 == null ? 0 : bool4.booleanValue() ? 1 : 2;
        parcel.writeByte((byte) i6);
        parcel.writeParcelable(this.mShippingAddressRequirements, i);
        Boolean bool5 = this.mAllowPrepaidCards;
        int i7 = bool5 == null ? 0 : bool5.booleanValue() ? 1 : 2;
        parcel.writeByte((byte) i7);
        Boolean bool6 = this.mUiRequired;
        if (bool6 == null) {
            i2 = 0;
        } else if (bool6.booleanValue()) {
            i2 = 1;
        }
        parcel.writeByte((byte) i2);
    }

    protected GooglePaymentRequest(Parcel parcel) {
        Boolean bool;
        Boolean bool2;
        Boolean bool3;
        Boolean bool4;
        Boolean bool5;
        Boolean bool6 = null;
        this.mTransactionInfo = parcel.readParcelable(TransactionInfo.class.getClassLoader());
        byte readByte = parcel.readByte();
        boolean z = false;
        if (readByte == 0) {
            bool = null;
        } else {
            bool = Boolean.valueOf(readByte == 1);
        }
        this.mEmailRequired = bool;
        byte readByte2 = parcel.readByte();
        if (readByte2 == 0) {
            bool2 = null;
        } else {
            bool2 = Boolean.valueOf(readByte2 == 1);
        }
        this.mPhoneNumberRequired = bool2;
        byte readByte3 = parcel.readByte();
        if (readByte3 == 0) {
            bool3 = null;
        } else {
            bool3 = Boolean.valueOf(readByte3 == 1);
        }
        this.mBillingAddressRequired = bool3;
        if (parcel.readByte() == 0) {
            this.mBillingAddressFormat = null;
        } else {
            this.mBillingAddressFormat = Integer.valueOf(parcel.readInt());
        }
        byte readByte4 = parcel.readByte();
        if (readByte4 == 0) {
            bool4 = null;
        } else {
            bool4 = Boolean.valueOf(readByte4 == 1);
        }
        this.mShippingAddressRequired = bool4;
        this.mShippingAddressRequirements = parcel.readParcelable(ShippingAddressRequirements.class.getClassLoader());
        byte readByte5 = parcel.readByte();
        if (readByte5 == 0) {
            bool5 = null;
        } else {
            bool5 = Boolean.valueOf(readByte5 == 1);
        }
        this.mAllowPrepaidCards = bool5;
        byte readByte6 = parcel.readByte();
        if (readByte6 != 0) {
            if (readByte6 == 1) {
                z = true;
            }
            bool6 = Boolean.valueOf(z);
        }
        this.mUiRequired = bool6;
    }
}
