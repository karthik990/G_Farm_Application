package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.Json;
import org.json.JSONException;
import org.json.JSONObject;

public class LocalPaymentResult extends PaymentMethodNonce implements Parcelable {
    private static final String ACCOUNT_ADDRESS_KEY = "accountAddress";
    protected static final String API_RESOURCE_KEY = "paypalAccounts";
    private static final String BILLING_ADDRESS_KEY = "billingAddress";
    private static final String CLIENT_METADATA_ID_KEY = "correlationId";
    public static final Creator<LocalPaymentResult> CREATOR = new Creator<LocalPaymentResult>() {
        public LocalPaymentResult createFromParcel(Parcel parcel) {
            return new LocalPaymentResult(parcel);
        }

        public LocalPaymentResult[] newArray(int i) {
            return new LocalPaymentResult[i];
        }
    };
    private static final String DETAILS_KEY = "details";
    private static final String EMAIL_KEY = "email";
    private static final String FIRST_NAME_KEY = "firstName";
    private static final String LAST_NAME_KEY = "lastName";
    private static final String PAYER_ID_KEY = "payerId";
    private static final String PAYER_INFO_KEY = "payerInfo";
    private static final String PHONE_KEY = "phone";
    private static final String SHIPPING_ADDRESS_KEY = "shippingAddress";
    protected static final String TYPE = "PayPalAccount";
    private static final String TYPE_KEY = "type";
    private PostalAddress mBillingAddress;
    private String mClientMetadataId;
    private String mEmail;
    private String mGivenName;
    private String mPayerId;
    private String mPhone;
    private PostalAddress mShippingAddress;
    private String mSurname;
    private String mType;

    public static LocalPaymentResult fromJson(String str) throws JSONException {
        LocalPaymentResult localPaymentResult = new LocalPaymentResult();
        localPaymentResult.fromJson(getJsonObjectForType(API_RESOURCE_KEY, new JSONObject(str)));
        return localPaymentResult;
    }

    /* access modifiers changed from: protected */
    public void fromJson(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2;
        String str = ACCOUNT_ADDRESS_KEY;
        String str2 = "";
        super.fromJson(jSONObject);
        JSONObject jSONObject3 = jSONObject.getJSONObject(DETAILS_KEY);
        String str3 = "email";
        this.mEmail = Json.optString(jSONObject3, str3, null);
        this.mClientMetadataId = Json.optString(jSONObject3, CLIENT_METADATA_ID_KEY, null);
        this.mType = Json.optString(jSONObject, "type", TYPE);
        try {
            JSONObject jSONObject4 = jSONObject3.getJSONObject(PAYER_INFO_KEY);
            if (jSONObject4.has(str)) {
                jSONObject2 = jSONObject4.optJSONObject(str);
            } else {
                jSONObject2 = jSONObject4.optJSONObject("billingAddress");
            }
            JSONObject optJSONObject = jSONObject4.optJSONObject("shippingAddress");
            this.mBillingAddress = PostalAddressParser.fromJson(jSONObject2);
            this.mShippingAddress = PostalAddressParser.fromJson(optJSONObject);
            this.mGivenName = Json.optString(jSONObject4, FIRST_NAME_KEY, str2);
            this.mSurname = Json.optString(jSONObject4, LAST_NAME_KEY, str2);
            this.mPhone = Json.optString(jSONObject4, "phone", str2);
            this.mPayerId = Json.optString(jSONObject4, PAYER_ID_KEY, str2);
            if (this.mEmail == null) {
                this.mEmail = Json.optString(jSONObject4, str3, null);
            }
        } catch (JSONException unused) {
            this.mBillingAddress = new PostalAddress();
            this.mShippingAddress = new PostalAddress();
        }
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getDescription() {
        return super.getDescription();
    }

    public String getTypeLabel() {
        return this.mType;
    }

    public PostalAddress getBillingAddress() {
        return this.mBillingAddress;
    }

    public PostalAddress getShippingAddress() {
        return this.mShippingAddress;
    }

    public String getGivenName() {
        return this.mGivenName;
    }

    public String getSurname() {
        return this.mSurname;
    }

    public String getPhone() {
        return this.mPhone;
    }

    public String getClientMetadataId() {
        return this.mClientMetadataId;
    }

    public String getPayerId() {
        return this.mPayerId;
    }

    public LocalPaymentResult() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mClientMetadataId);
        parcel.writeParcelable(this.mBillingAddress, i);
        parcel.writeParcelable(this.mShippingAddress, i);
        parcel.writeString(this.mGivenName);
        parcel.writeString(this.mSurname);
        parcel.writeString(this.mEmail);
        parcel.writeString(this.mPhone);
        parcel.writeString(this.mPayerId);
        parcel.writeString(this.mType);
    }

    private LocalPaymentResult(Parcel parcel) {
        super(parcel);
        this.mClientMetadataId = parcel.readString();
        this.mBillingAddress = (PostalAddress) parcel.readParcelable(PostalAddress.class.getClassLoader());
        this.mShippingAddress = (PostalAddress) parcel.readParcelable(PostalAddress.class.getClassLoader());
        this.mGivenName = parcel.readString();
        this.mSurname = parcel.readString();
        this.mEmail = parcel.readString();
        this.mPhone = parcel.readString();
        this.mPayerId = parcel.readString();
        this.mType = parcel.readString();
    }
}
