package com.mobiroller.module;

import android.app.Activity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ActivityModule_ProvidesAppCompatActivityFactory implements Factory<Activity> {
    private final ActivityModule module;

    public ActivityModule_ProvidesAppCompatActivityFactory(ActivityModule activityModule) {
        this.module = activityModule;
    }

    public Activity get() {
        return proxyProvidesAppCompatActivity(this.module);
    }

    public static ActivityModule_ProvidesAppCompatActivityFactory create(ActivityModule activityModule) {
        return new ActivityModule_ProvidesAppCompatActivityFactory(activityModule);
    }

    public static Activity proxyProvidesAppCompatActivity(ActivityModule activityModule) {
        return (Activity) Preconditions.checkNotNull(activityModule.providesAppCompatActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
