package com.mobiroller.activities.chat;

import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ChatArchivedDialogActivity_MembersInjector implements MembersInjector<ChatArchivedDialogActivity> {
    private final Provider<BannerHelper> bannerHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;

    public ChatArchivedDialogActivity_MembersInjector(Provider<BannerHelper> provider, Provider<SharedPrefHelper> provider2) {
        this.bannerHelperProvider = provider;
        this.sharedPrefHelperProvider = provider2;
    }

    public static MembersInjector<ChatArchivedDialogActivity> create(Provider<BannerHelper> provider, Provider<SharedPrefHelper> provider2) {
        return new ChatArchivedDialogActivity_MembersInjector(provider, provider2);
    }

    public void injectMembers(ChatArchivedDialogActivity chatArchivedDialogActivity) {
        injectBannerHelper(chatArchivedDialogActivity, (BannerHelper) this.bannerHelperProvider.get());
        injectSharedPrefHelper(chatArchivedDialogActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
    }

    public static void injectBannerHelper(ChatArchivedDialogActivity chatArchivedDialogActivity, BannerHelper bannerHelper) {
        chatArchivedDialogActivity.bannerHelper = bannerHelper;
    }

    public static void injectSharedPrefHelper(ChatArchivedDialogActivity chatArchivedDialogActivity, SharedPrefHelper sharedPrefHelper) {
        chatArchivedDialogActivity.sharedPrefHelper = sharedPrefHelper;
    }
}
