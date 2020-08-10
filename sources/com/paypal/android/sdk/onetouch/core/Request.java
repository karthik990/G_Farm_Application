package com.paypal.android.sdk.onetouch.core;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.paypal.android.sdk.onetouch.core.Request;
import com.paypal.android.sdk.onetouch.core.base.ContextInspector;
import com.paypal.android.sdk.onetouch.core.config.OtcConfiguration;
import com.paypal.android.sdk.onetouch.core.config.Recipe;
import com.paypal.android.sdk.onetouch.core.enums.Protocol;
import com.paypal.android.sdk.onetouch.core.exception.InvalidEncryptionDataException;
import com.paypal.android.sdk.onetouch.core.fpti.TrackingPoint;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;

public abstract class Request<T extends Request<T>> implements Parcelable {
    private String mCancelUrl;
    private String mClientId;
    private String mClientMetadataId;
    private String mEnvironment;
    private String mSuccessUrl;

    private static String redirectURLHostAndPath() {
        return "onetouch/v1/";
    }

    public int describeContents() {
        return 0;
    }

    public abstract Recipe getBrowserSwitchRecipe(OtcConfiguration otcConfiguration);

    public abstract String getBrowserSwitchUrl();

    @Deprecated
    public abstract String getBrowserSwitchUrl(Context context, OtcConfiguration otcConfiguration) throws CertificateException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, JSONException, BadPaddingException, InvalidEncryptionDataException, InvalidKeyException;

    public abstract Recipe getRecipeToExecute(Context context, OtcConfiguration otcConfiguration);

    public abstract Result parseBrowserResponse(Uri uri);

    @Deprecated
    public abstract Result parseBrowserResponse(ContextInspector contextInspector, Uri uri);

    public abstract void trackFpti(Context context, TrackingPoint trackingPoint, Protocol protocol);

    public abstract boolean validateV1V2Response(Bundle bundle);

    @Deprecated
    public abstract boolean validateV1V2Response(ContextInspector contextInspector, Bundle bundle);

    public T environment(String str) {
        this.mEnvironment = str;
        return this;
    }

    public String getEnvironment() {
        return this.mEnvironment;
    }

    public T clientMetadataId(String str) {
        this.mClientMetadataId = str;
        return this;
    }

    public String getClientMetadataId() {
        return this.mClientMetadataId;
    }

    public T clientId(String str) {
        this.mClientId = str;
        return this;
    }

    public String getClientId() {
        return this.mClientId;
    }

    public T cancelUrl(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("://");
        sb.append(redirectURLHostAndPath());
        sb.append(str2);
        this.mCancelUrl = sb.toString();
        return this;
    }

    public String getCancelUrl() {
        return this.mCancelUrl;
    }

    public T successUrl(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("://");
        sb.append(redirectURLHostAndPath());
        sb.append(str2);
        this.mSuccessUrl = sb.toString();
        return this;
    }

    public String getSuccessUrl() {
        return this.mSuccessUrl;
    }

    protected Request() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mEnvironment);
        parcel.writeString(this.mClientId);
        parcel.writeString(this.mClientMetadataId);
        parcel.writeString(this.mCancelUrl);
        parcel.writeString(this.mSuccessUrl);
    }

    protected Request(Parcel parcel) {
        this.mEnvironment = parcel.readString();
        this.mClientId = parcel.readString();
        this.mClientMetadataId = parcel.readString();
        this.mCancelUrl = parcel.readString();
        this.mSuccessUrl = parcel.readString();
    }
}
