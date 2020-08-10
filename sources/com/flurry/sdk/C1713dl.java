package com.flurry.sdk;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;

/* renamed from: com.flurry.sdk.dl */
public final class C1713dl {
    /* renamed from: a */
    public static MemoryInfo m834a(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }
}
