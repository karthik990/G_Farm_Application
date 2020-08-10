package com.startapp.android.publish.adsCommon.p083g.p084a;

import android.net.Uri;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.startapp.android.publish.adsCommon.p083g.p087d.C5031a;
import com.startapp.android.publish.adsCommon.p083g.p087d.C5032b;
import com.startapp.common.p092a.C5155g;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

/* renamed from: com.startapp.android.publish.adsCommon.g.a.e */
/* compiled from: StartAppSDK */
public class C5027e extends WebViewClient {

    /* renamed from: a */
    private C5024b f3265a;

    /* renamed from: b */
    private boolean f3266b = false;

    public C5027e(C5024b bVar) {
        this.f3265a = bVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo62322a(String str) {
        return str != null && str.startsWith("mraid://");
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("shouldOverrideUrlLoading: ");
        sb.append(str);
        C5155g.m3807a("MraidWebViewClient", 3, sb.toString());
        if (mo62322a(str)) {
            return mo62324c(str);
        }
        return this.f3265a.open(str);
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("shouldInterceptRequest: ");
        sb.append(str);
        C5155g.m3807a("MraidWebViewClient", 3, sb.toString());
        if (this.f3266b || !mo62323b(str)) {
            return super.shouldInterceptRequest(webView, str);
        }
        this.f3266b = true;
        return m3276a();
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("onReceivedError: ");
        sb.append(str);
        C5155g.m3807a("MraidWebViewClient", 6, sb.toString());
        super.onReceivedError(webView, i, str, str2);
    }

    /* renamed from: b */
    public boolean mo62323b(String str) {
        try {
            return "mraid.js".equals(Uri.parse(str.toLowerCase(Locale.US)).getLastPathSegment());
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("matchesInjectionUrl Exception: ");
            sb.append(e.getMessage());
            C5155g.m3807a("MraidWebViewClient", 6, sb.toString());
            return false;
        }
    }

    /* renamed from: a */
    private WebResourceResponse m3276a() {
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:");
        sb.append(C5031a.m3289a());
        return new WebResourceResponse("text/javascript", "UTF-8", new ByteArrayInputStream(sb.toString().getBytes()));
    }

    /* renamed from: c */
    public boolean mo62324c(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("invokeMraidMethod ");
        sb.append(str);
        String str2 = "MraidWebViewClient";
        C5155g.m3807a(str2, 3, sb.toString());
        String[] strArr = {"close", "resize"};
        String str3 = "createCalendarEvent";
        String str4 = "useCustomClose";
        String[] strArr2 = {str3, "expand", "open", "playVideo", "storePicture", str4};
        String[] strArr3 = {"setOrientationProperties", "setResizeProperties"};
        try {
            Map a = C5032b.m3294a(str);
            String str5 = (String) a.get("command");
            if (Arrays.asList(strArr).contains(str5)) {
                C5024b.class.getDeclaredMethod(str5, new Class[0]).invoke(this.f3265a, new Object[0]);
            } else if (Arrays.asList(strArr2).contains(str5)) {
                Method declaredMethod = C5024b.class.getDeclaredMethod(str5, new Class[]{String.class});
                char c = 65535;
                int hashCode = str5.hashCode();
                if (hashCode != -733616544) {
                    if (hashCode == 1614272768) {
                        if (str5.equals(str4)) {
                            c = 1;
                        }
                    }
                } else if (str5.equals(str3)) {
                    c = 0;
                }
                if (c == 0) {
                    str4 = "eventJSON";
                } else if (c != 1) {
                    str4 = "url";
                }
                String str6 = (String) a.get(str4);
                declaredMethod.invoke(this.f3265a, new Object[]{str6});
            } else if (Arrays.asList(strArr3).contains(str5)) {
                C5024b.class.getDeclaredMethod(str5, new Class[]{Map.class}).invoke(this.f3265a, new Object[]{a});
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("successfully invoked ");
            sb2.append(str);
            C5155g.m3807a(str2, 3, sb2.toString());
            return true;
        } catch (Exception e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("failed to invoke ");
            sb3.append(str);
            sb3.append(". ");
            sb3.append(e.getMessage());
            C5155g.m3807a(str2, 6, sb3.toString());
            return false;
        }
    }
}
