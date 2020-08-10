package com.braintreepayments.api.exceptions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class BraintreeApiErrorResponse extends Exception implements Parcelable {
    public static final Creator<BraintreeApiErrorResponse> CREATOR = new Creator<BraintreeApiErrorResponse>() {
        public BraintreeApiErrorResponse createFromParcel(Parcel parcel) {
            return new BraintreeApiErrorResponse(parcel);
        }

        public BraintreeApiErrorResponse[] newArray(int i) {
            return new BraintreeApiErrorResponse[i];
        }
    };
    private List<BraintreeApiError> mErrors;
    private String mMessage;
    private String mOriginalResponse;

    public int describeContents() {
        return 0;
    }

    public BraintreeApiErrorResponse(String str) {
        this.mOriginalResponse = str;
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("error");
            this.mMessage = Json.optString(jSONObject, "developer_message", "No message was returned");
            this.mErrors = BraintreeApiError.fromJsonArray(jSONObject.optJSONArray("details"));
        } catch (JSONException unused) {
            this.mMessage = "Parsing error response failed";
        }
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getErrorResponse() {
        return this.mOriginalResponse;
    }

    public List<BraintreeApiError> getErrors() {
        return this.mErrors;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mMessage);
        parcel.writeString(this.mOriginalResponse);
        parcel.writeTypedList(this.mErrors);
    }

    protected BraintreeApiErrorResponse(Parcel parcel) {
        this.mMessage = parcel.readString();
        this.mOriginalResponse = parcel.readString();
        this.mErrors = parcel.createTypedArrayList(BraintreeApiError.CREATOR);
    }
}
