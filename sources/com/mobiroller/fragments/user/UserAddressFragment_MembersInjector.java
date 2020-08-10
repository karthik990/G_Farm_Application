package com.mobiroller.fragments.user;

import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserAddressFragment_MembersInjector implements MembersInjector<UserAddressFragment> {
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public UserAddressFragment_MembersInjector(Provider<SharedPrefHelper> provider) {
        this.sharedPrefHelperProvider = provider;
    }

    public static MembersInjector<UserAddressFragment> create(Provider<SharedPrefHelper> provider) {
        return new UserAddressFragment_MembersInjector(provider);
    }

    public void injectMembers(UserAddressFragment userAddressFragment) {
        injectSharedPrefHelper(userAddressFragment, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static void injectSharedPrefHelper(UserAddressFragment userAddressFragment, SharedPrefHelper sharedPrefHelper) {
        userAddressFragment.sharedPrefHelper = sharedPrefHelper;
    }
}
