package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/* renamed from: com.flurry.sdk.dx */
public final class C1731dx {
    /* renamed from: a */
    public static String m857a(Context context) {
        PackageInfo b = m858b(context);
        return (b == null || b.packageName == null) ? "" : b.packageName;
    }

    /* renamed from: b */
    private static PackageInfo m858b(Context context) {
        if (context != null) {
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            } catch (NameNotFoundException unused) {
                StringBuilder sb = new StringBuilder("Cannot find package info for package: ");
                sb.append(context.getPackageName());
                C1685cy.m756a("ContextUtil", sb.toString());
            }
        }
        return null;
    }
}
