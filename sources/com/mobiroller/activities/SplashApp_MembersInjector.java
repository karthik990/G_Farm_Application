package com.mobiroller.activities;

import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.FileDownloader;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.util.ImageManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class SplashApp_MembersInjector implements MembersInjector<SplashApp> {
    private final Provider<ApiRequestManager> apiRequestManagerProvider;
    private final Provider<MobiRollerApplication> appProvider;
    private final Provider<FileDownloader> fileDownloaderProvider;
    private final Provider<ImageManager> imageManagerProvider;
    private final Provider<JSONParser> jParserNewProvider;
    private final Provider<MenuHelper> menuHelperProvider;
    private final Provider<ScreenHelper> screenHelperProvider;

    public SplashApp_MembersInjector(Provider<JSONParser> provider, Provider<ImageManager> provider2, Provider<FileDownloader> provider3, Provider<ApiRequestManager> provider4, Provider<ScreenHelper> provider5, Provider<MobiRollerApplication> provider6, Provider<MenuHelper> provider7) {
        this.jParserNewProvider = provider;
        this.imageManagerProvider = provider2;
        this.fileDownloaderProvider = provider3;
        this.apiRequestManagerProvider = provider4;
        this.screenHelperProvider = provider5;
        this.appProvider = provider6;
        this.menuHelperProvider = provider7;
    }

    public static MembersInjector<SplashApp> create(Provider<JSONParser> provider, Provider<ImageManager> provider2, Provider<FileDownloader> provider3, Provider<ApiRequestManager> provider4, Provider<ScreenHelper> provider5, Provider<MobiRollerApplication> provider6, Provider<MenuHelper> provider7) {
        SplashApp_MembersInjector splashApp_MembersInjector = new SplashApp_MembersInjector(provider, provider2, provider3, provider4, provider5, provider6, provider7);
        return splashApp_MembersInjector;
    }

    public void injectMembers(SplashApp splashApp) {
        injectJParserNew(splashApp, (JSONParser) this.jParserNewProvider.get());
        injectImageManager(splashApp, (ImageManager) this.imageManagerProvider.get());
        injectFileDownloader(splashApp, (FileDownloader) this.fileDownloaderProvider.get());
        injectApiRequestManager(splashApp, (ApiRequestManager) this.apiRequestManagerProvider.get());
        injectScreenHelper(splashApp, (ScreenHelper) this.screenHelperProvider.get());
        injectApp(splashApp, (MobiRollerApplication) this.appProvider.get());
        injectMenuHelper(splashApp, (MenuHelper) this.menuHelperProvider.get());
    }

    public static void injectJParserNew(SplashApp splashApp, JSONParser jSONParser) {
        splashApp.jParserNew = jSONParser;
    }

    public static void injectImageManager(SplashApp splashApp, ImageManager imageManager) {
        splashApp.imageManager = imageManager;
    }

    public static void injectFileDownloader(SplashApp splashApp, FileDownloader fileDownloader) {
        splashApp.fileDownloader = fileDownloader;
    }

    public static void injectApiRequestManager(SplashApp splashApp, ApiRequestManager apiRequestManager) {
        splashApp.apiRequestManager = apiRequestManager;
    }

    public static void injectScreenHelper(SplashApp splashApp, ScreenHelper screenHelper) {
        splashApp.screenHelper = screenHelper;
    }

    public static void injectApp(SplashApp splashApp, MobiRollerApplication mobiRollerApplication) {
        splashApp.app = mobiRollerApplication;
    }

    public static void injectMenuHelper(SplashApp splashApp, MenuHelper menuHelper) {
        splashApp.menuHelper = menuHelper;
    }
}
