package com.mobiroller.fragments.chat;

import com.mobiroller.helpers.BannerHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ChatMessageListFragment_MembersInjector implements MembersInjector<ChatMessageListFragment> {
    private final Provider<BannerHelper> bannerHelperProvider;

    public ChatMessageListFragment_MembersInjector(Provider<BannerHelper> provider) {
        this.bannerHelperProvider = provider;
    }

    public static MembersInjector<ChatMessageListFragment> create(Provider<BannerHelper> provider) {
        return new ChatMessageListFragment_MembersInjector(provider);
    }

    public void injectMembers(ChatMessageListFragment chatMessageListFragment) {
        injectBannerHelper(chatMessageListFragment, (BannerHelper) this.bannerHelperProvider.get());
    }

    public static void injectBannerHelper(ChatMessageListFragment chatMessageListFragment, BannerHelper bannerHelper) {
        chatMessageListFragment.bannerHelper = bannerHelper;
    }
}
