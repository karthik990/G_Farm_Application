package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import org.json.JSONException;
import org.json.JSONObject;

public class VisaCheckoutNonce extends PaymentMethodNonce implements Parcelable {
    protected static final String API_RESOURCE_KEY = "visaCheckoutCards";
    private static final String BILLING_ADDRESS_KEY = "billingAddress";
    private static final String CALL_ID_KEY = "callId";
    private static final String CARD_DETAILS_KEY = "details";
    private static final String CARD_TYPE_KEY = "cardType";
    public static final Creator<VisaCheckoutNonce> CREATOR = new Creator<VisaCheckoutNonce>() {
        public VisaCheckoutNonce createFromParcel(Parcel parcel) {
            return new VisaCheckoutNonce(parcel);
        }

        public VisaCheckoutNonce[] newArray(int i) {
            return new VisaCheckoutNonce[i];
        }
    };
    private static final String LAST_TWO_KEY = "lastTwo";
    private static final String SHIPPING_ADDRESS_KEY = "shippingAddress";
    protected static final String TYPE = "VisaCheckoutCard";
    private static final String USER_DATA_KEY = "userData";
    private VisaCheckoutAddress mBillingAddress;
    private BinData mBinData;
    private String mCallId;
    private String mCardType;
    private String mLastTwo;
    private VisaCheckoutAddress mShippingAddress;
    private VisaCheckoutUserData mUserData;

    public String getTypeLabel() {
        return "Visa Checkout";
    }

    public static VisaCheckoutNonce fromJson(String str) throws JSONException {
        VisaCheckoutNonce visaCheckoutNonce = new VisaCheckoutNonce();
        visaCheckoutNonce.fromJson(PaymentMethodNonce.getJsonObjectForType(API_RESOURCE_KEY, new JSONObject(str)));
        return visaCheckoutNonce;
    }

    /* access modifiers changed from: protected */
    public void fromJson(JSONObject jSONObject) throws JSONException {
        super.fromJson(jSONObject);
        JSONObject jSONObject2 = jSONObject.getJSONObject(CARD_DETAILS_KEY);
        this.mLastTwo = jSONObject2.getString(LAST_TWO_KEY);
        this.mCardType = jSONObject2.getString(CARD_TYPE_KEY);
        this.mBillingAddress = VisaCheckoutAddress.fromJson(jSONObject.optJSONObject("billingAddress"));
        this.mShippingAddress = VisaCheckoutAddress.fromJson(jSONObject.optJSONObject("shippingAddress"));
        this.mUserData = VisaCheckoutUserData.fromJson(jSONObject.optJSONObject(USER_DATA_KEY));
        this.mCallId = Json.optString(jSONObject, CALL_ID_KEY, "");
        this.mBinData = BinData.fromJson(jSONObject.optJSONObject(BinData.BIN_DATA_KEY));
    }

    public String getLastTwo() {
        return this.mLastTwo;
    }

    public String getCardType() {
        return this.mCardType;
    }

    public VisaCheckoutAddress getBillingAddress() {
        return this.mBillingAddress;
    }

    public VisaCheckoutAddress getShippingAddress() {
        return this.mShippingAddress;
    }

    public VisaCheckoutUserData getUserData() {
        return this.mUserData;
    }

    public String getCallId() {
        return this.mCallId;
    }

    public BinData getBinData() {
        return this.mBinData;
    }

    public VisaCheckoutNonce() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mLastTwo);
        parcel.writeString(this.mCardType);
        parcel.writeParcelable(this.mBillingAddress, i);
        parcel.writeParcelable(this.mShippingAddress, i);
        parcel.writeParcelable(this.mUserData, i);
        parcel.writeString(this.mCallId);
        parcel.writeParcelable(this.mBinData, i);
    }

    protected VisaCheckoutNonce(Parcel parcel) {
        super(parcel);
        this.mLastTwo = parcel.readString();
        this.mCardType = parcel.readString();
        this.mBillingAddress = (VisaCheckoutAddress) parcel.readParcelable(VisaCheckoutAddress.class.getClassLoader());
        this.mShippingAddress = (VisaCheckoutAddress) parcel.readParcelable(VisaCheckoutAddress.class.getClassLoader());
        this.mUserData = (VisaCheckoutUserData) parcel.readParcelable(VisaCheckoutUserData.class.getClassLoader());
        this.mCallId = parcel.readString();
        this.mBinData = (BinData) parcel.readParcelable(BinData.class.getClassLoader());
    }
}
