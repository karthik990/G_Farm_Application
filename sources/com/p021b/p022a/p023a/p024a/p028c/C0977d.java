package com.p021b.p022a.p023a.p024a.p028c;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.WebView;
import com.p021b.p022a.p023a.p024a.p030e.C0989c;
import org.json.JSONObject;

/* renamed from: com.b.a.a.a.c.d */
public class C0977d {

    /* renamed from: a */
    private static C0977d f144a = new C0977d();

    private C0977d() {
    }

    /* renamed from: a */
    public static C0977d m192a() {
        return f144a;
    }

    /* renamed from: a */
    public void mo11527a(WebView webView) {
        mo11531a(webView, "finishSession", new Object[0]);
    }

    /* renamed from: a */
    public void mo11528a(WebView webView, float f) {
        mo11531a(webView, "setDeviceVolume", Float.valueOf(f));
    }

    /* renamed from: a */
    public void mo11529a(WebView webView, String str, JSONObject jSONObject) {
        String str2 = "publishVideoEvent";
        if (jSONObject != null) {
            mo11531a(webView, str2, str, jSONObject);
            return;
        }
        mo11531a(webView, str2, str);
    }

    /* renamed from: a */
    public void mo11530a(WebView webView, String str, JSONObject jSONObject, JSONObject jSONObject2) {
        mo11531a(webView, "startSession", str, jSONObject, jSONObject2);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo11531a(WebView webView, String str, Object... objArr) {
        if (webView != null) {
            StringBuilder sb = new StringBuilder(128);
            sb.append("javascript: if(window.omidBridge!==undefined){omidBridge.");
            sb.append(str);
            sb.append("(");
            mo11534a(sb, objArr);
            sb.append(")}");
            mo11532a(webView, sb);
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("The WebView is null for ");
        sb2.append(str);
        C0989c.m250a(sb2.toString());
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo11532a(final WebView webView, StringBuilder sb) {
        final String sb2 = sb.toString();
        Handler handler = webView.getHandler();
        if (handler == null || Looper.myLooper() == handler.getLooper()) {
            webView.loadUrl(sb2);
        } else {
            handler.post(new Runnable() {
                public void run() {
                    webView.loadUrl(sb2);
                }
            });
        }
    }

    /* renamed from: a */
    public void mo11533a(WebView webView, JSONObject jSONObject) {
        mo11531a(webView, "init", jSONObject);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo11534a(StringBuilder sb, Object[] objArr) {
        if (objArr != null && objArr.length > 0) {
            for (Object obj : objArr) {
                if (obj == null) {
                    sb.append('\"');
                } else {
                    if (obj instanceof String) {
                        String obj2 = obj.toString();
                        if (obj2.startsWith("{")) {
                            sb.append(obj2);
                        } else {
                            sb.append('\"');
                            sb.append(obj2);
                        }
                    } else {
                        sb.append(obj);
                    }
                    sb.append(",");
                }
                sb.append('\"');
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
        }
    }

    /* renamed from: a */
    public boolean mo11535a(WebView webView, String str) {
        if (webView == null || TextUtils.isEmpty(str)) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("javascript: ");
        sb.append(str);
        webView.loadUrl(sb.toString());
        return true;
    }

    /* renamed from: b */
    public void mo11536b(WebView webView) {
        mo11531a(webView, "publishImpressionEvent", new Object[0]);
    }

    /* renamed from: b */
    public void mo11537b(WebView webView, String str) {
        if (str != null) {
            mo11535a(webView, "var script=document.createElement('script');script.setAttribute(\"type\",\"text/javascript\");script.setAttribute(\"src\",\"%SCRIPT_SRC%\");document.body.appendChild(script);".replace("%SCRIPT_SRC%", str));
        }
    }

    /* renamed from: c */
    public void mo11538c(WebView webView, String str) {
        mo11531a(webView, "setNativeViewHierarchy", str);
    }

    /* renamed from: d */
    public void mo11539d(WebView webView, String str) {
        mo11531a(webView, "setState", str);
    }
}
