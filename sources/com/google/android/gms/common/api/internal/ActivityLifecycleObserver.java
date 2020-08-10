package com.google.android.gms.common.api.internal;

import android.app.Activity;

public abstract class ActivityLifecycleObserver {
    public abstract ActivityLifecycleObserver onStopCallOnce(Runnable runnable);

    /* renamed from: of */
    public static final ActivityLifecycleObserver m1399of(Activity activity) {
        return new zaa(activity);
    }
}
