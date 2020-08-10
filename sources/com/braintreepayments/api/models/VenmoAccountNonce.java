package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.json.JSONException;
import org.json.JSONObject;

public class VenmoAccountNonce extends PaymentMethodNonce implements Parcelable {
    protected static final String API_RESOURCE_KEY = "venmoAccounts";
    public static final Creator<VenmoAccountNonce> CREATOR = new Creator<VenmoAccountNonce>() {
        public VenmoAccountNonce createFromParcel(Parcel parcel) {
            return new VenmoAccountNonce(parcel);
        }

        public VenmoAccountNonce[] newArray(int i) {
            return new VenmoAccountNonce[i];
        }
    };
    protected static final String TYPE = "VenmoAccount";
    private static final String VENMO_DETAILS_KEY = "details";
    private static final String VENMO_USERNAME_KEY = "username";
    private String mUsername;

    public String getTypeLabel() {
        return "Venmo";
    }

    public VenmoAccountNonce(String str, String str2, String str3) {
        this.mNonce = str;
        this.mDescription = str2;
        this.mUsername = str3;
    }

    public static VenmoAccountNonce fromJson(String str) throws JSONException {
        VenmoAccountNonce venmoAccountNonce = new VenmoAccountNonce();
        venmoAccountNonce.fromJson(getJsonObjectForType(API_RESOURCE_KEY, new JSONObject(str)));
        return venmoAccountNonce;
    }

    /* access modifiers changed from: protected */
    public void fromJson(JSONObject jSONObject) throws JSONException {
        super.fromJson(jSONObject);
        this.mUsername = jSONObject.getJSONObject(VENMO_DETAILS_KEY).getString(VENMO_USERNAME_KEY);
        this.mDescription = this.mUsername;
    }

    public String getUsername() {
        return this.mUsername;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mUsername);
    }

    public VenmoAccountNonce() {
    }

    protected VenmoAccountNonce(Parcel parcel) {
        super(parcel);
        this.mUsername = parcel.readString();
    }
}
