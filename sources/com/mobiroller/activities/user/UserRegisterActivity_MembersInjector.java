package com.mobiroller.activities.user;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserRegisterActivity_MembersInjector implements MembersInjector<UserRegisterActivity> {
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public UserRegisterActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<MobiRollerApplication> provider3, Provider<LocalizationHelper> provider4) {
        this.sharedPrefHelperProvider = provider;
        this.networkHelperProvider = provider2;
        this.appProvider = provider3;
        this.localizationHelperProvider = provider4;
    }

    public static MembersInjector<UserRegisterActivity> create(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<MobiRollerApplication> provider3, Provider<LocalizationHelper> provider4) {
        return new UserRegisterActivity_MembersInjector(provider, provider2, provider3, provider4);
    }

    public void injectMembers(UserRegisterActivity userRegisterActivity) {
        injectSharedPrefHelper(userRegisterActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectNetworkHelper(userRegisterActivity, (NetworkHelper) this.networkHelperProvider.get());
        injectApp(userRegisterActivity, (MobiRollerApplication) this.appProvider.get());
        injectLocalizationHelper(userRegisterActivity, (LocalizationHelper) this.localizationHelperProvider.get());
    }

    public static void injectSharedPrefHelper(UserRegisterActivity userRegisterActivity, SharedPrefHelper sharedPrefHelper) {
        userRegisterActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectNetworkHelper(UserRegisterActivity userRegisterActivity, NetworkHelper networkHelper) {
        userRegisterActivity.networkHelper = networkHelper;
    }

    public static void injectApp(UserRegisterActivity userRegisterActivity, MobiRollerApplication mobiRollerApplication) {
        userRegisterActivity.app = mobiRollerApplication;
    }

    public static void injectLocalizationHelper(UserRegisterActivity userRegisterActivity, LocalizationHelper localizationHelper) {
        userRegisterActivity.localizationHelper = localizationHelper;
    }
}
