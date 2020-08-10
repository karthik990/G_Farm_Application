package com.mobiroller.activities.chat;

import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ChatAdminActivity_MembersInjector implements MembersInjector<ChatAdminActivity> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public ChatAdminActivity_MembersInjector(Provider<ApiRequestManager> provider, Provider<SharedPrefHelper> provider2) {
        this.apiRequestManagerProvider = provider;
        this.sharedPrefHelperProvider = provider2;
    }

    public static MembersInjector<ChatAdminActivity> create(Provider<ApiRequestManager> provider, Provider<SharedPrefHelper> provider2) {
        return new ChatAdminActivity_MembersInjector(provider, provider2);
    }

    public void injectMembers(ChatAdminActivity chatAdminActivity) {
        injectApiRequestManager(chatAdminActivity, (ApiRequestManager) this.apiRequestManagerProvider.get());
        injectSharedPrefHelper(chatAdminActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static void injectApiRequestManager(ChatAdminActivity chatAdminActivity, ApiRequestManager apiRequestManager) {
        chatAdminActivity.apiRequestManager = apiRequestManager;
    }

    public static void injectSharedPrefHelper(ChatAdminActivity chatAdminActivity, SharedPrefHelper sharedPrefHelper) {
        chatAdminActivity.sharedPrefHelper = sharedPrefHelper;
    }
}
