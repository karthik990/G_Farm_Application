package com.flurry.sdk;

import android.os.Bundle;

/* renamed from: com.flurry.sdk.ao */
public final class C1550ao {

    /* renamed from: a */
    public final C1551a f526a;

    /* renamed from: b */
    public final Bundle f527b;

    /* renamed from: com.flurry.sdk.ao$a */
    public enum C1551a {
        CREATED,
        STARTED,
        RESUMED,
        PAUSED,
        STOPPED,
        DESTROYED,
        SAVE_STATE,
        APP_ORIENTATION_CHANGE,
        APP_BACKGROUND,
        TRIM_MEMORY
    }

    public C1550ao(C1551a aVar, Bundle bundle) {
        this.f526a = aVar;
        this.f527b = bundle;
    }
}
