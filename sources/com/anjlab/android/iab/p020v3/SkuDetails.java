package com.anjlab.android.iab.p020v3;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.anjlab.android.iab.v3.SkuDetails */
public class SkuDetails implements Parcelable {
    public static final Creator<SkuDetails> CREATOR = new Creator<SkuDetails>() {
        public SkuDetails createFromParcel(Parcel parcel) {
            return new SkuDetails(parcel);
        }

        public SkuDetails[] newArray(int i) {
            return new SkuDetails[i];
        }
    };
    public final String currency;
    public final String description;
    public final boolean haveIntroductoryPeriod;
    public final boolean haveTrialPeriod;
    public final int introductoryPriceCycles;
    public final long introductoryPriceLong;
    public final String introductoryPricePeriod;
    public final String introductoryPriceText;
    public final double introductoryPriceValue;
    public final boolean isSubscription;
    public final long priceLong;
    public final String priceText;
    public final Double priceValue;
    public final String productId;
    public final String subscriptionFreeTrialPeriod;
    public final String subscriptionPeriod;
    public final String title;

    public int describeContents() {
        return 0;
    }

    public SkuDetails(JSONObject jSONObject) throws JSONException {
        String optString = jSONObject.optString("type");
        if (optString == null) {
            optString = "inapp";
        }
        this.productId = jSONObject.optString("productId");
        this.title = jSONObject.optString("title");
        this.description = jSONObject.optString(Constants.RESPONSE_DESCRIPTION);
        this.isSubscription = optString.equalsIgnoreCase("subs");
        this.currency = jSONObject.optString(Constants.RESPONSE_PRICE_CURRENCY);
        this.priceLong = jSONObject.optLong(Constants.RESPONSE_PRICE_MICROS);
        double d = (double) this.priceLong;
        Double.isNaN(d);
        this.priceValue = Double.valueOf(d / 1000000.0d);
        this.priceText = jSONObject.optString("price");
        this.subscriptionPeriod = jSONObject.optString(Constants.RESPONSE_SUBSCRIPTION_PERIOD);
        this.subscriptionFreeTrialPeriod = jSONObject.optString(Constants.RESPONSE_FREE_TRIAL_PERIOD);
        this.haveTrialPeriod = !TextUtils.isEmpty(this.subscriptionFreeTrialPeriod);
        this.introductoryPriceLong = jSONObject.optLong(Constants.RESPONSE_INTRODUCTORY_PRICE_MICROS);
        double d2 = (double) this.introductoryPriceLong;
        Double.isNaN(d2);
        this.introductoryPriceValue = d2 / 1000000.0d;
        this.introductoryPriceText = jSONObject.optString(Constants.RESPONSE_INTRODUCTORY_PRICE);
        this.introductoryPricePeriod = jSONObject.optString(Constants.RESPONSE_INTRODUCTORY_PRICE_PERIOD);
        this.haveIntroductoryPeriod = !TextUtils.isEmpty(this.introductoryPricePeriod);
        this.introductoryPriceCycles = jSONObject.optInt(Constants.RESPONSE_INTRODUCTORY_PRICE_CYCLES);
    }

    public String toString() {
        return String.format(Locale.US, "%s: %s(%s) %f in %s (%s)", new Object[]{this.productId, this.title, this.description, this.priceValue, this.currency, this.priceText});
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SkuDetails skuDetails = (SkuDetails) obj;
        if (this.isSubscription != skuDetails.isSubscription) {
            return false;
        }
        String str = this.productId;
        String str2 = skuDetails.productId;
        if (str == null ? str2 != null : !str.equals(str2)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        String str = this.productId;
        return ((str != null ? str.hashCode() : 0) * 31) + (this.isSubscription ? 1 : 0);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.productId);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeByte(this.isSubscription ? (byte) 1 : 0);
        parcel.writeString(this.currency);
        parcel.writeDouble(this.priceValue.doubleValue());
        parcel.writeLong(this.priceLong);
        parcel.writeString(this.priceText);
        parcel.writeString(this.subscriptionPeriod);
        parcel.writeString(this.subscriptionFreeTrialPeriod);
        parcel.writeByte(this.haveTrialPeriod ? (byte) 1 : 0);
        parcel.writeDouble(this.introductoryPriceValue);
        parcel.writeLong(this.introductoryPriceLong);
        parcel.writeString(this.introductoryPriceText);
        parcel.writeString(this.introductoryPricePeriod);
        parcel.writeByte(this.haveIntroductoryPeriod ? (byte) 1 : 0);
        parcel.writeInt(this.introductoryPriceCycles);
    }

    protected SkuDetails(Parcel parcel) {
        this.productId = parcel.readString();
        this.title = parcel.readString();
        this.description = parcel.readString();
        boolean z = true;
        this.isSubscription = parcel.readByte() != 0;
        this.currency = parcel.readString();
        this.priceValue = Double.valueOf(parcel.readDouble());
        this.priceLong = parcel.readLong();
        this.priceText = parcel.readString();
        this.subscriptionPeriod = parcel.readString();
        this.subscriptionFreeTrialPeriod = parcel.readString();
        this.haveTrialPeriod = parcel.readByte() != 0;
        this.introductoryPriceValue = parcel.readDouble();
        this.introductoryPriceLong = parcel.readLong();
        this.introductoryPriceText = parcel.readString();
        this.introductoryPricePeriod = parcel.readString();
        if (parcel.readByte() == 0) {
            z = false;
        }
        this.haveIntroductoryPeriod = z;
        this.introductoryPriceCycles = parcel.readInt();
    }
}
