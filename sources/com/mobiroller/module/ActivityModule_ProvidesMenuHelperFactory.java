package com.mobiroller.module;

import android.app.Activity;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ActivityModule_ProvidesMenuHelperFactory implements Factory<MenuHelper> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<Activity> contextProvider;
    private final Provider<JSONParser> jParserNewProvider;
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final ActivityModule module;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public ActivityModule_ProvidesMenuHelperFactory(ActivityModule activityModule, Provider<Activity> provider, Provider<NetworkHelper> provider2, Provider<JSONParser> provider3, Provider<SharedPrefHelper> provider4, Provider<ApiRequestManager> provider5, Provider<LocalizationHelper> provider6, Provider<MobiRollerApplication> provider7, Provider<ScreenHelper> provider8) {
        this.module = activityModule;
        this.contextProvider = provider;
        this.networkHelperProvider = provider2;
        this.jParserNewProvider = provider3;
        this.sharedPrefHelperProvider = provider4;
        this.apiRequestManagerProvider = provider5;
        this.localizationHelperProvider = provider6;
        this.appProvider = provider7;
        this.screenHelperProvider = provider8;
    }

    public MenuHelper get() {
        return proxyProvidesMenuHelper(this.module, (Activity) this.contextProvider.get(), (NetworkHelper) this.networkHelperProvider.get(), (JSONParser) this.jParserNewProvider.get(), (SharedPrefHelper) this.sharedPrefHelperProvider.get(), (ApiRequestManager) this.apiRequestManagerProvider.get(), (LocalizationHelper) this.localizationHelperProvider.get(), (MobiRollerApplication) this.appProvider.get(), (ScreenHelper) this.screenHelperProvider.get());
    }

    public static ActivityModule_ProvidesMenuHelperFactory create(ActivityModule activityModule, Provider<Activity> provider, Provider<NetworkHelper> provider2, Provider<JSONParser> provider3, Provider<SharedPrefHelper> provider4, Provider<ApiRequestManager> provider5, Provider<LocalizationHelper> provider6, Provider<MobiRollerApplication> provider7, Provider<ScreenHelper> provider8) {
        ActivityModule_ProvidesMenuHelperFactory activityModule_ProvidesMenuHelperFactory = new ActivityModule_ProvidesMenuHelperFactory(activityModule, provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
        return activityModule_ProvidesMenuHelperFactory;
    }

    public static MenuHelper proxyProvidesMenuHelper(ActivityModule activityModule, Activity activity, NetworkHelper networkHelper, JSONParser jSONParser, SharedPrefHelper sharedPrefHelper, ApiRequestManager apiRequestManager, LocalizationHelper localizationHelper, MobiRollerApplication mobiRollerApplication, ScreenHelper screenHelper) {
        return (MenuHelper) Preconditions.checkNotNull(activityModule.providesMenuHelper(activity, networkHelper, jSONParser, sharedPrefHelper, apiRequestManager, localizationHelper, mobiRollerApplication, screenHelper), "Cannot return null from a non-@Nullable @Provides method");
    }
}
