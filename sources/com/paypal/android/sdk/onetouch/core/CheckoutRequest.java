package com.paypal.android.sdk.onetouch.core;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.paypal.android.sdk.onetouch.core.base.ContextInspector;
import com.paypal.android.sdk.onetouch.core.config.CheckoutRecipe;
import com.paypal.android.sdk.onetouch.core.config.OtcConfiguration;
import com.paypal.android.sdk.onetouch.core.config.Recipe;
import com.paypal.android.sdk.onetouch.core.enums.Protocol;
import com.paypal.android.sdk.onetouch.core.enums.RequestTarget;
import com.paypal.android.sdk.onetouch.core.enums.ResponseType;
import com.paypal.android.sdk.onetouch.core.exception.BrowserSwitchException;
import com.paypal.android.sdk.onetouch.core.exception.ResponseParsingException;
import com.paypal.android.sdk.onetouch.core.fpti.TrackingPoint;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckoutRequest extends Request<CheckoutRequest> implements Parcelable {
    public static final Creator<CheckoutRequest> CREATOR = new Creator<CheckoutRequest>() {
        public CheckoutRequest[] newArray(int i) {
            return new CheckoutRequest[i];
        }

        public CheckoutRequest createFromParcel(Parcel parcel) {
            return new CheckoutRequest(parcel);
        }
    };
    private static final String TOKEN_QUERY_PARAM_KEY_TOKEN = "token";
    protected String mApprovalUrl;
    private String mPairingId;
    protected String mTokenQueryParamKey;

    public CheckoutRequest() {
        this.mTokenQueryParamKey = TOKEN_QUERY_PARAM_KEY_TOKEN;
    }

    public String getPairingId() {
        return this.mPairingId;
    }

    @Deprecated
    public CheckoutRequest pairingId(String str) {
        return pairingId(null, str);
    }

    public CheckoutRequest pairingId(Context context, String str) {
        this.mPairingId = str;
        clientMetadataId(PayPalOneTouchCore.getClientMetadataId(context, str));
        return this;
    }

    public CheckoutRequest approvalURL(String str) {
        this.mApprovalUrl = str;
        this.mTokenQueryParamKey = TOKEN_QUERY_PARAM_KEY_TOKEN;
        return this;
    }

    public String getBrowserSwitchUrl() {
        return this.mApprovalUrl;
    }

    @Deprecated
    public String getBrowserSwitchUrl(Context context, OtcConfiguration otcConfiguration) {
        return getBrowserSwitchUrl();
    }

    public Recipe getBrowserSwitchRecipe(OtcConfiguration otcConfiguration) {
        return otcConfiguration.getBrowserCheckoutConfig();
    }

    public Result parseBrowserResponse(Uri uri) {
        if (!Uri.parse(getSuccessUrl()).getLastPathSegment().equals(uri.getLastPathSegment())) {
            return new Result();
        }
        String queryParameter = Uri.parse(this.mApprovalUrl).getQueryParameter(this.mTokenQueryParamKey);
        String queryParameter2 = uri.getQueryParameter(this.mTokenQueryParamKey);
        if (queryParameter2 == null || !TextUtils.equals(queryParameter, queryParameter2)) {
            return new Result((Throwable) new BrowserSwitchException("The response contained inconsistent data."));
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("webURL", uri.toString());
            return new Result(null, ResponseType.web, jSONObject, null);
        } catch (JSONException e) {
            return new Result((Throwable) new ResponseParsingException((Throwable) e));
        }
    }

    @Deprecated
    public Result parseBrowserResponse(ContextInspector contextInspector, Uri uri) {
        return parseBrowserResponse(uri);
    }

    public boolean validateV1V2Response(Bundle bundle) {
        String queryParameter = Uri.parse(this.mApprovalUrl).getQueryParameter(this.mTokenQueryParamKey);
        String string = bundle.getString("webURL");
        if (string != null) {
            String queryParameter2 = Uri.parse(string).getQueryParameter(this.mTokenQueryParamKey);
            if (queryParameter2 != null && TextUtils.equals(queryParameter, queryParameter2)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public boolean validateV1V2Response(ContextInspector contextInspector, Bundle bundle) {
        return validateV1V2Response(bundle);
    }

    public Recipe getRecipeToExecute(Context context, OtcConfiguration otcConfiguration) {
        for (CheckoutRecipe checkoutRecipe : otcConfiguration.getCheckoutRecipes()) {
            if (RequestTarget.wallet == checkoutRecipe.getTarget()) {
                if (checkoutRecipe.isValidAppTarget(context)) {
                    return checkoutRecipe;
                }
            } else if (RequestTarget.browser == checkoutRecipe.getTarget() && checkoutRecipe.isValidBrowserTarget(context, getBrowserSwitchUrl(context, otcConfiguration))) {
                return checkoutRecipe;
            }
        }
        return null;
    }

    public void trackFpti(Context context, TrackingPoint trackingPoint, Protocol protocol) {
        String queryParameter = Uri.parse(this.mApprovalUrl).getQueryParameter(this.mTokenQueryParamKey);
        HashMap hashMap = new HashMap();
        hashMap.put("fltk", queryParameter);
        hashMap.put("clid", getClientId());
        PayPalOneTouchCore.getFptiManager(context).trackFpti(trackingPoint, getEnvironment(), hashMap, protocol);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mApprovalUrl);
        parcel.writeString(this.mTokenQueryParamKey);
        parcel.writeString(this.mPairingId);
    }

    protected CheckoutRequest(Parcel parcel) {
        super(parcel);
        this.mApprovalUrl = parcel.readString();
        this.mTokenQueryParamKey = parcel.readString();
        this.mPairingId = parcel.readString();
    }
}
