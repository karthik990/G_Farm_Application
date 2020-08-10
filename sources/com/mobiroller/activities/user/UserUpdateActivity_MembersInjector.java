package com.mobiroller.activities.user;

import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserUpdateActivity_MembersInjector implements MembersInjector<UserUpdateActivity> {
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public UserUpdateActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<LocalizationHelper> provider2, Provider<ToolbarHelper> provider3, Provider<NetworkHelper> provider4) {
        this.sharedPrefHelperProvider = provider;
        this.localizationHelperProvider = provider2;
        this.toolbarHelperProvider = provider3;
        this.networkHelperProvider = provider4;
    }

    public static MembersInjector<UserUpdateActivity> create(Provider<SharedPrefHelper> provider, Provider<LocalizationHelper> provider2, Provider<ToolbarHelper> provider3, Provider<NetworkHelper> provider4) {
        return new UserUpdateActivity_MembersInjector(provider, provider2, provider3, provider4);
    }

    public void injectMembers(UserUpdateActivity userUpdateActivity) {
        injectSharedPrefHelper(userUpdateActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectLocalizationHelper(userUpdateActivity, (LocalizationHelper) this.localizationHelperProvider.get());
        injectToolbarHelper(userUpdateActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectNetworkHelper(userUpdateActivity, (NetworkHelper) this.networkHelperProvider.get());
    }

    public static void injectSharedPrefHelper(UserUpdateActivity userUpdateActivity, SharedPrefHelper sharedPrefHelper) {
        userUpdateActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectLocalizationHelper(UserUpdateActivity userUpdateActivity, LocalizationHelper localizationHelper) {
        userUpdateActivity.localizationHelper = localizationHelper;
    }

    public static void injectToolbarHelper(UserUpdateActivity userUpdateActivity, ToolbarHelper toolbarHelper) {
        userUpdateActivity.toolbarHelper = toolbarHelper;
    }

    public static void injectNetworkHelper(UserUpdateActivity userUpdateActivity, NetworkHelper networkHelper) {
        userUpdateActivity.networkHelper = networkHelper;
    }
}
