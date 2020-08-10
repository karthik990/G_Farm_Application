package com.mobiroller;

import com.mobiroller.helpers.LocalizationHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvidesLocalizationHelperFactory implements Factory<LocalizationHelper> {
    private final AppModule module;

    public AppModule_ProvidesLocalizationHelperFactory(AppModule appModule) {
        this.module = appModule;
    }

    public LocalizationHelper get() {
        return proxyProvidesLocalizationHelper(this.module);
    }

    public static AppModule_ProvidesLocalizationHelperFactory create(AppModule appModule) {
        return new AppModule_ProvidesLocalizationHelperFactory(appModule);
    }

    public static LocalizationHelper proxyProvidesLocalizationHelper(AppModule appModule) {
        return (LocalizationHelper) Preconditions.checkNotNull(appModule.providesLocalizationHelper(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
