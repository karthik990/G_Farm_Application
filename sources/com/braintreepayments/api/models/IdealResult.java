package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class IdealResult extends BraintreePaymentResult {
    public static final Creator<IdealResult> CREATOR = new Creator<IdealResult>() {
        public IdealResult createFromParcel(Parcel parcel) {
            return new IdealResult(parcel);
        }

        public IdealResult[] newArray(int i) {
            return new IdealResult[i];
        }
    };
    private String mId;
    private String mShortId;
    private String mStatus;

    public static IdealResult fromJson(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        IdealResult idealResult = new IdealResult();
        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
        idealResult.mId = jSONObject2.getString(TtmlNode.ATTR_ID);
        idealResult.mShortId = jSONObject2.getString("short_id");
        idealResult.mStatus = jSONObject2.getString("status");
        return idealResult;
    }

    public String getShortId() {
        return this.mShortId;
    }

    public String getStatus() {
        return this.mStatus;
    }

    public String getId() {
        return this.mId;
    }

    private IdealResult() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeString(this.mShortId);
        parcel.writeString(this.mStatus);
    }

    protected IdealResult(Parcel parcel) {
        super(parcel);
        this.mId = parcel.readString();
        this.mShortId = parcel.readString();
        this.mStatus = parcel.readString();
    }
}
