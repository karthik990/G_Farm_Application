package com.startapp.android.p063a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.core.p012os.EnvironmentCompat;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.mobiroller.constants.Constants;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import p043io.fabric.sdk.android.services.common.AbstractSpiCall;
import p043io.fabric.sdk.android.services.common.CommonUtils;

/* renamed from: com.startapp.android.a.a */
/* compiled from: StartAppSDK */
public final class C4727a {

    /* renamed from: a */
    private static final String[] f2438a = {"15555215554", "15555215556", "15555215558", "15555215560", "15555215562", "15555215564", "15555215566", "15555215568", "15555215570", "15555215572", "15555215574", "15555215576", "15555215578", "15555215580", "15555215582", "15555215584"};

    /* renamed from: b */
    private static final String[] f2439b = {"000000000000000", "e21833235b6eef10", "012345678912345"};

    /* renamed from: c */
    private static final String[] f2440c = {"310260000000000"};

    /* renamed from: d */
    private static final String[] f2441d = {"/dev/socket/genyd", "/dev/socket/baseband_genyd"};

    /* renamed from: e */
    private static final String[] f2442e = {"goldfish"};

    /* renamed from: f */
    private static final String[] f2443f = {"/dev/socket/qemud", "/dev/qemu_pipe"};

    /* renamed from: g */
    private static final String[] f2444g = {"ueventd.android_x86.rc", "x86.prop", "ueventd.ttVM_x86.rc", "init.ttVM_x86.rc", "fstab.ttVM_x86", "fstab.vbox86", "init.vbox86.rc", "ueventd.vbox86.rc"};

    /* renamed from: h */
    private static final String[] f2445h = {"fstab.andy", "ueventd.andy.rc"};

    /* renamed from: i */
    private static final String[] f2446i = {"fstab.nox", "init.nox.rc", "ueventd.nox.rc", "/BigNoxGameHD", "/YSLauncher"};

    /* renamed from: j */
    private static final C4728b[] f2447j = {new C4728b("init.svc.qemud", null), new C4728b("init.svc.qemu-props", null), new C4728b("qemu.hw.mainkeys", null), new C4728b("qemu.sf.fake_camera", null), new C4728b("qemu.sf.lcd_density", null), new C4728b("ro.bootloader", EnvironmentCompat.MEDIA_UNKNOWN), new C4728b("ro.bootmode", EnvironmentCompat.MEDIA_UNKNOWN), new C4728b("ro.hardware", "goldfish"), new C4728b("ro.kernel.android.qemud", null), new C4728b("ro.kernel.qemu.gles", null), new C4728b("ro.kernel.qemu", IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE), new C4728b("ro.product.device", "generic"), new C4728b("ro.product.model", CommonUtils.SDK), new C4728b("ro.product.name", CommonUtils.SDK), new C4728b("ro.serialno", null), new C4728b("ro.build.description", "72656C656173652D6B657973"), new C4728b("ro.build.fingerprint", "3A757365722F72656C656173652D6B657973"), new C4728b("net.eth0.dns1", null), new C4728b("rild.libpath", "2F73797374656D2F6C69622F6C69627265666572656E63652D72696C2E736F"), new C4728b("ro.radio.use-ppp", null), new C4728b("gsm.version.baseband", null), new C4728b("ro.build.tags", "72656C656173652D6B65"), new C4728b("ro.build.display.id", "746573742D"), new C4728b("init.svc.console", null)};

    /* renamed from: o */
    private static C4727a f2448o;

    /* renamed from: p */
    private static Boolean f2449p;

    /* renamed from: k */
    private final Context f2450k;

    /* renamed from: l */
    private boolean f2451l = false;

    /* renamed from: m */
    private boolean f2452m = true;

    /* renamed from: n */
    private List<String> f2453n = new ArrayList();

    /* renamed from: a */
    public static boolean m2138a(Context context) {
        if (f2449p == null) {
            f2449p = Boolean.valueOf(m2140b(context).m2137a());
        }
        return f2449p.booleanValue();
    }

    /* renamed from: b */
    private static C4727a m2140b(Context context) {
        if (context != null) {
            if (f2448o == null) {
                f2448o = new C4727a(context.getApplicationContext());
            }
            return f2448o;
        }
        throw new IllegalArgumentException("Context must not be null.");
    }

    private C4727a(Context context) {
        this.f2450k = context;
        this.f2453n.add("com.google.android.launcher.layouts.genymotion");
        this.f2453n.add("com.bluestacks");
        this.f2453n.add("com.bignox.app");
        this.f2453n.add("com.vphone.launcher");
    }

    /* renamed from: a */
    private boolean m2137a() {
        boolean b = m2141b();
        if (!b) {
            b = m2143c();
        }
        return !b ? m2144d() : b;
    }

    /* renamed from: b */
    private boolean m2141b() {
        String str = "generic";
        if (!Build.FINGERPRINT.startsWith(str)) {
            String str2 = Build.MODEL;
            String str3 = CommonUtils.GOOGLE_SDK;
            if (!str2.contains(str3) && !Build.MODEL.toLowerCase().contains("droid4x") && !Build.MODEL.contains("Emulator") && !Build.MODEL.contains("Android SDK built for") && !Build.MANUFACTURER.contains("Genymotion") && !Build.HARDWARE.equals("goldfish") && !Build.HARDWARE.equals("vbox86")) {
                String str4 = Build.PRODUCT;
                String str5 = CommonUtils.SDK;
                if (!str4.equals(str5) && !Build.PRODUCT.equals(str3) && !Build.PRODUCT.equals("sdk_x86")) {
                    String str6 = "vbox86p";
                    if (!Build.PRODUCT.equals(str6)) {
                        String str7 = "nox";
                        if (!Build.BOARD.toLowerCase().contains(str7) && !Build.BOOTLOADER.toLowerCase().contains(str7) && !Build.HARDWARE.toLowerCase().contains(str7) && !Build.PRODUCT.toLowerCase().contains(str7) && !Build.SERIAL.toLowerCase().contains(str7) && !Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN)) {
                            String str8 = "Andy";
                            if (!Build.FINGERPRINT.contains(str8) && !Build.FINGERPRINT.contains("ttVM_Hdragon") && !Build.FINGERPRINT.contains(str6) && !Build.HARDWARE.contains("ttVM_x86") && !Build.MODEL.equals(str5) && !Build.MODEL.contains("Droid4X") && !Build.MODEL.contains("TiantianVM") && !Build.MODEL.contains(str8) && (!Build.BRAND.startsWith(str) || !Build.DEVICE.startsWith(str))) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /* renamed from: c */
    private boolean m2143c() {
        return m2145e() || m2139a(f2441d, "Geny") || m2139a(f2445h, "Andy") || m2139a(f2446i, "Nox") || m2150j() || m2139a(f2443f, "Pipes") || m2152l() || (m2151k() && m2139a(f2444g, "X86"));
    }

    /* renamed from: d */
    private boolean m2144d() {
        if (this.f2452m && !this.f2453n.isEmpty()) {
            PackageManager packageManager = this.f2450k.getPackageManager();
            for (String launchIntentForPackage : this.f2453n) {
                Intent launchIntentForPackage2 = packageManager.getLaunchIntentForPackage(launchIntentForPackage);
                if (launchIntentForPackage2 != null && !packageManager.queryIntentActivities(launchIntentForPackage2, 65536).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: e */
    private boolean m2145e() {
        if (!m2142b(this.f2450k, "android.permission.READ_PHONE_STATE") || !this.f2451l || !m2153m()) {
            return false;
        }
        if (m2146f() || m2147g() || m2148h() || m2149i()) {
            return true;
        }
        return false;
    }

    /* renamed from: f */
    private boolean m2146f() {
        TelephonyManager telephonyManager = (TelephonyManager) this.f2450k.getSystemService("phone");
        if (telephonyManager != null) {
            String line1Number = telephonyManager.getLine1Number();
            for (String equalsIgnoreCase : f2438a) {
                if (equalsIgnoreCase.equalsIgnoreCase(line1Number)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: g */
    private boolean m2147g() {
        TelephonyManager telephonyManager = (TelephonyManager) this.f2450k.getSystemService("phone");
        if (telephonyManager != null) {
            String deviceId = telephonyManager.getDeviceId();
            for (String equalsIgnoreCase : f2439b) {
                if (equalsIgnoreCase.equalsIgnoreCase(deviceId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: h */
    private boolean m2148h() {
        TelephonyManager telephonyManager = (TelephonyManager) this.f2450k.getSystemService("phone");
        if (telephonyManager != null) {
            String subscriberId = telephonyManager.getSubscriberId();
            for (String equalsIgnoreCase : f2440c) {
                if (equalsIgnoreCase.equalsIgnoreCase(subscriberId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: i */
    private boolean m2149i() {
        TelephonyManager telephonyManager = (TelephonyManager) this.f2450k.getSystemService("phone");
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperatorName().equalsIgnoreCase(AbstractSpiCall.ANDROID_CLIENT_TYPE);
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x006e A[SYNTHETIC, Splitter:B:28:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0075 A[SYNTHETIC, Splitter:B:34:0x0075] */
    /* renamed from: j */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m2150j() {
        /*
            r12 = this;
            r0 = 2
            java.io.File[] r0 = new java.io.File[r0]
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "/proc/tty/drivers"
            r1.<init>(r2)
            r2 = 0
            r0[r2] = r1
            java.io.File r1 = new java.io.File
            java.lang.String r3 = "/proc/cpuinfo"
            r1.<init>(r3)
            r3 = 1
            r0[r3] = r1
            int r1 = r0.length
            r4 = 0
        L_0x0019:
            if (r4 >= r1) goto L_0x007c
            r5 = r0[r4]
            boolean r6 = r5.exists()
            if (r6 == 0) goto L_0x0079
            boolean r6 = r5.canRead()
            if (r6 == 0) goto L_0x0079
            r6 = 1024(0x400, float:1.435E-42)
            char[] r6 = new char[r6]
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r8 = 0
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0072, all -> 0x006b }
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0072, all -> 0x006b }
            java.io.FileInputStream r11 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0072, all -> 0x006b }
            r11.<init>(r5)     // Catch:{ Exception -> 0x0072, all -> 0x006b }
            r10.<init>(r11)     // Catch:{ Exception -> 0x0072, all -> 0x006b }
            r9.<init>(r10)     // Catch:{ Exception -> 0x0072, all -> 0x006b }
        L_0x0042:
            int r5 = r9.read(r6)     // Catch:{ Exception -> 0x0069, all -> 0x0066 }
            r8 = -1
            if (r5 == r8) goto L_0x004d
            r7.append(r6, r2, r5)     // Catch:{ Exception -> 0x0069, all -> 0x0066 }
            goto L_0x0042
        L_0x004d:
            r9.close()     // Catch:{ IOException -> 0x0050 }
        L_0x0050:
            java.lang.String r5 = r7.toString()
            java.lang.String[] r6 = f2442e
            int r7 = r6.length
            r8 = 0
        L_0x0058:
            if (r8 >= r7) goto L_0x0079
            r9 = r6[r8]
            boolean r9 = r5.contains(r9)
            if (r9 == 0) goto L_0x0063
            return r3
        L_0x0063:
            int r8 = r8 + 1
            goto L_0x0058
        L_0x0066:
            r0 = move-exception
            r8 = r9
            goto L_0x006c
        L_0x0069:
            r8 = r9
            goto L_0x0073
        L_0x006b:
            r0 = move-exception
        L_0x006c:
            if (r8 == 0) goto L_0x0071
            r8.close()     // Catch:{ IOException -> 0x0071 }
        L_0x0071:
            throw r0
        L_0x0072:
        L_0x0073:
            if (r8 == 0) goto L_0x0078
            r8.close()     // Catch:{ IOException -> 0x0078 }
        L_0x0078:
            return r2
        L_0x0079:
            int r4 = r4 + 1
            goto L_0x0019
        L_0x007c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.p063a.C4727a.m2150j():boolean");
    }

    /* renamed from: a */
    private boolean m2139a(String[] strArr, String str) {
        File file;
        for (String str2 : strArr) {
            if (!m2142b(this.f2450k, "android.permission.READ_EXTERNAL_STORAGE") || !str2.contains("/") || !str.equals("Nox")) {
                file = new File(str2);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(Environment.getExternalStorageDirectory());
                sb.append(str2);
                file = new File(sb.toString());
            }
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: k */
    private boolean m2151k() {
        C4728b[] bVarArr;
        int i = 0;
        for (C4728b bVar : f2447j) {
            String a = m2136a(this.f2450k, bVar.f2454a);
            if (bVar.f2455b == null && a != null) {
                i++;
            }
            if (!(bVar.f2455b == null || a == null || !a.contains(bVar.f2455b))) {
                i++;
            }
        }
        if (i >= 5) {
            return true;
        }
        return false;
    }

    /* renamed from: l */
    private boolean m2152l() {
        String[] split;
        if (!m2142b(this.f2450k, "android.permission.INTERNET")) {
            return false;
        }
        String[] strArr = {"/system/bin/netcfg"};
        StringBuilder sb = new StringBuilder();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(strArr);
            processBuilder.directory(new File("/system/bin/"));
            processBuilder.redirectErrorStream(true);
            InputStream inputStream = processBuilder.start().getInputStream();
            byte[] bArr = new byte[1024];
            while (inputStream.read(bArr) != -1) {
                sb.append(new String(bArr));
            }
            inputStream.close();
        } catch (Exception unused) {
        }
        String sb2 = sb.toString();
        if (TextUtils.isEmpty(sb2)) {
            return false;
        }
        for (String str : sb2.split(Constants.NEW_LINE)) {
            if ((str.contains("wlan0") || str.contains("tunl0") || str.contains("eth0")) && str.contains("10.0.2.15")) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    private String m2136a(Context context, String str) {
        try {
            Class loadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) loadClass.getMethod("get", new Class[]{String.class}).invoke(loadClass, new Object[]{str});
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: m */
    private boolean m2153m() {
        return this.f2450k.getPackageManager().hasSystemFeature("android.hardware.telephony");
    }

    /* renamed from: b */
    private boolean m2142b(Context context, String str) {
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
        } catch (Throwable unused) {
        }
    }
}
