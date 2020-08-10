package com.mobiroller;

import com.mobiroller.helpers.FileDownloader;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvidesFileDownloaderFactory implements Factory<FileDownloader> {
    private final AppModule module;

    public AppModule_ProvidesFileDownloaderFactory(AppModule appModule) {
        this.module = appModule;
    }

    public FileDownloader get() {
        return proxyProvidesFileDownloader(this.module);
    }

    public static AppModule_ProvidesFileDownloaderFactory create(AppModule appModule) {
        return new AppModule_ProvidesFileDownloaderFactory(appModule);
    }

    public static FileDownloader proxyProvidesFileDownloader(AppModule appModule) {
        return (FileDownloader) Preconditions.checkNotNull(appModule.providesFileDownloader(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
