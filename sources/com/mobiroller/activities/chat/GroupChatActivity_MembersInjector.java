package com.mobiroller.activities.chat;

import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class GroupChatActivity_MembersInjector implements MembersInjector<GroupChatActivity> {
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public GroupChatActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<ToolbarHelper> provider3) {
        this.sharedPrefHelperProvider = provider;
        this.networkHelperProvider = provider2;
        this.toolbarHelperProvider = provider3;
    }

    public static MembersInjector<GroupChatActivity> create(Provider<SharedPrefHelper> provider, Provider<NetworkHelper> provider2, Provider<ToolbarHelper> provider3) {
        return new GroupChatActivity_MembersInjector(provider, provider2, provider3);
    }

    public void injectMembers(GroupChatActivity groupChatActivity) {
        injectSharedPrefHelper(groupChatActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectNetworkHelper(groupChatActivity, (NetworkHelper) this.networkHelperProvider.get());
        injectToolbarHelper(groupChatActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
    }

    public static void injectSharedPrefHelper(GroupChatActivity groupChatActivity, SharedPrefHelper sharedPrefHelper) {
        groupChatActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectNetworkHelper(GroupChatActivity groupChatActivity, NetworkHelper networkHelper) {
        groupChatActivity.networkHelper = networkHelper;
    }

    public static void injectToolbarHelper(GroupChatActivity groupChatActivity, ToolbarHelper toolbarHelper) {
        groupChatActivity.toolbarHelper = toolbarHelper;
    }
}
