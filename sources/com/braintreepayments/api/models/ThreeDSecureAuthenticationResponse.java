package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

public class ThreeDSecureAuthenticationResponse implements Parcelable {
    public static final Creator<ThreeDSecureAuthenticationResponse> CREATOR = new Creator<ThreeDSecureAuthenticationResponse>() {
        public ThreeDSecureAuthenticationResponse createFromParcel(Parcel parcel) {
            return new ThreeDSecureAuthenticationResponse(parcel);
        }

        public ThreeDSecureAuthenticationResponse[] newArray(int i) {
            return new ThreeDSecureAuthenticationResponse[i];
        }
    };
    private static final String PAYMENT_METHOD_KEY = "paymentMethod";
    private static final String SUCCESS_KEY = "success";
    private CardNonce mCardNonce;
    private String mErrors;
    private String mException;
    private boolean mSuccess;

    public int describeContents() {
        return 0;
    }

    public static ThreeDSecureAuthenticationResponse fromJson(String str) {
        ThreeDSecureAuthenticationResponse threeDSecureAuthenticationResponse = new ThreeDSecureAuthenticationResponse();
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject(PAYMENT_METHOD_KEY);
            if (optJSONObject != null) {
                CardNonce cardNonce = new CardNonce();
                cardNonce.fromJson(optJSONObject);
                threeDSecureAuthenticationResponse.mCardNonce = cardNonce;
            }
            threeDSecureAuthenticationResponse.mSuccess = jSONObject.getBoolean("success");
            if (!threeDSecureAuthenticationResponse.mSuccess) {
                threeDSecureAuthenticationResponse.mErrors = str;
            }
        } catch (JSONException unused) {
            threeDSecureAuthenticationResponse.mSuccess = false;
        }
        return threeDSecureAuthenticationResponse;
    }

    @Deprecated
    public static ThreeDSecureAuthenticationResponse fromException(String str) {
        ThreeDSecureAuthenticationResponse threeDSecureAuthenticationResponse = new ThreeDSecureAuthenticationResponse();
        threeDSecureAuthenticationResponse.mSuccess = false;
        threeDSecureAuthenticationResponse.mException = str;
        return threeDSecureAuthenticationResponse;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public CardNonce getCardNonce() {
        return this.mCardNonce;
    }

    public String getErrors() {
        return this.mErrors;
    }

    public String getException() {
        return this.mException;
    }

    public ThreeDSecureAuthenticationResponse() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mSuccess ? (byte) 1 : 0);
        parcel.writeParcelable(this.mCardNonce, i);
        parcel.writeString(this.mErrors);
        parcel.writeString(this.mException);
    }

    private ThreeDSecureAuthenticationResponse(Parcel parcel) {
        this.mSuccess = parcel.readByte() != 0;
        this.mCardNonce = (CardNonce) parcel.readParcelable(CardNonce.class.getClassLoader());
        this.mErrors = parcel.readString();
        this.mException = parcel.readString();
    }
}
