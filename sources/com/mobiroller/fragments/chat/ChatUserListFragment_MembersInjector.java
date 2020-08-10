package com.mobiroller.fragments.chat;

import com.mobiroller.helpers.BannerHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ChatUserListFragment_MembersInjector implements MembersInjector<ChatUserListFragment> {
    private final Provider<BannerHelper> bannerHelperProvider;

    public ChatUserListFragment_MembersInjector(Provider<BannerHelper> provider) {
        this.bannerHelperProvider = provider;
    }

    public static MembersInjector<ChatUserListFragment> create(Provider<BannerHelper> provider) {
        return new ChatUserListFragment_MembersInjector(provider);
    }

    public void injectMembers(ChatUserListFragment chatUserListFragment) {
        injectBannerHelper(chatUserListFragment, (BannerHelper) this.bannerHelperProvider.get());
    }

    public static void injectBannerHelper(ChatUserListFragment chatUserListFragment, BannerHelper bannerHelper) {
        chatUserListFragment.bannerHelper = bannerHelper;
    }
}
