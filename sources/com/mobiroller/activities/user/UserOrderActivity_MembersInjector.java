package com.mobiroller.activities.user;

import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UserOrderActivity_MembersInjector implements MembersInjector<UserOrderActivity> {
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public UserOrderActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<ToolbarHelper> provider2) {
        this.sharedPrefHelperProvider = provider;
        this.toolbarHelperProvider = provider2;
    }

    public static MembersInjector<UserOrderActivity> create(Provider<SharedPrefHelper> provider, Provider<ToolbarHelper> provider2) {
        return new UserOrderActivity_MembersInjector(provider, provider2);
    }

    public void injectMembers(UserOrderActivity userOrderActivity) {
        injectSharedPrefHelper(userOrderActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectToolbarHelper(userOrderActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
    }

    public static void injectSharedPrefHelper(UserOrderActivity userOrderActivity, SharedPrefHelper sharedPrefHelper) {
        userOrderActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectToolbarHelper(UserOrderActivity userOrderActivity, ToolbarHelper toolbarHelper) {
        userOrderActivity.toolbarHelper = toolbarHelper;
    }
}
