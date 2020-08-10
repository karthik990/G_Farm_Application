package com.google.android.gms.measurement.internal;

import com.google.android.gms.measurement.AppMeasurement.UserProperty;

public class zzcw {
    public static final String[] zzaqx = {"firebase_last_notification", "first_open_time", "first_visit_time", "last_deep_link_referrer", "user_id", "first_open_after_install", "lifetime_user_engagement", "google_allow_ad_personalization_signals", "session_number", "session_id"};
    public static final String[] zzaqy = {UserProperty.FIREBASE_LAST_NOTIFICATION, "_fot", "_fvt", "_ldl", "_id", "_fi", "_lte", "_ap", "_sno", "_sid"};

    protected zzcw() {
    }

    public static String zzco(String str) {
        return zzdw.zza(str, zzaqx, zzaqy);
    }
}
