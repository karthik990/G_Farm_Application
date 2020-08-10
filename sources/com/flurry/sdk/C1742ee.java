package com.flurry.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import androidx.browser.customtabs.CustomTabsService;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.flurry.sdk.ee */
final class C1742ee {

    /* renamed from: a */
    private static String f999a;

    /* renamed from: a */
    static String m883a(Context context) {
        String str = f999a;
        if (str != null) {
            return str;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.example.com"));
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
        String str2 = resolveActivity != null ? resolveActivity.activityInfo.packageName : null;
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            Intent intent2 = new Intent();
            intent2.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
            intent2.setPackage(resolveInfo.activityInfo.packageName);
            if (packageManager.resolveService(intent2, 0) != null) {
                arrayList.add(resolveInfo.activityInfo.packageName);
            }
        }
        if (arrayList.isEmpty()) {
            f999a = null;
        } else if (arrayList.size() == 1) {
            f999a = (String) arrayList.get(0);
        } else if (TextUtils.isEmpty(str2) || m884a(context, intent) || !arrayList.contains(str2)) {
            String str3 = "com.android.chrome";
            if (arrayList.contains(str3)) {
                f999a = str3;
            } else {
                String str4 = "com.chrome.beta";
                if (arrayList.contains(str4)) {
                    f999a = str4;
                } else {
                    String str5 = "com.chrome.dev";
                    if (arrayList.contains(str5)) {
                        f999a = str5;
                    } else {
                        String str6 = "com.google.android.apps.chrome";
                        if (arrayList.contains(str6)) {
                            f999a = str6;
                        }
                    }
                }
            }
        } else {
            f999a = str2;
        }
        return f999a;
    }

    /* renamed from: a */
    private static boolean m884a(Context context, Intent intent) {
        try {
            List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 64);
            if (queryIntentActivities != null) {
                if (queryIntentActivities.size() != 0) {
                    for (ResolveInfo resolveInfo : queryIntentActivities) {
                        IntentFilter intentFilter = resolveInfo.filter;
                        if (intentFilter != null && intentFilter.countDataAuthorities() != 0 && intentFilter.countDataPaths() != 0 && resolveInfo.activityInfo != null) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            return false;
        } catch (RuntimeException unused) {
            C1685cy.m754a(6, "CustomTabsPackageHelper", "Runtime exception while getting specialized handlers");
        }
    }
}
