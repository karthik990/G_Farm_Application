package com.mobiroller;

import android.content.Context;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesRequestHelperFactory implements Factory<RequestHelper> {
    private final Provider<Context> contextProvider;
    private final AppModule module;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public AppModule_ProvidesRequestHelperFactory(AppModule appModule, Provider<Context> provider, Provider<SharedPrefHelper> provider2) {
        this.module = appModule;
        this.contextProvider = provider;
        this.sharedPrefHelperProvider = provider2;
    }

    public RequestHelper get() {
        return proxyProvidesRequestHelper(this.module, (Context) this.contextProvider.get(), (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static AppModule_ProvidesRequestHelperFactory create(AppModule appModule, Provider<Context> provider, Provider<SharedPrefHelper> provider2) {
        return new AppModule_ProvidesRequestHelperFactory(appModule, provider, provider2);
    }

    public static RequestHelper proxyProvidesRequestHelper(AppModule appModule, Context context, SharedPrefHelper sharedPrefHelper) {
        return (RequestHelper) Preconditions.checkNotNull(appModule.providesRequestHelper(context, sharedPrefHelper), "Cannot return null from a non-@Nullable @Provides method");
    }
}
