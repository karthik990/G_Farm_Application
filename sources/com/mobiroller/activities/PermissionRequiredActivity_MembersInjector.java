package com.mobiroller.activities;

import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class PermissionRequiredActivity_MembersInjector implements MembersInjector<PermissionRequiredActivity> {
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public PermissionRequiredActivity_MembersInjector(Provider<ToolbarHelper> provider, Provider<SharedPrefHelper> provider2, Provider<ScreenHelper> provider3) {
        this.toolbarHelperProvider = provider;
        this.sharedPrefHelperProvider = provider2;
        this.screenHelperProvider = provider3;
    }

    public static MembersInjector<PermissionRequiredActivity> create(Provider<ToolbarHelper> provider, Provider<SharedPrefHelper> provider2, Provider<ScreenHelper> provider3) {
        return new PermissionRequiredActivity_MembersInjector(provider, provider2, provider3);
    }

    public void injectMembers(PermissionRequiredActivity permissionRequiredActivity) {
        injectToolbarHelper(permissionRequiredActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectSharedPrefHelper(permissionRequiredActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectScreenHelper(permissionRequiredActivity, (ScreenHelper) this.screenHelperProvider.get());
    }

    public static void injectToolbarHelper(PermissionRequiredActivity permissionRequiredActivity, ToolbarHelper toolbarHelper) {
        permissionRequiredActivity.toolbarHelper = toolbarHelper;
    }

    public static void injectSharedPrefHelper(PermissionRequiredActivity permissionRequiredActivity, SharedPrefHelper sharedPrefHelper) {
        permissionRequiredActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectScreenHelper(PermissionRequiredActivity permissionRequiredActivity, ScreenHelper screenHelper) {
        permissionRequiredActivity.screenHelper = screenHelper;
    }
}
