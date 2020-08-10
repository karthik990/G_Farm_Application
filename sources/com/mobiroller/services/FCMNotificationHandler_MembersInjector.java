package com.mobiroller.services;

import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class FCMNotificationHandler_MembersInjector implements MembersInjector<FCMNotificationHandler> {
    private final Provider<MenuHelper> menuHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public FCMNotificationHandler_MembersInjector(Provider<MenuHelper> provider, Provider<SharedPrefHelper> provider2) {
        this.menuHelperProvider = provider;
        this.sharedPrefHelperProvider = provider2;
    }

    public static MembersInjector<FCMNotificationHandler> create(Provider<MenuHelper> provider, Provider<SharedPrefHelper> provider2) {
        return new FCMNotificationHandler_MembersInjector(provider, provider2);
    }

    public void injectMembers(FCMNotificationHandler fCMNotificationHandler) {
        injectMenuHelper(fCMNotificationHandler, (MenuHelper) this.menuHelperProvider.get());
        injectSharedPrefHelper(fCMNotificationHandler, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static void injectMenuHelper(FCMNotificationHandler fCMNotificationHandler, MenuHelper menuHelper) {
        fCMNotificationHandler.menuHelper = menuHelper;
    }

    public static void injectSharedPrefHelper(FCMNotificationHandler fCMNotificationHandler, SharedPrefHelper sharedPrefHelper) {
        fCMNotificationHandler.sharedPrefHelper = sharedPrefHelper;
    }
}
