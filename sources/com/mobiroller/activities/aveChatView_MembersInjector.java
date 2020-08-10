package com.mobiroller.activities;

import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class aveChatView_MembersInjector implements MembersInjector<aveChatView> {
    private final Provider<JSONParser> jParserNewProvider;
    private final Provider<LocalizationHelper> localizationHelperProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public aveChatView_MembersInjector(Provider<SharedPrefHelper> provider, Provider<ToolbarHelper> provider2, Provider<LocalizationHelper> provider3, Provider<JSONParser> provider4, Provider<NetworkHelper> provider5) {
        this.sharedPrefHelperProvider = provider;
        this.toolbarHelperProvider = provider2;
        this.localizationHelperProvider = provider3;
        this.jParserNewProvider = provider4;
        this.networkHelperProvider = provider5;
    }

    public static MembersInjector<aveChatView> create(Provider<SharedPrefHelper> provider, Provider<ToolbarHelper> provider2, Provider<LocalizationHelper> provider3, Provider<JSONParser> provider4, Provider<NetworkHelper> provider5) {
        aveChatView_MembersInjector avechatview_membersinjector = new aveChatView_MembersInjector(provider, provider2, provider3, provider4, provider5);
        return avechatview_membersinjector;
    }

    public void injectMembers(aveChatView avechatview) {
        injectSharedPrefHelper(avechatview, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectToolbarHelper(avechatview, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectLocalizationHelper(avechatview, (LocalizationHelper) this.localizationHelperProvider.get());
        injectJParserNew(avechatview, (JSONParser) this.jParserNewProvider.get());
        injectNetworkHelper(avechatview, (NetworkHelper) this.networkHelperProvider.get());
    }

    public static void injectSharedPrefHelper(aveChatView avechatview, SharedPrefHelper sharedPrefHelper) {
        avechatview.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectToolbarHelper(aveChatView avechatview, ToolbarHelper toolbarHelper) {
        avechatview.toolbarHelper = toolbarHelper;
    }

    public static void injectLocalizationHelper(aveChatView avechatview, LocalizationHelper localizationHelper) {
        avechatview.localizationHelper = localizationHelper;
    }

    public static void injectJParserNew(aveChatView avechatview, JSONParser jSONParser) {
        avechatview.jParserNew = jSONParser;
    }

    public static void injectNetworkHelper(aveChatView avechatview, NetworkHelper networkHelper) {
        avechatview.networkHelper = networkHelper;
    }
}
