package com.paypal.android.sdk.onetouch.core.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import androidx.core.p012os.EnvironmentCompat;

public class DeviceInspector {
    public static String getApplicationInfoName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageInfo(context.getPackageName(), 0).applicationInfo.loadLabel(packageManager).toString();
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    public static String getSimOperatorName(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSimOperatorName();
        } catch (SecurityException unused) {
            return null;
        }
    }

    public static String getDeviceName() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str.equalsIgnoreCase(EnvironmentCompat.MEDIA_UNKNOWN) || str2.startsWith(str)) {
            return str2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        return sb.toString();
    }

    public static String getOs() {
        StringBuilder sb = new StringBuilder();
        sb.append("Android ");
        sb.append(VERSION.RELEASE);
        return sb.toString();
    }
}
