package com.mobiroller;

import android.content.Context;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesSharedPrefHelperFactory implements Factory<SharedPrefHelper> {
    private final Provider<Context> mApplicationProvider;
    private final AppModule module;

    public AppModule_ProvidesSharedPrefHelperFactory(AppModule appModule, Provider<Context> provider) {
        this.module = appModule;
        this.mApplicationProvider = provider;
    }

    public SharedPrefHelper get() {
        return proxyProvidesSharedPrefHelper(this.module, (Context) this.mApplicationProvider.get());
    }

    public static AppModule_ProvidesSharedPrefHelperFactory create(AppModule appModule, Provider<Context> provider) {
        return new AppModule_ProvidesSharedPrefHelperFactory(appModule, provider);
    }

    public static SharedPrefHelper proxyProvidesSharedPrefHelper(AppModule appModule, Context context) {
        return (SharedPrefHelper) Preconditions.checkNotNull(appModule.providesSharedPrefHelper(context), "Cannot return null from a non-@Nullable @Provides method");
    }
}
