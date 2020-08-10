package com.mobiroller.activities.chat;

import com.mobiroller.helpers.BannerHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ChatUserSearchResultActivity_MembersInjector implements MembersInjector<ChatUserSearchResultActivity> {
    private final Provider<BannerHelper> bannerHelperProvider;

    public ChatUserSearchResultActivity_MembersInjector(Provider<BannerHelper> provider) {
        this.bannerHelperProvider = provider;
    }

    public static MembersInjector<ChatUserSearchResultActivity> create(Provider<BannerHelper> provider) {
        return new ChatUserSearchResultActivity_MembersInjector(provider);
    }

    public void injectMembers(ChatUserSearchResultActivity chatUserSearchResultActivity) {
        injectBannerHelper(chatUserSearchResultActivity, (BannerHelper) this.bannerHelperProvider.get());
    }

    public static void injectBannerHelper(ChatUserSearchResultActivity chatUserSearchResultActivity, BannerHelper bannerHelper) {
        chatUserSearchResultActivity.bannerHelper = bannerHelper;
    }
}
