package com.mobiroller.activities.menu;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class SlidingPanelActivity_MembersInjector implements MembersInjector<SlidingPanelActivity> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<JSONParser> jsonParserProvider;
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<MenuHelper> menuHelperProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public SlidingPanelActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<MobiRollerApplication> provider2, Provider<NetworkHelper> provider3, Provider<JSONParser> provider4, Provider<ScreenHelper> provider5, Provider<ToolbarHelper> provider6, Provider<LocalizationHelper> provider7, Provider<MenuHelper> provider8, Provider<ApiRequestManager> provider9) {
        this.sharedPrefHelperProvider = provider;
        this.appProvider = provider2;
        this.networkHelperProvider = provider3;
        this.jsonParserProvider = provider4;
        this.screenHelperProvider = provider5;
        this.toolbarHelperProvider = provider6;
        this.localizationHelperProvider = provider7;
        this.menuHelperProvider = provider8;
        this.apiRequestManagerProvider = provider9;
    }

    public static MembersInjector<SlidingPanelActivity> create(Provider<SharedPrefHelper> provider, Provider<MobiRollerApplication> provider2, Provider<NetworkHelper> provider3, Provider<JSONParser> provider4, Provider<ScreenHelper> provider5, Provider<ToolbarHelper> provider6, Provider<LocalizationHelper> provider7, Provider<MenuHelper> provider8, Provider<ApiRequestManager> provider9) {
        SlidingPanelActivity_MembersInjector slidingPanelActivity_MembersInjector = new SlidingPanelActivity_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
        return slidingPanelActivity_MembersInjector;
    }

    public void injectMembers(SlidingPanelActivity slidingPanelActivity) {
        injectSharedPrefHelper(slidingPanelActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectApp(slidingPanelActivity, (MobiRollerApplication) this.appProvider.get());
        injectNetworkHelper(slidingPanelActivity, (NetworkHelper) this.networkHelperProvider.get());
        injectJsonParser(slidingPanelActivity, (JSONParser) this.jsonParserProvider.get());
        injectScreenHelper(slidingPanelActivity, (ScreenHelper) this.screenHelperProvider.get());
        injectToolbarHelper(slidingPanelActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(slidingPanelActivity, (LocalizationHelper) this.localizationHelperProvider.get());
        injectMenuHelper(slidingPanelActivity, (MenuHelper) this.menuHelperProvider.get());
        injectApiRequestManager(slidingPanelActivity, (ApiRequestManager) this.apiRequestManagerProvider.get());
    }

    public static void injectSharedPrefHelper(SlidingPanelActivity slidingPanelActivity, SharedPrefHelper sharedPrefHelper) {
        slidingPanelActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectApp(SlidingPanelActivity slidingPanelActivity, MobiRollerApplication mobiRollerApplication) {
        slidingPanelActivity.app = mobiRollerApplication;
    }

    public static void injectNetworkHelper(SlidingPanelActivity slidingPanelActivity, NetworkHelper networkHelper) {
        slidingPanelActivity.networkHelper = networkHelper;
    }

    public static void injectJsonParser(SlidingPanelActivity slidingPanelActivity, JSONParser jSONParser) {
        slidingPanelActivity.jsonParser = jSONParser;
    }

    public static void injectScreenHelper(SlidingPanelActivity slidingPanelActivity, ScreenHelper screenHelper) {
        slidingPanelActivity.screenHelper = screenHelper;
    }

    public static void injectToolbarHelper(SlidingPanelActivity slidingPanelActivity, ToolbarHelper toolbarHelper) {
        slidingPanelActivity.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(SlidingPanelActivity slidingPanelActivity, LocalizationHelper localizationHelper) {
        slidingPanelActivity.localizationHelper = localizationHelper;
    }

    public static void injectMenuHelper(SlidingPanelActivity slidingPanelActivity, MenuHelper menuHelper) {
        slidingPanelActivity.menuHelper = menuHelper;
    }

    public static void injectApiRequestManager(SlidingPanelActivity slidingPanelActivity, ApiRequestManager apiRequestManager) {
        slidingPanelActivity.apiRequestManager = apiRequestManager;
    }
}
