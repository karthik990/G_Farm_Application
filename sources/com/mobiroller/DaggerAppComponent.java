package com.mobiroller;

import android.content.Context;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ComponentHelper;
import com.mobiroller.helpers.FileDownloader;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.util.ImageManager;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerAppComponent implements AppComponent {
    private Provider<ApiRequestManager> providesApiRequestManagerProvider;
    private Provider<Context> providesApplicationContextProvider;
    private Provider<MobiRollerApplication> providesApplicationProvider;
    private Provider<BannerHelper> providesBannerHelperProvider;
    private Provider<ComponentHelper> providesComponentHelperProvider;
    private Provider<FileDownloader> providesFileDownloaderProvider;
    private Provider<ImageManager> providesImageManagerProvider;
    private Provider<JSONParser> providesJSONParserProvider;
    private Provider<LocalizationHelper> providesLocalizationHelperProvider;
    private Provider<NetworkHelper> providesNetworkHelperProvider;
    private Provider<RequestHelper> providesRequestHelperProvider;
    private Provider<ScreenHelper> providesScreenHelperProvider;
    private Provider<SharedPrefHelper> providesSharedPrefHelperProvider;
    private Provider<ToolbarHelper> providesToolbarHelperProvider;

    public static final class Builder {
        private AppModule appModule;

        private Builder() {
        }

        public Builder appModule(AppModule appModule2) {
            this.appModule = (AppModule) Preconditions.checkNotNull(appModule2);
            return this;
        }

        public AppComponent build() {
            Preconditions.checkBuilderRequirement(this.appModule, AppModule.class);
            return new DaggerAppComponent(this.appModule);
        }
    }

    public void inject(MobiRollerApplication mobiRollerApplication) {
    }

    private DaggerAppComponent(AppModule appModule) {
        initialize(appModule);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(AppModule appModule) {
        this.providesApplicationContextProvider = DoubleCheck.provider(AppModule_ProvidesApplicationContextFactory.create(appModule));
        this.providesSharedPrefHelperProvider = DoubleCheck.provider(AppModule_ProvidesSharedPrefHelperFactory.create(appModule, this.providesApplicationContextProvider));
        this.providesApplicationProvider = DoubleCheck.provider(AppModule_ProvidesApplicationFactory.create(appModule));
        this.providesNetworkHelperProvider = DoubleCheck.provider(AppModule_ProvidesNetworkHelperFactory.create(appModule, this.providesApplicationContextProvider));
        this.providesScreenHelperProvider = DoubleCheck.provider(AppModule_ProvidesScreenHelperFactory.create(appModule, this.providesApplicationContextProvider));
        this.providesFileDownloaderProvider = DoubleCheck.provider(AppModule_ProvidesFileDownloaderFactory.create(appModule));
        this.providesRequestHelperProvider = DoubleCheck.provider(AppModule_ProvidesRequestHelperFactory.create(appModule, this.providesApplicationContextProvider, this.providesSharedPrefHelperProvider));
        this.providesApiRequestManagerProvider = DoubleCheck.provider(AppModule_ProvidesApiRequestManagerFactory.create(appModule, this.providesSharedPrefHelperProvider, this.providesRequestHelperProvider));
        this.providesJSONParserProvider = DoubleCheck.provider(AppModule_ProvidesJSONParserFactory.create(appModule, this.providesFileDownloaderProvider, this.providesApiRequestManagerProvider, this.providesNetworkHelperProvider));
        this.providesImageManagerProvider = DoubleCheck.provider(AppModule_ProvidesImageManagerFactory.create(appModule));
        this.providesToolbarHelperProvider = DoubleCheck.provider(AppModule_ProvidesToolbarHelperFactory.create(appModule, this.providesSharedPrefHelperProvider));
        this.providesLocalizationHelperProvider = DoubleCheck.provider(AppModule_ProvidesLocalizationHelperFactory.create(appModule));
        this.providesBannerHelperProvider = DoubleCheck.provider(AppModule_ProvidesBannerHelperFactory.create(appModule, this.providesSharedPrefHelperProvider, this.providesApplicationContextProvider, this.providesNetworkHelperProvider));
        this.providesComponentHelperProvider = DoubleCheck.provider(AppModule_ProvidesComponentHelperFactory.create(appModule, this.providesSharedPrefHelperProvider, this.providesScreenHelperProvider));
    }

    public SharedPrefHelper getSharedPref() {
        return (SharedPrefHelper) this.providesSharedPrefHelperProvider.get();
    }

    public MobiRollerApplication getApplication() {
        return (MobiRollerApplication) this.providesApplicationProvider.get();
    }

    public Context getContext() {
        return (Context) this.providesApplicationContextProvider.get();
    }

    public NetworkHelper getNetworkHelper() {
        return (NetworkHelper) this.providesNetworkHelperProvider.get();
    }

    public ScreenHelper getScreenHelper() {
        return (ScreenHelper) this.providesScreenHelperProvider.get();
    }

    public FileDownloader getFileDownloader() {
        return (FileDownloader) this.providesFileDownloaderProvider.get();
    }

    public JSONParser getJsonParser() {
        return (JSONParser) this.providesJSONParserProvider.get();
    }

    public ImageManager getImageManager() {
        return (ImageManager) this.providesImageManagerProvider.get();
    }

    public ApiRequestManager getApiRequestManager() {
        return (ApiRequestManager) this.providesApiRequestManagerProvider.get();
    }

    public ToolbarHelper getToolbarHelper() {
        return (ToolbarHelper) this.providesToolbarHelperProvider.get();
    }

    public LocalizationHelper getLocalizationHelper() {
        return (LocalizationHelper) this.providesLocalizationHelperProvider.get();
    }

    public BannerHelper getBannerHelper() {
        return (BannerHelper) this.providesBannerHelperProvider.get();
    }

    public ComponentHelper getComponentHelper() {
        return (ComponentHelper) this.providesComponentHelperProvider.get();
    }
}
