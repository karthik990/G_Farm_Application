package com.mobiroller.activities;

import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class aveRssContentViewPager_MembersInjector implements MembersInjector<aveRssContentViewPager> {
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public aveRssContentViewPager_MembersInjector(Provider<NetworkHelper> provider, Provider<SharedPrefHelper> provider2, Provider<ToolbarHelper> provider3) {
        this.networkHelperProvider = provider;
        this.sharedPrefHelperProvider = provider2;
        this.toolbarHelperProvider = provider3;
    }

    public static MembersInjector<aveRssContentViewPager> create(Provider<NetworkHelper> provider, Provider<SharedPrefHelper> provider2, Provider<ToolbarHelper> provider3) {
        return new aveRssContentViewPager_MembersInjector(provider, provider2, provider3);
    }

    public void injectMembers(aveRssContentViewPager aversscontentviewpager) {
        injectNetworkHelper(aversscontentviewpager, (NetworkHelper) this.networkHelperProvider.get());
        injectSharedPrefHelper(aversscontentviewpager, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectToolbarHelper(aversscontentviewpager, (ToolbarHelper) this.toolbarHelperProvider.get());
    }

    public static void injectNetworkHelper(aveRssContentViewPager aversscontentviewpager, NetworkHelper networkHelper) {
        aversscontentviewpager.networkHelper = networkHelper;
    }

    public static void injectSharedPrefHelper(aveRssContentViewPager aversscontentviewpager, SharedPrefHelper sharedPrefHelper) {
        aversscontentviewpager.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectToolbarHelper(aveRssContentViewPager aversscontentviewpager, ToolbarHelper toolbarHelper) {
        aversscontentviewpager.toolbarHelper = toolbarHelper;
    }
}
