package com.mobiroller;

import com.mobiroller.util.ImageManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvidesImageManagerFactory implements Factory<ImageManager> {
    private final AppModule module;

    public AppModule_ProvidesImageManagerFactory(AppModule appModule) {
        this.module = appModule;
    }

    public ImageManager get() {
        return proxyProvidesImageManager(this.module);
    }

    public static AppModule_ProvidesImageManagerFactory create(AppModule appModule) {
        return new AppModule_ProvidesImageManagerFactory(appModule);
    }

    public static ImageManager proxyProvidesImageManager(AppModule appModule) {
        return (ImageManager) Preconditions.checkNotNull(appModule.providesImageManager(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
