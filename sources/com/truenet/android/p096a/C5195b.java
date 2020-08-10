package com.truenet.android.p096a;

import android.content.res.Configuration;
import android.os.Build.VERSION;
import java.util.Locale;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: com.truenet.android.a.b */
/* compiled from: StartAppSDK */
public final class C5195b {
    /* renamed from: a */
    public static final Locale m3937a(Configuration configuration) {
        C0032h.m44b(configuration, "$receiver");
        return VERSION.SDK_INT >= 24 ? configuration.getLocales().get(0) : configuration.locale;
    }
}
