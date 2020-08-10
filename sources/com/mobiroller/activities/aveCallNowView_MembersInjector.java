package com.mobiroller.activities;

import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class aveCallNowView_MembersInjector implements MembersInjector<aveCallNowView> {
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public aveCallNowView_MembersInjector(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        this.toolbarHelperProvider = provider;
        this.localizationHelperProvider = provider2;
    }

    public static MembersInjector<aveCallNowView> create(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        return new aveCallNowView_MembersInjector(provider, provider2);
    }

    public void injectMembers(aveCallNowView avecallnowview) {
        injectToolbarHelper(avecallnowview, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(avecallnowview, (LocalizationHelper) this.localizationHelperProvider.get());
    }

    public static void injectToolbarHelper(aveCallNowView avecallnowview, ToolbarHelper toolbarHelper) {
        avecallnowview.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(aveCallNowView avecallnowview, LocalizationHelper localizationHelper) {
        avecallnowview.localizationHelper = localizationHelper;
    }
}
