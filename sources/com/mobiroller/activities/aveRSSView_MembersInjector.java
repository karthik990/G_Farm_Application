package com.mobiroller.activities;

import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class aveRSSView_MembersInjector implements MembersInjector<aveRSSView> {
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public aveRSSView_MembersInjector(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        this.toolbarHelperProvider = provider;
        this.localizationHelperProvider = provider2;
    }

    public static MembersInjector<aveRSSView> create(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        return new aveRSSView_MembersInjector(provider, provider2);
    }

    public void injectMembers(aveRSSView averssview) {
        injectToolbarHelper(averssview, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(averssview, (LocalizationHelper) this.localizationHelperProvider.get());
    }

    public static void injectToolbarHelper(aveRSSView averssview, ToolbarHelper toolbarHelper) {
        averssview.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(aveRSSView averssview, LocalizationHelper localizationHelper) {
        averssview.localizationHelper = localizationHelper;
    }
}
