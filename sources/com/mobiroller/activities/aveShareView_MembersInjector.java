package com.mobiroller.activities;

import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class aveShareView_MembersInjector implements MembersInjector<aveShareView> {
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public aveShareView_MembersInjector(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        this.toolbarHelperProvider = provider;
        this.localizationHelperProvider = provider2;
    }

    public static MembersInjector<aveShareView> create(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        return new aveShareView_MembersInjector(provider, provider2);
    }

    public void injectMembers(aveShareView aveshareview) {
        injectToolbarHelper(aveshareview, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(aveshareview, (LocalizationHelper) this.localizationHelperProvider.get());
    }

    public static void injectToolbarHelper(aveShareView aveshareview, ToolbarHelper toolbarHelper) {
        aveshareview.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(aveShareView aveshareview, LocalizationHelper localizationHelper) {
        aveshareview.localizationHelper = localizationHelper;
    }
}
