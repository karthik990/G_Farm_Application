package com.mobiroller.module;

import android.app.Activity;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ActivityModule_ProvidesLayoutHelperFactory implements Factory<LayoutHelper> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<Activity> appCompatActivityProvider;
    private final ActivityModule module;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public ActivityModule_ProvidesLayoutHelperFactory(ActivityModule activityModule, Provider<SharedPrefHelper> provider, Provider<Activity> provider2, Provider<ApiRequestManager> provider3) {
        this.module = activityModule;
        this.sharedPrefHelperProvider = provider;
        this.appCompatActivityProvider = provider2;
        this.apiRequestManagerProvider = provider3;
    }

    public LayoutHelper get() {
        return proxyProvidesLayoutHelper(this.module, (SharedPrefHelper) this.sharedPrefHelperProvider.get(), (Activity) this.appCompatActivityProvider.get(), (ApiRequestManager) this.apiRequestManagerProvider.get());
    }

    public static ActivityModule_ProvidesLayoutHelperFactory create(ActivityModule activityModule, Provider<SharedPrefHelper> provider, Provider<Activity> provider2, Provider<ApiRequestManager> provider3) {
        return new ActivityModule_ProvidesLayoutHelperFactory(activityModule, provider, provider2, provider3);
    }

    public static LayoutHelper proxyProvidesLayoutHelper(ActivityModule activityModule, SharedPrefHelper sharedPrefHelper, Activity activity, ApiRequestManager apiRequestManager) {
        return (LayoutHelper) Preconditions.checkNotNull(activityModule.providesLayoutHelper(sharedPrefHelper, activity, apiRequestManager), "Cannot return null from a non-@Nullable @Provides method");
    }
}
