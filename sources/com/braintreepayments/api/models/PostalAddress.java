package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import org.json.JSONObject;

public class PostalAddress implements Parcelable {
    public static final Creator<PostalAddress> CREATOR = new Creator<PostalAddress>() {
        public PostalAddress createFromParcel(Parcel parcel) {
            return new PostalAddress(parcel);
        }

        public PostalAddress[] newArray(int i) {
            return new PostalAddress[i];
        }
    };
    private String mCountryCodeAlpha2;
    private String mExtendedAddress;
    private String mLocality;
    private String mPhoneNumber;
    private String mPostalCode;
    private String mRecipientName;
    private String mRegion;
    private String mSortingCode;
    private String mStreetAddress;

    public int describeContents() {
        return 0;
    }

    public PostalAddress() {
    }

    @Deprecated
    public static PostalAddress fromJson(JSONObject jSONObject) {
        return PostalAddressParser.fromJson(jSONObject);
    }

    public PostalAddress recipientName(String str) {
        this.mRecipientName = str;
        return this;
    }

    public PostalAddress phoneNumber(String str) {
        this.mPhoneNumber = str;
        return this;
    }

    public PostalAddress streetAddress(String str) {
        this.mStreetAddress = str;
        return this;
    }

    public PostalAddress extendedAddress(String str) {
        this.mExtendedAddress = str;
        return this;
    }

    public PostalAddress locality(String str) {
        this.mLocality = str;
        return this;
    }

    public PostalAddress region(String str) {
        this.mRegion = str;
        return this;
    }

    public PostalAddress postalCode(String str) {
        this.mPostalCode = str;
        return this;
    }

    public PostalAddress sortingCode(String str) {
        this.mSortingCode = str;
        return this;
    }

    public PostalAddress countryCodeAlpha2(String str) {
        this.mCountryCodeAlpha2 = str;
        return this;
    }

    public String getRecipientName() {
        return this.mRecipientName;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
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

    public String getSortingCode() {
        return this.mSortingCode;
    }

    public String getCountryCodeAlpha2() {
        return this.mCountryCodeAlpha2;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.mCountryCodeAlpha2);
    }

    public String toString() {
        return String.format("%s\n%s\n%s\n%s, %s\n%s %s", new Object[]{this.mRecipientName, this.mStreetAddress, this.mExtendedAddress, this.mLocality, this.mRegion, this.mPostalCode, this.mCountryCodeAlpha2});
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mStreetAddress);
        parcel.writeString(this.mExtendedAddress);
        parcel.writeString(this.mLocality);
        parcel.writeString(this.mRegion);
        parcel.writeString(this.mPostalCode);
        parcel.writeString(this.mCountryCodeAlpha2);
        parcel.writeString(this.mRecipientName);
        parcel.writeString(this.mPhoneNumber);
        parcel.writeString(this.mSortingCode);
    }

    private PostalAddress(Parcel parcel) {
        this.mStreetAddress = parcel.readString();
        this.mExtendedAddress = parcel.readString();
        this.mLocality = parcel.readString();
        this.mRegion = parcel.readString();
        this.mPostalCode = parcel.readString();
        this.mCountryCodeAlpha2 = parcel.readString();
        this.mRecipientName = parcel.readString();
        this.mPhoneNumber = parcel.readString();
        this.mSortingCode = parcel.readString();
    }
}
