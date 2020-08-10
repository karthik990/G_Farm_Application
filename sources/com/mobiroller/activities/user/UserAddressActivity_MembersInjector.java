package com.mobiroller.activities.user;

import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserAddressActivity_MembersInjector implements MembersInjector<UserAddressActivity> {
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public UserAddressActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<ToolbarHelper> provider2) {
        this.sharedPrefHelperProvider = provider;
        this.toolbarHelperProvider = provider2;
    }

    public static MembersInjector<UserAddressActivity> create(Provider<SharedPrefHelper> provider, Provider<ToolbarHelper> provider2) {
        return new UserAddressActivity_MembersInjector(provider, provider2);
    }

    public void injectMembers(UserAddressActivity userAddressActivity) {
        injectSharedPrefHelper(userAddressActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectToolbarHelper(userAddressActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
    }

    public static void injectSharedPrefHelper(UserAddressActivity userAddressActivity, SharedPrefHelper sharedPrefHelper) {
        userAddressActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectToolbarHelper(UserAddressActivity userAddressActivity, ToolbarHelper toolbarHelper) {
        userAddressActivity.toolbarHelper = toolbarHelper;
    }
}
