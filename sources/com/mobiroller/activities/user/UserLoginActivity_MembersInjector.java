package com.mobiroller.activities.user;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserLoginActivity_MembersInjector implements MembersInjector<UserLoginActivity> {
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public UserLoginActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<MobiRollerApplication> provider3) {
        this.sharedPrefHelperProvider = provider;
        this.networkHelperProvider = provider2;
        this.appProvider = provider3;
    }

    public static MembersInjector<UserLoginActivity> create(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<MobiRollerApplication> provider3) {
        return new UserLoginActivity_MembersInjector(provider, provider2, provider3);
    }

    public void injectMembers(UserLoginActivity userLoginActivity) {
        injectSharedPrefHelper(userLoginActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectNetworkHelper(userLoginActivity, (NetworkHelper) this.networkHelperProvider.get());
        injectApp(userLoginActivity, (MobiRollerApplication) this.appProvider.get());
    }

    public static void injectSharedPrefHelper(UserLoginActivity userLoginActivity, SharedPrefHelper sharedPrefHelper) {
        userLoginActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectNetworkHelper(UserLoginActivity userLoginActivity, NetworkHelper networkHelper) {
        userLoginActivity.networkHelper = networkHelper;
    }

    public static void injectApp(UserLoginActivity userLoginActivity, MobiRollerApplication mobiRollerApplication) {
        userLoginActivity.app = mobiRollerApplication;
    }
}
