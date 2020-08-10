package com.bumptech.glide;

import com.bumptech.glide.manager.RequestManagerRetriever.RequestManagerFactory;
import com.bumptech.glide.module.AppGlideModule;
import java.util.Set;

abstract class GeneratedAppGlideModule extends AppGlideModule {
    /* access modifiers changed from: 0000 */
    public abstract Set<Class<?>> getExcludedModuleClasses();

    /* access modifiers changed from: 0000 */
    public RequestManagerFactory getRequestManagerFactory() {
        return null;
    }

    GeneratedAppGlideModule() {
    }
}
