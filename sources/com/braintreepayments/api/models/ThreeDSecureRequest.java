package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

public class ThreeDSecureRequest implements Parcelable {
    protected static final String AMOUNT_KEY = "amount";
    protected static final String BILLING_ADDRESS_KEY = "billingAddress";
    public static final Creator<ThreeDSecureRequest> CREATOR = new Creator<ThreeDSecureRequest>() {
        public ThreeDSecureRequest createFromParcel(Parcel parcel) {
            return new ThreeDSecureRequest(parcel);
        }

        public ThreeDSecureRequest[] newArray(int i) {
            return new ThreeDSecureRequest[i];
        }
    };
    protected static final String CUSTOMER_KEY = "customer";
    protected static final String EMAIL_KEY = "email";
    protected static final String MOBILE_PHONE_NUMBER_KEY = "mobilePhoneNumber";
    protected static final String SHIPPING_METHOD_KEY = "shippingMethod";
    private String mAmount;
    private ThreeDSecurePostalAddress mBillingAddress;
    private String mEmail;
    private String mMobilePhoneNumber;
    private String mNonce;
    private String mShippingMethod;

    public int describeContents() {
        return 0;
    }

    public ThreeDSecureRequest nonce(String str) {
        this.mNonce = str;
        return this;
    }

    public ThreeDSecureRequest amount(String str) {
        this.mAmount = str;
        return this;
    }

    public ThreeDSecureRequest mobilePhoneNumber(String str) {
        this.mMobilePhoneNumber = str;
        return this;
    }

    public ThreeDSecureRequest email(String str) {
        this.mEmail = str;
        return this;
    }

    public ThreeDSecureRequest shippingMethod(String str) {
        this.mShippingMethod = str;
        return this;
    }

    public ThreeDSecureRequest billingAddress(ThreeDSecurePostalAddress threeDSecurePostalAddress) {
        this.mBillingAddress = threeDSecurePostalAddress;
        return this;
    }

    public String getNonce() {
        return this.mNonce;
    }

    public String getAmount() {
        return this.mAmount;
    }

    public String getMobilePhoneNumber() {
        return this.mMobilePhoneNumber;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getShippingMethod() {
        return this.mShippingMethod;
    }

    public ThreeDSecurePostalAddress getBillingAddress() {
        return this.mBillingAddress;
    }

    public ThreeDSecureRequest() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mNonce);
        parcel.writeString(this.mAmount);
        parcel.writeString(this.mMobilePhoneNumber);
        parcel.writeString(this.mEmail);
        parcel.writeString(this.mShippingMethod);
        parcel.writeParcelable(this.mBillingAddress, i);
    }

    public ThreeDSecureRequest(Parcel parcel) {
        this.mNonce = parcel.readString();
        this.mAmount = parcel.readString();
        this.mMobilePhoneNumber = parcel.readString();
        this.mEmail = parcel.readString();
        this.mShippingMethod = parcel.readString();
        this.mBillingAddress = (ThreeDSecurePostalAddress) parcel.readParcelable(ThreeDSecurePostalAddress.class.getClassLoader());
    }

    public String build() {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put(AMOUNT_KEY, this.mAmount);
            jSONObject2.putOpt(MOBILE_PHONE_NUMBER_KEY, this.mMobilePhoneNumber);
            jSONObject2.putOpt("email", this.mEmail);
            jSONObject2.putOpt(SHIPPING_METHOD_KEY, this.mShippingMethod);
            if (this.mBillingAddress != null) {
                jSONObject2.put("billingAddress", this.mBillingAddress.toJson());
            }
            jSONObject.put(CUSTOMER_KEY, jSONObject2);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }
}
