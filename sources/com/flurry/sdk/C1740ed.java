package com.flurry.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import androidx.browser.customtabs.CustomTabsIntent;

/* renamed from: com.flurry.sdk.ed */
public final class C1740ed {

    /* renamed from: a */
    private static Boolean f998a;

    /* renamed from: com.flurry.sdk.ed$a */
    public interface C1741a {
        /* renamed from: a */
        void mo16453a(Context context);
    }

    /* renamed from: a */
    public static void m880a(Context context, CustomTabsIntent customTabsIntent, Uri uri, C1741a aVar) {
        if (!m881a(context)) {
            aVar.mo16453a(context);
            return;
        }
        if (VERSION.SDK_INT >= 22) {
            Intent intent = customTabsIntent.intent;
            StringBuilder sb = new StringBuilder("2//");
            sb.append(context.getPackageName());
            intent.putExtra("android.intent.extra.REFERRER", Uri.parse(sb.toString()));
        }
        String a = C1742ee.m883a(context);
        customTabsIntent.intent.setFlags(268435456);
        customTabsIntent.intent.setPackage(a);
        customTabsIntent.launchUrl(context, uri);
    }

    /* renamed from: a */
    public static boolean m881a(Context context) {
        Boolean bool = f998a;
        if (bool != null) {
            return bool.booleanValue();
        }
        f998a = Boolean.TRUE;
        try {
            Class.forName("androidx.browser.customtabs.CustomTabsClient");
        } catch (ClassNotFoundException unused) {
            C1685cy.m769e("CustomTabsHelper", "Couldn't find Chrome Custom Tab dependency. For better user experience include Chrome Custom Tab dependency in gradle");
            f998a = Boolean.FALSE;
        }
        Boolean valueOf = Boolean.valueOf(f998a.booleanValue() && C1742ee.m883a(context) != null);
        f998a = valueOf;
        return valueOf.booleanValue();
    }
}
