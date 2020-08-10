package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import org.json.JSONObject;

public class BinData implements Parcelable {
    public static final String BIN_DATA_KEY = "binData";
    private static final String COMMERCIAL_KEY = "commercial";
    private static final String COUNTRY_OF_ISSUANCE_KEY = "countryOfIssuance";
    public static final Creator<BinData> CREATOR = new Creator<BinData>() {
        public BinData createFromParcel(Parcel parcel) {
            return new BinData(parcel);
        }

        public BinData[] newArray(int i) {
            return new BinData[i];
        }
    };
    private static final String DEBIT_KEY = "debit";
    private static final String DURBIN_REGULATED_KEY = "durbinRegulated";
    private static final String HEALTHCARE_KEY = "healthcare";
    private static final String ISSUING_BANK_KEY = "issuingBank";

    /* renamed from: NO */
    public static final String f230NO = "No";
    private static final String PAYROLL_KEY = "payroll";
    private static final String PREPAID_KEY = "prepaid";
    private static final String PRODUCT_ID_KEY = "productId";
    public static final String UNKNOWN = "Unknown";
    public static final String YES = "Yes";
    private String mCommercial;
    private String mCountryOfIssuance;
    private String mDebit;
    private String mDurbinRegulated;
    private String mHealthcare;
    private String mIssuingBank;
    private String mPayroll;
    private String mPrepaid;
    private String mProductId;

    public int describeContents() {
        return 0;
    }

    protected static BinData fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        BinData binData = new BinData();
        String str = UNKNOWN;
        binData.mPrepaid = Json.optString(jSONObject, PREPAID_KEY, str);
        binData.mHealthcare = Json.optString(jSONObject, HEALTHCARE_KEY, str);
        binData.mDebit = Json.optString(jSONObject, DEBIT_KEY, str);
        binData.mDurbinRegulated = Json.optString(jSONObject, DURBIN_REGULATED_KEY, str);
        binData.mCommercial = Json.optString(jSONObject, COMMERCIAL_KEY, str);
        binData.mPayroll = Json.optString(jSONObject, PAYROLL_KEY, str);
        binData.mIssuingBank = convertNullToUnknown(jSONObject, ISSUING_BANK_KEY);
        binData.mCountryOfIssuance = convertNullToUnknown(jSONObject, COUNTRY_OF_ISSUANCE_KEY);
        binData.mProductId = convertNullToUnknown(jSONObject, "productId");
        return binData;
    }

    private static String convertNullToUnknown(JSONObject jSONObject, String str) {
        if (!jSONObject.has(str) || !jSONObject.isNull(str)) {
            return Json.optString(jSONObject, str, "");
        }
        return UNKNOWN;
    }

    public String getPrepaid() {
        return this.mPrepaid;
    }

    public String getHealthcare() {
        return this.mHealthcare;
    }

    public String getDebit() {
        return this.mDebit;
    }

    public String getDurbinRegulated() {
        return this.mDurbinRegulated;
    }

    public String getCommercial() {
        return this.mCommercial;
    }

    public String getPayroll() {
        return this.mPayroll;
    }

    public String getIssuingBank() {
        return this.mIssuingBank;
    }

    public String getCountryOfIssuance() {
        return this.mCountryOfIssuance;
    }

    public String getProductId() {
        return this.mProductId;
    }

    public BinData() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPrepaid);
        parcel.writeString(this.mHealthcare);
        parcel.writeString(this.mDebit);
        parcel.writeString(this.mDurbinRegulated);
        parcel.writeString(this.mCommercial);
        parcel.writeString(this.mPayroll);
        parcel.writeString(this.mIssuingBank);
        parcel.writeString(this.mCountryOfIssuance);
        parcel.writeString(this.mProductId);
    }

    private BinData(Parcel parcel) {
        this.mPrepaid = parcel.readString();
        this.mHealthcare = parcel.readString();
        this.mDebit = parcel.readString();
        this.mDurbinRegulated = parcel.readString();
        this.mCommercial = parcel.readString();
        this.mPayroll = parcel.readString();
        this.mIssuingBank = parcel.readString();
        this.mCountryOfIssuance = parcel.readString();
        this.mProductId = parcel.readString();
    }
}
