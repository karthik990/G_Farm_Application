package com.google.android.gms.internal.ads;

import android.webkit.WebView;
import com.google.android.gms.common.util.PlatformVersion;

@zzadh
final class zzasy {
    private static Boolean zzdfk;

    private zzasy() {
    }

    static void zza(WebView webView, String str) {
        if (!PlatformVersion.isAtLeastKitKat() || !zzb(webView)) {
            String str2 = "javascript:";
            String valueOf = String.valueOf(str);
            webView.loadUrl(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return;
        }
        webView.evaluateJavascript(str, null);
    }

    private static boolean zzb(WebView webView) {
        boolean booleanValue;
        synchronized (zzasy.class) {
            if (zzdfk == null) {
                try {
                    webView.evaluateJavascript("(function(){})()", null);
                    zzdfk = Boolean.valueOf(true);
                } catch (IllegalStateException unused) {
                    zzdfk = Boolean.valueOf(false);
                }
            }
            booleanValue = zzdfk.booleanValue();
        }
        return booleanValue;
    }
}
