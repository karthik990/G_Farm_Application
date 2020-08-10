package com.startapp.android.publish.ads.p066a;

import android.os.Handler;
import android.webkit.WebView;

/* renamed from: com.startapp.android.publish.ads.a.f */
/* compiled from: StartAppSDK */
public class C4755f extends C4740c {
    /* renamed from: a */
    public void mo61193a(WebView webView) {
        super.mo61193a(webView);
        if (mo61168g().equals("interstitial")) {
            webView.setBackgroundColor(0);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public void mo61198c(final WebView webView) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    webView.setBackgroundColor(0);
                } catch (Exception unused) {
                }
            }
        }, 1000);
    }
}
