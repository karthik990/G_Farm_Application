package com.crashlytics.android.answers;

import java.io.IOException;
import p043io.fabric.sdk.android.services.settings.AnalyticsSettingsData;

class DisabledSessionAnalyticsManagerStrategy implements SessionAnalyticsManagerStrategy {
    public void cancelTimeBasedFileRollOver() {
    }

    public void deleteAllEvents() {
    }

    public void processEvent(Builder builder) {
    }

    public boolean rollFileOver() throws IOException {
        return false;
    }

    public void scheduleTimeBasedRollOverIfNeeded() {
    }

    public void sendEvents() {
    }

    public void setAnalyticsSettingsData(AnalyticsSettingsData analyticsSettingsData, String str) {
    }

    DisabledSessionAnalyticsManagerStrategy() {
    }
}
