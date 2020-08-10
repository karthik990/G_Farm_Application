package com.mobiroller;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvidesApplicationFactory implements Factory<MobiRollerApplication> {
    private final AppModule module;

    public AppModule_ProvidesApplicationFactory(AppModule appModule) {
        this.module = appModule;
    }

    public MobiRollerApplication get() {
        return proxyProvidesApplication(this.module);
    }

    public static AppModule_ProvidesApplicationFactory create(AppModule appModule) {
        return new AppModule_ProvidesApplicationFactory(appModule);
    }

    public static MobiRollerApplication proxyProvidesApplication(AppModule appModule) {
        return (MobiRollerApplication) Preconditions.checkNotNull(appModule.providesApplication(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
