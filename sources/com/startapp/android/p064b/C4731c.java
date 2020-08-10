package com.startapp.android.p064b;

import android.content.Context;
import android.os.Build;
import com.mobiroller.constants.ChatConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/* renamed from: com.startapp.android.b.c */
/* compiled from: StartAppSDK */
public class C4731c {

    /* renamed from: a */
    private static C4729a f2462a;

    /* renamed from: a */
    public static boolean m2169a(Context context) {
        if (f2462a == null) {
            f2462a = new C4729a(context.getApplicationContext());
        }
        return f2462a.mo61133a() || m2168a() || m2171b() || m2171b() || m2173c() || m2174d() || m2172b(context);
    }

    /* renamed from: a */
    private static boolean m2168a() {
        String str = Build.TAGS;
        return str != null && str.contains("test-keys");
    }

    /* renamed from: b */
    private static boolean m2171b() {
        for (String file : new String[]{"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"}) {
            if (new File(file).exists()) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: c */
    private static boolean m2173c() {
        boolean z = false;
        Process process = null;
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", ChatConstants.ARG_USERS_ROLES_SUPER_USER});
            if (new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine() != null) {
                z = true;
            }
            if (exec != null) {
                exec.destroy();
            }
            return z;
        } catch (Throwable unused) {
            if (process != null) {
                process.destroy();
            }
            return false;
        }
    }

    /* renamed from: d */
    private static boolean m2174d() {
        try {
            return new File("/system/app/Superuser.apk").exists();
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: b */
    private static boolean m2172b(Context context) {
        for (String a : new String[]{"com.noshufou.android.su", "com.thirdparty.superuser", "eu.chainfire.supersu", "com.koushikdutta.superuser", "com.zachspong.temprootremovejb", "com.ramdroid.appquarantine"}) {
            if (m2170a(context, a)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    private static boolean m2170a(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }
}
