package com.mobiroller.fragments;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserLoginFragment_MembersInjector implements MembersInjector<UserLoginFragment> {
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public UserLoginFragment_MembersInjector(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<MobiRollerApplication> provider3) {
        this.sharedPrefHelperProvider = provider;
        this.networkHelperProvider = provider2;
        this.appProvider = provider3;
    }

    public static MembersInjector<UserLoginFragment> create(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<MobiRollerApplication> provider3) {
        return new UserLoginFragment_MembersInjector(provider, provider2, provider3);
    }

    public void injectMembers(UserLoginFragment userLoginFragment) {
        injectSharedPrefHelper(userLoginFragment, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectNetworkHelper(userLoginFragment, (NetworkHelper) this.networkHelperProvider.get());
        injectApp(userLoginFragment, (MobiRollerApplication) this.appProvider.get());
    }

    public static void injectSharedPrefHelper(UserLoginFragment userLoginFragment, SharedPrefHelper sharedPrefHelper) {
        userLoginFragment.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectNetworkHelper(UserLoginFragment userLoginFragment, NetworkHelper networkHelper) {
        userLoginFragment.networkHelper = networkHelper;
    }

    public static void injectApp(UserLoginFragment userLoginFragment, MobiRollerApplication mobiRollerApplication) {
        userLoginFragment.app = mobiRollerApplication;
    }
}
