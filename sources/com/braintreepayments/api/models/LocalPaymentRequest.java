package com.braintreepayments.api.models;

import org.json.JSONException;
import org.json.JSONObject;

public class LocalPaymentRequest {
    private static final String AMOUNT_KEY = "amount";
    private static final String CANCEL_URL_KEY = "cancelUrl";
    private static final String COUNTRY_CODE_KEY = "countryCode";
    private static final String CURRENCY_CODE_KEY = "currencyIsoCode";
    private static final String EMAIL_KEY = "payerEmail";
    private static final String EXPERIENCE_PROFILE_KEY = "experienceProfile";
    private static final String EXTENDED_ADDRESS_KEY = "line2";
    private static final String FUNDING_SOURCE_KEY = "fundingSource";
    private static final String GIVEN_NAME_KEY = "firstName";
    private static final String INTENT_KEY = "intent";
    private static final String LOCALITY_KEY = "city";
    private static final String MERCHANT_ACCOUNT_ID_KEY = "merchantAccountId";
    private static final String NO_SHIPPING_KEY = "noShipping";
    private static final String PHONE_KEY = "phone";
    private static final String POSTAL_CODE_KEY = "postalCode";
    private static final String REGION_KEY = "state";
    private static final String RETURN_URL_KEY = "returnUrl";
    private static final String STREET_ADDRESS_KEY = "line1";
    private static final String SURNAME_KEY = "lastName";
    private PostalAddress mAddress;
    private String mAmount;
    private String mApprovalUrl;
    private String mCurrencyCode;
    private String mEmail;
    private String mGivenName;
    private String mMerchantAccountId;
    private String mPaymentId;
    private String mPaymentType;
    private String mPhone;
    private boolean mShippingAddressRequired;
    private String mSurname;

    public LocalPaymentRequest address(PostalAddress postalAddress) {
        this.mAddress = postalAddress;
        return this;
    }

    public LocalPaymentRequest amount(String str) {
        this.mAmount = str;
        return this;
    }

    public LocalPaymentRequest approvalUrl(String str) {
        if (this.mApprovalUrl == null) {
            this.mApprovalUrl = str;
        }
        return this;
    }

    public LocalPaymentRequest currencyCode(String str) {
        this.mCurrencyCode = str;
        return this;
    }

    public LocalPaymentRequest email(String str) {
        this.mEmail = str;
        return this;
    }

    public LocalPaymentRequest givenName(String str) {
        this.mGivenName = str;
        return this;
    }

    public LocalPaymentRequest merchantAccountId(String str) {
        this.mMerchantAccountId = str;
        return this;
    }

    public LocalPaymentRequest paymentId(String str) {
        if (this.mPaymentId == null) {
            this.mPaymentId = str;
        }
        return this;
    }

    public LocalPaymentRequest paymentType(String str) {
        this.mPaymentType = str;
        return this;
    }

    public LocalPaymentRequest phone(String str) {
        this.mPhone = str;
        return this;
    }

    public LocalPaymentRequest shippingAddressRequired(boolean z) {
        this.mShippingAddressRequired = z;
        return this;
    }

    public LocalPaymentRequest surname(String str) {
        this.mSurname = str;
        return this;
    }

    public PostalAddress getAddress() {
        return this.mAddress;
    }

    public String getAmount() {
        return this.mAmount;
    }

    public String getApprovalUrl() {
        return this.mApprovalUrl;
    }

    public String getCurrencyCode() {
        return this.mCurrencyCode;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public String getGivenName() {
        return this.mGivenName;
    }

    public String getMerchantAccountId() {
        return this.mMerchantAccountId;
    }

    public String getPaymentId() {
        return this.mPaymentId;
    }

    public String getPaymentType() {
        return this.mPaymentType;
    }

    public String getPhone() {
        return this.mPhone;
    }

    public boolean getShippingAddressRequired() {
        return this.mShippingAddressRequired;
    }

    public String getSurname() {
        return this.mSurname;
    }

    public String build(String str, String str2) {
        try {
            JSONObject put = new JSONObject().put(INTENT_KEY, PayPalRequest.INTENT_SALE).put(RETURN_URL_KEY, str).put(CANCEL_URL_KEY, str2).put(FUNDING_SOURCE_KEY, this.mPaymentType).put(AMOUNT_KEY, this.mAmount).put(CURRENCY_CODE_KEY, this.mCurrencyCode).put(GIVEN_NAME_KEY, this.mGivenName).put(SURNAME_KEY, this.mSurname).put(EMAIL_KEY, this.mEmail).put("phone", this.mPhone).put(MERCHANT_ACCOUNT_ID_KEY, this.mMerchantAccountId);
            if (this.mAddress != null) {
                put.put("line1", this.mAddress.getStreetAddress()).put("line2", this.mAddress.getExtendedAddress()).put("city", this.mAddress.getLocality()).put("state", this.mAddress.getRegion()).put("postalCode", this.mAddress.getPostalCode()).put("countryCode", this.mAddress.getCountryCodeAlpha2());
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(NO_SHIPPING_KEY, !this.mShippingAddressRequired);
            put.put(EXPERIENCE_PROFILE_KEY, jSONObject);
            return put.toString();
        } catch (JSONException unused) {
            return new JSONObject().toString();
        }
    }
}
