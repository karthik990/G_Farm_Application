package com.google.android.youtube.player.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;

/* renamed from: com.google.android.youtube.player.internal.z */
public final class C2838z {

    /* renamed from: a */
    private static final Uri f1736a = Uri.parse("http://play.google.com/store/apps/details");

    /* renamed from: b */
    private static final String[] f1737b = {"com.google.android.youtube", "com.google.android.youtube.tv", "com.google.android.youtube.googletv", "com.google.android.gms", null};

    /* renamed from: a */
    public static Intent m1748a(String str) {
        Uri fromParts = Uri.fromParts("package", str, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(fromParts);
        return intent;
    }

    /* renamed from: a */
    public static String m1749a() {
        StringBuilder sb = new StringBuilder(35);
        sb.append(1);
        sb.append(".2.2");
        return sb.toString();
    }

    /* renamed from: a */
    public static String m1750a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        for (String str : f1737b) {
            ResolveInfo resolveService = packageManager.resolveService(new Intent("com.google.android.youtube.api.service.START").setPackage(str), 0);
            if (resolveService != null && resolveService.serviceInfo != null && resolveService.serviceInfo.packageName != null) {
                return resolveService.serviceInfo.packageName;
            }
        }
        return packageManager.hasSystemFeature("android.software.leanback") ? "com.google.android.youtube.tv" : packageManager.hasSystemFeature("com.google.android.tv") ? "com.google.android.youtube.googletv" : "com.google.android.youtube";
    }

    /* renamed from: a */
    public static boolean m1751a(Context context, String str) {
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(str);
            if (str.equals("com.google.android.youtube.googletvdev")) {
                str = "com.google.android.youtube.googletv";
            }
            int identifier = resourcesForApplication.getIdentifier("youtube_api_version_code", "integer", str);
            return identifier == 0 || 12 > resourcesForApplication.getInteger(identifier) / 100;
        } catch (NameNotFoundException unused) {
            return true;
        }
    }

    /* renamed from: a */
    public static boolean m1752a(PackageManager packageManager) {
        return packageManager.hasSystemFeature("com.google.android.tv");
    }

    /* renamed from: b */
    public static Context m1753b(Context context) {
        try {
            return context.createPackageContext(m1750a(context), 3);
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    /* renamed from: b */
    public static Intent m1754b(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(f1736a.buildUpon().appendQueryParameter(TtmlNode.ATTR_ID, str).build());
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }

    /* renamed from: b */
    public static boolean m1755b(PackageManager packageManager) {
        return packageManager.hasSystemFeature("android.software.leanback");
    }

    /* renamed from: c */
    public static int m1756c(Context context) {
        Context b = m1753b(context);
        int identifier = b != null ? b.getResources().getIdentifier("clientTheme", TtmlNode.TAG_STYLE, m1750a(context)) : 0;
        if (identifier != 0) {
            return identifier;
        }
        if (VERSION.SDK_INT >= 14) {
            return 16974120;
        }
        return VERSION.SDK_INT >= 11 ? 16973931 : 16973829;
    }

    /* renamed from: d */
    public static String m1757d(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            throw new IllegalStateException("Cannot retrieve calling Context's PackageInfo", e);
        }
    }
}
