package com.paypal.android.sdk.onetouch.core;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.paypal.android.sdk.onetouch.core.config.BillingAgreementRecipe;
import com.paypal.android.sdk.onetouch.core.config.OtcConfiguration;
import com.paypal.android.sdk.onetouch.core.config.Recipe;
import com.paypal.android.sdk.onetouch.core.enums.RequestTarget;

public class BillingAgreementRequest extends CheckoutRequest {
    public static final Creator<BillingAgreementRequest> CREATOR = new Creator<BillingAgreementRequest>() {
        public BillingAgreementRequest[] newArray(int i) {
            return new BillingAgreementRequest[i];
        }

        public BillingAgreementRequest createFromParcel(Parcel parcel) {
            return new BillingAgreementRequest(parcel);
        }
    };
    private static final String TOKEN_QUERY_PARAM_KEY_BA_TOKEN = "ba_token";

    public BillingAgreementRequest() {
    }

    @Deprecated
    public BillingAgreementRequest pairingId(String str) {
        return pairingId((Context) null, str);
    }

    public BillingAgreementRequest pairingId(Context context, String str) {
        super.pairingId(context, str);
        return this;
    }

    public BillingAgreementRequest approvalURL(String str) {
        super.approvalURL(str);
        this.mTokenQueryParamKey = TOKEN_QUERY_PARAM_KEY_BA_TOKEN;
        return this;
    }

    public Recipe getRecipeToExecute(Context context, OtcConfiguration otcConfiguration) {
        for (BillingAgreementRecipe billingAgreementRecipe : otcConfiguration.getBillingAgreementRecipes()) {
            if (RequestTarget.wallet == billingAgreementRecipe.getTarget()) {
                if (billingAgreementRecipe.isValidAppTarget(context)) {
                    return billingAgreementRecipe;
                }
            } else if (RequestTarget.browser == billingAgreementRecipe.getTarget() && billingAgreementRecipe.isValidBrowserTarget(context, getBrowserSwitchUrl())) {
                return billingAgreementRecipe;
            }
        }
        return null;
    }

    protected BillingAgreementRequest(Parcel parcel) {
        super(parcel);
    }
}
