package com.mobiroller.module;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ActivityModule_ProvidesApplicationContextFactory implements Factory<Context> {
    private final ActivityModule module;

    public ActivityModule_ProvidesApplicationContextFactory(ActivityModule activityModule) {
        this.module = activityModule;
    }

    public Context get() {
        return proxyProvidesApplicationContext(this.module);
    }

    public static ActivityModule_ProvidesApplicationContextFactory create(ActivityModule activityModule) {
        return new ActivityModule_ProvidesApplicationContextFactory(activityModule);
    }

    public static Context proxyProvidesApplicationContext(ActivityModule activityModule) {
        return (Context) Preconditions.checkNotNull(activityModule.providesApplicationContext(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
