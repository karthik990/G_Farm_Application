package com.braintreepayments.api.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.models.Authorization;
import com.braintreepayments.api.models.ClientToken;
import com.mobiroller.constants.ChatConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p043io.fabric.sdk.android.services.common.CommonUtils;

public class AnalyticsSender {
    private static final String ANALYTICS_KEY = "analytics";
    private static final String ANDROID_ID_KEY = "androidId";
    private static final String AUTHORIZATION_FINGERPRINT_KEY = "authorization_fingerprint";
    private static final String DEVICE_APP_GENERATED_PERSISTENT_UUID_KEY = "deviceAppGeneratedPersistentUuid";
    private static final String DEVICE_MANUFACTURER_KEY = "deviceManufacturer";
    private static final String DEVICE_MODEL_KEY = "deviceModel";
    private static final String DEVICE_ROOTED_KEY = "deviceRooted";
    private static final String INTEGRATION_TYPE_KEY = "integrationType";
    private static final String IS_SIMULATOR_KEY = "isSimulator";
    private static final String KIND_KEY = "kind";
    private static final String MERCHANT_APP_ID_KEY = "merchantAppId";
    private static final String MERCHANT_APP_NAME_KEY = "merchantAppName";
    private static final String META_KEY = "_meta";
    private static final String PLATFORM_KEY = "platform";
    private static final String PLATFORM_VERSION_KEY = "platformVersion";
    private static final String SDK_VERSION_KEY = "sdkVersion";
    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String TOKENIZATION_KEY = "tokenization_key";

    public static void send(Context context, Authorization authorization, BraintreeHttpClient braintreeHttpClient, String str, boolean z) {
        final AnalyticsDatabase instance = AnalyticsDatabase.getInstance(context);
        try {
            for (final List list : instance.getPendingRequests()) {
                JSONObject serializeEvents = serializeEvents(context, authorization, list);
                if (z) {
                    try {
                        braintreeHttpClient.post(str, serializeEvents.toString());
                        instance.removeEvents(list);
                    } catch (Exception unused) {
                    }
                } else {
                    braintreeHttpClient.post(str, serializeEvents.toString(), new HttpResponseCallback() {
                        public void failure(Exception exc) {
                        }

                        public void success(String str) {
                            instance.removeEvents(list);
                        }
                    });
                }
            }
        } catch (JSONException unused2) {
        }
    }

    private static JSONObject serializeEvents(Context context, Authorization authorization, List<AnalyticsEvent> list) throws JSONException {
        AnalyticsEvent analyticsEvent = (AnalyticsEvent) list.get(0);
        JSONObject jSONObject = new JSONObject();
        if (authorization instanceof ClientToken) {
            jSONObject.put(AUTHORIZATION_FINGERPRINT_KEY, authorization.getBearer());
        } else {
            jSONObject.put(TOKENIZATION_KEY, authorization.getBearer());
        }
        jSONObject.put("_meta", analyticsEvent.metadata.put(PLATFORM_KEY, "Android").put(INTEGRATION_TYPE_KEY, analyticsEvent.getIntegrationType()).put(PLATFORM_VERSION_KEY, Integer.toString(VERSION.SDK_INT)).put(SDK_VERSION_KEY, "2.21.0").put(MERCHANT_APP_ID_KEY, context.getPackageName()).put(MERCHANT_APP_NAME_KEY, getAppName(context)).put(DEVICE_ROOTED_KEY, isDeviceRooted()).put(DEVICE_MANUFACTURER_KEY, Build.MANUFACTURER).put(DEVICE_MODEL_KEY, Build.MODEL).put(DEVICE_APP_GENERATED_PERSISTENT_UUID_KEY, UUIDHelper.getPersistentUUID(context)).put(IS_SIMULATOR_KEY, detectEmulator()));
        JSONArray jSONArray = new JSONArray();
        for (AnalyticsEvent analyticsEvent2 : list) {
            jSONArray.put(new JSONObject().put(KIND_KEY, analyticsEvent2.event).put("timestamp", analyticsEvent2.timestamp));
        }
        jSONObject.put("analytics", jSONArray);
        return jSONObject;
    }

    private static String detectEmulator() {
        if (!CommonUtils.GOOGLE_SDK.equalsIgnoreCase(Build.PRODUCT)) {
            if (!CommonUtils.SDK.equalsIgnoreCase(Build.PRODUCT)) {
                if (!"Genymotion".equalsIgnoreCase(Build.MANUFACTURER) && !Build.FINGERPRINT.contains("generic")) {
                    return "false";
                }
            }
        }
        return "true";
    }

    private static String getAppName(Context context) {
        ApplicationInfo applicationInfo;
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        String str = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            str = (String) packageManager.getApplicationLabel(applicationInfo);
        }
        return str == null ? "ApplicationNameUnknown" : str;
    }

    private static String isDeviceRooted() {
        boolean z;
        boolean z2;
        String str = Build.TAGS;
        boolean z3 = true;
        boolean z4 = str != null && str.contains("test-keys");
        try {
            z = new File("/system/app/Superuser.apk").exists();
        } catch (Exception unused) {
            z = false;
        }
        try {
            if (new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"/system/xbin/which", ChatConstants.ARG_USERS_ROLES_SUPER_USER}).getInputStream())).readLine() != null) {
                z2 = true;
                if (!z4 && !z && !z2) {
                    z3 = false;
                }
                return Boolean.toString(z3);
            }
        } catch (Exception unused2) {
        }
        z2 = false;
        z3 = false;
        return Boolean.toString(z3);
    }
}
