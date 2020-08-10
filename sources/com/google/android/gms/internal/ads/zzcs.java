package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;

final class zzcs implements zzcv {
    private final /* synthetic */ Activity val$activity;

    zzcs(zzcn zzcn, Activity activity) {
        this.val$activity = activity;
    }

    public final void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        activityLifecycleCallbacks.onActivityStopped(this.val$activity);
    }
}
