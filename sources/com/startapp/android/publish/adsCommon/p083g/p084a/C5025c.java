package com.startapp.android.publish.adsCommon.p083g.p084a;

import android.content.Context;
import android.webkit.WebView;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.adsCommon.g.a.c */
/* compiled from: StartAppSDK */
public final class C5025c {
    /* renamed from: a */
    public static void m3273a(String str, WebView webView) {
        StringBuilder sb = new StringBuilder();
        sb.append("setPlacementType: ");
        sb.append(str);
        C5155g.m3807a("MraidJsFunctions", 3, sb.toString());
        C4946i.m2917a(webView, "mraid.setPlacementType", str);
    }

    /* renamed from: a */
    public static void m3272a(C5026d dVar, WebView webView) {
        StringBuilder sb = new StringBuilder();
        sb.append("fireStateChangeEvent: ");
        sb.append(dVar);
        C5155g.m3807a("MraidJsFunctions", 3, sb.toString());
        C4946i.m2917a(webView, "mraid.fireStateChangeEvent", dVar.toString());
    }

    /* renamed from: a */
    public static void m3268a(Context context, int i, int i2, WebView webView) {
        StringBuilder sb = new StringBuilder();
        sb.append("setScreenSize ");
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        C5155g.m3807a("MraidJsFunctions", 3, sb.toString());
        C4946i.m2917a(webView, "mraid.setScreenSize", Integer.valueOf(C4945h.m2900b(context, i)), Integer.valueOf(C4945h.m2900b(context, i2)));
    }

    /* renamed from: b */
    public static void m3275b(Context context, int i, int i2, WebView webView) {
        StringBuilder sb = new StringBuilder();
        sb.append("setMaxSize ");
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        C5155g.m3807a("MraidJsFunctions", 3, sb.toString());
        C4946i.m2917a(webView, "mraid.setMaxSize", Integer.valueOf(C4945h.m2900b(context, i)), Integer.valueOf(C4945h.m2900b(context, i2)));
    }

    /* renamed from: a */
    public static void m3267a(Context context, int i, int i2, int i3, int i4, WebView webView) {
        StringBuilder sb = new StringBuilder();
        sb.append("setCurrentPosition [");
        sb.append(i);
        sb.append(",");
        sb.append(i2);
        sb.append("] (");
        sb.append(i3);
        sb.append("x");
        sb.append(i4);
        sb.append(")");
        C5155g.m3807a("MraidJsFunctions", 3, sb.toString());
        C4946i.m2917a(webView, "mraid.setCurrentPosition", Integer.valueOf(C4945h.m2900b(context, i)), Integer.valueOf(C4945h.m2900b(context, i2)), Integer.valueOf(C4945h.m2900b(context, i3)), Integer.valueOf(C4945h.m2900b(context, i4)));
    }

    /* renamed from: b */
    public static void m3274b(Context context, int i, int i2, int i3, int i4, WebView webView) {
        StringBuilder sb = new StringBuilder();
        sb.append("setDefaultPosition [");
        sb.append(i);
        sb.append(",");
        sb.append(i2);
        sb.append("] (");
        sb.append(i3);
        sb.append("x");
        sb.append(i4);
        sb.append(")");
        C5155g.m3807a("MraidJsFunctions", 3, sb.toString());
        C4946i.m2917a(webView, "mraid.setDefaultPosition", Integer.valueOf(C4945h.m2900b(context, i)), Integer.valueOf(C4945h.m2900b(context, i2)), Integer.valueOf(C4945h.m2900b(context, i3)), Integer.valueOf(C4945h.m2900b(context, i4)));
    }

    /* renamed from: a */
    public static void m3269a(WebView webView) {
        C5155g.m3807a("MraidJsFunctions", 3, "fireReadyEvent");
        C4946i.m2917a(webView, "mraid.fireReadyEvent", new Object[0]);
    }

    /* renamed from: a */
    public static void m3271a(WebView webView, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("fireViewableChangeEvent ");
        sb.append(z);
        C5155g.m3807a("MraidJsFunctions", 3, sb.toString());
        C4946i.m2917a(webView, "mraid.fireViewableChangeEvent", Boolean.valueOf(z));
    }

    /* renamed from: a */
    public static void m3270a(WebView webView, String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("setSupports feature: ");
        sb.append(str);
        sb.append(", isSupported:");
        sb.append(z);
        C5155g.m3807a("MraidJsFunctions", 3, sb.toString());
        C4946i.m2918a(webView, false, "mraid.setSupports", str, Boolean.valueOf(z));
    }
}
