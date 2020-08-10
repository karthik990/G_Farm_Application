package com.mobiroller.activities.youtubeadvanced;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ChannelDetailActivity_MembersInjector implements MembersInjector<ChannelDetailActivity> {
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<JSONParser> jsonParserProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public ChannelDetailActivity_MembersInjector(Provider<SharedPrefHelper> provider, Provider<MobiRollerApplication> provider2, Provider<NetworkHelper> provider3, Provider<JSONParser> provider4, Provider<ScreenHelper> provider5, Provider<ToolbarHelper> provider6) {
        this.sharedPrefHelperProvider = provider;
        this.appProvider = provider2;
        this.networkHelperProvider = provider3;
        this.jsonParserProvider = provider4;
        this.screenHelperProvider = provider5;
        this.toolbarHelperProvider = provider6;
    }

    public static MembersInjector<ChannelDetailActivity> create(Provider<SharedPrefHelper> provider, Provider<MobiRollerApplication> provider2, Provider<NetworkHelper> provider3, Provider<JSONParser> provider4, Provider<ScreenHelper> provider5, Provider<ToolbarHelper> provider6) {
        ChannelDetailActivity_MembersInjector channelDetailActivity_MembersInjector = new ChannelDetailActivity_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6);
        return channelDetailActivity_MembersInjector;
    }

    public void injectMembers(ChannelDetailActivity channelDetailActivity) {
        injectSharedPrefHelper(channelDetailActivity, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectApp(channelDetailActivity, (MobiRollerApplication) this.appProvider.get());
        injectNetworkHelper(channelDetailActivity, (NetworkHelper) this.networkHelperProvider.get());
        injectJsonParser(channelDetailActivity, (JSONParser) this.jsonParserProvider.get());
        injectScreenHelper(channelDetailActivity, (ScreenHelper) this.screenHelperProvider.get());
        injectToolbarHelper(channelDetailActivity, (ToolbarHelper) this.toolbarHelperProvider.get());
    }

    public static void injectSharedPrefHelper(ChannelDetailActivity channelDetailActivity, SharedPrefHelper sharedPrefHelper) {
        channelDetailActivity.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectApp(ChannelDetailActivity channelDetailActivity, MobiRollerApplication mobiRollerApplication) {
        channelDetailActivity.app = mobiRollerApplication;
    }

    public static void injectNetworkHelper(ChannelDetailActivity channelDetailActivity, NetworkHelper networkHelper) {
        channelDetailActivity.networkHelper = networkHelper;
    }

    public static void injectJsonParser(ChannelDetailActivity channelDetailActivity, JSONParser jSONParser) {
        channelDetailActivity.jsonParser = jSONParser;
    }

    public static void injectScreenHelper(ChannelDetailActivity channelDetailActivity, ScreenHelper screenHelper) {
        channelDetailActivity.screenHelper = screenHelper;
    }

    public static void injectToolbarHelper(ChannelDetailActivity channelDetailActivity, ToolbarHelper toolbarHelper) {
        channelDetailActivity.toolbarHelper = toolbarHelper;
    }
}
