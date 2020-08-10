package com.mobiroller.helpers;

import android.app.Activity;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class LayoutHelper_Factory implements Factory<LayoutHelper> {
    private final Provider<Activity> activityProvider;
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public LayoutHelper_Factory(Provider<SharedPrefHelper> provider, Provider<Activity> provider2, Provider<ApiRequestManager> provider3) {
        this.sharedPrefHelperProvider = provider;
        this.activityProvider = provider2;
        this.apiRequestManagerProvider = provider3;
    }

    public LayoutHelper get() {
        return new LayoutHelper((SharedPrefHelper) this.sharedPrefHelperProvider.get(), (Activity) this.activityProvider.get(), (ApiRequestManager) this.apiRequestManagerProvider.get());
    }

    public static LayoutHelper_Factory create(Provider<SharedPrefHelper> provider, Provider<Activity> provider2, Provider<ApiRequestManager> provider3) {
        return new LayoutHelper_Factory(provider, provider2, provider3);
    }

    public static LayoutHelper newLayoutHelper(SharedPrefHelper sharedPrefHelper, Activity activity, ApiRequestManager apiRequestManager) {
        return new LayoutHelper(sharedPrefHelper, activity, apiRequestManager);
    }
}
