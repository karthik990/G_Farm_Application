package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import org.json.JSONException;
import org.json.JSONObject;

public class AmericanExpressRewardsBalance implements Parcelable {
    private static final String CONVERSION_RATE_KEY = "conversionRate";
    public static final Creator<AmericanExpressRewardsBalance> CREATOR = new Creator<AmericanExpressRewardsBalance>() {
        public AmericanExpressRewardsBalance createFromParcel(Parcel parcel) {
            return new AmericanExpressRewardsBalance(parcel);
        }

        public AmericanExpressRewardsBalance[] newArray(int i) {
            return new AmericanExpressRewardsBalance[i];
        }
    };
    private static final String CURRENCY_AMOUNT_KEY = "currencyAmount";
    private static final String CURRENCY_ISO_CODE_KEY = "currencyIsoCode";
    private static final String ERROR_CODE_KEY = "code";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MESSAGE_KEY = "message";
    private static final String REQUEST_ID_KEY = "requestId";
    private static final String REWARDS_AMOUNT_KEY = "rewardsAmount";
    private static final String REWARDS_UNIT_KEY = "rewardsUnit";
    private String mConversionRate;
    private String mCurrencyAmount;
    private String mCurrencyIsoCode;
    private String mErrorCode;
    private String mErrorMessage;
    private String mRequestId;
    private String mRewardsAmount;
    private String mRewardsUnit;

    public int describeContents() {
        return 0;
    }

    public static AmericanExpressRewardsBalance fromJson(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        AmericanExpressRewardsBalance americanExpressRewardsBalance = new AmericanExpressRewardsBalance();
        String str2 = ERROR_KEY;
        if (jSONObject.has(str2)) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str2);
            americanExpressRewardsBalance.mErrorMessage = jSONObject2.getString("message");
            americanExpressRewardsBalance.mErrorCode = jSONObject2.getString(ERROR_CODE_KEY);
        }
        americanExpressRewardsBalance.mConversionRate = Json.optString(jSONObject, CONVERSION_RATE_KEY, null);
        americanExpressRewardsBalance.mCurrencyAmount = Json.optString(jSONObject, CURRENCY_AMOUNT_KEY, null);
        americanExpressRewardsBalance.mCurrencyIsoCode = Json.optString(jSONObject, CURRENCY_ISO_CODE_KEY, null);
        americanExpressRewardsBalance.mRequestId = Json.optString(jSONObject, REQUEST_ID_KEY, null);
        americanExpressRewardsBalance.mRewardsAmount = Json.optString(jSONObject, REWARDS_AMOUNT_KEY, null);
        americanExpressRewardsBalance.mRewardsUnit = Json.optString(jSONObject, REWARDS_UNIT_KEY, null);
        return americanExpressRewardsBalance;
    }

    public String getErrorCode() {
        return this.mErrorCode;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public String getConversionRate() {
        return this.mConversionRate;
    }

    public String getCurrencyAmount() {
        return this.mCurrencyAmount;
    }

    public String getCurrencyIsoCode() {
        return this.mCurrencyIsoCode;
    }

    public String getRequestId() {
        return this.mRequestId;
    }

    public String getRewardsAmount() {
        return this.mRewardsAmount;
    }

    public String getRewardsUnit() {
        return this.mRewardsUnit;
    }

    public AmericanExpressRewardsBalance() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mErrorCode);
        parcel.writeString(this.mErrorMessage);
        parcel.writeString(this.mConversionRate);
        parcel.writeString(this.mCurrencyAmount);
        parcel.writeString(this.mCurrencyIsoCode);
        parcel.writeString(this.mRequestId);
        parcel.writeString(this.mRewardsAmount);
        parcel.writeString(this.mRewardsUnit);
    }

    private AmericanExpressRewardsBalance(Parcel parcel) {
        this.mErrorCode = parcel.readString();
        this.mErrorMessage = parcel.readString();
        this.mConversionRate = parcel.readString();
        this.mCurrencyAmount = parcel.readString();
        this.mCurrencyIsoCode = parcel.readString();
        this.mRequestId = parcel.readString();
        this.mRewardsAmount = parcel.readString();
        this.mRewardsUnit = parcel.readString();
    }
}
