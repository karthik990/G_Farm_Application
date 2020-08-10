package com.mobiroller.activities.menu;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.util.ImageManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<BannerHelper> bannerHelperProvider;
    private final Provider<ImageManager> imageManagerProvider;
    private final Provider<JSONParser> jsonParserProvider;
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<MenuHelper> menuHelperProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public MainActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<MobiRollerApplication> provider2, Provider<NetworkHelper> provider3, Provider<JSONParser> provider4, Provider<BannerHelper> provider5, Provider<ScreenHelper> provider6, Provider<ImageManager> provider7, Provider<ToolbarHelper> provider8, Provider<LocalizationHelper> provider9, Provider<MenuHelper> provider10, Provider<ApiRequestManager> provider11) {
        this.sharedPrefHelperProvider = provider;
        this.appProvider = provider2;
        this.networkHelperProvider = provider3;
        this.jsonParserProvider = provider4;
        this.bannerHelperProvider = provider5;
        this.screenHelperProvider = provider6;
        this.imageManagerProvider = provider7;
        this.toolbarHelperProvider = provider8;
        this.localizationHelperProvider = provider9;
        this.menuHelperProvider = provider10;
        this.apiRequestManagerProvider = provider11;
    }

    public static MembersInjector<MainActivity> create(Provider<SharedPrefHelper> provider, Provider<MobiRollerApplication> provider2, Provider<NetworkHelper> provider3, Provider<JSONParser> provider4, Provider<BannerHelper> provider5, Provider<ScreenHelper> provider6, Provider<ImageManager> provider7, Provider<ToolbarHelper> provider8, Provider<LocalizationHelper> provider9, Provider<MenuHelper> provider10, Provider<ApiRequestManager> provider11) {
        MainActivity_MembersInjector mainActivity_MembersInjector = new MainActivity_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
        return mainActivity_MembersInjector;
    }

    public void injectMembers(MainActivity mainActivity) {
        injectSharedPrefHelper(mainActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectApp(mainActivity, (MobiRollerApplication) this.appProvider.get());
        injectNetworkHelper(mainActivity, (NetworkHelper) this.networkHelperProvider.get());
        injectJsonParser(mainActivity, (JSONParser) this.jsonParserProvider.get());
        injectBannerHelper(mainActivity, (BannerHelper) this.bannerHelperProvider.get());
        injectScreenHelper(mainActivity, (ScreenHelper) this.screenHelperProvider.get());
        injectImageManager(mainActivity, (ImageManager) this.imageManagerProvider.get());
        injectToolbarHelper(mainActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(mainActivity, (LocalizationHelper) this.localizationHelperProvider.get());
        injectMenuHelper(mainActivity, (MenuHelper) this.menuHelperProvider.get());
        injectApiRequestManager(mainActivity, (ApiRequestManager) this.apiRequestManagerProvider.get());
    }

    public static void injectSharedPrefHelper(MainActivity mainActivity, SharedPrefHelper sharedPrefHelper) {
        mainActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectApp(MainActivity mainActivity, MobiRollerApplication mobiRollerApplication) {
        mainActivity.app = mobiRollerApplication;
    }

    public static void injectNetworkHelper(MainActivity mainActivity, NetworkHelper networkHelper) {
        mainActivity.networkHelper = networkHelper;
    }

    public static void injectJsonParser(MainActivity mainActivity, JSONParser jSONParser) {
        mainActivity.jsonParser = jSONParser;
    }

    public static void injectBannerHelper(MainActivity mainActivity, BannerHelper bannerHelper) {
        mainActivity.bannerHelper = bannerHelper;
    }

    public static void injectScreenHelper(MainActivity mainActivity, ScreenHelper screenHelper) {
        mainActivity.screenHelper = screenHelper;
    }

    public static void injectImageManager(MainActivity mainActivity, ImageManager imageManager) {
        mainActivity.imageManager = imageManager;
    }

    public static void injectToolbarHelper(MainActivity mainActivity, ToolbarHelper toolbarHelper) {
        mainActivity.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(MainActivity mainActivity, LocalizationHelper localizationHelper) {
        mainActivity.localizationHelper = localizationHelper;
    }

    public static void injectMenuHelper(MainActivity mainActivity, MenuHelper menuHelper) {
        mainActivity.menuHelper = menuHelper;
    }

    public static void injectApiRequestManager(MainActivity mainActivity, ApiRequestManager apiRequestManager) {
        mainActivity.apiRequestManager = apiRequestManager;
    }
}
