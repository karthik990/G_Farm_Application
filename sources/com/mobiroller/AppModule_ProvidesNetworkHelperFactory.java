package com.mobiroller;

import android.content.Context;
import com.mobiroller.helpers.NetworkHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesNetworkHelperFactory implements Factory<NetworkHelper> {
    private final Provider<Context> mApplicationProvider;
    private final AppModule module;

    public AppModule_ProvidesNetworkHelperFactory(AppModule appModule, Provider<Context> provider) {
        this.module = appModule;
        this.mApplicationProvider = provider;
    }

    public NetworkHelper get() {
        return proxyProvidesNetworkHelper(this.module, (Context) this.mApplicationProvider.get());
    }

    public static AppModule_ProvidesNetworkHelperFactory create(AppModule appModule, Provider<Context> provider) {
        return new AppModule_ProvidesNetworkHelperFactory(appModule, provider);
    }

    public static NetworkHelper proxyProvidesNetworkHelper(AppModule appModule, Context context) {
        return (NetworkHelper) Preconditions.checkNotNull(appModule.providesNetworkHelper(context), "Cannot return null from a non-@Nullable @Provides method");
    }
}
