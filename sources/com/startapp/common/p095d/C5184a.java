package com.startapp.common.p095d;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Map;

/* renamed from: com.startapp.common.d.a */
/* compiled from: StartAppSDK */
public class C5184a {

    /* renamed from: a */
    private static CookieManager f3623a;

    /* renamed from: a */
    public static void m3920a(Context context) {
        f3623a = new CookieManager(new C5185b(context), CookiePolicy.ACCEPT_ALL);
    }

    /* renamed from: b */
    public static void m3922b(Context context) {
        if (VERSION.SDK_INT >= 9) {
            m3920a(context);
        }
    }

    /* renamed from: a */
    public static void m3921a(HttpURLConnection httpURLConnection, String str) {
        if (VERSION.SDK_INT >= 9) {
            CookieManager a = m3919a();
            if (a != null) {
                Map map = a.get(URI.create(str), httpURLConnection.getRequestProperties());
                if (map != null && map.size() > 0) {
                    String str2 = "Cookie";
                    if (((List) map.get(str2)).size() > 0) {
                        httpURLConnection.addRequestProperty(str2, TextUtils.join("=", (Iterable) map.get(str2)));
                    }
                }
            }
        }
    }

    /* renamed from: b */
    public static void m3923b(HttpURLConnection httpURLConnection, String str) {
        if (VERSION.SDK_INT >= 9) {
            CookieManager a = m3919a();
            if (a != null) {
                a.put(URI.create(str), httpURLConnection.getHeaderFields());
            }
        }
    }

    /* renamed from: a */
    public static CookieManager m3919a() {
        return f3623a;
    }
}
