package com.mobiroller.activities;

import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class aveYoutubeAdvancedView_MembersInjector implements MembersInjector<aveYoutubeAdvancedView> {
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public aveYoutubeAdvancedView_MembersInjector(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        this.toolbarHelperProvider = provider;
        this.localizationHelperProvider = provider2;
    }

    public static MembersInjector<aveYoutubeAdvancedView> create(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        return new aveYoutubeAdvancedView_MembersInjector(provider, provider2);
    }

    public void injectMembers(aveYoutubeAdvancedView aveyoutubeadvancedview) {
        injectToolbarHelper(aveyoutubeadvancedview, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(aveyoutubeadvancedview, (LocalizationHelper) this.localizationHelperProvider.get());
    }

    public static void injectToolbarHelper(aveYoutubeAdvancedView aveyoutubeadvancedview, ToolbarHelper toolbarHelper) {
        aveyoutubeadvancedview.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(aveYoutubeAdvancedView aveyoutubeadvancedview, LocalizationHelper localizationHelper) {
        aveyoutubeadvancedview.localizationHelper = localizationHelper;
    }
}
