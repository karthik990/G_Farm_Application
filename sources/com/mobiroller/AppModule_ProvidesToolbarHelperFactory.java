package com.mobiroller;

import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesToolbarHelperFactory implements Factory<ToolbarHelper> {
    private final AppModule module;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public AppModule_ProvidesToolbarHelperFactory(AppModule appModule, Provider<SharedPrefHelper> provider) {
        this.module = appModule;
        this.sharedPrefHelperProvider = provider;
    }

    public ToolbarHelper get() {
        return proxyProvidesToolbarHelper(this.module, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static AppModule_ProvidesToolbarHelperFactory create(AppModule appModule, Provider<SharedPrefHelper> provider) {
        return new AppModule_ProvidesToolbarHelperFactory(appModule, provider);
    }

    public static ToolbarHelper proxyProvidesToolbarHelper(AppModule appModule, SharedPrefHelper sharedPrefHelper) {
        return (ToolbarHelper) Preconditions.checkNotNull(appModule.providesToolbarHelper(sharedPrefHelper), "Cannot return null from a non-@Nullable @Provides method");
    }
}
