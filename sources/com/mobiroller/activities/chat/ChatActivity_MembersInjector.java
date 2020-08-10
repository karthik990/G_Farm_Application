package com.mobiroller.activities.chat;

import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ChatActivity_MembersInjector implements MembersInjector<ChatActivity> {
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public ChatActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<ToolbarHelper> provider3) {
        this.sharedPrefHelperProvider = provider;
        this.networkHelperProvider = provider2;
        this.toolbarHelperProvider = provider3;
    }

    public static MembersInjector<ChatActivity> create(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<ToolbarHelper> provider3) {
        return new ChatActivity_MembersInjector(provider, provider2, provider3);
    }

    public void injectMembers(ChatActivity chatActivity) {
        injectSharedPrefHelper(chatActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectNetworkHelper(chatActivity, (NetworkHelper) this.networkHelperProvider.get());
        injectToolbarHelper(chatActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
    }

    public static void injectSharedPrefHelper(ChatActivity chatActivity, SharedPrefHelper sharedPrefHelper) {
        chatActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectNetworkHelper(ChatActivity chatActivity, NetworkHelper networkHelper) {
        chatActivity.networkHelper = networkHelper;
    }

    public static void injectToolbarHelper(ChatActivity chatActivity, ToolbarHelper toolbarHelper) {
        chatActivity.toolbarHelper = toolbarHelper;
    }
}
