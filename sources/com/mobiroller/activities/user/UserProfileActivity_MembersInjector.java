package com.mobiroller.activities.user;

import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserProfileActivity_MembersInjector implements MembersInjector<UserProfileActivity> {
    private final Provider<BannerHelper> bannerHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public UserProfileActivity_MembersInjector(Provider<BannerHelper> provider, Provider<ToolbarHelper> provider2, Provider<SharedPrefHelper> provider3) {
        this.bannerHelperProvider = provider;
        this.toolbarHelperProvider = provider2;
        this.sharedPrefHelperProvider = provider3;
    }

    public static MembersInjector<UserProfileActivity> create(Provider<BannerHelper> provider, Provider<ToolbarHelper> provider2, Provider<SharedPrefHelper> provider3) {
        return new UserProfileActivity_MembersInjector(provider, provider2, provider3);
    }

    public void injectMembers(UserProfileActivity userProfileActivity) {
        injectBannerHelper(userProfileActivity, (BannerHelper) this.bannerHelperProvider.get());
        injectToolbarHelper(userProfileActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectSharedPrefHelper(userProfileActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static void injectBannerHelper(UserProfileActivity userProfileActivity, BannerHelper bannerHelper) {
        userProfileActivity.bannerHelper = bannerHelper;
    }

    public static void injectToolbarHelper(UserProfileActivity userProfileActivity, ToolbarHelper toolbarHelper) {
        userProfileActivity.toolbarHelper = toolbarHelper;
    }

    public static void injectSharedPrefHelper(UserProfileActivity userProfileActivity, SharedPrefHelper sharedPrefHelper) {
        userProfileActivity.sharedPrefHelper = sharedPrefHelper;
    }
}
