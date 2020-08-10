package com.crashlytics.android.beta;

import java.util.Collections;
import java.util.Map;
import p043io.fabric.sdk.android.Fabric;
import p043io.fabric.sdk.android.Kit;
import p043io.fabric.sdk.android.services.common.DeviceIdentifierProvider;
import p043io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;

public class Beta extends Kit<Boolean> implements DeviceIdentifierProvider {
    public static final String TAG = "Beta";

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:beta";
    }

    public String getVersion() {
        return "1.2.10.27";
    }

    public static Beta getInstance() {
        return (Beta) Fabric.getKit(Beta.class);
    }

    /* access modifiers changed from: protected */
    public Boolean doInBackground() {
        Fabric.getLogger().mo64074d(TAG, "Beta kit initializing...");
        return Boolean.valueOf(true);
    }

    public Map<DeviceIdentifierType, String> getDeviceIdentifiers() {
        return Collections.emptyMap();
    }
}
