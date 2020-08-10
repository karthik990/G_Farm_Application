package com.startapp.android.publish.adsCommon;

import android.app.Activity;
import android.content.Context;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.startapp.android.publish.common.metaData.MetaDataRequest.C5109a;

public class StartAppSDK {
    public static void init(Activity activity, String str) {
        init(activity, str, new SDKAdPreferences());
    }

    public static void init(Activity activity, String str, String str2) {
        init(activity, str, str2, new SDKAdPreferences());
    }

    public static void init(Activity activity, String str, SDKAdPreferences sDKAdPreferences) {
        C5059m.m3378a().mo62376a((Context) activity, (String) null, str, sDKAdPreferences, true);
    }

    public static void init(Activity activity, String str, String str2, SDKAdPreferences sDKAdPreferences) {
        C5059m.m3378a().mo62376a((Context) activity, str, str2, sDKAdPreferences, true);
    }

    public static void init(Activity activity, String str, boolean z) {
        init(activity, str, new SDKAdPreferences(), z);
    }

    public static void init(Activity activity, String str, String str2, boolean z) {
        init(activity, str, str2, new SDKAdPreferences(), z);
    }

    public static void init(Activity activity, String str, SDKAdPreferences sDKAdPreferences, boolean z) {
        C5059m.m3378a().mo62376a((Context) activity, (String) null, str, sDKAdPreferences, z);
    }

    public static void init(Activity activity, String str, String str2, SDKAdPreferences sDKAdPreferences, boolean z) {
        C5059m.m3378a().mo62376a((Context) activity, str, str2, sDKAdPreferences, z);
    }

    @Deprecated
    public static void init(Context context, String str, String str2) {
        init(context, str, str2, new SDKAdPreferences());
    }

    @Deprecated
    public static void init(Context context, String str, String str2, SDKAdPreferences sDKAdPreferences) {
        C5059m.m3378a().mo62376a(context, str, str2, sDKAdPreferences, true);
    }

    @Deprecated
    public static void init(Context context, String str, boolean z) {
        init(context, (String) null, str, z);
    }

    @Deprecated
    public static void init(Context context, String str, String str2, boolean z) {
        init(context, str, str2, new SDKAdPreferences(), z);
    }

    @Deprecated
    public static void init(Context context, String str, String str2, SDKAdPreferences sDKAdPreferences, boolean z) {
        C5059m.m3378a().mo62376a(context, str, str2, sDKAdPreferences, z);
    }

    public static void inAppPurchaseMade(Context context) {
        inAppPurchaseMade(context, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
    }

    public static void inAppPurchaseMade(Context context, double d) {
        C5051k.m3342b(context, "payingUser", Boolean.valueOf(true));
        String str = "inAppPurchaseAmount";
        double floatValue = (double) C5051k.m3336a(context, str, Float.valueOf(0.0f)).floatValue();
        Double.isNaN(floatValue);
        C5051k.m3343b(context, str, Float.valueOf((float) (floatValue + d)));
        C5059m.m3378a().mo62373a(context, C5109a.IN_APP_PURCHASE);
    }

    public static void startNewSession(Context context) {
        C5059m.m3378a().mo62373a(context, C5109a.CUSTOM);
    }

    public static void addWrapper(Context context, String str, String str2) {
        C5059m.m3378a().mo62375a(context, str, str2);
    }

    public static void enableReturnAds(boolean z) {
        C5059m.m3378a().mo62395e(z);
    }

    private static void pauseServices(Context context) {
        C5059m.m3378a().mo62372a(context);
        C5059m.m3378a().mo62382b(context);
    }

    private static void resumeServices(Context context) {
        C5059m.m3378a().mo62386c(context);
        C5059m.m3378a().mo62390d(context);
    }

    public static void setUserConsent(Context context, String str, long j, boolean z) {
        C5059m.m3378a().mo62374a(context, str, j, z, true);
    }
}
