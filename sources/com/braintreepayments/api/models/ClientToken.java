package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientToken extends Authorization {
    private static final String AUTHORIZATION_FINGERPRINT_KEY = "authorizationFingerprint";
    protected static final String BASE_64_MATCHER = "([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)";
    private static final String CONFIG_URL_KEY = "configUrl";
    public static final Creator<ClientToken> CREATOR = new Creator<ClientToken>() {
        public ClientToken createFromParcel(Parcel parcel) {
            return new ClientToken(parcel);
        }

        public ClientToken[] newArray(int i) {
            return new ClientToken[i];
        }
    };
    private String mAuthorizationFingerprint;
    private String mConfigUrl;

    public int describeContents() {
        return 0;
    }

    ClientToken(String str) throws InvalidArgumentException {
        super(str);
        try {
            if (str.matches(BASE_64_MATCHER)) {
                str = new String(Base64.decode(str, 0));
            }
            JSONObject jSONObject = new JSONObject(str);
            this.mConfigUrl = jSONObject.getString(CONFIG_URL_KEY);
            this.mAuthorizationFingerprint = jSONObject.getString(AUTHORIZATION_FINGERPRINT_KEY);
        } catch (NullPointerException | JSONException unused) {
            throw new InvalidArgumentException("Client token was invalid");
        }
    }

    public String getConfigUrl() {
        return this.mConfigUrl;
    }

    public String getBearer() {
        return this.mAuthorizationFingerprint;
    }

    public String getAuthorizationFingerprint() {
        return this.mAuthorizationFingerprint;
    }

    public String getCustomerId() {
        String[] split;
        for (String str : getAuthorizationFingerprint().split("&")) {
            if (str.contains("customer_id=")) {
                String[] split2 = str.split("=");
                if (split2.length > 1) {
                    return split2[1];
                }
            }
        }
        return null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mConfigUrl);
        parcel.writeString(this.mAuthorizationFingerprint);
    }

    protected ClientToken(Parcel parcel) {
        super(parcel);
        this.mConfigUrl = parcel.readString();
        this.mAuthorizationFingerprint = parcel.readString();
    }
}
