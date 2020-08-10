package com.mobiroller;

import com.mobiroller.helpers.ComponentHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesComponentHelperFactory implements Factory<ComponentHelper> {
    private final AppModule module;
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public AppModule_ProvidesComponentHelperFactory(AppModule appModule, Provider<SharedPrefHelper> provider, Provider<ScreenHelper> provider2) {
        this.module = appModule;
        this.sharedPrefHelperProvider = provider;
        this.screenHelperProvider = provider2;
    }

    public ComponentHelper get() {
        return proxyProvidesComponentHelper(this.module, (SharedPrefHelper) this.sharedPrefHelperProvider.get(), (ScreenHelper) this.screenHelperProvider.get());
    }

    public static AppModule_ProvidesComponentHelperFactory create(AppModule appModule, Provider<SharedPrefHelper> provider, Provider<ScreenHelper> provider2) {
        return new AppModule_ProvidesComponentHelperFactory(appModule, provider, provider2);
    }

    public static ComponentHelper proxyProvidesComponentHelper(AppModule appModule, SharedPrefHelper sharedPrefHelper, ScreenHelper screenHelper) {
        return (ComponentHelper) Preconditions.checkNotNull(appModule.providesComponentHelper(sharedPrefHelper, screenHelper), "Cannot return null from a non-@Nullable @Provides method");
    }
}
