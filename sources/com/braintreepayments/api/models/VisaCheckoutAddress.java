package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import org.json.JSONObject;

public class VisaCheckoutAddress implements Parcelable {
    public static final Creator<VisaCheckoutAddress> CREATOR = new Creator<VisaCheckoutAddress>() {
        public VisaCheckoutAddress createFromParcel(Parcel parcel) {
            return new VisaCheckoutAddress(parcel);
        }

        public VisaCheckoutAddress[] newArray(int i) {
            return new VisaCheckoutAddress[i];
        }
    };
    private String mCountryCode;
    private String mExtendedAddress;
    private String mFirstName;
    private String mLastName;
    private String mLocality;
    private String mPhoneNumber;
    private String mPostalCode;
    private String mRegion;
    private String mStreetAddress;

    public int describeContents() {
        return 0;
    }

    public static VisaCheckoutAddress fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        VisaCheckoutAddress visaCheckoutAddress = new VisaCheckoutAddress();
        String str = "";
        visaCheckoutAddress.mFirstName = Json.optString(jSONObject, "firstName", str);
        visaCheckoutAddress.mLastName = Json.optString(jSONObject, "lastName", str);
        visaCheckoutAddress.mStreetAddress = Json.optString(jSONObject, "streetAddress", str);
        visaCheckoutAddress.mExtendedAddress = Json.optString(jSONObject, "extendedAddress", str);
        visaCheckoutAddress.mLocality = Json.optString(jSONObject, PostalAddressParser.USER_ADDRESS_LOCALITY_KEY, str);
        visaCheckoutAddress.mRegion = Json.optString(jSONObject, TtmlNode.TAG_REGION, str);
        visaCheckoutAddress.mPostalCode = Json.optString(jSONObject, "postalCode", str);
        visaCheckoutAddress.mCountryCode = Json.optString(jSONObject, "countryCode", str);
        visaCheckoutAddress.mPhoneNumber = Json.optString(jSONObject, PostalAddressParser.USER_ADDRESS_PHONE_NUMBER_KEY, str);
        return visaCheckoutAddress;
    }

    public VisaCheckoutAddress() {
    }

    public String getFirstName() {
        return this.mFirstName;
    }

    public String getLastName() {
        return this.mLastName;
    }

    public String getStreetAddress() {
        return this.mStreetAddress;
    }

    public String getExtendedAddress() {
        return this.mExtendedAddress;
    }

    public String getLocality() {
        return this.mLocality;
    }

    public String getRegion() {
        return this.mRegion;
    }

    public String getPostalCode() {
        return this.mPostalCode;
    }

    public String getCountryCode() {
        return this.mCountryCode;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public VisaCheckoutAddress(Parcel parcel) {
        this.mFirstName = parcel.readString();
        this.mLastName = parcel.readString();
        this.mStreetAddress = parcel.readString();
        this.mExtendedAddress = parcel.readString();
        this.mLocality = parcel.readString();
        this.mRegion = parcel.readString();
        this.mPostalCode = parcel.readString();
        this.mCountryCode = parcel.readString();
        this.mPhoneNumber = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mFirstName);
        parcel.writeString(this.mLastName);
        parcel.writeString(this.mStreetAddress);
        parcel.writeString(this.mExtendedAddress);
        parcel.writeString(this.mLocality);
        parcel.writeString(this.mRegion);
        parcel.writeString(this.mPostalCode);
        parcel.writeString(this.mCountryCode);
        parcel.writeString(this.mPhoneNumber);
    }
}
