package com.mobiroller;

import android.content.Context;
import com.mobiroller.helpers.ScreenHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesScreenHelperFactory implements Factory<ScreenHelper> {
    private final Provider<Context> mApplicationProvider;
    private final AppModule module;

    public AppModule_ProvidesScreenHelperFactory(AppModule appModule, Provider<Context> provider) {
        this.module = appModule;
        this.mApplicationProvider = provider;
    }

    public ScreenHelper get() {
        return proxyProvidesScreenHelper(this.module, (Context) this.mApplicationProvider.get());
    }

    public static AppModule_ProvidesScreenHelperFactory create(AppModule appModule, Provider<Context> provider) {
        return new AppModule_ProvidesScreenHelperFactory(appModule, provider);
    }

    public static ScreenHelper proxyProvidesScreenHelper(AppModule appModule, Context context) {
        return (ScreenHelper) Preconditions.checkNotNull(appModule.providesScreenHelper(context), "Cannot return null from a non-@Nullable @Provides method");
    }
}
