package com.flurry.sdk;

import android.content.Context;

/* renamed from: com.flurry.sdk.b */
public final class C1576b {

    /* renamed from: a */
    private static Context f609a;

    /* renamed from: a */
    public static Context m502a() {
        return f609a;
    }

    /* renamed from: a */
    public static void m503a(Context context) {
        f609a = context.getApplicationContext();
    }
}
