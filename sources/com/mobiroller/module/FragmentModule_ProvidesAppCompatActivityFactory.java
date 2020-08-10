package com.mobiroller.module;

import android.app.Activity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class FragmentModule_ProvidesAppCompatActivityFactory implements Factory<Activity> {
    private final FragmentModule module;

    public FragmentModule_ProvidesAppCompatActivityFactory(FragmentModule fragmentModule) {
        this.module = fragmentModule;
    }

    public Activity get() {
        return proxyProvidesAppCompatActivity(this.module);
    }

    public static FragmentModule_ProvidesAppCompatActivityFactory create(FragmentModule fragmentModule) {
        return new FragmentModule_ProvidesAppCompatActivityFactory(fragmentModule);
    }

    public static Activity proxyProvidesAppCompatActivity(FragmentModule fragmentModule) {
        return (Activity) Preconditions.checkNotNull(fragmentModule.providesAppCompatActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
