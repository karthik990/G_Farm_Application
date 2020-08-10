package com.mobiroller.fragments;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ComponentHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.util.ImageManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BaseModuleFragment_MembersInjector implements MembersInjector<BaseModuleFragment> {
    private final Provider<MobiRollerApplication> appAndApplicationProvider;
    private final Provider<BannerHelper> bannerHelperProvider;
    private final Provider<ComponentHelper> componentHelperProvider;
    private final Provider<ImageManager> imageManagerProvider;
    private final Provider<JSONParser> jsonParserProvider;
    private final Provider<LayoutHelper> layoutHelperProvider;
    private final Provider<SharedPrefHelper> mSharedPrfProvider;
    private final Provider<MenuHelper> menuHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;

    public BaseModuleFragment_MembersInjector(Provider<BannerHelper> provider, Provider<LayoutHelper> provider2, Provider<MobiRollerApplication> provider3, Provider<ScreenHelper> provider4, Provider<SharedPrefHelper> provider5, Provider<MenuHelper> provider6, Provider<ImageManager> provider7, Provider<JSONParser> provider8, Provider<ComponentHelper> provider9) {
        this.bannerHelperProvider = provider;
        this.layoutHelperProvider = provider2;
        this.appAndApplicationProvider = provider3;
        this.screenHelperProvider = provider4;
        this.mSharedPrfProvider = provider5;
        this.menuHelperProvider = provider6;
        this.imageManagerProvider = provider7;
        this.jsonParserProvider = provider8;
        this.componentHelperProvider = provider9;
    }

    public static MembersInjector<BaseModuleFragment> create(Provider<BannerHelper> provider, Provider<LayoutHelper> provider2, Provider<MobiRollerApplication> provider3, Provider<ScreenHelper> provider4, Provider<SharedPrefHelper> provider5, Provider<MenuHelper> provider6, Provider<ImageManager> provider7, Provider<JSONParser> provider8, Provider<ComponentHelper> provider9) {
        BaseModuleFragment_MembersInjector baseModuleFragment_MembersInjector = new BaseModuleFragment_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
        return baseModuleFragment_MembersInjector;
    }

    public void injectMembers(BaseModuleFragment baseModuleFragment) {
        injectBannerHelper(baseModuleFragment, (BannerHelper) this.bannerHelperProvider.get());
        injectLayoutHelper(baseModuleFragment, (LayoutHelper) this.layoutHelperProvider.get());
        injectApp(baseModuleFragment, (MobiRollerApplication) this.appAndApplicationProvider.get());
        injectScreenHelper(baseModuleFragment, (ScreenHelper) this.screenHelperProvider.get());
        injectApplication(baseModuleFragment, (MobiRollerApplication) this.appAndApplicationProvider.get());
        injectMSharedPrf(baseModuleFragment, (SharedPrefHelper) this.mSharedPrfProvider.get());
        injectMenuHelper(baseModuleFragment, (MenuHelper) this.menuHelperProvider.get());
        injectImageManager(baseModuleFragment, (ImageManager) this.imageManagerProvider.get());
        injectJsonParser(baseModuleFragment, (JSONParser) this.jsonParserProvider.get());
        injectComponentHelper(baseModuleFragment, (ComponentHelper) this.componentHelperProvider.get());
    }

    public static void injectBannerHelper(BaseModuleFragment baseModuleFragment, BannerHelper bannerHelper) {
        baseModuleFragment.bannerHelper = bannerHelper;
    }

    public static void injectLayoutHelper(BaseModuleFragment baseModuleFragment, LayoutHelper layoutHelper) {
        baseModuleFragment.layoutHelper = layoutHelper;
    }

    public static void injectApp(BaseModuleFragment baseModuleFragment, MobiRollerApplication mobiRollerApplication) {
        baseModuleFragment.app = mobiRollerApplication;
    }

    public static void injectScreenHelper(BaseModuleFragment baseModuleFragment, ScreenHelper screenHelper) {
        baseModuleFragment.screenHelper = screenHelper;
    }

    public static void injectApplication(BaseModuleFragment baseModuleFragment, MobiRollerApplication mobiRollerApplication) {
        baseModuleFragment.application = mobiRollerApplication;
    }

    public static void injectMSharedPrf(BaseModuleFragment baseModuleFragment, SharedPrefHelper sharedPrefHelper) {
        baseModuleFragment.mSharedPrf = sharedPrefHelper;
    }

    public static void injectMenuHelper(BaseModuleFragment baseModuleFragment, MenuHelper menuHelper) {
        baseModuleFragment.menuHelper = menuHelper;
    }

    public static void injectImageManager(BaseModuleFragment baseModuleFragment, ImageManager imageManager) {
        baseModuleFragment.imageManager = imageManager;
    }

    public static void injectJsonParser(BaseModuleFragment baseModuleFragment, JSONParser jSONParser) {
        baseModuleFragment.jsonParser = jSONParser;
    }

    public static void injectComponentHelper(BaseModuleFragment baseModuleFragment, ComponentHelper componentHelper) {
        baseModuleFragment.componentHelper = componentHelper;
    }
}
