package com.mobiroller;

import android.content.Context;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesBannerHelperFactory implements Factory<BannerHelper> {
    private final Provider<Context> contextProvider;
    private final AppModule module;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public AppModule_ProvidesBannerHelperFactory(AppModule appModule, Provider<SharedPrefHelper> provider, Provider<Context> provider2, Provider<NetworkHelper> provider3) {
        this.module = appModule;
        this.sharedPrefHelperProvider = provider;
        this.contextProvider = provider2;
        this.networkHelperProvider = provider3;
    }

    public BannerHelper get() {
        return proxyProvidesBannerHelper(this.module, (SharedPrefHelper) this.sharedPrefHelperProvider.get(), (Context) this.contextProvider.get(), (NetworkHelper) this.networkHelperProvider.get());
    }

    public static AppModule_ProvidesBannerHelperFactory create(AppModule appModule, Provider<SharedPrefHelper> provider, Provider<Context> provider2, Provider<NetworkHelper> provider3) {
        return new AppModule_ProvidesBannerHelperFactory(appModule, provider, provider2, provider3);
    }

    public static BannerHelper proxyProvidesBannerHelper(AppModule appModule, SharedPrefHelper sharedPrefHelper, Context context, NetworkHelper networkHelper) {
        return (BannerHelper) Preconditions.checkNotNull(appModule.providesBannerHelper(sharedPrefHelper, context, networkHelper), "Cannot return null from a non-@Nullable @Provides method");
    }
}
