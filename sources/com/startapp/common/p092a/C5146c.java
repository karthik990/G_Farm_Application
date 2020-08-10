package com.startapp.common.p092a;

import android.app.Activity;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Paint;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.webkit.WebView;
import com.startapp.android.p063a.C4727a;
import com.startapp.android.p064b.C4731c;
import com.startapp.common.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.startapp.common.a.c */
/* compiled from: StartAppSDK */
public class C5146c {

    /* renamed from: a */
    private static Object f3559a;

    /* renamed from: com.startapp.common.a.c$a */
    /* compiled from: StartAppSDK */
    public interface C5149a {
        /* renamed from: a */
        void mo61802a(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);
    }

    /* renamed from: a */
    public static void m3751a(Editor editor) {
        if (VERSION.SDK_INT < 9) {
            editor.commit();
        } else {
            editor.apply();
        }
    }

    /* renamed from: a */
    public static void m3749a(Activity activity) {
        if (VERSION.SDK_INT >= 9) {
            m3750a(activity, 7);
        } else {
            m3750a(activity, 1);
        }
    }

    /* renamed from: b */
    public static void m3767b(Activity activity) {
        if (VERSION.SDK_INT >= 9) {
            m3750a(activity, 6);
        } else {
            m3750a(activity, 0);
        }
    }

    /* renamed from: a */
    public static int m3741a(int i, int i2, boolean z) {
        if (i != 1) {
            if (i != 2) {
                return 1;
            }
            if (VERSION.SDK_INT > 8 && !z && i2 != 0 && i2 != 1) {
                return 8;
            }
            return 0;
        } else if (VERSION.SDK_INT > 8 && !z && (i2 == 1 || i2 == 2)) {
            return 9;
        } else {
            return 1;
        }
    }

    /* renamed from: a */
    public static boolean m3758a() {
        return VERSION.SDK_INT >= 12;
    }

    /* renamed from: a */
    public static void m3752a(View view, float f) {
        if (VERSION.SDK_INT >= 11) {
            view.setAlpha(f);
        }
    }

    /* renamed from: a */
    public static void m3753a(View view, long j) {
        view.animate().alpha(1.0f).setDuration(j).setListener(null);
    }

    /* renamed from: a */
    public static void m3755a(ViewTreeObserver viewTreeObserver, OnGlobalLayoutListener onGlobalLayoutListener) {
        if (VERSION.SDK_INT >= 16) {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
    }

    /* renamed from: a */
    public static boolean m3759a(Context context) {
        try {
            String str = "install_non_market_apps";
            if (VERSION.SDK_INT >= 17) {
                if (VERSION.SDK_INT < 21) {
                    if (Global.getInt(context.getContentResolver(), str) != 1) {
                        return false;
                    }
                    return true;
                }
            }
            if (Secure.getInt(context.getContentResolver(), str) != 1) {
                return false;
            }
            return true;
        } catch (SettingNotFoundException unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static void m3756a(WebView webView) {
        if (VERSION.SDK_INT >= 17) {
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
    }

    /* renamed from: a */
    public static int m3742a(Activity activity, int i, boolean z) {
        int a = m3741a(i, activity.getWindowManager().getDefaultDisplay().getRotation(), z);
        m3750a(activity, a);
        return a;
    }

    /* renamed from: a */
    public static void m3750a(Activity activity, int i) {
        try {
            activity.setRequestedOrientation(i);
        } catch (Exception e) {
            C5155g.m3806a(6, "Error to setRequestedOrientation ", (Throwable) e);
        }
    }

    /* renamed from: b */
    public static boolean m3769b() {
        return VERSION.SDK_INT >= 14;
    }

    /* renamed from: b */
    public static void m3768b(WebView webView) {
        if (VERSION.SDK_INT >= 11) {
            webView.onPause();
            return;
        }
        try {
            Class.forName("android.webkit.WebView").getMethod("onPause", null).invoke(webView, null);
        } catch (Exception unused) {
            C5155g.m3805a(6, "Error while calling webView.onPause()");
        }
    }

    /* renamed from: c */
    public static void m3770c(WebView webView) {
        if (VERSION.SDK_INT >= 11) {
            webView.onResume();
            return;
        }
        try {
            Class.forName("android.webkit.WebView").getMethod("onResume", null).invoke(webView, null);
        } catch (Exception unused) {
            C5155g.m3805a(6, "Error while calling webView.onResume()");
        }
    }

    /* renamed from: a */
    public static void m3757a(WebView webView, Paint paint) {
        if (VERSION.SDK_INT >= 11) {
            webView.setLayerType(1, paint);
        }
    }

    /* renamed from: a */
    public static void m3754a(View view, final C5149a aVar) {
        if (VERSION.SDK_INT >= 11) {
            view.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    aVar.mo61802a(view, i, i2, i3, i4, i5, i6, i7, i8);
                }
            });
        }
    }

    /* renamed from: a */
    public static Long m3745a(MemoryInfo memoryInfo) {
        if (VERSION.SDK_INT >= 16) {
            return Long.valueOf(memoryInfo.totalMem);
        }
        return null;
    }

    /* renamed from: b */
    public static Set<String> m3766b(Context context) {
        HashSet hashSet = new HashSet();
        if (VERSION.SDK_INT >= 11) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
                for (InputMethodInfo enabledInputMethodSubtypeList : inputMethodManager.getEnabledInputMethodList()) {
                    for (InputMethodSubtype inputMethodSubtype : inputMethodManager.getEnabledInputMethodSubtypeList(enabledInputMethodSubtypeList, true)) {
                        if (inputMethodSubtype.getMode().equals("keyboard")) {
                            hashSet.add(inputMethodSubtype.getLocale());
                        }
                    }
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to retreive keyboard input langs: ");
                sb.append(e.getLocalizedMessage());
                C5155g.m3807a("ApiUtil", 6, sb.toString());
            }
        }
        return hashSet;
    }

    /* renamed from: a */
    public static boolean m3763a(View view, boolean z) {
        if (VERSION.SDK_INT >= 11 && 1 != view.getLayerType() && z) {
            return view.isHardwareAccelerated();
        }
        return false;
    }

    /* renamed from: a */
    public static long m3744a(File file, long j) {
        long j2;
        long j3;
        String str = "ApiUtil";
        if (file != null) {
            try {
                if (file.isDirectory()) {
                    if (VERSION.SDK_INT >= 9) {
                        return file.getFreeSpace();
                    }
                    StatFs statFs = new StatFs(file.getPath());
                    if (VERSION.SDK_INT >= 18) {
                        j3 = statFs.getBlockSizeLong();
                        j2 = statFs.getFreeBlocksLong();
                    } else {
                        j3 = (long) statFs.getBlockSize();
                        j2 = (long) statFs.getFreeBlocks();
                    }
                    return j3 * j2;
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed calculating free space with error: ");
                sb.append(e.getMessage());
                C5155g.m3807a(str, 6, sb.toString());
                return j;
            }
        }
        C5155g.m3807a(str, 6, "Invalid filesDir argument - null or not a directory");
        return j;
    }

    /* renamed from: c */
    public static boolean m3771c(Context context) {
        String str = "auto_time";
        if (VERSION.SDK_INT >= 17) {
            if (Global.getInt(context.getContentResolver(), str, 0) > 0) {
                return true;
            }
        } else if (System.getInt(context.getContentResolver(), str, 0) > 0) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m3760a(Context context, String str) {
        boolean z = false;
        try {
            if (VERSION.SDK_INT >= 23) {
                if (context.checkSelfPermission(str) == 0) {
                    z = true;
                }
                return z;
            }
            if (context.checkCallingOrSelfPermission(str) == 0) {
                z = true;
            }
            return z;
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append("Exception while checking permission: ");
            sb.append(th);
            C5155g.m3805a(6, sb.toString() != null ? th.getMessage() : "");
            return false;
        }
    }

    /* renamed from: a */
    public static List<ScanResult> m3746a(Context context, WifiManager wifiManager) {
        if (!(context == null || wifiManager == null)) {
            boolean z = true;
            if (VERSION.SDK_INT >= 23 && !m3760a(context, "android.permission.ACCESS_FINE_LOCATION") && !m3760a(context, "android.permission.ACCESS_COARSE_LOCATION")) {
                z = false;
            }
            String str = "ApiUtil";
            if (z) {
                try {
                    return wifiManager.getScanResults();
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to retreive WIFI scan results: ");
                    sb.append(e.getLocalizedMessage());
                    C5155g.m3807a(str, 6, sb.toString());
                    return null;
                }
            } else {
                C5155g.m3807a(str, 3, "Unable to get WIFI scan results: API level >= 23 but no location permission granted");
            }
        }
        return null;
    }

    /* renamed from: a */
    public static List<CellInfo> m3747a(Context context, TelephonyManager telephonyManager) {
        if (context == null || telephonyManager == null || ((!m3760a(context, "android.permission.ACCESS_FINE_LOCATION") && !m3760a(context, "android.permission.ACCESS_COARSE_LOCATION")) || VERSION.SDK_INT < 17)) {
            return null;
        }
        return telephonyManager.getAllCellInfo();
    }

    /* renamed from: a */
    public static int m3740a(int i) {
        if (VERSION.SDK_INT >= 17) {
            return i;
        }
        if (i == 16) {
            return 0;
        }
        if (i == 17) {
            return 1;
        }
        if (i == 20) {
            return 9;
        }
        if (i == 21) {
            return 11;
        }
        if (i != 8388611) {
            if (i == 8388613 && VERSION.SDK_INT < 14) {
                return 5;
            }
            return i;
        } else if (VERSION.SDK_INT < 14) {
            return 3;
        } else {
            return i;
        }
    }

    /* renamed from: a */
    public static long m3743a(ScanResult scanResult) {
        if (VERSION.SDK_INT >= 17) {
            return (System.currentTimeMillis() - SystemClock.elapsedRealtime()) + (scanResult.timestamp / 1000);
        }
        return 0;
    }

    /* renamed from: b */
    public static CharSequence m3764b(ScanResult scanResult) {
        return VERSION.SDK_INT >= 23 ? scanResult.venueName : "";
    }

    /* renamed from: b */
    public static String m3765b(Context context, TelephonyManager telephonyManager) {
        if (VERSION.SDK_INT >= 17 && (m3760a(context, "android.permission.ACCESS_FINE_LOCATION") || m3760a(context, "android.permission.ACCESS_COARSE_LOCATION"))) {
            List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
            if (allCellInfo != null) {
                for (CellInfo cellInfo : allCellInfo) {
                    if (cellInfo.isRegistered()) {
                        try {
                            Object invoke = Class.forName(cellInfo.getClass().getName()).getMethod("getCellSignalStrength", null).invoke(cellInfo, null);
                            return Integer.toString(Integer.parseInt(Class.forName(invoke.getClass().getName()).getMethod("getTimingAdvance", null).invoke(invoke, null).toString()));
                        } catch (Exception unused) {
                            C5155g.m3805a(6, "Error while calling ApiUtil.getCellTimingAdv()");
                        }
                    }
                }
            }
        }
        return null;
    }

    /* renamed from: d */
    public static int m3772d(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), i).versionCode;
        } catch (NameNotFoundException | Exception unused) {
            return i;
        }
    }

    /* renamed from: e */
    public static String m3773e(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException | Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public String mo62859a(String str, Context context) {
        String str2;
        try {
            str2 = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).sourceDir;
        } catch (NameNotFoundException unused) {
            str2 = null;
        }
        if (str2 != null) {
            return mo62858a((InputStream) new FileInputStream(str2), str);
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r6.close();
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String mo62858a(java.io.InputStream r6, java.lang.String r7) {
        /*
            r5 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 8192(0x2000, float:1.14794E-41)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0046, all -> 0x003f }
            java.security.MessageDigest r7 = java.security.MessageDigest.getInstance(r7)     // Catch:{ Exception -> 0x0046, all -> 0x003f }
        L_0x000d:
            int r2 = r6.read(r1)     // Catch:{ Exception -> 0x0046, all -> 0x003f }
            r3 = -1
            r4 = 0
            if (r2 == r3) goto L_0x001b
            if (r2 <= 0) goto L_0x000d
            r7.update(r1, r4, r2)     // Catch:{ Exception -> 0x0046, all -> 0x003f }
            goto L_0x000d
        L_0x001b:
            byte[] r7 = r7.digest()     // Catch:{ Exception -> 0x0046, all -> 0x003f }
        L_0x001f:
            int r1 = r7.length     // Catch:{ Exception -> 0x0046, all -> 0x003f }
            if (r4 >= r1) goto L_0x0039
            byte r1 = r7[r4]     // Catch:{ Exception -> 0x0046, all -> 0x003f }
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 + 256
            r2 = 16
            java.lang.String r1 = java.lang.Integer.toString(r1, r2)     // Catch:{ Exception -> 0x0046, all -> 0x003f }
            r2 = 1
            java.lang.String r1 = r1.substring(r2)     // Catch:{ Exception -> 0x0046, all -> 0x003f }
            r0.append(r1)     // Catch:{ Exception -> 0x0046, all -> 0x003f }
            int r4 = r4 + 1
            goto L_0x001f
        L_0x0039:
            if (r6 == 0) goto L_0x004a
        L_0x003b:
            r6.close()     // Catch:{ IOException -> 0x004a }
            goto L_0x004a
        L_0x003f:
            r7 = move-exception
            if (r6 == 0) goto L_0x0045
            r6.close()     // Catch:{ IOException -> 0x0045 }
        L_0x0045:
            throw r7
        L_0x0046:
            if (r6 == 0) goto L_0x004a
            goto L_0x003b
        L_0x004a:
            java.lang.String r6 = r0.toString()
            java.lang.String r6 = r6.toUpperCase()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.common.p092a.C5146c.mo62858a(java.io.InputStream, java.lang.String):java.lang.String");
    }

    /* renamed from: f */
    public static int m3774f(Context context) {
        String str = "ApiUtil";
        C5155g.m3807a(str, 3, "getPackageList entered");
        int i = 0;
        try {
            for (PackageInfo packageInfo : m3748a(context.getPackageManager())) {
                if (!m3762a(packageInfo) || packageInfo.packageName.equals(Constants.f3532a)) {
                    i++;
                }
            }
        } catch (Exception e) {
            C5155g.m3808a(str, 6, "Could not complete getPackagesList", e);
        }
        return i;
    }

    /* renamed from: a */
    public static boolean m3762a(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & 1) == 0 && (packageInfo.applicationInfo.flags & 128) == 0) ? false : true;
    }

    /* renamed from: a */
    public static boolean m3761a(Context context, String str, int i) {
        try {
            if (context.getPackageManager().getPackageInfo(str, 128).versionCode >= i) {
                return true;
            }
            return false;
        } catch (NameNotFoundException | Exception unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static List<PackageInfo> m3748a(PackageManager packageManager) {
        String str = new String("getInstalledPackages");
        return (List) packageManager.getClass().getMethod(str, new Class[]{Integer.TYPE}).invoke(packageManager, new Object[]{Integer.valueOf(8192)});
    }

    /* renamed from: g */
    public static boolean m3775g(Context context) {
        int i;
        boolean z = false;
        try {
            String str = "adb_enabled";
            if (VERSION.SDK_INT < 17) {
                i = Secure.getInt(context.getContentResolver(), str, 0);
            } else {
                i = Global.getInt(context.getContentResolver(), str, 0);
            }
            if (i != 0) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            C5155g.m3808a("ApiUtil", 6, "getUsbDebug", e);
            return false;
        }
    }

    /* renamed from: h */
    public static boolean m3776h(Context context) {
        try {
            return C4731c.m2169a(context);
        } catch (Throwable th) {
            C5155g.m3808a("ApiUtil", 6, "isRooted", th);
            return false;
        }
    }

    /* renamed from: i */
    public static boolean m3777i(Context context) {
        try {
            return C4727a.m2138a(context);
        } catch (Throwable th) {
            C5155g.m3808a("ApiUtil", 6, "isSimulator", th);
            return false;
        }
    }

    /* renamed from: j */
    public static String m3778j(Context context) {
        String str = "ApiUtil";
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
            if (signatureArr == null || signatureArr.length <= 0) {
                C5155g.m3807a(str, 6, "getApkSignature: empty signatures");
                return null;
            }
            if (signatureArr.length == 1) {
                return signatureArr[0].toCharsString();
            }
            Arrays.sort(signatureArr, new Comparator<Signature>() {
                /* renamed from: a */
                public int compare(Signature signature, Signature signature2) {
                    return signature.toCharsString().compareTo(signature2.toCharsString());
                }
            });
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < signatureArr.length; i++) {
                sb.append(signatureArr[i].toCharsString());
                if (i < signatureArr.length - 1) {
                    sb.append(';');
                }
            }
            return sb.toString();
        } catch (Exception e) {
            C5155g.m3808a(str, 6, "getApkSignature", e);
            return null;
        }
    }
}
