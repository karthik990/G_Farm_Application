package com.mobiroller.activities.menu;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.util.ImageManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ListMenu_MembersInjector implements MembersInjector<ListMenu> {
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<BannerHelper> bannerHelperProvider;
    private final Provider<ImageManager> imageManagerProvider;
    private final Provider<JSONParser> jsonParserProvider;
    private final Provider<LayoutHelper> layoutHelperProvider;
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<MenuHelper> menuHelperProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public ListMenu_MembersInjector(Provider<SharedPrefHelper> provider, Provider<MobiRollerApplication> provider2, Provider<NetworkHelper> provider3, Provider<JSONParser> provider4, Provider<BannerHelper> provider5, Provider<LayoutHelper> provider6, Provider<ScreenHelper> provider7, Provider<ImageManager> provider8, Provider<ToolbarHelper> provider9, Provider<LocalizationHelper> provider10, Provider<MenuHelper> provider11) {
        this.sharedPrefHelperProvider = provider;
        this.appProvider = provider2;
        this.networkHelperProvider = provider3;
        this.jsonParserProvider = provider4;
        this.bannerHelperProvider = provider5;
        this.layoutHelperProvider = provider6;
        this.screenHelperProvider = provider7;
        this.imageManagerProvider = provider8;
        this.toolbarHelperProvider = provider9;
        this.localizationHelperProvider = provider10;
        this.menuHelperProvider = provider11;
    }

    public static MembersInjector<ListMenu> create(Provider<SharedPrefHelper> provider, Provider<MobiRollerApplication> provider2, Provider<NetworkHelper> provider3, Provider<JSONParser> provider4, Provider<BannerHelper> provider5, Provider<LayoutHelper> provider6, Provider<ScreenHelper> provider7, Provider<ImageManager> provider8, Provider<ToolbarHelper> provider9, Provider<LocalizationHelper> provider10, Provider<MenuHelper> provider11) {
        ListMenu_MembersInjector listMenu_MembersInjector = new ListMenu_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
        return listMenu_MembersInjector;
    }

    public void injectMembers(ListMenu listMenu) {
        injectSharedPrefHelper(listMenu, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectApp(listMenu, (MobiRollerApplication) this.appProvider.get());
        injectNetworkHelper(listMenu, (NetworkHelper) this.networkHelperProvider.get());
        injectJsonParser(listMenu, (JSONParser) this.jsonParserProvider.get());
        injectBannerHelper(listMenu, (BannerHelper) this.bannerHelperProvider.get());
        injectLayoutHelper(listMenu, (LayoutHelper) this.layoutHelperProvider.get());
        injectScreenHelper(listMenu, (ScreenHelper) this.screenHelperProvider.get());
        injectImageManager(listMenu, (ImageManager) this.imageManagerProvider.get());
        injectToolbarHelper(listMenu, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(listMenu, (LocalizationHelper) this.localizationHelperProvider.get());
        injectMenuHelper(listMenu, (MenuHelper) this.menuHelperProvider.get());
    }

    public static void injectSharedPrefHelper(ListMenu listMenu, SharedPrefHelper sharedPrefHelper) {
        listMenu.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectApp(ListMenu listMenu, MobiRollerApplication mobiRollerApplication) {
        listMenu.app = mobiRollerApplication;
    }

    public static void injectNetworkHelper(ListMenu listMenu, NetworkHelper networkHelper) {
        listMenu.networkHelper = networkHelper;
    }

    public static void injectJsonParser(ListMenu listMenu, JSONParser jSONParser) {
        listMenu.jsonParser = jSONParser;
    }

    public static void injectBannerHelper(ListMenu listMenu, BannerHelper bannerHelper) {
        listMenu.bannerHelper = bannerHelper;
    }

    public static void injectLayoutHelper(ListMenu listMenu, LayoutHelper layoutHelper) {
        listMenu.layoutHelper = layoutHelper;
    }

    public static void injectScreenHelper(ListMenu listMenu, ScreenHelper screenHelper) {
        listMenu.screenHelper = screenHelper;
    }

    public static void injectImageManager(ListMenu listMenu, ImageManager imageManager) {
        listMenu.imageManager = imageManager;
    }

    public static void injectToolbarHelper(ListMenu listMenu, ToolbarHelper toolbarHelper) {
        listMenu.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(ListMenu listMenu, LocalizationHelper localizationHelper) {
        listMenu.localizationHelper = localizationHelper;
    }

    public static void injectMenuHelper(ListMenu listMenu, MenuHelper menuHelper) {
        listMenu.menuHelper = menuHelper;
    }
}
