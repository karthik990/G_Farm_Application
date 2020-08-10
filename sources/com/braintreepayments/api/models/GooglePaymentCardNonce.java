package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.wallet.PaymentData;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePaymentCardNonce extends PaymentMethodNonce implements Parcelable {
    private static final String API_RESOURCE_KEY = "androidPayCards";
    private static final String CARD_DETAILS_KEY = "details";
    private static final String CARD_TYPE_KEY = "cardType";
    public static final Creator<GooglePaymentCardNonce> CREATOR = new Creator<GooglePaymentCardNonce>() {
        public GooglePaymentCardNonce createFromParcel(Parcel parcel) {
            return new GooglePaymentCardNonce(parcel);
        }

        public GooglePaymentCardNonce[] newArray(int i) {
            return new GooglePaymentCardNonce[i];
        }
    };
    private static final String LAST_FOUR_KEY = "lastFour";
    private static final String LAST_TWO_KEY = "lastTwo";
    private UserAddress mBillingAddress;
    private BinData mBinData;
    private String mCardType;
    private String mEmail;
    private String mLastFour;
    private String mLastTwo;
    private UserAddress mShippingAddress;

    public String getTypeLabel() {
        return "Google Pay";
    }

    public static GooglePaymentCardNonce fromPaymentData(PaymentData paymentData) throws JSONException {
        GooglePaymentCardNonce fromJson = fromJson(paymentData.getPaymentMethodToken().getToken());
        fromJson.mDescription = paymentData.getCardInfo().getCardDescription();
        fromJson.mEmail = paymentData.getEmail();
        fromJson.mBillingAddress = paymentData.getCardInfo().getBillingAddress();
        fromJson.mShippingAddress = paymentData.getShippingAddress();
        return fromJson;
    }

    public static GooglePaymentCardNonce fromJson(String str) throws JSONException {
        GooglePaymentCardNonce googlePaymentCardNonce = new GooglePaymentCardNonce();
        googlePaymentCardNonce.fromJson(getJsonObjectForType(API_RESOURCE_KEY, new JSONObject(str)));
        return googlePaymentCardNonce;
    }

    /* access modifiers changed from: protected */
    public void fromJson(JSONObject jSONObject) throws JSONException {
        super.fromJson(jSONObject);
        this.mDescription = getTypeLabel();
        this.mBinData = BinData.fromJson(jSONObject.optJSONObject(BinData.BIN_DATA_KEY));
        JSONObject jSONObject2 = jSONObject.getJSONObject(CARD_DETAILS_KEY);
        this.mLastTwo = jSONObject2.getString(LAST_TWO_KEY);
        this.mLastFour = jSONObject2.getString(LAST_FOUR_KEY);
        this.mCardType = jSONObject2.getString(CARD_TYPE_KEY);
    }

    public String getCardType() {
        return this.mCardType;
    }

    public String getLastTwo() {
        return this.mLastTwo;
    }

    public String getLastFour() {
        return this.mLastFour;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public UserAddress getBillingAddress() {
        return this.mBillingAddress;
    }

    public UserAddress getShippingAddress() {
        return this.mShippingAddress;
    }

    public BinData getBinData() {
        return this.mBinData;
    }

    public GooglePaymentCardNonce() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mCardType);
        parcel.writeString(this.mLastTwo);
        parcel.writeString(this.mLastFour);
        parcel.writeString(this.mEmail);
        parcel.writeParcelable(this.mBillingAddress, i);
        parcel.writeParcelable(this.mShippingAddress, i);
        parcel.writeParcelable(this.mBinData, i);
    }

    private GooglePaymentCardNonce(Parcel parcel) {
        super(parcel);
        this.mCardType = parcel.readString();
        this.mLastTwo = parcel.readString();
        this.mLastFour = parcel.readString();
        this.mEmail = parcel.readString();
        this.mBillingAddress = parcel.readParcelable(UserAddress.class.getClassLoader());
        this.mShippingAddress = parcel.readParcelable(UserAddress.class.getClassLoader());
        this.mBinData = (BinData) parcel.readParcelable(BinData.class.getClassLoader());
    }
}
