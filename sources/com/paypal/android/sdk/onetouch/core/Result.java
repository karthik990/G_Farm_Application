package com.paypal.android.sdk.onetouch.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.paypal.android.sdk.onetouch.core.enums.ResponseType;
import com.paypal.android.sdk.onetouch.core.enums.ResultType;
import org.json.JSONException;
import org.json.JSONObject;

public final class Result implements Parcelable {
    public static final Creator<Result> CREATOR = new Creator<Result>() {
        public Result createFromParcel(Parcel parcel) {
            return new Result(parcel);
        }

        public Result[] newArray(int i) {
            return new Result[i];
        }
    };
    private static final String TAG = Result.class.getSimpleName();
    private final String mEnvironment;
    private final Throwable mError;
    private final JSONObject mResponse;
    private final ResponseType mResponseType;
    private final ResultType mResultType;
    private final String mUserEmail;

    public int describeContents() {
        return 0;
    }

    public Result(String str, ResponseType responseType, JSONObject jSONObject, String str2) {
        this(ResultType.Success, str, responseType, jSONObject, str2, null);
    }

    public Result(Throwable th) {
        this(ResultType.Error, null, null, null, null, th);
    }

    public Result() {
        this(ResultType.Cancel, null, null, null, null, null);
    }

    private Result(ResultType resultType, String str, ResponseType responseType, JSONObject jSONObject, String str2, Throwable th) {
        this.mEnvironment = str;
        this.mResultType = resultType;
        this.mResponseType = responseType;
        this.mResponse = jSONObject;
        this.mUserEmail = str2;
        this.mError = th;
    }

    public ResultType getResultType() {
        return this.mResultType;
    }

    public JSONObject getResponse() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("environment", this.mEnvironment);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("client", jSONObject);
            if (this.mResponse != null) {
                jSONObject2.put("response", this.mResponse);
            }
            if (this.mResponseType != null) {
                jSONObject2.put("response_type", this.mResponseType.name());
            }
            if (this.mUserEmail != null) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("display_string", this.mUserEmail);
                jSONObject2.put("user", jSONObject3);
            }
            return jSONObject2;
        } catch (JSONException e) {
            Log.e(TAG, "Error encoding JSON", e);
            return null;
        }
    }

    public Throwable getError() {
        return this.mError;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mEnvironment);
        parcel.writeSerializable(this.mResultType);
        parcel.writeSerializable(this.mResponseType);
        JSONObject jSONObject = this.mResponse;
        parcel.writeString(jSONObject == null ? null : jSONObject.toString());
        parcel.writeString(this.mUserEmail);
        parcel.writeSerializable(this.mError);
    }

    private Result(Parcel parcel) {
        this.mEnvironment = parcel.readString();
        this.mResultType = (ResultType) parcel.readSerializable();
        this.mResponseType = (ResponseType) parcel.readSerializable();
        JSONObject jSONObject = null;
        try {
            String readString = parcel.readString();
            if (readString != null) {
                jSONObject = new JSONObject(readString);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to read parceled JSON for mResponse", e);
        }
        this.mResponse = jSONObject;
        this.mUserEmail = parcel.readString();
        this.mError = (Throwable) parcel.readSerializable();
    }
}
