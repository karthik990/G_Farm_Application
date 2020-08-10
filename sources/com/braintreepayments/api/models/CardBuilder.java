package com.braintreepayments.api.models;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.braintreepayments.api.C1074R;
import com.braintreepayments.api.exceptions.BraintreeException;
import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import com.braintreepayments.api.internal.GraphQLQueryHelper;
import com.mobiroller.constants.UserConstants;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class CardBuilder extends BaseCardBuilder<CardBuilder> implements Parcelable {
    public static final Creator<CardBuilder> CREATOR = new Creator<CardBuilder>() {
        public CardBuilder createFromParcel(Parcel parcel) {
            return new CardBuilder(parcel);
        }

        public CardBuilder[] newArray(int i) {
            return new CardBuilder[i];
        }
    };

    /* access modifiers changed from: protected */
    public void buildGraphQL(Context context, JSONObject jSONObject, JSONObject jSONObject2) throws BraintreeException, JSONException {
        try {
            jSONObject.put("query", GraphQLQueryHelper.getQuery(context, C1074R.raw.tokenize_credit_card_mutation));
            jSONObject.put(Keys.OPERATION_NAME, "TokenizeCreditCard");
            String str = "expirationMonth";
            String str2 = "expirationYear";
            String str3 = "cvv";
            String str4 = "cardholderName";
            JSONObject put = new JSONObject().put("number", this.mCardnumber).put(str, this.mExpirationMonth).put(str2, this.mExpirationYear).put(str3, this.mCvv).put(str4, this.mCardholderName);
            String str5 = "lastName";
            String str6 = "company";
            String str7 = "countryCode";
            String str8 = "countryName";
            String str9 = "countryCodeAlpha2";
            String str10 = "countryCodeAlpha3";
            String str11 = "countryCodeNumeric";
            String str12 = "postalCode";
            String str13 = "streetAddress";
            String str14 = "extendedAddress";
            JSONObject put2 = new JSONObject().put("firstName", this.mFirstName).put(str5, this.mLastName).put(str6, this.mCompany).put(str7, this.mCountryCode).put(str8, this.mCountryName).put(str9, this.mCountryCodeAlpha2).put(str10, this.mCountryCodeAlpha3).put(str11, this.mCountryCodeNumeric).put(PostalAddressParser.USER_ADDRESS_LOCALITY_KEY, this.mLocality).put(str12, this.mPostalCode).put(TtmlNode.TAG_REGION, this.mRegion).put(str13, this.mStreetAddress).put(str14, this.mExtendedAddress);
            if (put2.length() > 0) {
                put.put(UserConstants.BUNDLE_EXTRA_USER_BILLING_ADDRESS, put2);
            }
            jSONObject2.put("creditCard", put);
        } catch (NotFoundException | IOException e) {
            throw new BraintreeException("Unable to read GraphQL query", e);
        }
    }

    public CardBuilder() {
    }

    protected CardBuilder(Parcel parcel) {
        super(parcel);
    }
}
