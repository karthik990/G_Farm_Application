package com.mobiroller;

import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesApiRequestManagerFactory implements Factory<ApiRequestManager> {
    private final AppModule module;
    private final Provider<RequestHelper> requestHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public AppModule_ProvidesApiRequestManagerFactory(AppModule appModule, Provider<SharedPrefHelper> provider, Provider<RequestHelper> provider2) {
        this.module = appModule;
        this.sharedPrefHelperProvider = provider;
        this.requestHelperProvider = provider2;
    }

    public ApiRequestManager get() {
        return proxyProvidesApiRequestManager(this.module, (SharedPrefHelper) this.sharedPrefHelperProvider.get(), (RequestHelper) this.requestHelperProvider.get());
    }

    public static AppModule_ProvidesApiRequestManagerFactory create(AppModule appModule, Provider<SharedPrefHelper> provider, Provider<RequestHelper> provider2) {
        return new AppModule_ProvidesApiRequestManagerFactory(appModule, provider, provider2);
    }

    public static ApiRequestManager proxyProvidesApiRequestManager(AppModule appModule, SharedPrefHelper sharedPrefHelper, RequestHelper requestHelper) {
        return (ApiRequestManager) Preconditions.checkNotNull(appModule.providesApiRequestManager(sharedPrefHelper, requestHelper), "Cannot return null from a non-@Nullable @Provides method");
    }
}
