package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import org.json.JSONObject;

public class VisaCheckoutUserData implements Parcelable {
    public static final Creator<VisaCheckoutUserData> CREATOR = new Creator<VisaCheckoutUserData>() {
        public VisaCheckoutUserData createFromParcel(Parcel parcel) {
            return new VisaCheckoutUserData(parcel);
        }

        public VisaCheckoutUserData[] newArray(int i) {
            return new VisaCheckoutUserData[i];
        }
    };
    private String mUserEmail;
    private String mUserFirstName;
    private String mUserFullName;
    private String mUserLastName;
    private String mUsername;

    public int describeContents() {
        return 0;
    }

    public static VisaCheckoutUserData fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        VisaCheckoutUserData visaCheckoutUserData = new VisaCheckoutUserData();
        String str = "";
        visaCheckoutUserData.mUserFirstName = Json.optString(jSONObject, "userFirstName", str);
        visaCheckoutUserData.mUserLastName = Json.optString(jSONObject, "userLastName", str);
        visaCheckoutUserData.mUserFullName = Json.optString(jSONObject, "userFullName", str);
        visaCheckoutUserData.mUsername = Json.optString(jSONObject, "userName", str);
        visaCheckoutUserData.mUserEmail = Json.optString(jSONObject, "userEmail", str);
        return visaCheckoutUserData;
    }

    public VisaCheckoutUserData() {
    }

    public String getUserFirstName() {
        return this.mUserFirstName;
    }

    public String getUserLastName() {
        return this.mUserLastName;
    }

    public String getUserFullName() {
        return this.mUserFullName;
    }

    public String getUsername() {
        return this.mUsername;
    }

    public String getUserEmail() {
        return this.mUserEmail;
    }

    public VisaCheckoutUserData(Parcel parcel) {
        this.mUserFirstName = parcel.readString();
        this.mUserLastName = parcel.readString();
        this.mUserFullName = parcel.readString();
        this.mUsername = parcel.readString();
        this.mUserEmail = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUserFirstName);
        parcel.writeString(this.mUserLastName);
        parcel.writeString(this.mUserFullName);
        parcel.writeString(this.mUsername);
        parcel.writeString(this.mUserEmail);
    }
}
