package com.crashlytics.android;

import android.content.Context;
import p043io.fabric.sdk.android.services.common.DataCollectionArbiter;

class ManifestEnabledCheckStrategy implements EnabledCheckStrategy {
    ManifestEnabledCheckStrategy() {
    }

    public boolean isCrashlyticsEnabled(Context context) {
        return DataCollectionArbiter.getInstance(context).shouldAutoInitialize();
    }
}
