package com.truenet.android.p096a;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: com.truenet.android.a.j */
/* compiled from: StartAppSDK */
public final class C5206j {
    /* renamed from: a */
    public static final Bitmap m3956a(WebView webView) {
        C0032h.m44b(webView, "$receiver");
        if (VERSION.SDK_INT >= 21) {
            WebView.enableSlowWholeDocumentDraw();
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Context context = webView.getContext();
        C0032h.m41a((Object) context, "context");
        C5196c.m3939b(context).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        webView.measure(i, i2);
        webView.layout(0, 0, i, i2);
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache(true);
        Thread.sleep(500);
        Bitmap createBitmap = Bitmap.createBitmap(webView.getDrawingCache());
        webView.setDrawingCacheEnabled(false);
        C0032h.m41a((Object) createBitmap, "bitmap");
        return createBitmap;
    }
}
