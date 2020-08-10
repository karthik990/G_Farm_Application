package com.mobiroller.activities;

import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class aveWebView_MembersInjector implements MembersInjector<aveWebView> {
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public aveWebView_MembersInjector(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        this.toolbarHelperProvider = provider;
        this.localizationHelperProvider = provider2;
    }

    public static MembersInjector<aveWebView> create(Provider<ToolbarHelper> provider, Provider<LocalizationHelper> provider2) {
        return new aveWebView_MembersInjector(provider, provider2);
    }

    public void injectMembers(aveWebView avewebview) {
        injectToolbarHelper(avewebview, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(avewebview, (LocalizationHelper) this.localizationHelperProvider.get());
    }

    public static void injectToolbarHelper(aveWebView avewebview, ToolbarHelper toolbarHelper) {
        avewebview.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(aveWebView avewebview, LocalizationHelper localizationHelper) {
        avewebview.localizationHelper = localizationHelper;
    }
}
