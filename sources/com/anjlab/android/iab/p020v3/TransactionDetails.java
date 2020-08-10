package com.anjlab.android.iab.p020v3;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.anjlab.android.iab.v3.TransactionDetails */
public class TransactionDetails implements Parcelable {
    public static final Creator<TransactionDetails> CREATOR = new Creator<TransactionDetails>() {
        public TransactionDetails createFromParcel(Parcel parcel) {
            return new TransactionDetails(parcel);
        }

        public TransactionDetails[] newArray(int i) {
            return new TransactionDetails[i];
        }
    };
    @Deprecated
    public final String orderId = this.purchaseInfo.purchaseData.orderId;
    @Deprecated
    public final String productId = this.purchaseInfo.purchaseData.productId;
    public final PurchaseInfo purchaseInfo;
    @Deprecated
    public final Date purchaseTime = this.purchaseInfo.purchaseData.purchaseTime;
    @Deprecated
    public final String purchaseToken = this.purchaseInfo.purchaseData.purchaseToken;

    public int describeContents() {
        return 0;
    }

    public TransactionDetails(PurchaseInfo purchaseInfo2) {
        this.purchaseInfo = purchaseInfo2;
    }

    public String toString() {
        return String.format(Locale.US, "%s purchased at %s(%s). Token: %s, Signature: %s", new Object[]{this.productId, this.purchaseTime, this.orderId, this.purchaseToken, this.purchaseInfo.signature});
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TransactionDetails transactionDetails = (TransactionDetails) obj;
        String str = this.orderId;
        String str2 = transactionDetails.orderId;
        if (str == null ? str2 != null : !str.equals(str2)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        String str = this.orderId;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.purchaseInfo, i);
    }

    protected TransactionDetails(Parcel parcel) {
        this.purchaseInfo = (PurchaseInfo) parcel.readParcelable(PurchaseInfo.class.getClassLoader());
    }
}
