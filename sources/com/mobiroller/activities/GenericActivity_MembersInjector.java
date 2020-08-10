package com.mobiroller.activities;

import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class GenericActivity_MembersInjector implements MembersInjector<GenericActivity> {
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public GenericActivity_MembersInjector(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        this.toolbarHelperProvider = provider;
        this.localizationHelperProvider = provider2;
    }

    public static MembersInjector<GenericActivity> create(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        return new GenericActivity_MembersInjector(provider, provider2);
    }

    public void injectMembers(GenericActivity genericActivity) {
        injectToolbarHelper(genericActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(genericActivity, (LocalizationHelper) this.localizationHelperProvider.get());
    }

    public static void injectToolbarHelper(GenericActivity genericActivity, ToolbarHelper toolbarHelper) {
        genericActivity.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(GenericActivity genericActivity, LocalizationHelper localizationHelper) {
        genericActivity.localizationHelper = localizationHelper;
    }
}
