package com.paypal.android.sdk.onetouch.core.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.braintreepayments.api.internal.SignatureVerification;
import com.paypal.android.sdk.data.collector.InstallationIdentifier;
import com.paypal.android.sdk.onetouch.core.AuthorizationRequest;
import com.paypal.android.sdk.onetouch.core.CheckoutRequest;
import com.paypal.android.sdk.onetouch.core.Request;
import com.paypal.android.sdk.onetouch.core.Result;
import com.paypal.android.sdk.onetouch.core.base.ContextInspector;
import com.paypal.android.sdk.onetouch.core.base.DeviceInspector;
import com.paypal.android.sdk.onetouch.core.config.ConfigManager;
import com.paypal.android.sdk.onetouch.core.config.Recipe;
import com.paypal.android.sdk.onetouch.core.enums.ResponseType;
import com.paypal.android.sdk.onetouch.core.exception.ResponseParsingException;
import com.paypal.android.sdk.onetouch.core.exception.WalletSwitchException;
import com.paypal.android.sdk.onetouch.core.fpti.TrackingPoint;
import com.paypal.android.sdk.onetouch.core.network.EnvironmentManager;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

public class AppSwitchHelper {
    private static final String WALLET_APP_CERT_ISSUER = "O=Paypal";
    private static final String WALLET_APP_CERT_SUBJECT = "O=Paypal";
    private static final String WALLET_APP_PACKAGE = "com.paypal.android.p2pmobile";
    private static final int WALLET_APP_PUBLIC_KEY_HASH_CODE = 34172764;

    public static boolean isSignatureValid(Context context, String str) {
        String str2 = "O=Paypal";
        return SignatureVerification.isSignatureValid(context, str, str2, str2, WALLET_APP_PUBLIC_KEY_HASH_CODE);
    }

    public static Intent createBaseIntent(String str, String str2) {
        return new Intent(str).setPackage(str2);
    }

    public static Intent getAppSwitchIntent(ContextInspector contextInspector, ConfigManager configManager, Request request, Recipe recipe) {
        String str = "app_guid";
        String str2 = "client_metadata_id";
        String str3 = "client_id";
        String str4 = "app_name";
        String str5 = "environment";
        String str6 = "environment_url";
        Intent putExtra = createBaseIntent(recipe.getTargetIntentAction(), WALLET_APP_PACKAGE).putExtra(ClientCookie.VERSION_ATTR, recipe.getProtocol().getVersion()).putExtra(str, InstallationIdentifier.getInstallationGUID(contextInspector.getContext())).putExtra(str2, request.getClientMetadataId()).putExtra(str3, request.getClientId()).putExtra(str4, DeviceInspector.getApplicationInfoName(contextInspector.getContext())).putExtra(str5, request.getEnvironment()).putExtra(str6, EnvironmentManager.getEnvironmentUrl(request.getEnvironment()));
        String str7 = "response_type";
        if (request instanceof AuthorizationRequest) {
            AuthorizationRequest authorizationRequest = (AuthorizationRequest) request;
            String str8 = "privacy_url";
            putExtra.putExtra("scope", authorizationRequest.getScopeString()).putExtra(str7, "code").putExtra(str8, authorizationRequest.getPrivacyUrl()).putExtra("agreement_url", authorizationRequest.getUserAgreementUrl());
        } else {
            putExtra.putExtra(str7, "web").putExtra("webURL", ((CheckoutRequest) request).getBrowserSwitchUrl(contextInspector.getContext(), configManager.getConfig()));
        }
        return putExtra;
    }

    public static Result parseAppSwitchResponse(ContextInspector contextInspector, Request request, Intent intent) {
        Bundle extras = intent.getExtras();
        if (request.validateV1V2Response(contextInspector, extras)) {
            request.trackFpti(contextInspector.getContext(), TrackingPoint.Return, null);
            return processResponseIntent(extras);
        }
        String str = "error";
        if (extras.containsKey(str)) {
            request.trackFpti(contextInspector.getContext(), TrackingPoint.Error, null);
            return new Result((Throwable) new WalletSwitchException(extras.getString(str)));
        }
        request.trackFpti(contextInspector.getContext(), TrackingPoint.Error, null);
        return new Result((Throwable) new ResponseParsingException("invalid wallet response"));
    }

    private static Result processResponseIntent(Bundle bundle) {
        ResponseType responseType;
        String str = "webURL";
        String string = bundle.getString("error");
        if (!TextUtils.isEmpty(string)) {
            return new Result((Throwable) new WalletSwitchException(string));
        }
        String string2 = bundle.getString("environment");
        String str2 = "code";
        if (str2.equals(bundle.getString("response_type").toLowerCase(Locale.US))) {
            responseType = ResponseType.authorization_code;
        } else {
            responseType = ResponseType.web;
        }
        try {
            if (ResponseType.web == responseType) {
                return new Result(string2, responseType, new JSONObject().put(str, bundle.getString(str)), null);
            }
            String string3 = bundle.getString("authorization_code");
            return new Result(string2, responseType, new JSONObject().put(str2, string3), bundle.getString("email"));
        } catch (JSONException e) {
            return new Result((Throwable) new ResponseParsingException((Throwable) e));
        }
    }
}
