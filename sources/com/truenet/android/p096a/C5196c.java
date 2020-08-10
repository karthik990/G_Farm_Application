package com.truenet.android.p096a;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import p000a.p001a.C0073h;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: com.truenet.android.a.c */
/* compiled from: StartAppSDK */
public final class C5196c {
    /* renamed from: a */
    public static final TelephonyManager m3938a(Context context) {
        C0032h.m44b(context, "$receiver");
        Object systemService = context.getSystemService("phone");
        if (systemService != null) {
            return (TelephonyManager) systemService;
        }
        throw new C0073h("null cannot be cast to non-null type android.telephony.TelephonyManager");
    }

    /* renamed from: b */
    public static final WindowManager m3939b(Context context) {
        C0032h.m44b(context, "$receiver");
        Object systemService = context.getSystemService("window");
        if (systemService != null) {
            return (WindowManager) systemService;
        }
        throw new C0073h("null cannot be cast to non-null type android.view.WindowManager");
    }
}
