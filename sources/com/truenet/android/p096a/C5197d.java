package com.truenet.android.p096a;

import android.content.Context;
import android.net.ConnectivityManager;
import p000a.p001a.C0073h;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: com.truenet.android.a.d */
/* compiled from: StartAppSDK */
public final class C5197d {
    /* renamed from: a */
    public static final ConnectivityManager m3940a(Context context) {
        C0032h.m44b(context, "$receiver");
        Object systemService = context.getSystemService("connectivity");
        if (systemService != null) {
            return (ConnectivityManager) systemService;
        }
        throw new C0073h("null cannot be cast to non-null type android.net.ConnectivityManager");
    }

    /* renamed from: b */
    public static final C5198e m3941b(Context context) {
        C0032h.m44b(context, "$receiver");
        return new C5198e(context);
    }
}
