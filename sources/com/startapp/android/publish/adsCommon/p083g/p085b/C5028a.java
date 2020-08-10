package com.startapp.android.publish.adsCommon.p083g.p085b;

import android.content.Context;
import android.webkit.WebView;
import com.startapp.android.publish.adsCommon.p083g.p084a.C5025c;

/* renamed from: com.startapp.android.publish.adsCommon.g.b.a */
/* compiled from: StartAppSDK */
public final class C5028a {
    /* renamed from: a */
    public static void m3280a(Context context, WebView webView, C5029b bVar) {
        if (bVar == null) {
            bVar = new C5029b(context);
        }
        C5025c.m3270a(webView, "mraid.SUPPORTED_FEATURES.CALENDAR", bVar.mo62328a());
        C5025c.m3270a(webView, "mraid.SUPPORTED_FEATURES.INLINEVIDEO", bVar.mo62330b());
        C5025c.m3270a(webView, "mraid.SUPPORTED_FEATURES.SMS", bVar.mo62331c());
        C5025c.m3270a(webView, "mraid.SUPPORTED_FEATURES.STOREPICTURE", bVar.mo62332d());
        C5025c.m3270a(webView, "mraid.SUPPORTED_FEATURES.TEL", bVar.mo62333e());
    }
}
