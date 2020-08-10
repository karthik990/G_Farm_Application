package com.mobiroller;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvidesApplicationContextFactory implements Factory<Context> {
    private final AppModule module;

    public AppModule_ProvidesApplicationContextFactory(AppModule appModule) {
        this.module = appModule;
    }

    public Context get() {
        return proxyProvidesApplicationContext(this.module);
    }

    public static AppModule_ProvidesApplicationContextFactory create(AppModule appModule) {
        return new AppModule_ProvidesApplicationContextFactory(appModule);
    }

    public static Context proxyProvidesApplicationContext(AppModule appModule) {
        return (Context) Preconditions.checkNotNull(appModule.providesApplicationContext(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
