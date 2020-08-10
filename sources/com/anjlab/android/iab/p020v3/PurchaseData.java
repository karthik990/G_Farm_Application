package com.anjlab.android.iab.p020v3;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Date;

/* renamed from: com.anjlab.android.iab.v3.PurchaseData */
public class PurchaseData implements Parcelable {
    public static final Creator<PurchaseData> CREATOR = new Creator<PurchaseData>() {
        public PurchaseData createFromParcel(Parcel parcel) {
            return new PurchaseData(parcel);
        }

        public PurchaseData[] newArray(int i) {
            return new PurchaseData[i];
        }
    };
    public boolean autoRenewing;
    public String developerPayload;
    public String orderId;
    public String packageName;
    public String productId;
    public PurchaseState purchaseState;
    public Date purchaseTime;
    public String purchaseToken;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.orderId);
        parcel.writeString(this.packageName);
        parcel.writeString(this.productId);
        Date date = this.purchaseTime;
        parcel.writeLong(date != null ? date.getTime() : -1);
        PurchaseState purchaseState2 = this.purchaseState;
        parcel.writeInt(purchaseState2 == null ? -1 : purchaseState2.ordinal());
        parcel.writeString(this.developerPayload);
        parcel.writeString(this.purchaseToken);
        parcel.writeByte(this.autoRenewing ? (byte) 1 : 0);
    }

    public PurchaseData() {
    }

    protected PurchaseData(Parcel parcel) {
        Date date;
        this.orderId = parcel.readString();
        this.packageName = parcel.readString();
        this.productId = parcel.readString();
        long readLong = parcel.readLong();
        PurchaseState purchaseState2 = null;
        if (readLong == -1) {
            date = null;
        } else {
            date = new Date(readLong);
        }
        this.purchaseTime = date;
        int readInt = parcel.readInt();
        if (readInt != -1) {
            purchaseState2 = PurchaseState.values()[readInt];
        }
        this.purchaseState = purchaseState2;
        this.developerPayload = parcel.readString();
        this.purchaseToken = parcel.readString();
        this.autoRenewing = parcel.readByte() != 0;
    }
}
