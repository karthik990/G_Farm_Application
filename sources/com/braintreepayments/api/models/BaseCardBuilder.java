package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseCardBuilder<T> extends PaymentMethodBuilder<T> implements Parcelable {
    protected static final String BILLING_ADDRESS_KEY = "billingAddress";
    protected static final String CARDHOLDER_NAME_KEY = "cardholderName";
    protected static final String COMPANY_KEY = "company";
    protected static final String COUNTRY_CODE_ALPHA2_KEY = "countryCodeAlpha2";
    protected static final String COUNTRY_CODE_ALPHA3_KEY = "countryCodeAlpha3";
    protected static final String COUNTRY_CODE_KEY = "countryCode";
    protected static final String COUNTRY_CODE_NUMERIC_KEY = "countryCodeNumeric";
    protected static final String COUNTRY_NAME_KEY = "countryName";
    protected static final String CREDIT_CARD_KEY = "creditCard";
    protected static final String CVV_KEY = "cvv";
    protected static final String EXPIRATION_MONTH_KEY = "expirationMonth";
    protected static final String EXPIRATION_YEAR_KEY = "expirationYear";
    protected static final String EXTENDED_ADDRESS_KEY = "extendedAddress";
    protected static final String FIRST_NAME_KEY = "firstName";
    protected static final String LAST_NAME_KEY = "lastName";
    protected static final String LOCALITY_KEY = "locality";
    protected static final String NUMBER_KEY = "number";
    protected static final String POSTAL_CODE_KEY = "postalCode";
    protected static final String REGION_KEY = "region";
    protected static final String STREET_ADDRESS_KEY = "streetAddress";
    protected String mCardholderName;
    protected String mCardnumber;
    protected String mCompany;
    protected String mCountryCode;
    protected String mCountryCodeAlpha2;
    protected String mCountryCodeAlpha3;
    protected String mCountryCodeNumeric;
    protected String mCountryName;
    protected String mCvv;
    protected String mExpirationMonth;
    protected String mExpirationYear;
    protected String mExtendedAddress;
    protected String mFirstName;
    protected String mLastName;
    protected String mLocality;
    protected String mPostalCode;
    protected String mRegion;
    protected String mStreetAddress;

    public int describeContents() {
        return 0;
    }

    public String getApiPath() {
        return "credit_cards";
    }

    public String getResponsePaymentMethodType() {
        return "CreditCard";
    }

    public BaseCardBuilder() {
    }

    public T cardNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCardnumber = null;
        } else {
            this.mCardnumber = str;
        }
        return this;
    }

    public T cvv(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCvv = null;
        } else {
            this.mCvv = str;
        }
        return this;
    }

    public T expirationMonth(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mExpirationMonth = null;
        } else {
            this.mExpirationMonth = str;
        }
        return this;
    }

    public T expirationYear(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mExpirationYear = null;
        } else {
            this.mExpirationYear = str;
        }
        return this;
    }

    public T expirationDate(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mExpirationMonth = null;
            this.mExpirationYear = null;
        } else {
            String[] split = str.split("/");
            this.mExpirationMonth = split[0];
            if (split.length > 1) {
                this.mExpirationYear = split[1];
            }
        }
        return this;
    }

    public T cardholderName(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCardholderName = null;
        } else {
            this.mCardholderName = str;
        }
        return this;
    }

    public T firstName(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mFirstName = null;
        } else {
            this.mFirstName = str;
        }
        return this;
    }

    public T lastName(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mLastName = null;
        } else {
            this.mLastName = str;
        }
        return this;
    }

    public T company(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCompany = null;
        } else {
            this.mCompany = str;
        }
        return this;
    }

    public T countryCode(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCountryCode = null;
        } else {
            this.mCountryCode = str;
        }
        return this;
    }

    @Deprecated
    public T countryName(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCountryName = null;
        } else {
            this.mCountryName = str;
        }
        return this;
    }

    @Deprecated
    public T countryCodeAlpha2(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCountryCodeAlpha2 = null;
        } else {
            this.mCountryCodeAlpha2 = str;
        }
        return this;
    }

    @Deprecated
    public T countryCodeAlpha3(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCountryCodeAlpha3 = null;
        } else {
            this.mCountryCodeAlpha3 = str;
        }
        return this;
    }

    @Deprecated
    public T countryCodeNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mCountryCodeNumeric = null;
        } else {
            this.mCountryCodeNumeric = str;
        }
        return this;
    }

    public T locality(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mLocality = null;
        } else {
            this.mLocality = str;
        }
        return this;
    }

    public T postalCode(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mPostalCode = null;
        } else {
            this.mPostalCode = str;
        }
        return this;
    }

    public T region(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mRegion = null;
        } else {
            this.mRegion = str;
        }
        return this;
    }

    public T streetAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mStreetAddress = null;
        } else {
            this.mStreetAddress = str;
        }
        return this;
    }

    public T extendedAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mExtendedAddress = null;
        } else {
            this.mExtendedAddress = str;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void build(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        jSONObject2.put(NUMBER_KEY, this.mCardnumber);
        jSONObject2.put(CVV_KEY, this.mCvv);
        jSONObject2.put(EXPIRATION_MONTH_KEY, this.mExpirationMonth);
        jSONObject2.put(EXPIRATION_YEAR_KEY, this.mExpirationYear);
        jSONObject2.put(CARDHOLDER_NAME_KEY, this.mCardholderName);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(FIRST_NAME_KEY, this.mFirstName);
        jSONObject3.put(LAST_NAME_KEY, this.mLastName);
        jSONObject3.put(COMPANY_KEY, this.mCompany);
        jSONObject3.put(COUNTRY_NAME_KEY, this.mCountryName);
        jSONObject3.put(COUNTRY_CODE_ALPHA2_KEY, this.mCountryCodeAlpha2);
        String str = this.mCountryCodeAlpha3;
        String str2 = COUNTRY_CODE_ALPHA3_KEY;
        jSONObject3.put(str2, str);
        jSONObject3.put(COUNTRY_CODE_NUMERIC_KEY, this.mCountryCodeNumeric);
        jSONObject3.put("locality", this.mLocality);
        jSONObject3.put("postalCode", this.mPostalCode);
        jSONObject3.put("region", this.mRegion);
        jSONObject3.put(STREET_ADDRESS_KEY, this.mStreetAddress);
        jSONObject3.put(EXTENDED_ADDRESS_KEY, this.mExtendedAddress);
        String str3 = this.mCountryCode;
        if (str3 != null) {
            jSONObject3.put(str2, str3);
        }
        if (jSONObject3.length() > 0) {
            jSONObject2.put("billingAddress", jSONObject3);
        }
        jSONObject.put(CREDIT_CARD_KEY, jSONObject2);
    }

    protected BaseCardBuilder(Parcel parcel) {
        super(parcel);
        this.mCardnumber = parcel.readString();
        this.mCvv = parcel.readString();
        this.mExpirationMonth = parcel.readString();
        this.mExpirationYear = parcel.readString();
        this.mCardholderName = parcel.readString();
        this.mFirstName = parcel.readString();
        this.mLastName = parcel.readString();
        this.mCompany = parcel.readString();
        this.mCountryCode = parcel.readString();
        this.mCountryName = parcel.readString();
        this.mCountryCodeAlpha2 = parcel.readString();
        this.mCountryCodeAlpha3 = parcel.readString();
        this.mCountryCodeNumeric = parcel.readString();
        this.mLocality = parcel.readString();
        this.mPostalCode = parcel.readString();
        this.mRegion = parcel.readString();
        this.mStreetAddress = parcel.readString();
        this.mExtendedAddress = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mCardnumber);
        parcel.writeString(this.mCvv);
        parcel.writeString(this.mExpirationMonth);
        parcel.writeString(this.mExpirationYear);
        parcel.writeString(this.mCardholderName);
        parcel.writeString(this.mFirstName);
        parcel.writeString(this.mLastName);
        parcel.writeString(this.mCompany);
        parcel.writeString(this.mCountryCode);
        parcel.writeString(this.mCountryName);
        parcel.writeString(this.mCountryCodeAlpha2);
        parcel.writeString(this.mCountryCodeAlpha3);
        parcel.writeString(this.mCountryCodeNumeric);
        parcel.writeString(this.mLocality);
        parcel.writeString(this.mPostalCode);
        parcel.writeString(this.mRegion);
        parcel.writeString(this.mStreetAddress);
        parcel.writeString(this.mExtendedAddress);
    }
}
