package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWallet;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class AndroidPayCardNonce extends PaymentMethodNonce implements Parcelable {
    protected static final String API_RESOURCE_KEY = "androidPayCards";
    private static final String CARD_DETAILS_KEY = "details";
    private static final String CARD_TYPE_KEY = "cardType";
    public static final Creator<AndroidPayCardNonce> CREATOR = new Creator<AndroidPayCardNonce>() {
        public AndroidPayCardNonce createFromParcel(Parcel parcel) {
            return new AndroidPayCardNonce(parcel);
        }

        public AndroidPayCardNonce[] newArray(int i) {
            return new AndroidPayCardNonce[i];
        }
    };
    private static final String LAST_TWO_KEY = "lastTwo";
    protected static final String TYPE = "AndroidPayCard";
    private UserAddress mBillingAddress;
    private BinData mBinData;
    private String mCardType;
    private Cart mCart;
    private String mEmail;
    private String mGoogleTransactionId;
    private String mLastTwo;
    private UserAddress mShippingAddress;

    public String getTypeLabel() {
        return "Android Pay";
    }

    @Deprecated
    public static AndroidPayCardNonce fromFullWallet(FullWallet fullWallet) throws JSONException {
        return fromFullWallet(fullWallet, null);
    }

    @Deprecated
    public static AndroidPayCardNonce fromFullWallet(FullWallet fullWallet, Cart cart) throws JSONException {
        AndroidPayCardNonce fromJson = fromJson(fullWallet.getPaymentMethodToken().getToken());
        fromJson.mDescription = fullWallet.getPaymentDescriptions()[0];
        fromJson.mEmail = fullWallet.getEmail();
        fromJson.mBillingAddress = fullWallet.getBuyerBillingAddress();
        fromJson.mShippingAddress = fullWallet.getBuyerShippingAddress();
        fromJson.mGoogleTransactionId = fullWallet.getGoogleTransactionId();
        fromJson.mCart = cart;
        return fromJson;
    }

    @Deprecated
    public static AndroidPayCardNonce fromJson(String str) throws JSONException {
        AndroidPayCardNonce androidPayCardNonce = new AndroidPayCardNonce();
        androidPayCardNonce.fromJson(getJsonObjectForType(API_RESOURCE_KEY, new JSONObject(str)));
        return androidPayCardNonce;
    }

    /* access modifiers changed from: protected */
    public void fromJson(JSONObject jSONObject) throws JSONException {
        super.fromJson(jSONObject);
        this.mBinData = BinData.fromJson(jSONObject.optJSONObject(BinData.BIN_DATA_KEY));
        JSONObject jSONObject2 = jSONObject.getJSONObject(CARD_DETAILS_KEY);
        this.mLastTwo = jSONObject2.getString(LAST_TWO_KEY);
        this.mCardType = jSONObject2.getString(CARD_TYPE_KEY);
    }

    public String getCardType() {
        return this.mCardType;
    }

    public String getLastTwo() {
        return this.mLastTwo;
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

    public String getGoogleTransactionId() {
        return this.mGoogleTransactionId;
    }

    public Cart getCart() {
        return this.mCart;
    }

    public BinData getBinData() {
        return this.mBinData;
    }

    public AndroidPayCardNonce() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mCardType);
        parcel.writeString(this.mLastTwo);
        parcel.writeString(this.mEmail);
        parcel.writeParcelable(this.mBillingAddress, i);
        parcel.writeParcelable(this.mShippingAddress, i);
        parcel.writeString(this.mGoogleTransactionId);
        parcel.writeParcelable(this.mCart, i);
        parcel.writeParcelable(this.mBinData, i);
    }

    private AndroidPayCardNonce(Parcel parcel) {
        super(parcel);
        this.mCardType = parcel.readString();
        this.mLastTwo = parcel.readString();
        this.mEmail = parcel.readString();
        this.mBillingAddress = parcel.readParcelable(UserAddress.class.getClassLoader());
        this.mShippingAddress = parcel.readParcelable(UserAddress.class.getClassLoader());
        this.mGoogleTransactionId = parcel.readString();
        this.mCart = parcel.readParcelable(Cart.class.getClassLoader());
        this.mBinData = (BinData) parcel.readParcelable(BinData.class.getClassLoader());
    }
}
