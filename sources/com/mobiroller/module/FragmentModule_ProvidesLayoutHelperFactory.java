package com.mobiroller.module;

import android.app.Activity;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class FragmentModule_ProvidesLayoutHelperFactory implements Factory<LayoutHelper> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<Activity> appCompatActivityProvider;
    private final FragmentModule module;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public FragmentModule_ProvidesLayoutHelperFactory(FragmentModule fragmentModule, Provider<SharedPrefHelper> provider, Provider<Activity> provider2, Provider<ApiRequestManager> provider3) {
        this.module = fragmentModule;
        this.sharedPrefHelperProvider = provider;
        this.appCompatActivityProvider = provider2;
        this.apiRequestManagerProvider = provider3;
    }

    public LayoutHelper get() {
        return proxyProvidesLayoutHelper(this.module, (SharedPrefHelper) this.sharedPrefHelperProvider.get(), (Activity) this.appCompatActivityProvider.get(), (ApiRequestManager) this.apiRequestManagerProvider.get());
    }

    public static FragmentModule_ProvidesLayoutHelperFactory create(FragmentModule fragmentModule, Provider<SharedPrefHelper> provider, Provider<Activity> provider2, Provider<ApiRequestManager> provider3) {
        return new FragmentModule_ProvidesLayoutHelperFactory(fragmentModule, provider, provider2, provider3);
    }

    public static LayoutHelper proxyProvidesLayoutHelper(FragmentModule fragmentModule, SharedPrefHelper sharedPrefHelper, Activity activity, ApiRequestManager apiRequestManager) {
        return (LayoutHelper) Preconditions.checkNotNull(fragmentModule.providesLayoutHelper(sharedPrefHelper, activity, apiRequestManager), "Cannot return null from a non-@Nullable @Provides method");
    }
}
