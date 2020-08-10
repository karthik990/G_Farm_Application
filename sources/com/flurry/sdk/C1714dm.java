package com.flurry.sdk;

import android.app.ActivityManager.MemoryInfo;
import android.content.Context;

/* renamed from: com.flurry.sdk.dm */
public final class C1714dm {

    /* renamed from: a */
    public long f963a = System.nanoTime();

    /* renamed from: b */
    public long f964b;

    /* renamed from: c */
    public long f965c;

    public C1714dm() {
        Runtime runtime = Runtime.getRuntime();
        this.f964b = runtime.totalMemory() - runtime.freeMemory();
        Context a = C1576b.m502a();
        if (a != null) {
            MemoryInfo a2 = C1713dl.m834a(a);
            this.f965c = a2.totalMem - a2.availMem;
        }
    }
}
