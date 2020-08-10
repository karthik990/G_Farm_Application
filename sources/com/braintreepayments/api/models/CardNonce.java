package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.braintreepayments.api.Json;
import org.json.JSONException;
import org.json.JSONObject;

public class CardNonce extends PaymentMethodNonce implements Parcelable {
    protected static final String API_RESOURCE_KEY = "creditCards";
    private static final String CARD_DETAILS_KEY = "details";
    private static final String CARD_TYPE_KEY = "cardType";
    public static final Creator<CardNonce> CREATOR = new Creator<CardNonce>() {
        public CardNonce createFromParcel(Parcel parcel) {
            return new CardNonce(parcel);
        }

        public CardNonce[] newArray(int i) {
            return new CardNonce[i];
        }
    };
    private static final String GRAPHQL_BRAND_KEY = "brand";
    private static final String GRAPHQL_CREDIT_CARD_KEY = "creditCard";
    private static final String GRAPHQL_LAST_FOUR_KEY = "last4";
    private static final String GRAPHQL_TOKENIZE_CREDIT_CARD_KEY = "tokenizeCreditCard";
    private static final String LAST_FOUR_KEY = "lastFour";
    private static final String LAST_TWO_KEY = "lastTwo";
    private static final String THREE_D_SECURE_INFO_KEY = "threeDSecureInfo";
    protected static final String TYPE = "CreditCard";
    private BinData mBinData;
    private String mCardType;
    private String mLastFour;
    private String mLastTwo;
    private ThreeDSecureInfo mThreeDSecureInfo;

    public static CardNonce fromJson(String str) throws JSONException {
        CardNonce cardNonce = new CardNonce();
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("data")) {
            cardNonce.fromGraphQLJson(jSONObject);
        } else {
            cardNonce.fromJson(getJsonObjectForType(API_RESOURCE_KEY, jSONObject));
        }
        return cardNonce;
    }

    /* access modifiers changed from: protected */
    public void fromJson(JSONObject jSONObject) throws JSONException {
        super.fromJson(jSONObject);
        JSONObject jSONObject2 = jSONObject.getJSONObject(CARD_DETAILS_KEY);
        this.mLastTwo = jSONObject2.getString(LAST_TWO_KEY);
        this.mLastFour = jSONObject2.getString(LAST_FOUR_KEY);
        this.mCardType = jSONObject2.getString(CARD_TYPE_KEY);
        this.mThreeDSecureInfo = ThreeDSecureInfo.fromJson(jSONObject.optJSONObject(THREE_D_SECURE_INFO_KEY));
        this.mBinData = BinData.fromJson(jSONObject.optJSONObject(BinData.BIN_DATA_KEY));
    }

    private void fromGraphQLJson(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
        String str = GRAPHQL_TOKENIZE_CREDIT_CARD_KEY;
        if (jSONObject2.has(str)) {
            JSONObject jSONObject3 = jSONObject2.getJSONObject(str);
            JSONObject jSONObject4 = jSONObject3.getJSONObject(GRAPHQL_CREDIT_CARD_KEY);
            String str2 = "";
            this.mLastFour = Json.optString(jSONObject4, GRAPHQL_LAST_FOUR_KEY, str2);
            this.mLastTwo = this.mLastFour.length() < 4 ? str2 : this.mLastFour.substring(2);
            this.mCardType = Json.optString(jSONObject4, GRAPHQL_BRAND_KEY, BinData.UNKNOWN);
            this.mThreeDSecureInfo = ThreeDSecureInfo.fromJson(null);
            this.mBinData = BinData.fromJson(jSONObject4.optJSONObject(BinData.BIN_DATA_KEY));
            this.mNonce = jSONObject3.getString("token");
            if (!TextUtils.isEmpty(this.mLastTwo)) {
                StringBuilder sb = new StringBuilder();
                sb.append("ending in ••");
                sb.append(this.mLastTwo);
                str2 = sb.toString();
            }
            this.mDescription = str2;
            this.mDefault = false;
            return;
        }
        throw new JSONException("Failed to parse GraphQL response JSON");
    }

    public String getTypeLabel() {
        return this.mCardType;
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

    public ThreeDSecureInfo getThreeDSecureInfo() {
        return this.mThreeDSecureInfo;
    }

    public BinData getBinData() {
        return this.mBinData;
    }

    public CardNonce() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mCardType);
        parcel.writeString(this.mLastTwo);
        parcel.writeString(this.mLastFour);
        parcel.writeParcelable(this.mBinData, i);
        parcel.writeParcelable(this.mThreeDSecureInfo, i);
    }

    protected CardNonce(Parcel parcel) {
        super(parcel);
        this.mCardType = parcel.readString();
        this.mLastTwo = parcel.readString();
        this.mLastFour = parcel.readString();
        this.mBinData = (BinData) parcel.readParcelable(BinData.class.getClassLoader());
        this.mThreeDSecureInfo = (ThreeDSecureInfo) parcel.readParcelable(ThreeDSecureInfo.class.getClassLoader());
    }
}
