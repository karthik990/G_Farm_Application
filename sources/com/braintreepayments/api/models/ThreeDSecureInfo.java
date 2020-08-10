package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONObject;

public class ThreeDSecureInfo implements Parcelable {
    public static final Creator<ThreeDSecureInfo> CREATOR = new Creator<ThreeDSecureInfo>() {
        public ThreeDSecureInfo createFromParcel(Parcel parcel) {
            return new ThreeDSecureInfo(parcel);
        }

        public ThreeDSecureInfo[] newArray(int i) {
            return new ThreeDSecureInfo[i];
        }
    };
    private static final String LIABILITY_SHIFTED_KEY = "liabilityShifted";
    private static final String LIABILITY_SHIFT_POSSIBLE_KEY = "liabilityShiftPossible";
    private boolean mLiabilityShiftPossible;
    private boolean mLiabilityShifted;
    private boolean mWasVerified;

    public int describeContents() {
        return 0;
    }

    protected static ThreeDSecureInfo fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        ThreeDSecureInfo threeDSecureInfo = new ThreeDSecureInfo();
        String str = LIABILITY_SHIFTED_KEY;
        threeDSecureInfo.mLiabilityShifted = jSONObject.optBoolean(str);
        String str2 = LIABILITY_SHIFT_POSSIBLE_KEY;
        threeDSecureInfo.mLiabilityShiftPossible = jSONObject.optBoolean(str2);
        threeDSecureInfo.mWasVerified = jSONObject.has(str) && jSONObject.has(str2);
        return threeDSecureInfo;
    }

    public boolean isLiabilityShifted() {
        return this.mLiabilityShifted;
    }

    public boolean isLiabilityShiftPossible() {
        return this.mLiabilityShiftPossible;
    }

    public boolean wasVerified() {
        return this.mWasVerified;
    }

    public ThreeDSecureInfo() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mLiabilityShifted ? (byte) 1 : 0);
        parcel.writeByte(this.mLiabilityShiftPossible ? (byte) 1 : 0);
        parcel.writeByte(this.mWasVerified ? (byte) 1 : 0);
    }

    private ThreeDSecureInfo(Parcel parcel) {
        boolean z = true;
        this.mLiabilityShifted = parcel.readByte() != 0;
        this.mLiabilityShiftPossible = parcel.readByte() != 0;
        if (parcel.readByte() == 0) {
            z = false;
        }
        this.mWasVerified = z;
    }
}
