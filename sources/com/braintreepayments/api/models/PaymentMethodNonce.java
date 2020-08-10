package com.braintreepayments.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class PaymentMethodNonce implements Parcelable {
    protected static final String DATA_KEY = "data";
    private static final String DESCRIPTION_KEY = "description";
    private static final String PAYMENT_METHOD_DEFAULT_KEY = "default";
    private static final String PAYMENT_METHOD_NONCE_COLLECTION_KEY = "paymentMethods";
    private static final String PAYMENT_METHOD_NONCE_KEY = "nonce";
    private static final String PAYMENT_METHOD_TYPE_KEY = "type";
    protected static final String TOKEN_KEY = "token";
    protected boolean mDefault;
    protected String mDescription;
    protected String mNonce;

    public int describeContents() {
        return 0;
    }

    public abstract String getTypeLabel();

    protected static JSONObject getJsonObjectForType(String str, JSONObject jSONObject) throws JSONException {
        return jSONObject.getJSONArray(str).getJSONObject(0);
    }

    /* access modifiers changed from: protected */
    public void fromJson(JSONObject jSONObject) throws JSONException {
        this.mNonce = jSONObject.getString(PAYMENT_METHOD_NONCE_KEY);
        this.mDescription = jSONObject.getString("description");
        this.mDefault = jSONObject.optBoolean("default", false);
    }

    public String getNonce() {
        return this.mNonce;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean isDefault() {
        return this.mDefault;
    }

    public static List<PaymentMethodNonce> parsePaymentMethodNonces(String str) throws JSONException {
        JSONArray jSONArray = new JSONObject(str).getJSONArray(PAYMENT_METHOD_NONCE_COLLECTION_KEY);
        if (jSONArray == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            PaymentMethodNonce parsePaymentMethodNonces = parsePaymentMethodNonces(jSONObject, jSONObject.getString("type"));
            if (parsePaymentMethodNonces != null) {
                arrayList.add(parsePaymentMethodNonces);
            }
        }
        return arrayList;
    }

    public static PaymentMethodNonce parsePaymentMethodNonces(String str, String str2) throws JSONException {
        return parsePaymentMethodNonces(new JSONObject(str), str2);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.braintreepayments.api.models.PaymentMethodNonce parsePaymentMethodNonces(org.json.JSONObject r4, java.lang.String r5) throws org.json.JSONException {
        /*
            int r0 = r5.hashCode()
            r1 = 3
            r2 = 2
            r3 = 1
            switch(r0) {
                case -1807185524: goto L_0x0029;
                case -650599305: goto L_0x001f;
                case 1212590010: goto L_0x0015;
                case 1428640201: goto L_0x000b;
                default: goto L_0x000a;
            }
        L_0x000a:
            goto L_0x0033
        L_0x000b:
            java.lang.String r0 = "CreditCard"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0033
            r5 = 0
            goto L_0x0034
        L_0x0015:
            java.lang.String r0 = "PayPalAccount"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0033
            r5 = 1
            goto L_0x0034
        L_0x001f:
            java.lang.String r0 = "VisaCheckoutCard"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0033
            r5 = 3
            goto L_0x0034
        L_0x0029:
            java.lang.String r0 = "VenmoAccount"
            boolean r5 = r5.equals(r0)
            if (r5 == 0) goto L_0x0033
            r5 = 2
            goto L_0x0034
        L_0x0033:
            r5 = -1
        L_0x0034:
            if (r5 == 0) goto L_0x008e
            if (r5 == r3) goto L_0x0074
            if (r5 == r2) goto L_0x0059
            if (r5 == r1) goto L_0x003e
            r4 = 0
            return r4
        L_0x003e:
            java.lang.String r5 = "visaCheckoutCards"
            boolean r5 = r4.has(r5)
            if (r5 == 0) goto L_0x0050
            java.lang.String r4 = r4.toString()
            com.braintreepayments.api.models.VisaCheckoutNonce r4 = com.braintreepayments.api.models.VisaCheckoutNonce.fromJson(r4)
            return r4
        L_0x0050:
            com.braintreepayments.api.models.VisaCheckoutNonce r5 = new com.braintreepayments.api.models.VisaCheckoutNonce
            r5.<init>()
            r5.fromJson(r4)
            return r5
        L_0x0059:
            java.lang.String r5 = "venmoAccounts"
            boolean r5 = r4.has(r5)
            if (r5 == 0) goto L_0x006b
            java.lang.String r4 = r4.toString()
            com.braintreepayments.api.models.VenmoAccountNonce r4 = com.braintreepayments.api.models.VenmoAccountNonce.fromJson(r4)
            return r4
        L_0x006b:
            com.braintreepayments.api.models.VenmoAccountNonce r5 = new com.braintreepayments.api.models.VenmoAccountNonce
            r5.<init>()
            r5.fromJson(r4)
            return r5
        L_0x0074:
            java.lang.String r5 = "paypalAccounts"
            boolean r5 = r4.has(r5)
            if (r5 == 0) goto L_0x0085
            java.lang.String r4 = r4.toString()
            com.braintreepayments.api.models.PayPalAccountNonce r4 = com.braintreepayments.api.models.PayPalAccountNonce.fromJson(r4)
            return r4
        L_0x0085:
            com.braintreepayments.api.models.PayPalAccountNonce r5 = new com.braintreepayments.api.models.PayPalAccountNonce
            r5.<init>()
            r5.fromJson(r4)
            return r5
        L_0x008e:
            java.lang.String r5 = "creditCards"
            boolean r5 = r4.has(r5)
            if (r5 != 0) goto L_0x00a8
            java.lang.String r5 = "data"
            boolean r5 = r4.has(r5)
            if (r5 == 0) goto L_0x009f
            goto L_0x00a8
        L_0x009f:
            com.braintreepayments.api.models.CardNonce r5 = new com.braintreepayments.api.models.CardNonce
            r5.<init>()
            r5.fromJson(r4)
            return r5
        L_0x00a8:
            java.lang.String r4 = r4.toString()
            com.braintreepayments.api.models.CardNonce r4 = com.braintreepayments.api.models.CardNonce.fromJson(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.braintreepayments.api.models.PaymentMethodNonce.parsePaymentMethodNonces(org.json.JSONObject, java.lang.String):com.braintreepayments.api.models.PaymentMethodNonce");
    }

    public PaymentMethodNonce() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mNonce);
        parcel.writeString(this.mDescription);
        parcel.writeByte(this.mDefault ? (byte) 1 : 0);
    }

    protected PaymentMethodNonce(Parcel parcel) {
        this.mNonce = parcel.readString();
        this.mDescription = parcel.readString();
        this.mDefault = parcel.readByte() > 0;
    }
}
