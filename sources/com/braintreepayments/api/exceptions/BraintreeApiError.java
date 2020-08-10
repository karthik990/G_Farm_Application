package com.braintreepayments.api.exceptions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BraintreeApiError implements Parcelable {
    public static final Creator<BraintreeApiError> CREATOR = new Creator<BraintreeApiError>() {
        public BraintreeApiError createFromParcel(Parcel parcel) {
            return new BraintreeApiError(parcel);
        }

        public BraintreeApiError[] newArray(int i) {
            return new BraintreeApiError[i];
        }
    };
    private String mAt;
    private String mCode;
    private String mIn;
    private String mMessage;

    public int describeContents() {
        return 0;
    }

    public static List<BraintreeApiError> fromJsonArray(JSONArray jSONArray) {
        if (jSONArray == null) {
            jSONArray = new JSONArray();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                arrayList.add(fromJson(jSONArray.getJSONObject(i)));
            } catch (JSONException unused) {
            }
        }
        return arrayList;
    }

    public static BraintreeApiError fromJson(JSONObject jSONObject) {
        BraintreeApiError braintreeApiError = new BraintreeApiError();
        braintreeApiError.mCode = Json.optString(jSONObject, "code", null);
        braintreeApiError.mMessage = Json.optString(jSONObject, "developer_message", null);
        braintreeApiError.mIn = Json.optString(jSONObject, "in", null);
        braintreeApiError.mAt = Json.optString(jSONObject, "at", null);
        return braintreeApiError;
    }

    public String getCode() {
        return this.mCode;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getIn() {
        return this.mIn;
    }

    public String getAt() {
        return this.mAt;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BraintreeApiError ");
        sb.append(this.mCode);
        sb.append(" for ");
        sb.append(this.mIn);
        sb.append(": ");
        sb.append(this.mMessage);
        return sb.toString();
    }

    public BraintreeApiError() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCode);
        parcel.writeString(this.mMessage);
        parcel.writeString(this.mIn);
        parcel.writeString(this.mAt);
    }

    protected BraintreeApiError(Parcel parcel) {
        this.mCode = parcel.readString();
        this.mMessage = parcel.readString();
        this.mIn = parcel.readString();
        this.mAt = parcel.readString();
    }
}
