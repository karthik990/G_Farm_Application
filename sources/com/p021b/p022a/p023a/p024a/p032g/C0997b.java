package com.p021b.p022a.p023a.p024a.p032g;

import android.webkit.WebView;

/* renamed from: com.b.a.a.a.g.b */
public class C0997b extends C0995a {
    public C0997b(WebView webView) {
        if (webView != null && !webView.getSettings().getJavaScriptEnabled()) {
            webView.getSettings().setJavaScriptEnabled(true);
        }
        mo11552a(webView);
    }
}
