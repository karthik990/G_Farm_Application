package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.BuildConfig;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.paypal.android.sdk.onetouch.core.network.EnvironmentManager;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

public class TokenizationKey extends Authorization implements Parcelable {
    public static final Creator<TokenizationKey> CREATOR = new Creator<TokenizationKey>() {
        public TokenizationKey createFromParcel(Parcel parcel) {
            return new TokenizationKey(parcel);
        }

        public TokenizationKey[] newArray(int i) {
            return new TokenizationKey[i];
        }
    };
    protected static final String MATCHER = "^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9_]+$";
    private final String mEnvironment;
    private final String mMerchantId;
    private final String mUrl;

    private enum BraintreeEnvironment {
        DEVELOPMENT("development", BuildConfig.DEVELOPMENT_URL),
        SANDBOX(EnvironmentManager.SANDBOX, "https://api.sandbox.braintreegateway.com/"),
        PRODUCTION(com.mobiroller.preview.BuildConfig.FLAVOR_server, "https://api.braintreegateway.com/");
        
        private String mEnvironment;
        private String mUrl;

        private BraintreeEnvironment(String str, String str2) {
            this.mEnvironment = str;
            this.mUrl = str2;
        }

        static String getUrl(String str) throws InvalidArgumentException {
            BraintreeEnvironment[] values;
            for (BraintreeEnvironment braintreeEnvironment : values()) {
                if (braintreeEnvironment.mEnvironment.equals(str)) {
                    return braintreeEnvironment.mUrl;
                }
            }
            throw new InvalidArgumentException("Tokenization Key contained invalid environment");
        }
    }

    public int describeContents() {
        return 0;
    }

    TokenizationKey(String str) throws InvalidArgumentException {
        super(str);
        String[] split = str.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, 3);
        this.mEnvironment = split[0];
        this.mMerchantId = split[2];
        StringBuilder sb = new StringBuilder();
        sb.append(BraintreeEnvironment.getUrl(this.mEnvironment));
        sb.append("merchants/");
        sb.append(this.mMerchantId);
        sb.append("/client_api/");
        this.mUrl = sb.toString();
    }

    public String getEnvironment() {
        return this.mEnvironment;
    }

    public String getMerchantId() {
        return this.mMerchantId;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getConfigUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mUrl);
        sb.append("v1/configuration");
        return sb.toString();
    }

    public String getBearer() {
        return toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mEnvironment);
        parcel.writeString(this.mMerchantId);
        parcel.writeString(this.mUrl);
    }

    protected TokenizationKey(Parcel parcel) {
        super(parcel);
        this.mEnvironment = parcel.readString();
        this.mMerchantId = parcel.readString();
        this.mUrl = parcel.readString();
    }
}
