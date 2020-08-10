package com.anjlab.android.iab.p020v3;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.anjlab.android.iab.v3.BillingHistoryRecord */
public class BillingHistoryRecord implements Parcelable {
    public static final Creator<BillingHistoryRecord> CREATOR = new Creator<BillingHistoryRecord>() {
        public BillingHistoryRecord createFromParcel(Parcel parcel) {
            return new BillingHistoryRecord(parcel);
        }

        public BillingHistoryRecord[] newArray(int i) {
            return new BillingHistoryRecord[i];
        }
    };
    public final String developerPayload;
    public final String productId;
    public final long purchaseTime;
    public final String purchaseToken;
    public final String signature;

    public int describeContents() {
        return 0;
    }

    public BillingHistoryRecord(String str, String str2) throws JSONException {
        this(new JSONObject(str), str2);
    }

    public BillingHistoryRecord(JSONObject jSONObject, String str) throws JSONException {
        this.productId = jSONObject.getString("productId");
        this.purchaseToken = jSONObject.getString(Constants.RESPONSE_PURCHASE_TOKEN);
        this.purchaseTime = jSONObject.getLong(Constants.RESPONSE_PURCHASE_TIME);
        this.developerPayload = jSONObject.getString("developerPayload");
        this.signature = str;
    }

    public BillingHistoryRecord(String str, String str2, long j, String str3, String str4) {
        this.productId = str;
        this.purchaseToken = str2;
        this.purchaseTime = j;
        this.developerPayload = str3;
        this.signature = str4;
    }

    protected BillingHistoryRecord(Parcel parcel) {
        this.productId = parcel.readString();
        this.purchaseToken = parcel.readString();
        this.purchaseTime = parcel.readLong();
        this.developerPayload = parcel.readString();
        this.signature = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.productId);
        parcel.writeString(this.purchaseToken);
        parcel.writeLong(this.purchaseTime);
        parcel.writeString(this.developerPayload);
        parcel.writeString(this.signature);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BillingHistoryRecord{productId='");
        sb.append(this.productId);
        sb.append('\'');
        sb.append(", purchaseToken='");
        sb.append(this.purchaseToken);
        sb.append('\'');
        sb.append(", purchaseTime=");
        sb.append(this.purchaseTime);
        sb.append(", developerPayload='");
        sb.append(this.developerPayload);
        sb.append('\'');
        sb.append(", signature='");
        sb.append(this.signature);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
