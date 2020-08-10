package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;

public class zzrw {
    private static volatile UserManager zzbrb;
    private static volatile boolean zzbrc = (!zztj());

    private zzrw() {
    }

    public static boolean zztj() {
        return VERSION.SDK_INT >= 24;
    }

    public static boolean isUserUnlocked(Context context) {
        return !zztj() || zzab(context);
    }

    private static boolean zzab(Context context) {
        boolean z = zzbrc;
        if (!z) {
            boolean z2 = z;
            int i = 1;
            while (true) {
                if (i > 2) {
                    break;
                }
                UserManager zzac = zzac(context);
                if (zzac == null) {
                    zzbrc = true;
                    return true;
                }
                try {
                    if (!zzac.isUserUnlocked()) {
                        if (zzac.isUserRunning(Process.myUserHandle())) {
                            z2 = false;
                            zzbrc = z2;
                        }
                    }
                    z2 = true;
                    zzbrc = z2;
                } catch (NullPointerException e) {
                    Log.w("DirectBootUtils", "Failed to check if user is unlocked", e);
                    zzbrb = null;
                    i++;
                }
            }
            z = z2;
            if (z) {
                zzbrb = null;
            }
        }
        return z;
    }

    private static UserManager zzac(Context context) {
        UserManager userManager = zzbrb;
        if (userManager == null) {
            synchronized (zzrw.class) {
                userManager = zzbrb;
                if (userManager == null) {
                    UserManager userManager2 = (UserManager) context.getSystemService(UserManager.class);
                    zzbrb = userManager2;
                    userManager = userManager2;
                }
            }
        }
        return userManager;
    }
}
