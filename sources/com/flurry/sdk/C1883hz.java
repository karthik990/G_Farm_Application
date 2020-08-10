package com.flurry.sdk;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

/* renamed from: com.flurry.sdk.hz */
public final class C1883hz extends C1927jm {
    private C1883hz(C1929jo joVar) {
        super(joVar);
    }

    /* renamed from: a */
    public final C1928jn mo16501a() {
        return C1928jn.APP_INFO;
    }

    /* renamed from: b */
    public static void m1128b() {
        String b = C1601bl.m537a().mo16290b();
        String str = C1601bl.m537a().f669a;
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        Context a = C1576b.m502a();
        int i = 0;
        try {
            i = a.getPackageManager().getPackageInfo(a.getPackageName(), i).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        C1771fb.m926a().mo16467a(new C1883hz(new C1887ia(b, str, String.valueOf(i), C1731dx.m857a(C1576b.m502a()))));
    }
}
