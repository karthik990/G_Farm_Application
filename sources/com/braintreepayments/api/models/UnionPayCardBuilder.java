package com.braintreepayments.api.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.braintreepayments.api.exceptions.BraintreeException;
import org.json.JSONException;
import org.json.JSONObject;

public class UnionPayCardBuilder extends BaseCardBuilder<UnionPayCardBuilder> implements Parcelable {
    public static final Creator<UnionPayCardBuilder> CREATOR = new Creator<UnionPayCardBuilder>() {
        public UnionPayCardBuilder createFromParcel(Parcel parcel) {
            return new UnionPayCardBuilder(parcel);
        }

        public UnionPayCardBuilder[] newArray(int i) {
            return new UnionPayCardBuilder[i];
        }
    };
    private static final String ENROLLMENT_ID_KEY = "id";
    private static final String MOBILE_COUNTRY_CODE_KEY = "mobileCountryCode";
    private static final String MOBILE_PHONE_NUMBER_KEY = "mobileNumber";
    private static final String SMS_CODE_KEY = "smsCode";
    private static final String UNIONPAY_ENROLLMENT_KEY = "unionPayEnrollment";
    private static final String UNIONPAY_KEY = "creditCard";
    private String mEnrollmentId;
    private String mMobileCountryCode;
    private String mMobilePhoneNumber;
    private String mSmsCode;

    /* access modifiers changed from: protected */
    public void buildGraphQL(Context context, JSONObject jSONObject, JSONObject jSONObject2) throws BraintreeException, JSONException {
    }

    @Deprecated
    public UnionPayCardBuilder validate(boolean z) {
        return this;
    }

    public UnionPayCardBuilder() {
    }

    public UnionPayCardBuilder mobileCountryCode(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mMobileCountryCode = null;
        } else {
            this.mMobileCountryCode = str;
        }
        return this;
    }

    public UnionPayCardBuilder mobilePhoneNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mMobilePhoneNumber = null;
        } else {
            this.mMobilePhoneNumber = str;
        }
        return this;
    }

    public UnionPayCardBuilder smsCode(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mSmsCode = null;
        } else {
            this.mSmsCode = str;
        }
        return this;
    }

    public UnionPayCardBuilder enrollmentId(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mEnrollmentId = null;
        } else {
            this.mEnrollmentId = str;
        }
        return this;
    }

    public JSONObject buildEnrollment() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("number", this.mCardnumber);
        jSONObject.put("expirationMonth", this.mExpirationMonth);
        jSONObject.put("expirationYear", this.mExpirationYear);
        jSONObject.put(MOBILE_COUNTRY_CODE_KEY, this.mMobileCountryCode);
        jSONObject.put(MOBILE_PHONE_NUMBER_KEY, this.mMobilePhoneNumber);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(UNIONPAY_ENROLLMENT_KEY, jSONObject);
        return jSONObject2;
    }

    /* access modifiers changed from: protected */
    public void build(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        super.build(jSONObject, jSONObject2);
        String str = "options";
        JSONObject optJSONObject = jSONObject2.optJSONObject(str);
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
            jSONObject2.put(str, optJSONObject);
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(SMS_CODE_KEY, this.mSmsCode);
        jSONObject3.put("id", this.mEnrollmentId);
        optJSONObject.put(UNIONPAY_ENROLLMENT_KEY, jSONObject3);
        jSONObject.put(UNIONPAY_KEY, jSONObject2);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mMobileCountryCode);
        parcel.writeString(this.mMobilePhoneNumber);
        parcel.writeString(this.mSmsCode);
        parcel.writeString(this.mEnrollmentId);
    }

    protected UnionPayCardBuilder(Parcel parcel) {
        super(parcel);
        this.mMobileCountryCode = parcel.readString();
        this.mMobilePhoneNumber = parcel.readString();
        this.mSmsCode = parcel.readString();
        this.mEnrollmentId = parcel.readString();
    }
}
