package com.mobiroller.activities;

import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ConnectionRequired_MembersInjector implements MembersInjector<ConnectionRequired> {
    private final Provider<JSONParser> jsonParserProvider;
    private final Provider<NetworkHelper> networkHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;
    private final Provider<SharedPrefHelper> sharedPrefHelperProvider;
    private final Provider<ToolbarHelper> toolbarHelperProvider;

    public ConnectionRequired_MembersInjector(Provider<NetworkHelper> provider, Provider<JSONParser> provider2, Provider<ToolbarHelper> provider3, Provider<SharedPrefHelper> provider4, Provider<ScreenHelper> provider5) {
        this.networkHelperProvider = provider;
        this.jsonParserProvider = provider2;
        this.toolbarHelperProvider = provider3;
        this.sharedPrefHelperProvider = provider4;
        this.screenHelperProvider = provider5;
    }

    public static MembersInjector<ConnectionRequired> create(Provider<NetworkHelper> provider, Provider<JSONParser> provider2, Provider<ToolbarHelper> provider3, Provider<SharedPrefHelper> provider4, Provider<ScreenHelper> provider5) {
        ConnectionRequired_MembersInjector connectionRequired_MembersInjector = new ConnectionRequired_MembersInjector(provider, provider2, provider3, provider4, provider5);
        return connectionRequired_MembersInjector;
    }

    public void injectMembers(ConnectionRequired connectionRequired) {
        injectNetworkHelper(connectionRequired, (NetworkHelper) this.networkHelperProvider.get());
        injectJsonParser(connectionRequired, (JSONParser) this.jsonParserProvider.get());
        injectToolbarHelper(connectionRequired, (ToolbarHelper) this.toolbarHelperProvider.get());
        injectSharedPrefHelper(connectionRequired, (SharedPrefHelper) this.sharedPrefHelperProvider.get());
        injectScreenHelper(connectionRequired, (ScreenHelper) this.screenHelperProvider.get());
    }

    public static void injectNetworkHelper(ConnectionRequired connectionRequired, NetworkHelper networkHelper) {
        connectionRequired.networkHelper = networkHelper;
    }

    public static void injectJsonParser(ConnectionRequired connectionRequired, JSONParser jSONParser) {
        connectionRequired.jsonParser = jSONParser;
    }

    public static void injectToolbarHelper(ConnectionRequired connectionRequired, ToolbarHelper toolbarHelper) {
        connectionRequired.toolbarHelper = toolbarHelper;
    }

    public static void injectSharedPrefHelper(ConnectionRequired connectionRequired, SharedPrefHelper sharedPrefHelper) {
        connectionRequired.sharedPrefHelper = sharedPrefHelper;
    }

    public static void injectScreenHelper(ConnectionRequired connectionRequired, ScreenHelper screenHelper) {
        connectionRequired.screenHelper = screenHelper;
    }
}
