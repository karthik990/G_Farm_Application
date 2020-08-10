package com.mobiroller.activities.user;

import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserResetPasswordActivity_MembersInjector implements MembersInjector<UserResetPasswordActivity> {
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public UserResetPasswordActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2) {
        this.sharedPrefHelperProvider = provider;
        this.networkHelperProvider = provider2;
    }

    public static MembersInjector<UserResetPasswordActivity> create(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2) {
        return new UserResetPasswordActivity_MembersInjector(provider, provider2);
    }

    public void injectMembers(UserResetPasswordActivity userResetPasswordActivity) {
        injectSharedPrefHelper(userResetPasswordActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectNetworkHelper(userResetPasswordActivity, (NetworkHelper) this.networkHelperProvider.get());
    }

    public static void injectSharedPrefHelper(UserResetPasswordActivity userResetPasswordActivity, SharedPrefHelper sharedPrefHelper) {
        userResetPasswordActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectNetworkHelper(UserResetPasswordActivity userResetPasswordActivity, NetworkHelper networkHelper) {
        userResetPasswordActivity.networkHelper = networkHelper;
    }
}
