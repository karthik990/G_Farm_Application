package com.paypal.android.sdk.onetouch.core;

import android.content.Context;
import android.content.Intent;
import com.paypal.android.sdk.data.collector.PayPalDataCollector;
import com.paypal.android.sdk.onetouch.core.base.ContextInspector;
import com.paypal.android.sdk.onetouch.core.config.ConfigManager;
import com.paypal.android.sdk.onetouch.core.config.OAuth2Recipe;
import com.paypal.android.sdk.onetouch.core.config.Recipe;
import com.paypal.android.sdk.onetouch.core.enums.RequestTarget;
import com.paypal.android.sdk.onetouch.core.fpti.FptiManager;
import com.paypal.android.sdk.onetouch.core.fpti.TrackingPoint;
import com.paypal.android.sdk.onetouch.core.network.EnvironmentManager;
import com.paypal.android.sdk.onetouch.core.network.PayPalHttpClient;
import com.paypal.android.sdk.onetouch.core.sdk.AppSwitchHelper;
import com.paypal.android.sdk.onetouch.core.sdk.BrowserSwitchHelper;
import com.paypal.android.sdk.onetouch.core.sdk.PendingRequest;

public class PayPalOneTouchCore {
    private static ConfigManager sConfigManager;
    private static ContextInspector sContextInspector;
    private static FptiManager sFptiManager;

    public static boolean isWalletAppInstalled(Context context) {
        initService(context);
        for (OAuth2Recipe oAuth2Recipe : sConfigManager.getConfig().getOauth2Recipes()) {
            if (oAuth2Recipe.getTarget() == RequestTarget.wallet && oAuth2Recipe.isValidAppTarget(context)) {
                return true;
            }
        }
        return false;
    }

    public static PendingRequest getStartIntent(Context context, Request request) {
        initService(context);
        isWalletAppInstalled(context);
        Recipe recipeToExecute = request.getRecipeToExecute(context, sConfigManager.getConfig());
        if (recipeToExecute == null) {
            return new PendingRequest(false, null, null, null);
        }
        if (RequestTarget.wallet == recipeToExecute.getTarget()) {
            request.trackFpti(context, TrackingPoint.SwitchToWallet, recipeToExecute.getProtocol());
            return new PendingRequest(true, RequestTarget.wallet, request.getClientMetadataId(), AppSwitchHelper.getAppSwitchIntent(sContextInspector, sConfigManager, request, recipeToExecute));
        }
        Intent browserSwitchIntent = BrowserSwitchHelper.getBrowserSwitchIntent(sContextInspector, sConfigManager, request);
        if (browserSwitchIntent != null) {
            return new PendingRequest(true, RequestTarget.browser, request.getClientMetadataId(), browserSwitchIntent);
        }
        return new PendingRequest(false, RequestTarget.browser, request.getClientMetadataId(), null);
    }

    public static Result parseResponse(Context context, Request request, Intent intent) {
        initService(context);
        if (intent != null && intent.getData() != null) {
            return BrowserSwitchHelper.parseBrowserSwitchResponse(sContextInspector, request, intent.getData());
        }
        if (intent != null && intent.getExtras() != null && !intent.getExtras().isEmpty()) {
            return AppSwitchHelper.parseAppSwitchResponse(sContextInspector, request, intent);
        }
        request.trackFpti(context, TrackingPoint.Cancel, null);
        return new Result();
    }

    public static String getClientMetadataId(Context context) {
        return PayPalDataCollector.getClientMetadataId(context);
    }

    public static String getClientMetadataId(Context context, String str) {
        return PayPalDataCollector.getClientMetadataId(context, str);
    }

    public static void useHardcodedConfig(Context context, boolean z) {
        initService(context);
        sConfigManager.useHardcodedConfig(z);
    }

    public static FptiManager getFptiManager(Context context) {
        initService(context);
        return sFptiManager;
    }

    private static void initService(Context context) {
        if (sConfigManager == null || sFptiManager == null) {
            PayPalHttpClient payPalHttpClient = (PayPalHttpClient) new PayPalHttpClient().setBaseUrl(EnvironmentManager.LIVE_API_M_ENDPOINT);
            sConfigManager = new ConfigManager(getContextInspector(context), payPalHttpClient);
            sFptiManager = new FptiManager(getContextInspector(context), payPalHttpClient);
        }
        sConfigManager.refreshConfiguration();
    }

    private static ContextInspector getContextInspector(Context context) {
        if (sContextInspector == null) {
            sContextInspector = new ContextInspector(context);
        }
        return sContextInspector;
    }
}
