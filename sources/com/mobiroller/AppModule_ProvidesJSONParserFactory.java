package com.mobiroller;

import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.FileDownloader;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.NetworkHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvidesJSONParserFactory implements Factory<JSONParser> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<FileDownloader> fileDownloaderProvider;
    private final AppModule module;
    private final Provider<NetworkHelper> networkHelperProvider;

    public AppModule_ProvidesJSONParserFactory(AppModule appModule, Provider<FileDownloader> provider, Provider<ApiRequestManager> provider2, Provider<NetworkHelper> provider3) {
        this.module = appModule;
        this.fileDownloaderProvider = provider;
        this.apiRequestManagerProvider = provider2;
        this.networkHelperProvider = provider3;
    }

    public JSONParser get() {
        return proxyProvidesJSONParser(this.module, (FileDownloader) this.fileDownloaderProvider.get(), (ApiRequestManager) this.apiRequestManagerProvider.get(), (NetworkHelper) this.networkHelperProvider.get());
    }

    public static AppModule_ProvidesJSONParserFactory create(AppModule appModule, Provider<FileDownloader> provider, Provider<ApiRequestManager> provider2, Provider<NetworkHelper> provider3) {
        return new AppModule_ProvidesJSONParserFactory(appModule, provider, provider2, provider3);
    }

    public static JSONParser proxyProvidesJSONParser(AppModule appModule, FileDownloader fileDownloader, ApiRequestManager apiRequestManager, NetworkHelper networkHelper) {
        return (JSONParser) Preconditions.checkNotNull(appModule.providesJSONParser(fileDownloader, apiRequestManager, networkHelper), "Cannot return null from a non-@Nullable @Provides method");
    }
}
