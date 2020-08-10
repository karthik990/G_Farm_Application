package com.mobiroller.helpers;

import android.app.Activity;
import com.mobiroller.MobiRollerApplication;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class MenuHelper_Factory implements Factory<MenuHelper> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<Activity> contextProvider;
    private final Provider<JSONParser> jParserNewProvider;
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public MenuHelper_Factory(Provider<Activity> provider, Provider<NetworkHelper> provider2, Provider<JSONParser> provider3, Provider<SharedPrefHelper> provider4, Provider<ApiRequestManager> provider5, Provider<LocalizationHelper> provider6, Provider<MobiRollerApplication> provider7, Provider<ScreenHelper> provider8) {
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
        MenuHelper menuHelper = new MenuHelper((Activity) this.contextProvider.get(), (NetworkHelper) this.networkHelperProvider.get(), (JSONParser) this.jParserNewProvider.get(), (SharedPrefHelper) this.sharedPrefHelperProvider.get(), (ApiRequestManager) this.apiRequestManagerProvider.get(), (LocalizationHelper) this.localizationHelperProvider.get(), (MobiRollerApplication) this.appProvider.get(), (ScreenHelper) this.screenHelperProvider.get());
        return menuHelper;
    }

    public static MenuHelper_Factory create(Provider<Activity> provider, Provider<NetworkHelper> provider2, Provider<JSONParser> provider3, Provider<SharedPrefHelper> provider4, Provider<ApiRequestManager> provider5, Provider<LocalizationHelper> provider6, Provider<MobiRollerApplication> provider7, Provider<ScreenHelper> provider8) {
        MenuHelper_Factory menuHelper_Factory = new MenuHelper_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
        return menuHelper_Factory;
    }

    public static MenuHelper newMenuHelper(Activity activity, NetworkHelper networkHelper, JSONParser jSONParser, SharedPrefHelper sharedPrefHelper, ApiRequestManager apiRequestManager, LocalizationHelper localizationHelper, MobiRollerApplication mobiRollerApplication, ScreenHelper screenHelper) {
        MenuHelper menuHelper = new MenuHelper(activity, networkHelper, jSONParser, sharedPrefHelper, apiRequestManager, localizationHelper, mobiRollerApplication, screenHelper);
        return menuHelper;
    }
}
