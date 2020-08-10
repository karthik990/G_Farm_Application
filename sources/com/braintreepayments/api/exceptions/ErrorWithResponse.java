package com.braintreepayments.api.exceptions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.internal.GraphQLConstants.ErrorMessages;
import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import com.mobiroller.constants.Constants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ErrorWithResponse extends Exception implements Parcelable {
    public static final Creator<ErrorWithResponse> CREATOR = new Creator<ErrorWithResponse>() {
        public ErrorWithResponse createFromParcel(Parcel parcel) {
            return new ErrorWithResponse(parcel);
        }

        public ErrorWithResponse[] newArray(int i) {
            return new ErrorWithResponse[i];
        }
    };
    private static final String ERROR_KEY = "error";
    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private List<BraintreeError> mFieldErrors;
    private String mMessage;
    private String mOriginalResponse;
    private int mStatusCode;

    public int describeContents() {
        return 0;
    }

    public ErrorWithResponse(int i, String str) {
        this.mStatusCode = i;
        this.mOriginalResponse = str;
        try {
            parseJson(str);
        } catch (JSONException unused) {
            this.mMessage = "Parsing error response failed";
            this.mFieldErrors = new ArrayList();
        }
    }

    private ErrorWithResponse() {
    }

    public static ErrorWithResponse fromJson(String str) throws JSONException {
        ErrorWithResponse errorWithResponse = new ErrorWithResponse();
        errorWithResponse.mOriginalResponse = str;
        errorWithResponse.parseJson(str);
        return errorWithResponse;
    }

    public static ErrorWithResponse fromGraphQLJson(String str) {
        ErrorWithResponse errorWithResponse = new ErrorWithResponse();
        errorWithResponse.mOriginalResponse = str;
        errorWithResponse.mStatusCode = 422;
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray(Keys.ERRORS);
            errorWithResponse.mFieldErrors = BraintreeError.fromGraphQLJsonArray(jSONArray);
            if (errorWithResponse.mFieldErrors.isEmpty()) {
                errorWithResponse.mMessage = jSONArray.getJSONObject(0).getString("message");
            } else {
                errorWithResponse.mMessage = ErrorMessages.USER;
            }
        } catch (JSONException unused) {
            errorWithResponse.mMessage = "Parsing error response failed";
            errorWithResponse.mFieldErrors = new ArrayList();
        }
        return errorWithResponse;
    }

    private void parseJson(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        this.mMessage = jSONObject.getJSONObject(ERROR_KEY).getString("message");
        this.mFieldErrors = BraintreeError.fromJsonArray(jSONObject.optJSONArray(FIELD_ERRORS_KEY));
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getErrorResponse() {
        return this.mOriginalResponse;
    }

    public List<BraintreeError> getFieldErrors() {
        return this.mFieldErrors;
    }

    public BraintreeError errorFor(String str) {
        List<BraintreeError> list = this.mFieldErrors;
        if (list != null) {
            for (BraintreeError braintreeError : list) {
                if (braintreeError.getField().equals(str)) {
                    return braintreeError;
                }
                if (braintreeError.getFieldErrors() != null) {
                    BraintreeError errorFor = braintreeError.errorFor(str);
                    if (errorFor != null) {
                        return errorFor;
                    }
                }
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ErrorWithResponse (");
        sb.append(this.mStatusCode);
        sb.append("): ");
        sb.append(this.mMessage);
        sb.append(Constants.NEW_LINE);
        sb.append(this.mFieldErrors.toString());
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatusCode);
        parcel.writeString(this.mMessage);
        parcel.writeString(this.mOriginalResponse);
        parcel.writeTypedList(this.mFieldErrors);
    }

    protected ErrorWithResponse(Parcel parcel) {
        this.mStatusCode = parcel.readInt();
        this.mMessage = parcel.readString();
        this.mOriginalResponse = parcel.readString();
        this.mFieldErrors = parcel.createTypedArrayList(BraintreeError.CREATOR);
    }
}
