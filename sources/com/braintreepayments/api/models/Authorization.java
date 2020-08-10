package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.braintreepayments.api.exceptions.InvalidArgumentException;

public abstract class Authorization implements Parcelable {
    private final String mRawValue;

    public abstract String getBearer();

    public abstract String getConfigUrl();

    public Authorization(String str) {
        this.mRawValue = str;
    }

    public static Authorization fromString(String str) throws InvalidArgumentException {
        if (isTokenizationKey(str)) {
            return new TokenizationKey(str);
        }
        return new ClientToken(str);
    }

    public String toString() {
        return this.mRawValue;
    }

    public static boolean isTokenizationKey(String str) {
        return !TextUtils.isEmpty(str) && str.matches("^[a-zA-Z0-9]+_[a-zA-Z0-9]+_[a-zA-Z0-9_]+$");
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mRawValue);
    }

    public Authorization(Parcel parcel) {
        this.mRawValue = parcel.readString();
    }
}
