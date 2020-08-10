package p043io.fabric.sdk.android.services.common;

import android.content.Context;
import android.text.TextUtils;
import p043io.fabric.sdk.android.Fabric;

/* renamed from: io.fabric.sdk.android.services.common.FirebaseInfo */
public class FirebaseInfo {
    static final String AUTO_INITIALIZE = "io.fabric.auto_initialize";
    static final String FIREBASE_FEATURE_SWITCH = "com.crashlytics.useFirebaseAppId";
    static final String GOOGLE_APP_ID = "google_app_id";

    /* access modifiers changed from: 0000 */
    public String getApiKeyFromFirebaseAppId(Context context) {
        int resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, GOOGLE_APP_ID, "string");
        if (resourcesIdentifier == 0) {
            return null;
        }
        Fabric.getLogger().mo64074d(Fabric.TAG, "Generating Crashlytics ApiKey from google_app_id in Strings");
        return createApiKeyFromFirebaseAppId(context.getResources().getString(resourcesIdentifier));
    }

    /* access modifiers changed from: 0000 */
    public String createApiKeyFromFirebaseAppId(String str) {
        return CommonUtils.sha256(str).substring(0, 40);
    }

    public boolean isFirebaseCrashlyticsEnabled(Context context) {
        boolean z = false;
        if (CommonUtils.getBooleanResourceValue(context, FIREBASE_FEATURE_SWITCH, false)) {
            return true;
        }
        if (hasGoogleAppId(context) && !hasApiKey(context)) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasApiKey(Context context) {
        if (!TextUtils.isEmpty(new ApiKey().getApiKeyFromManifest(context))) {
            return true;
        }
        return !TextUtils.isEmpty(new ApiKey().getApiKeyFromStrings(context));
    }

    public boolean isAutoInitializeFlagEnabled(Context context) {
        int resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, AUTO_INITIALIZE, "bool");
        if (resourcesIdentifier == 0) {
            return false;
        }
        boolean z = context.getResources().getBoolean(resourcesIdentifier);
        if (z) {
            Fabric.getLogger().mo64074d(Fabric.TAG, "Found Fabric auto-initialization flag for joint Firebase/Fabric customers");
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasGoogleAppId(Context context) {
        int resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, GOOGLE_APP_ID, "string");
        if (resourcesIdentifier == 0) {
            return false;
        }
        return !TextUtils.isEmpty(context.getResources().getString(resourcesIdentifier));
    }
}
