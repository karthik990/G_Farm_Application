package com.anjlab.android.iab.p020v3;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.anjlab.android.iab.v3.PurchaseInfo */
public class PurchaseInfo implements Parcelable {
    public static final Creator<PurchaseInfo> CREATOR = new Creator<PurchaseInfo>() {
        public PurchaseInfo createFromParcel(Parcel parcel) {
            return new PurchaseInfo(parcel);
        }

        public PurchaseInfo[] newArray(int i) {
            return new PurchaseInfo[i];
        }
    };
    private static final String LOG_TAG = "iabv3.purchaseInfo";
    public final PurchaseData purchaseData = parseResponseDataImpl();
    public final String responseData;
    public final String signature;

    public int describeContents() {
        return 0;
    }

    public PurchaseInfo(String str, String str2) {
        this.responseData = str;
        this.signature = str2;
    }

    @Deprecated
    public PurchaseData parseResponseData() {
        return parseResponseDataImpl();
    }

    /* access modifiers changed from: 0000 */
    public PurchaseData parseResponseDataImpl() {
        try {
            JSONObject jSONObject = new JSONObject(this.responseData);
            PurchaseData purchaseData2 = new PurchaseData();
            purchaseData2.orderId = jSONObject.optString("orderId");
            purchaseData2.packageName = jSONObject.optString(Constants.RESPONSE_PACKAGE_NAME);
            purchaseData2.productId = jSONObject.optString("productId");
            long optLong = jSONObject.optLong(Constants.RESPONSE_PURCHASE_TIME, 0);
            purchaseData2.purchaseTime = optLong != 0 ? new Date(optLong) : null;
            purchaseData2.purchaseState = PurchaseState.values()[jSONObject.optInt(Constants.RESPONSE_PURCHASE_STATE, 1)];
            purchaseData2.developerPayload = jSONObject.optString("developerPayload");
            purchaseData2.purchaseToken = jSONObject.getString(Constants.RESPONSE_PURCHASE_TOKEN);
            purchaseData2.autoRenewing = jSONObject.optBoolean(Constants.RESPONSE_AUTO_RENEWING);
            return purchaseData2;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Failed to parse response data", e);
            return null;
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.responseData);
        parcel.writeString(this.signature);
    }

    protected PurchaseInfo(Parcel parcel) {
        this.responseData = parcel.readString();
        this.signature = parcel.readString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PurchaseInfo)) {
            return false;
        }
        PurchaseInfo purchaseInfo = (PurchaseInfo) obj;
        if (!this.responseData.equals(purchaseInfo.responseData) || !this.signature.equals(purchaseInfo.signature) || !this.purchaseData.purchaseToken.equals(purchaseInfo.purchaseData.purchaseToken) || !this.purchaseData.purchaseTime.equals(purchaseInfo.purchaseData.purchaseTime)) {
            z = false;
        }
        return z;
    }
}
