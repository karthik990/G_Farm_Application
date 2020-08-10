package com.mobiroller.activities.user;

import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserChangePasswordActivity_MembersInjector implements MembersInjector<UserChangePasswordActivity> {
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public UserChangePasswordActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<ToolbarHelper> provider3) {
        this.sharedPrefHelperProvider = provider;
        this.networkHelperProvider = provider2;
        this.toolbarHelperProvider = provider3;
    }

    public static MembersInjector<UserChangePasswordActivity> create(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<ToolbarHelper> provider3) {
        return new UserChangePasswordActivity_MembersInjector(provider, provider2, provider3);
    }

    public void injectMembers(UserChangePasswordActivity userChangePasswordActivity) {
        injectSharedPrefHelper(userChangePasswordActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectNetworkHelper(userChangePasswordActivity, (NetworkHelper) this.networkHelperProvider.get());
        injectToolbarHelper(userChangePasswordActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
    }

    public static void injectSharedPrefHelper(UserChangePasswordActivity userChangePasswordActivity, SharedPrefHelper sharedPrefHelper) {
        userChangePasswordActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectNetworkHelper(UserChangePasswordActivity userChangePasswordActivity, NetworkHelper networkHelper) {
        userChangePasswordActivity.networkHelper = networkHelper;
    }

    public static void injectToolbarHelper(UserChangePasswordActivity userChangePasswordActivity, ToolbarHelper toolbarHelper) {
        userChangePasswordActivity.toolbarHelper = toolbarHelper;
    }
}
