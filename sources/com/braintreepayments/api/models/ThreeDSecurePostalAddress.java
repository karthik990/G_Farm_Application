package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

public class ThreeDSecurePostalAddress implements Parcelable {
    protected static final String COUNTRY_CODE_ALPHA_2_KEY = "countryCode";
    public static final Creator<ThreeDSecurePostalAddress> CREATOR = new Creator<ThreeDSecurePostalAddress>() {
        public ThreeDSecurePostalAddress createFromParcel(Parcel parcel) {
            return new ThreeDSecurePostalAddress(parcel);
        }

        public ThreeDSecurePostalAddress[] newArray(int i) {
            return new ThreeDSecurePostalAddress[i];
        }
    };
    protected static final String EXTENDED_ADDRESS_KEY = "line2";
    protected static final String FIRST_NAME_KEY = "firstName";
    protected static final String LAST_NAME_KEY = "lastName";
    protected static final String LOCALITY_KEY = "city";
    protected static final String PHONE_NUMBER_KEY = "phoneNumber";
    protected static final String POSTAL_CODE_KEY = "postalCode";
    protected static final String REGION_KEY = "state";
    protected static final String STREET_ADDRESS_KEY = "line1";
    private String mCountryCodeAlpha2;
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

    public ThreeDSecurePostalAddress() {
    }

    public ThreeDSecurePostalAddress firstName(String str) {
        this.mFirstName = str;
        return this;
    }

    public ThreeDSecurePostalAddress lastName(String str) {
        this.mLastName = str;
        return this;
    }

    public ThreeDSecurePostalAddress streetAddress(String str) {
        this.mStreetAddress = str;
        return this;
    }

    public ThreeDSecurePostalAddress extendedAddress(String str) {
        this.mExtendedAddress = str;
        return this;
    }

    public ThreeDSecurePostalAddress locality(String str) {
        this.mLocality = str;
        return this;
    }

    public ThreeDSecurePostalAddress region(String str) {
        this.mRegion = str;
        return this;
    }

    public ThreeDSecurePostalAddress postalCode(String str) {
        this.mPostalCode = str;
        return this;
    }

    public ThreeDSecurePostalAddress countryCodeAlpha2(String str) {
        this.mCountryCodeAlpha2 = str;
        return this;
    }

    public ThreeDSecurePostalAddress phoneNumber(String str) {
        this.mPhoneNumber = str;
        return this;
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

    public String getCountryCodeAlpha2() {
        return this.mCountryCodeAlpha2;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public ThreeDSecurePostalAddress(Parcel parcel) {
        this.mFirstName = parcel.readString();
        this.mLastName = parcel.readString();
        this.mStreetAddress = parcel.readString();
        this.mExtendedAddress = parcel.readString();
        this.mLocality = parcel.readString();
        this.mRegion = parcel.readString();
        this.mPostalCode = parcel.readString();
        this.mCountryCodeAlpha2 = parcel.readString();
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
        parcel.writeString(this.mCountryCodeAlpha2);
        parcel.writeString(this.mPhoneNumber);
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt(FIRST_NAME_KEY, this.mFirstName);
            jSONObject.putOpt(LAST_NAME_KEY, this.mLastName);
            jSONObject.putOpt("line1", this.mStreetAddress);
            jSONObject.putOpt("line2", this.mExtendedAddress);
            jSONObject.putOpt("city", this.mLocality);
            jSONObject.putOpt("state", this.mRegion);
            jSONObject.putOpt("postalCode", this.mPostalCode);
            jSONObject.putOpt("countryCode", this.mCountryCodeAlpha2);
            jSONObject.putOpt("phoneNumber", this.mPhoneNumber);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }
}
