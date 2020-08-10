package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import androidx.core.p012os.EnvironmentCompat;
import com.braintreepayments.api.models.BinData;
import java.util.Locale;

/* renamed from: com.flurry.sdk.bl */
public final class C1601bl {

    /* renamed from: c */
    private static C1601bl f668c;

    /* renamed from: a */
    public String f669a;

    /* renamed from: b */
    public String f670b;

    /* renamed from: d */
    private String f671d;

    private C1601bl() {
        String str = "";
        this.f670b = String.format(Locale.getDefault(), "Flurry_Android_%d_%d.%d.%d%s%s", new Object[]{Integer.valueOf(313), Integer.valueOf(12), Integer.valueOf(3), Integer.valueOf(0), str, str});
    }

    /* renamed from: a */
    public static synchronized C1601bl m537a() {
        C1601bl blVar;
        synchronized (C1601bl.class) {
            if (f668c == null) {
                f668c = new C1601bl();
            }
            blVar = f668c;
        }
        return blVar;
    }

    /* renamed from: a */
    public static String m538a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            return packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException unused) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    /* renamed from: b */
    public static int m539b(Context context) {
        PackageManager packageManager = context.getPackageManager();
        int i = 0;
        if (packageManager == null) {
            return i;
        }
        try {
            return packageManager.getPackageInfo(context.getPackageName(), i).versionCode;
        } catch (NameNotFoundException unused) {
            return i;
        }
    }

    /* renamed from: b */
    public final synchronized String mo16290b() {
        if (!TextUtils.isEmpty(this.f669a)) {
            return this.f669a;
        } else if (!TextUtils.isEmpty(this.f671d)) {
            return this.f671d;
        } else {
            this.f671d = m540c();
            return this.f671d;
        }
    }

    /* renamed from: c */
    private static String m540c() {
        try {
            Context a = C1576b.m502a();
            PackageInfo packageInfo = a.getPackageManager().getPackageInfo(a.getPackageName(), 0);
            if (packageInfo.versionName != null) {
                return packageInfo.versionName;
            }
            if (packageInfo.versionCode != 0) {
                return Integer.toString(packageInfo.versionCode);
            }
            return BinData.UNKNOWN;
        } catch (Throwable th) {
            C1685cy.m755a(6, "VersionProvider", "", th);
        }
    }
}
