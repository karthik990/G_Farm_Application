package com.mobiroller.fragments;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ComponentHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.util.ImageManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class aveShareViewFragment_MembersInjector implements MembersInjector<aveShareViewFragment> {
    private final Provider<MobiRollerApplication> appAndApplicationProvider;
    private final Provider<BannerHelper> bannerHelperProvider;
    private final Provider<ComponentHelper> componentHelperProvider;
    private final Provider<ImageManager> imageManagerProvider;
    private final Provider<JSONParser> jsonParserProvider;
    private final Provider<LayoutHelper> layoutHelperProvider;
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<SharedPrefHelper> mSharedPrfAndSharedPrefHelperProvider;
    private final Provider<MenuHelper> menuHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;

    public aveShareViewFragment_MembersInjector(Provider<BannerHelper> provider, Provider<LayoutHelper> provider2, Provider<MobiRollerApplication> provider3, Provider<ScreenHelper> provider4, Provider<SharedPrefHelper> provider5, Provider<MenuHelper> provider6, Provider<ImageManager> provider7, Provider<JSONParser> provider8, Provider<ComponentHelper> provider9, Provider<LocalizationHelper> provider10) {
        this.bannerHelperProvider = provider;
        this.layoutHelperProvider = provider2;
        this.appAndApplicationProvider = provider3;
        this.screenHelperProvider = provider4;
        this.mSharedPrfAndSharedPrefHelperProvider = provider5;
        this.menuHelperProvider = provider6;
        this.imageManagerProvider = provider7;
        this.jsonParserProvider = provider8;
        this.componentHelperProvider = provider9;
        this.localizationHelperProvider = provider10;
    }

    public static MembersInjector<aveShareViewFragment> create(Provider<BannerHelper> provider, Provider<LayoutHelper> provider2, Provider<MobiRollerApplication> provider3, Provider<ScreenHelper> provider4, Provider<SharedPrefHelper> provider5, Provider<MenuHelper> provider6, Provider<ImageManager> provider7, Provider<JSONParser> provider8, Provider<ComponentHelper> provider9, Provider<LocalizationHelper> provider10) {
        aveShareViewFragment_MembersInjector aveshareviewfragment_membersinjector = new aveShareViewFragment_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
        return aveshareviewfragment_membersinjector;
    }

    public void injectMembers(aveShareViewFragment aveshareviewfragment) {
        BaseModuleFragment_MembersInjector.injectBannerHelper(aveshareviewfragment, (BannerHelper) this.bannerHelperProvider.get());
        BaseModuleFragment_MembersInjector.injectLayoutHelper(aveshareviewfragment, (LayoutHelper) this.layoutHelperProvider.get());
        BaseModuleFragment_MembersInjector.injectApp(aveshareviewfragment, (MobiRollerApplication) this.appAndApplicationProvider.get());
        BaseModuleFragment_MembersInjector.injectScreenHelper(aveshareviewfragment, (ScreenHelper) this.screenHelperProvider.get());
        BaseModuleFragment_MembersInjector.injectApplication(aveshareviewfragment, (MobiRollerApplication) this.appAndApplicationProvider.get());
        BaseModuleFragment_MembersInjector.injectMSharedPrf(aveshareviewfragment, (SharedPrefHelper) this.mSharedPrfAndSharedPrefHelperProvider.get());
        BaseModuleFragment_MembersInjector.injectMenuHelper(aveshareviewfragment, (MenuHelper) this.menuHelperProvider.get());
        BaseModuleFragment_MembersInjector.injectImageManager(aveshareviewfragment, (ImageManager) this.imageManagerProvider.get());
        BaseModuleFragment_MembersInjector.injectJsonParser(aveshareviewfragment, (JSONParser) this.jsonParserProvider.get());
        BaseModuleFragment_MembersInjector.injectComponentHelper(aveshareviewfragment, (ComponentHelper) this.componentHelperProvider.get());
        injectLocalizationHelper(aveshareviewfragment, (LocalizationHelper) this.localizationHelperProvider.get());
        injectSharedPrefHelper(aveshareviewfragment, (SharedPrefHelper) this.mSharedPrfAndSharedPrefHelperProvider.get());
    }

    public static void injectLocalizationHelper(aveShareViewFragment aveshareviewfragment, LocalizationHelper localizationHelper) {
        aveshareviewfragment.localizationHelper = localizationHelper;
    }

    public static void injectSharedPrefHelper(aveShareViewFragment aveshareviewfragment, SharedPrefHelper sharedPrefHelper) {
        aveshareviewfragment.sharedPrefHelper = sharedPrefHelper;
    }
}
